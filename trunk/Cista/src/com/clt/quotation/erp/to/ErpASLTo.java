package com.clt.quotation.erp.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;

public class ErpASLTo extends BaseObject implements Serializable {

	// Fields
	
	private String aslId;
	private String vendorSiteCode;
	private String vendorName;
	private String vendorSiteId;
	private String vendorId;
	private String itemId;
	
	public String getAslId() {
		return aslId;
	}
	public void setAslId(String aslId) {
		this.aslId = aslId;
	}
	public String getVendorSiteCode() {
		return vendorSiteCode;
	}
	public void setVendorSiteCode(String vendorSiteCode) {
		this.vendorSiteCode = vendorSiteCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorSiteId() {
		return vendorSiteId;
	}
	public void setVendorSiteId(String vendorSiteId) {
		this.vendorSiteId = vendorSiteId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	

	

	

}
