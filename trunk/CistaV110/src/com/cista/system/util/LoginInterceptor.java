package com.cista.system.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.cista.system.to.SysUserTo;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionSupport actionSupport = (ActionSupport)invocation.getAction();
		ActionContext actionContext = invocation.getInvocationContext();  
		HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response= (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

		SysUserTo curUser = (SysUserTo)request.getSession().getAttribute(CistaUtil.CUR_USERINFO);
		
		logger.debug("LoginInterceptor ");
	
		
		if ( curUser == null ){
			
            //ajax超時處理  
            if (request.getHeader("x-requested-with") != null  
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {  
                PrintWriter pw = response.getWriter();  
  
                pw.write("Session Timeout");  
                response.flushBuffer();  
                pw.close();  
                return null;  
            } else {  
                //TODO http超時的處理  
    			logger.debug("LoginInterceptor curUser == null");
    			actionSupport.addActionMessage(CistaUtil.getMessage("System.error.access.nologin"));
    			return Action.ERROR;
            } 		

		}else{
			return invocation.invoke();
		}
		
		
		
	}


}
