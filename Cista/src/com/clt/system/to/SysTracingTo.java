package com.clt.system.to;

/**
 * SysTracing entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTracingTo extends com.clt.system.util.BaseObject implements
		java.io.Serializable {

	// Fields

	private String id;
	private SysUserTo sysUser;
	private String requestUri;
	private String when;
	private String cast;
	private String forward;
	private String msg;
	private String ip;
	private String cdt;

	// Constructors

	/** default constructor */
	public SysTracingTo() {
	}



	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysUserTo getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUserTo sysUser) {
		this.sysUser = sysUser;
	}

	public String getRequestUri() {
		return this.requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getWhen() {
		return this.when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public String getCast() {
		return this.cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getForward() {
		return this.forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCdt() {
		return this.cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

}