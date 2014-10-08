package com.cista.system.to;

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

	private Long id;
	private String userId;
	private Long roleId;
	private String roleName;
	private String cdt;

	// Constructors

	/** default constructor */
	public SysUserRoleTo() {
	}


	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCdt() {
		return this.cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}