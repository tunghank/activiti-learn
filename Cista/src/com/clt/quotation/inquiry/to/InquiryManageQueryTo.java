package com.clt.quotation.inquiry.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;


public class InquiryManageQueryTo extends BaseObject implements Serializable , Cloneable {


	private String inquiryNum;
	private String inquiryScdt;
	private String inquiryEcdt;
	private String recoverStime;
	private String recoverEtime;
	private String inquiryPartNum;
	private String inquirySupplierCode;
	private String cltInquiryUser;
	
	
	public String getInquiryNum() {
		return inquiryNum;
	}
	public void setInquiryNum(String inquiryNum) {
		this.inquiryNum = inquiryNum;
	}
	public String getInquiryScdt() {
		return inquiryScdt;
	}
	public void setInquiryScdt(String inquiryScdt) {
		this.inquiryScdt = inquiryScdt;
	}
	public String getInquiryEcdt() {
		return inquiryEcdt;
	}
	public void setInquiryEcdt(String inquiryEcdt) {
		this.inquiryEcdt = inquiryEcdt;
	}
	public String getRecoverStime() {
		return recoverStime;
	}
	public void setRecoverStime(String recoverStime) {
		this.recoverStime = recoverStime;
	}
	public String getRecoverEtime() {
		return recoverEtime;
	}
	public void setRecoverEtime(String recoverEtime) {
		this.recoverEtime = recoverEtime;
	}
	public String getInquiryPartNum() {
		return inquiryPartNum;
	}
	public void setInquiryPartNum(String inquiryPartNum) {
		this.inquiryPartNum = inquiryPartNum;
	}
	public String getInquirySupplierCode() {
		return inquirySupplierCode;
	}
	public void setInquirySupplierCode(String inquirySupplierCode) {
		this.inquirySupplierCode = inquirySupplierCode;
	}
	public String getCltInquiryUser() {
		return cltInquiryUser;
	}
	public void setCltInquiryUser(String cltInquiryUser) {
		this.cltInquiryUser = cltInquiryUser;
	}

	
}
