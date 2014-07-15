package com.clt.system.to;

/**
 * SysUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserRoleTo extends com.clt.system.util.BaseObject implements
		java.io.Serializable {

	// Fields

	private Long id;
	private String userId;
	private Long roldId;
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

	public Long getRoldId() {
		return this.roldId;
	}

	public void setRoldId(Long roldId) {
		this.roldId = roldId;
	}

	public String getCdt() {
		return this.cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

}