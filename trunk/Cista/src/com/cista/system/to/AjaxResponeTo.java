package com.cista.system.to;

import java.util.Date;

/**
 * SysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AjaxResponeTo extends com.cista.system.util.BaseObject implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private String ajaxStatus;
	private String ajaxMessage;

	/** default constructor */
	public AjaxResponeTo() {}

	// Property accessors
	
	public String getAjaxStatus() {
		return ajaxStatus;
	}

	public void setAjaxStatus(String ajaxStatus) {
		this.ajaxStatus = ajaxStatus;
	}

	public String getAjaxMessage() {
		return ajaxMessage;
	}

	public void setAjaxMessage(String ajaxMessage) {
		this.ajaxMessage = ajaxMessage;
	}

	
	

	
	
}