package com.cista.system.to;

/**
 * THlhInform entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TSAPCustomerTo extends com.cista.system.util.BaseObject implements
		java.io.Serializable {

	// Fields
	private String customerCode;
	private String shortName;
	private String cdt;

	// Constructors

	/** default constructor */
	public TSAPCustomerTo() {
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
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


	// Property accessors


}