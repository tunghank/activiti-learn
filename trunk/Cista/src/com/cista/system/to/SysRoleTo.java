package com.cista.system.to;

import java.util.Date;

/**
 * SysRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysRoleTo extends com.cista.system.util.BaseObject implements
		java.io.Serializable {



	private String roleId;
	private String roleName;
	private Date cdt;

	// Constructors

	/** default constructor */
	public SysRoleTo() {
	}

	// Property accessors

	// Fields

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Date getCdt() {
		return cdt;
	}

	public void setCdt(Date cdt) {
		this.cdt = cdt;
	}
	
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



}