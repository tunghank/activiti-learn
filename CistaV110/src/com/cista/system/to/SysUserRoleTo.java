package com.cista.system.to;

import java.util.Date;


/**
 * SysUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserRoleTo extends com.cista.system.util.BaseObject implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String userId;
	private String roleId;
	private String roleName;
	private Date cdt;

	// Constructors

	/** default constructor */
	public SysUserRoleTo() {
	}


	// Property accessors



	public String getUserId() {
		return this.userId;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Date getCdt() {
		return cdt;
	}


	public void setCdt(Date cdt) {
		this.cdt = cdt;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}
	
}