package com.clt.quotation.inquiry.to;

import java.io.Serializable;
import java.util.Date;


import com.clt.system.util.BaseObject;


public class InquirySupplierTo extends BaseObject implements Serializable {

	// Fields
	private String inquirySupplierUid;
	private String inquiryHeaderUid;
	private String inquirySupplierCode;
	private String inquirySupplierName;
	private String inquirySupplierSiteCode;
	private String inquirySupplierSite;
	private String inquirySupplierPartNum;
	private String inquiryCurrency;
	private String inquiryPaymentMethod;
	private String inquiryDeliveryLocation;
	private String inquiryShippedBy;
	private Date quotationRecoverTime;
	private String cdt;
	//For Show
	private String inquiryDeliveryLocationDesc;
	private String inquiryShippedByDesc;
	
	
	public String getInquirySupplierUid() {
		return inquirySupplierUid;
	}
	public void setInquirySupplierUid(String inquirySupplierUid) {
		this.inquirySupplierUid = inquirySupplierUid;
	}

	public String getInquiryHeaderUid() {
		return inquiryHeaderUid;
	}
	public void setInquiryHeaderUid(String inquiryHeaderUid) {
		this.inquiryHeaderUid = inquiryHeaderUid;
	}
	public String getInquirySupplierCode() {
		return inquirySupplierCode;
	}
	public void setInquirySupplierCode(String inquirySupplierCode) {
		this.inquirySupplierCode = inquirySupplierCode;
	}
	public String getInquirySupplierName() {
		return inquirySupplierName;
	}
	public void setInquirySupplierName(String inquirySupplierName) {
		this.inquirySupplierName = inquirySupplierName;
	}
	public String getInquirySupplierSiteCode() {
		return inquirySupplierSiteCode;
	}
	public void setInquirySupplierSiteCode(String inquirySupplierSiteCode) {
		this.inquirySupplierSiteCode = inquirySupplierSiteCode;
	}
	public String getInquirySupplierSite() {
		return inquirySupplierSite;
	}
	public void setInquirySupplierSite(String inquirySupplierSite) {
		this.inquirySupplierSite = inquirySupplierSite;
	}
	public String getInquirySupplierPartNum() {
		return inquirySupplierPartNum;
	}
	public void setInquirySupplierPartNum(String inquirySupplierPartNum) {
		this.inquirySupplierPartNum = inquirySupplierPartNum;
	}
	public String getInquiryCurrency() {
		return inquiryCurrency;
	}
	public void setInquiryCurrency(String inquiryCurrency) {
		this.inquiryCurrency = inquiryCurrency;
	}
	public String getInquiryPaymentMethod() {
		return inquiryPaymentMethod;
	}
	public void setInquiryPaymentMethod(String inquiryPaymentMethod) {
		this.inquiryPaymentMethod = inquiryPaymentMethod;
	}
	public String getInquiryDeliveryLocation() {
		return inquiryDeliveryLocation;
	}
	public void setInquiryDeliveryLocation(String inquiryDeliveryLocation) {
		this.inquiryDeliveryLocation = inquiryDeliveryLocation;
	}
	public String getInquiryShippedBy() {
		return inquiryShippedBy;
	}
	public void setInquiryShippedBy(String inquiryShippedBy) {
		this.inquiryShippedBy = inquiryShippedBy;
	}
	public Date getQuotationRecoverTime() {
		return quotationRecoverTime;
	}
	public void setQuotationRecoverTime(Date quotationRecoverTime) {
		this.quotationRecoverTime = quotationRecoverTime;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	public String getInquiryDeliveryLocationDesc() {
		return inquiryDeliveryLocationDesc;
	}
	public void setInquiryDeliveryLocationDesc(String inquiryDeliveryLocationDesc) {
		this.inquiryDeliveryLocationDesc = inquiryDeliveryLocationDesc;
	}
	public String getInquiryShippedByDesc() {
		return inquiryShippedByDesc;
	}
	public void setInquiryShippedByDesc(String inquiryShippedByDesc) {
		this.inquiryShippedByDesc = inquiryShippedByDesc;
	}
	
	
	
	
}
