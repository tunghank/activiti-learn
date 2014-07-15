package com.clt.quotation.erp.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;

public class ErpVendorTo extends BaseObject implements Serializable {

	// Fields
	private String supplierCode;
	private String supplierName;
	private String supplierSite;
	private String supplierSiteCode;
	private String contact;
	private String contactEmail;
	private String paymentCurrency;
	private String paymentMethod;
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierSite() {
		return supplierSite;
	}
	public void setSupplierSite(String supplierSite) {
		this.supplierSite = supplierSite;
	}
	public String getSupplierSiteCode() {
		return supplierSiteCode;
	}
	public void setSupplierSiteCode(String supplierSiteCode) {
		this.supplierSiteCode = supplierSiteCode;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getPaymentCurrency() {
		return paymentCurrency;
	}
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	

	

}
