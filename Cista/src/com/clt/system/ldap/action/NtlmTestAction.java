package com.clt.system.ldap.action;

import com.clt.system.util.BaseAction;

import org.apache.struts2.ServletActionContext;

/**
 * @file : UserManage.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 29, 2008 10:21:54 AM
 */

public class NtlmTestAction extends BaseAction  {
	
	
	//20110629
	public String ntlmTest() throws Exception {
		
	    //:以下:這一段紀錄當user連上server時,將user的windows上的account show 出來---
	    //HttpServletRequest httpRequest = (HttpServletRequest) request;
	    //HttpServletResponse httpResponse = (HttpServletResponse) response;
	    //String auth = request.getHeader("Authorization");
	    String auth = request.getHeader("Authorization");
	    if (auth == null) {
	    	response.setStatus(response.SC_UNAUTHORIZED);
	    	response.setHeader("WWW-Authenticate", "NTLM");
	      return ERROR;
	    }
	 
	    if (auth.startsWith("NTLM ")) {
	      byte[] msg = new sun.misc.BASE64Decoder().decodeBuffer(auth.substring(5));
	      int off = 0, length, offset;
	      String s;
	 
		     if (msg[8] == 1){ // first step of authentication
			      off = 18;
			      // this part is for full hand-shaking, just tested, didn't care about result passwords
			      byte z = 0;
			      byte[] msg1 = {(byte)'N', (byte)'T', (byte)'L', (byte)'M', (byte)'S', (byte)'S', (byte)'P', z,
			      (byte)2, z, z, z, z, z, z, z,
			      (byte)40, z, z, z, (byte)1, (byte)130, z, z,
			      z, (byte)2, (byte)2, (byte)2, z, z, z, z, // this line is 'nonce'
			      z, z, z, z, z, z, z, z};
			      // remove next lines if you want see the result of first step
			      response.setStatus(response.SC_UNAUTHORIZED);
			      response.setHeader("WWW-Authenticate", "NTLM " + new sun.misc.BASE64Encoder().encodeBuffer(msg1).trim());
			      return NONE;
		    }else if (msg[8] == 3){ // third step of authentization - takes long time, nod needed if zou care only for loginname
		      off = 30;
		      length = msg[off+17]*256 + msg[off+16];
		      offset = msg[off+19]*256 + msg[off+18];
		      s = new String(msg, offset, length);
		      logger.debug(s + " ");

		    }else{
		    	return NONE;
		    }
		    
		     length = msg[off+17]*256 + msg[off+16];
		     offset = msg[off+19]*256 + msg[off+18];
		     String remoteHost = new String(msg, offset, length);

		     length = msg[off+1]*256 + msg[off];
		     offset = msg[off+3]*256 + msg[off+2];
		     String domain = new String(msg, offset, length);

		     length = msg[off+9]*256 + msg[off+8];
		     offset = msg[off+11]*256 + msg[off+10];
		     String username = new String(msg, offset, length);

		     logger.debug("Username:"+username);
		     logger.debug("RemoteHost:"+remoteHost);
		     logger.debug("Domain:"+domain);
		     
		    length = msg[off+1]*256 + msg[off];
		    offset = msg[off+3]*256 + msg[off+2];
		    s = new String(msg, offset, length);
		    
		    logger.debug(s + " ");
		    length = msg[off+9]*256 + msg[off+8];
		    offset = msg[off+11]*256 + msg[off+10];
		    s = new String(msg, offset, length);
		    
		    logger.debug("Welcome," + " " + s + " ");
		    char[] strChar = s.toCharArray();
		    for(int i = 0; i < strChar.length; i ++){
		    	s = s + String.valueOf(strChar[i]).trim();
		    }
		    
		    logger.debug("Welcome," + " " + s + " ");
	    }    
	    //:以上:這一段紀錄當user連上server時,將user的windows上的account show 出來---
	    logger.debug("HELLO FILTER");
	    //chain.doFilter(request,response);
	    
		return NONE;
	}

}
