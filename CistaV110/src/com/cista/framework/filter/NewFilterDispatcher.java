package com.cista.framework.filter;

import org.apache.struts2.dispatcher.FilterDispatcher;

import java.io.IOException;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @file : NewFilterDispatcher.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 25, 2008  2:59:17 PM
 */
public class NewFilterDispatcher extends FilterDispatcher {
	 private static String encoding = "BIG5";

	    public void init(FilterConfig filterConfig) throws ServletException {
	        super.init(filterConfig);
	        String encodingParam = filterConfig.getInitParameter("encoding");
	        if (encodingParam != null && encodingParam.trim().length() != 0) {
	            encoding = encodingParam;
	        }
	    }
	    public void doFilter(ServletRequest request, ServletResponse response,
	            FilterChain chain) throws IOException, ServletException {
	        request.setCharacterEncoding(encoding);
	        super.doFilter(request, response, chain);
	    }

}
