package com.cista.system.ldap.action;

import java.io.IOException;  
import java.util.Enumeration;  
  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  
import jcifs.Config;  
import jcifs.UniAddress;  
import jcifs.http.NtlmSsp;  
import jcifs.smb.NtlmChallenge;  
import jcifs.smb.NtlmPasswordAuthentication;  
import jcifs.smb.SmbAuthException;  
import jcifs.smb.SmbSession;  
import jcifs.util.Base64;  
import jcifs.util.LogStream;  
  
import org.apache.log4j.Logger;  
  
public class NtlmAuthFilter implements Filter   
{  
    private static LogStream log = LogStream.getInstance();  
      
    private final Logger logger = Logger.getLogger(NtlmAuthFilter.class);  
  
    protected FilterConfig filterConfig;  
  
    private String defaultDomain;  
      
    private String domainController;  
  
    private boolean loadBalance;  
  
    private boolean enableBasic;  
  
    private boolean insecureBasic;  
  
    private String realm;  
  
    private static final String BACK_SLASH = "\\";  
  
    private String portalError;  
  
    public void init(FilterConfig config) throws ServletException   {  
        logger.info("NTLM HTTP Authentication Filter Init");  
        this.filterConfig = config;  
  
        Config.setProperty("jcifs.smb.client.soTimeout", "300000"); // 1800000  
        Config.setProperty("jcifs.netbios.cachePolicy", "1200");  
        Config.setProperty("jcifs.smb.lmCompatibility", "0");  
        Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");  
          
        Enumeration e = filterConfig.getInitParameterNames();  
        while (e.hasMoreElements())   
        {  
            String name = (String)e.nextElement();  
            if (name.startsWith("jcifs."));  
                Config.setProperty(name, filterConfig.getInitParameter(name));  
        }  
          
        this.defaultDomain = Config.getProperty("jcifs.smb.client.domain");  
        this.domainController = Config.getProperty("jcifs.http.domainController");  
          
        this.loadBalance = Boolean.valueOf(Config.getProperty("jcifs.http.loadBalance")).booleanValue();  
        this.enableBasic = Boolean.valueOf(Config.getProperty("jcifs.http.enableBasic")).booleanValue();  
        this.insecureBasic = Boolean.valueOf(Config.getProperty("jcifs.http.insecureBasic")).booleanValue();  
        this.realm = Config.getProperty("jcifs.http.basicRealm");  
          
        this.portalError = config.getInitParameter("portalError");  
          
        int level;  
        if ((level = Config.getInt("jcifs.util.loglevel", -1)) != -1) {  
            LogStream.setLevel(level);  
        }  
        if (LogStream.level <= 2)   
            return;  
        try   
        {  
            Config.store(log, "JCIFS PROPERTIES");  
        }  
        catch (IOException ioe)  
        {  
        }  
    }  
  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws SmbAuthException, IOException, ServletException   
    {  
        logger.info("NTLM HTTP Authentication Filter...........");  
        HttpServletRequest req = (HttpServletRequest) request;  
        HttpServletResponse resp = (HttpServletResponse) response;  
  
        try   
        {  
            if (domainController == null || "".equals(domainController)) {  
                req.setAttribute("error", "初始化認證配置錯誤,缺少參數!");  
                req.getRequestDispatcher(portalError).forward(request, response);  
                return;  
            }  
              
            if (defaultDomain == null || "".equals(defaultDomain)) {  
                req.setAttribute("error", "初始化認證配置錯誤,缺少參數!");  
                req.getRequestDispatcher(portalError).forward(request, response);  
                return;  
            }  
              
            logger.info("Host: " + domainController);  
            logger.info("Domain: " + defaultDomain);  
              
            NtlmPasswordAuthentication ntlm = negotiate(req, resp, domainController, false);  
            if (ntlm == null) {  
                return;  
            }  
  
            String authuser = ntlm.getName();  
            int pos = authuser.indexOf(BACK_SLASH);  
            if (pos != -1) {  
                authuser = authuser.substring(pos + 1);  
            }  
  
            logger.info("NTLM HTTP Authentication User: " + authuser);  
            req.setAttribute("NTLM_USER", authuser);
            
        }    
        catch (IOException e)   
        {  
            logger.error(e);  
            req.setAttribute("error", "登錄失敗, 系統錯誤!");  
            req.getRequestDispatcher(portalError).forward(request, response);  
            return;  
        }   
        catch (Exception e)   
        {  
            logger.error(e);  
            req.setAttribute("error", "登錄失敗, 系統錯誤!");  
            req.getRequestDispatcher(portalError).forward(request, response);  
            return;  
        }  
  
        chain.doFilter(request, response);  
    }  
  
    
	/**
	 * 通過cifs獲得登陸的用戶名,用於自動登錄,選擇進行域驗證
	 * 
	 * @param req
	 * @param resp
	 * @param domainController
	 *            域的域控制地址，通常為IP地址
	 * @param skipDomainValidate
	 *            跳過域驗證
	 * @return
	 * @throws Exception
	 */
	protected NtlmPasswordAuthentication negotiate(HttpServletRequest req,
			HttpServletResponse resp, String domainController,
			boolean skipDomainValidate) throws Exception {
		NtlmPasswordAuthentication ntlm = null;
		UniAddress dc = null;
		String msg = req.getHeader("Authorization");
		boolean offerBasic = enableBasic && (insecureBasic || req.isSecure());
		if (msg != null
				&& (msg.startsWith("NTLM ") || (offerBasic && msg
						.startsWith("Basic ")))) {
			if (msg.startsWith("NTLM ")) {
				dc = UniAddress.getByName(domainController, true);
				byte[] challenge = SmbSession.getChallenge(dc);
				if ((ntlm = NtlmSsp.authenticate(req, resp, challenge)) == null) {
					return null;
				}
			}
			if (skipDomainValidate) {
				return ntlm;
			}
			try {
				SmbSession.logon(dc, ntlm);
				return ntlm;
			} catch (Exception e) {
				logger.error(e.getClass().getName() + ":"
						+ e.getMessage());
				resp.setHeader("WWW-Authenticate", "NTLM");
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				resp.setContentLength(0);
				resp.flushBuffer();
				return null;
			}
		} else {
			resp.setHeader("WWW-Authenticate", "NTLM");
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.setContentLength(0);
			resp.flushBuffer();
			return null;
		}
	}

/*    protected NtlmPasswordAuthentication negotiate(HttpServletRequest req,  
            HttpServletResponse resp, boolean skipAuthentication)  
            throws Exception  {  
        UniAddress dc;  
        String msg;  
        NtlmPasswordAuthentication ntlm = null;  
        msg = req.getHeader("Authorization");  
        boolean offerBasic = enableBasic && (insecureBasic || req.isSecure());  
  
        
        if (msg != null && (msg.startsWith("NTLM ") || (offerBasic && msg.startsWith("Basic ")))){  
        	logger.debug("0. " + msg);
        	
            if (msg.startsWith("NTLM ")){
            	
            	logger.debug("1. " + msg);  
                HttpSession ssn = req.getSession();  
                byte[] challenge;  
  
                if (loadBalance)   
                {  
                    NtlmChallenge chal = (NtlmChallenge) ssn.getAttribute("NtlmHttpChal");  
                    if (chal == null)   
                    {  
                        chal = SmbSession.getChallengeForDomain();  
                        ssn.setAttribute("NtlmHttpChal", chal);  
                    }  
                    dc = chal.dc;  
                    challenge = chal.challenge;  
                }   
                else   
                {  
                    dc = UniAddress.getByName(domainController, true);  
                    challenge = SmbSession.getChallenge(dc);  
                }  
                if ((ntlm = NtlmSsp.authenticate(req, resp, challenge)) == null) {  
                    return null;  
                }  
                 negotiation complete, remove the challenge object   
                ssn.removeAttribute("NtlmHttpChal");  
            } else {  
            	logger.debug("2. " + msg);
                String auth = new String(Base64.decode(msg.substring(6)), "US-ASCII"); 
                logger.debug("auth " +auth);
                
                int index = auth.indexOf(':');  
                String user = (index != -1) ? auth.substring(0, index) : auth;  
                String password = (index != -1) ? auth.substring(index + 1) : "";  
                index = user.indexOf('\\');  
                if (index == -1)  
                    index = user.indexOf('/');  
                String domain = (index != -1) ? user.substring(0, index) : defaultDomain;  
                user = (index != -1) ? user.substring(index + 1) : user;  
                ntlm = new NtlmPasswordAuthentication(domain, user, password);  
                dc = UniAddress.getByName(domainController, true);  
            }  
              
             try  
             {  
            	 logger.debug("SmbSession.logon " );
                 SmbSession.logon( dc, ntlm );  
                 logger.info("NTLM HTTP Authentication: " + ntlm    + " Successfully Authenticated Against " + dc);  
             }  
             catch( SmbAuthException sae )  
             {  
                 sae.printStackTrace();  
                 logger.info("NTLM HTTP Authentication: " + ntlm.getName()  
                    + ": 0x"  
                    + jcifs.util.Hexdump.toHexString(sae.getNtStatus(), 8)  
                    + ": " + sae);  
                 if(sae.getNtStatus() == sae.NT_STATUS_ACCESS_VIOLATION )   
                 {  
                  
                     * Server challenge no longer valid for externally supplied 
                     * password hashes. 
                       
                     HttpSession ssn = req.getSession(false);  
                     if (ssn != null) {  
                         ssn.removeAttribute( "NtlmHttpAuth" );  
                     }  
                 }  
                 resp.setHeader( "WWW-Authenticate", "NTLM" );  
                 if (offerBasic) {  
                     resp.addHeader( "WWW-Authenticate", "Basic realm=\"" + realm + "\"");  
                 }  
                 resp.setStatus( HttpServletResponse.SC_UNAUTHORIZED );  
                 resp.setContentLength(0);  Marcel Feb-15-2005   
                 resp.flushBuffer();  
                 return null;  
             }  
               
            req.getSession().setAttribute("NtlmHttpAuth", ntlm);  
        }  
        else   
        {  
            if (!skipAuthentication)   
            {  
                HttpSession ssn = req.getSession(false);  
                if (ssn == null || (ntlm = (NtlmPasswordAuthentication) ssn.getAttribute("NtlmHttpAuth")) == null)   
                {  
                    resp.setHeader("WWW-Authenticate", "NTLM");  
                    if (offerBasic) {  
                        resp.addHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");  
                    }  
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
                    resp.setContentLength(0);  
                    resp.flushBuffer();  
                    return null;  
                }  
            }  
        }  
  
        return ntlm;  
    }  */
  
    public void destroy() {  
    }  
}  




