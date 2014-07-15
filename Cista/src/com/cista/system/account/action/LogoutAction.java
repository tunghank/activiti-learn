package com.cista.system.account.action;

import java.util.List;

import com.cista.system.util.CLTUtil;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @file : UserManage.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 29, 2008 10:21:54 AM
 */
public class LogoutAction extends ActionSupport implements ServletRequestAware , ServletResponseAware {

	private String message;
	private HttpServletRequest request;  
	private HttpServletResponse response;  
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String userLogout() throws Exception {
		logger.debug("User Logout System " );
		// TODO Auto-generated method stub
        //1.0 Get Current session
		HttpSession session = request.getSession(false);
		
        //1.1 Remove Session value.
		if (session!=null){
			session.removeAttribute(CLTUtil.CUR_USERINFO);
			session.invalidate();
		}
        
        
		return SUCCESS;
	}
	

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}  
}
