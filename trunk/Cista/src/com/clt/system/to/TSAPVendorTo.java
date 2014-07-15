package com.clt.system.to;

/**
 * THlhInform entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TSAPVendorTo extends com.clt.system.util.BaseObject implements
		java.io.Serializable {

	// Fields
	private String vendorCode;
	private String shortName;
	private String cdt;

	// Constructors

	/** default constructor */
	public TSAPVendorTo() {
	}

	// Property accessors
	
	/**
	 * @return the vendorCode
	 */
	public String getVendorCode() {
		return vendorCode;
	}

	/**
	 * @param vendorCode the vendorCode to set
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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


}