package com.cista.system.to;

import java.util.Date;

/**
 * SysRoleFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysRoleFunctionTo extends com.cista.system.util.BaseObject
		implements java.io.Serializable {

	// Fields

	private String id;
	private String roleId;
	private Long functionId;
	private Date cdt;
	private String cls;
	
	// for role
	
	private String roleName;
	private String functionName;

	// Constructors

	/** default constructor */
	public SysRoleFunctionTo() {
	}


	// Property accessors



	public Long getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}



	public String getRoleName() {
		return this.roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getFunctionName() {
		return this.functionName;
	}
	
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}


	public String getCls() {
		return cls;
	}


	public void setCls(String cls) {
		this.cls = cls;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


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
}