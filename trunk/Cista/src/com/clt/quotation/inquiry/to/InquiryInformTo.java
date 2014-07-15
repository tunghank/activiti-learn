package com.clt.quotation.inquiry.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;


public class InquiryInformTo extends BaseObject implements Serializable {

	// Fields
	private String inquiryInformUid;
	private String inquiryHeaderUid;
	private String inquirySupplierUid;
	private String mailName;
	private String mailTo;
	private String cdt;
	
	public String getInquiryInformUid() {
		return inquiryInformUid;
	}
	public void setInquiryInformUid(String inquiryInformUid) {
		this.inquiryInformUid = inquiryInformUid;
	}
	public String getInquiryHeaderUid() {
		return inquiryHeaderUid;
	}
	public void setInquiryHeaderUid(String inquiryHeaderUid) {
		this.inquiryHeaderUid = inquiryHeaderUid;
	}
	public String getInquirySupplierUid() {
		return inquirySupplierUid;
	}
	public void setInquirySupplierUid(String inquirySupplierUid) {
		this.inquirySupplierUid = inquirySupplierUid;
	}
	public String getMailName() {
		return mailName;
	}
	public void setMailName(String mailName) {
		this.mailName = mailName;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	
	

	
	
	
}
