package com.cista.system.util;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @file : BaseAction.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 25, 2008  2:11:19 PM
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{
	
	//protected static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
    protected final Log logger = LogFactory.getLog(getClass());

    public BaseAction (){

    }
    
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}



}