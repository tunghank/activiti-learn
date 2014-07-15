package com.cista.system.to;

/**
 * SysRoleFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysRoleFunctionTo extends com.cista.system.util.BaseObject
		implements java.io.Serializable {

	// Fields

	private Long id;
	private Long roleId;
	private Long functionId;
	private String cdt;
	private String cls;
	
	// for role
	
	private String roleName;
	private String functionName;

	// Constructors

	/** default constructor */
	public SysRoleFunctionTo() {
	}


	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getCdt() {
		return this.cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
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
}