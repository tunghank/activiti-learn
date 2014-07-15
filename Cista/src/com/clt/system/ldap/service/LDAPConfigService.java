package com.clt.system.ldap.service;

import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;


import com.clt.system.ldap.dao.LDAPConfigDao;
import com.clt.system.to.SysUserTo;
import com.clt.system.to.SysLdapConfigTo;
import com.clt.system.util.BaseService;
/**
 * @author 900730
 *
 */
public class LDAPConfigService extends BaseService {

	
/**
     * Added:Hank_Tang 2007/09/09
     * For:整合各Site AD
     * @param userId
     * @return
     */
    public SysUserTo getOUUser(String userId) {
    	LDAPConfigDao ldapConfigDao = new LDAPConfigDao();
    	List<SysLdapConfigTo> ldapList  = ldapConfigDao.getAllConfig();
    	for (int i=0;i < ldapList.size(); i++){
    		SysLdapConfigTo configTo = (SysLdapConfigTo)ldapList.get(i);
    		
    		Hashtable<String, Object> env = new Hashtable<String, Object>();
            env.put(Context.PROVIDER_URL, configTo.getLdapUrl());
            env.put(Context.INITIAL_CONTEXT_FACTORY, configTo.getLdapFactory());
            env.put(Context.SECURITY_AUTHENTICATION, configTo.getLdapAuthentication());
            env.put(Context.SECURITY_PRINCIPAL, configTo.getLdapUsername());
            env.put(Context.SECURITY_CREDENTIALS, configTo.getLdapPassword());

    		
            try {
                LdapContext ctx = new InitialLdapContext(env, null);
                
                //logger.debug("Login by " + configTo.getLdapUsername() + " successfully.");

                SearchControls searchCtls = new SearchControls();
                searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String searchFilter = "(sAMAccountName=" + userId + ")";
                String searchBase = configTo.getLdapCn().toString();

                NamingEnumeration answer = ctx.search(searchBase, searchFilter,
                        searchCtls);
                
                if (answer.hasMoreElements()) {
                    SearchResult sr = (SearchResult) answer.next();
                    Attributes attrs = sr.getAttributes();

                    //logger.debug("NEW Find user " + userId + ".");
                    
                    SysUserTo user = new SysUserTo();
                    user.setUserId(userId);
                    Attribute a = null;
                    //Get email
                    a = attrs.get("mail");
                    if (a != null) {
                        user.setEmail(a.get().toString());
                    }
                    //Get last name
                    a = attrs.get("sn");
                    String lastName = "";
                    if (a != null) {
                    	lastName = a.get().toString();

                    }
                    //Get first name
                    a = attrs.get("givenName");
                    String firstName = "";
                    if (a != null) {
                    	firstName = a.get().toString();
                    }
                    String realName = lastName + " " + firstName;
                    user.setRealName(realName);
                    
                    return user;
                }
                
            } catch (AuthenticationException ae) {
            	logger.debug(ae.toString());
            	logger.debug("Authentication fail, incorrect username or password.");
               
            } catch (NamingException ne) {
            	logger.debug(ne.toString());
            	logger.debug("Access to AD server fail.");
                ne.printStackTrace();
                
            }

    	}
    	logger.debug("Can not find user with name " + userId);
    	return null;
    }
    


    public boolean checkOUUser(String userId, String password) {
    	LDAPConfigDao ldapConfigDao = new LDAPConfigDao();
    	
    	List<SysLdapConfigTo> ldapList  = ldapConfigDao.getAllConfig();
    	for (int i=0;i < ldapList.size(); i++){
    		SysLdapConfigTo configTo = (SysLdapConfigTo)ldapList.get(i);
    		           
	        try {
	    		//logger.debug("1. Step");
	    		Hashtable<String, Object> env = new Hashtable<String, Object>();
	            env.put(Context.PROVIDER_URL, configTo.getLdapUrl());
	            env.put(Context.INITIAL_CONTEXT_FACTORY, configTo.getLdapFactory());
	            env.put(Context.SECURITY_AUTHENTICATION, configTo.getLdapAuthentication());
	            //env.put(Context.SECURITY_PRINCIPAL, configTo.getLdapUsername());
	            //logger.debug("2. Step");
		        env.put(Context.SECURITY_PRINCIPAL, getOUUserCN(userId) );
		        env.put(Context.SECURITY_CREDENTIALS, password);
	 
	            //logger.debug("3. Step");
	            
	            LdapContext ctx = new InitialLdapContext(env, null);
	            logger.debug("Login by " + userId + " successfully.");
	            return true;
	        } catch (AuthenticationException ae) {
	        	logger.debug(ae);
	        	logger.debug("Authentication fail, incorrect username or password.");

	        } catch (NamingException ne) {
	        	logger.debug(ne);
	        	logger.debug("Access to AD server fail.");

	        } catch (NullPointerException npe) {
	        	logger.debug(npe.toString());
	        	logger.debug("Access to AD server fail.");

	        } catch (Exception ex) {
	        	logger.debug(ex.toString());
	        	logger.debug("Access to AD server fail.");

	        }
    	}
        return false;
    }
    

    /**
     * Find user's CN by sAMAccountName.
     * @param userId sAMAccountName
     * @return CN
     */
    public String getOUUserCN(String userId) {
    	LDAPConfigDao ldapConfigDao = new LDAPConfigDao();
    	List<SysLdapConfigTo> ldapList  = ldapConfigDao.getAllConfig();
    	for (int i=0;i < ldapList.size(); i++){
    		SysLdapConfigTo configTo = (SysLdapConfigTo)ldapList.get(i);
    		
	        try {
	    		Hashtable<String, Object> env = new Hashtable<String, Object>();
	            env.put(Context.PROVIDER_URL, configTo.getLdapUrl());
	            env.put(Context.INITIAL_CONTEXT_FACTORY, configTo.getLdapFactory());
	            env.put(Context.SECURITY_AUTHENTICATION, configTo.getLdapAuthentication());
	            env.put(Context.SECURITY_PRINCIPAL, configTo.getLdapUsername());
	            env.put(Context.SECURITY_CREDENTIALS, configTo.getLdapPassword());
	            
	            LdapContext ctx = new InitialLdapContext(env, null);
	            logger.debug("Login by " + configTo.getLdapUsername()
	                    + " successfully.");
	
	            SearchControls searchCtls = new SearchControls();
	            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	            String searchFilter = "(sAMAccountName=" + userId + ")";
	            String searchBase = configTo.getLdapCn().toString();
	
	            NamingEnumeration answer = ctx.search(searchBase, searchFilter,
	                    searchCtls);
	            if (answer.hasMoreElements()) {
	                SearchResult sr = (SearchResult) answer.next();
	                Attributes attrs = sr.getAttributes();
	
	                logger.debug("Find user " + userId + ".");
	
	                Attribute a = null;
	                
	                //Get distinguishedName
	                a = attrs.get("distinguishedName");
	                if (a != null) {
	                	return a.get().toString();
	                }
	            } 
	
	        } catch (AuthenticationException ae) {
	        	logger.debug(ae);
	        	logger.debug("Authentication fail, incorrect username or password.");
	        } catch (NamingException ne) {
	        	logger.debug(ne);
	        	logger.debug("Access to AD server fail.");
	        } catch (Exception ex) {
	        	logger.debug(ex.toString());
	        	logger.debug("Access to AD server fail.");
	        }
    	}
    	logger.debug("Can not find user with name " + userId);
    	return null;
    }

}
