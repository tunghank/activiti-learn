package com.clt.system.to;

/**
 * SysRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysRoleTo extends com.clt.system.util.BaseObject implements
		java.io.Serializable {

	// Fields

	private Long roleId;
	private String roleName;
	private String cdt;

	// Constructors

	/** default constructor */
	public SysRoleTo() {
	}

	// Property accessors

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCdt() {
		return this.cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

}