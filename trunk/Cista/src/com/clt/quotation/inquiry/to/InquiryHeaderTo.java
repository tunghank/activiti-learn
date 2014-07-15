package com.clt.quotation.inquiry.to;

import java.io.Serializable;
import java.util.List;

import com.clt.system.util.BaseObject;


public class InquiryHeaderTo extends BaseObject implements Serializable {

	// Fields
	private String inquiryHeaderUid;
	private String inquiryNum;
	private String paperVerUid;
	private String paperVerStartDt;
	private String paperVerEndDt;
	private String cltInquiryUser;
	private String inquiryStatus;
	private String compareStatus;
	private String cdt;
	
	//For Show
	private String quotPaperDesc;
	private String compareStatusDesc;
	private String inquiryStatusDesc;
	
	public String getInquiryHeaderUid() {
		return inquiryHeaderUid;
	}
	public void setInquiryHeaderUid(String inquiryHeaderUid) {
		this.inquiryHeaderUid = inquiryHeaderUid;
	}
	public String getInquiryNum() {
		return inquiryNum;
	}
	public void setInquiryNum(String inquiryNum) {
		this.inquiryNum = inquiryNum;
	}
	public String getPaperVerUid() {
		return paperVerUid;
	}
	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}
	public String getPaperVerStartDt() {
		return paperVerStartDt;
	}
	public void setPaperVerStartDt(String paperVerStartDt) {
		this.paperVerStartDt = paperVerStartDt;
	}
	public String getPaperVerEndDt() {
		return paperVerEndDt;
	}
	public void setPaperVerEndDt(String paperVerEndDt) {
		this.paperVerEndDt = paperVerEndDt;
	}
	public String getCltInquiryUser() {
		return cltInquiryUser;
	}
	public void setCltInquiryUser(String cltInquiryUser) {
		this.cltInquiryUser = cltInquiryUser;
	}
	public String getInquiryStatus() {
		return inquiryStatus;
	}
	public void setInquiryStatus(String inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}

	public String getCompareStatus() {
		return compareStatus;
	}
	public void setCompareStatus(String compareStatus) {
		this.compareStatus = compareStatus;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	public String getQuotPaperDesc() {
		return quotPaperDesc;
	}
	public void setQuotPaperDesc(String quotPaperDesc) {
		this.quotPaperDesc = quotPaperDesc;
	}
	public String getCompareStatusDesc() {
		return compareStatusDesc;
	}
	public void setCompareStatusDesc(String compareStatusDesc) {
		this.compareStatusDesc = compareStatusDesc;
	}
	public String getInquiryStatusDesc() {
		return inquiryStatusDesc;
	}
	public void setInquiryStatusDesc(String inquiryStatusDesc) {
		this.inquiryStatusDesc = inquiryStatusDesc;
	}

	
	
}
