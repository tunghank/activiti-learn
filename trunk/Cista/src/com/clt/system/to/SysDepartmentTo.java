package com.clt.system.to;

/**
 * SysDepartmentId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysDepartmentTo extends com.clt.system.util.BaseObject
		implements java.io.Serializable {

	// Fields

	private String company;
	private String departName;
	private String departDescription;
	private String cdt;

	// Constructors

	/** default constructor */
	public SysDepartmentTo() {
	}

	// Property accessors

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	/**
	 * @return the departDescription
	 */
	public String getDepartDescription() {
		return departDescription;
	}

	/**
	 * @param departDescription the departDescription to set
	 */
	public void setDepartDescription(String departDescription) {
		this.departDescription = departDescription;
	}

	/**
	 * @return the cdt
	 */
	public String getCdt() {
		return cdt;
	}

	/**
	 * @param cdt the cdt to set
	 */
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

}