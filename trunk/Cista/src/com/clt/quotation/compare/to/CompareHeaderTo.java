package com.clt.quotation.compare.to;

import java.io.Serializable;
import java.util.List;

import com.clt.quotation.compare.to.CompareMapQuoteTo;
import com.clt.system.util.BaseObject;


public class CompareHeaderTo extends BaseObject implements Serializable {

	// Fields
	private String compareHeaderUid;
	private String inquiryHeaderUid;
	private String compareNum;
	private String compareStatus;
	private String cltCompareUser;
	private String cdt;
	private List<CompareMapQuoteTo> compareMapQuoteList;
	
	public String getCompareHeaderUid() {
		return compareHeaderUid;
	}
	public void setCompareHeaderUid(String compareHeaderUid) {
		this.compareHeaderUid = compareHeaderUid;
	}
	public String getCompareNum() {
		return compareNum;
	}
	public void setCompareNum(String compareNum) {
		this.compareNum = compareNum;
	}
	public String getCompareStatus() {
		return compareStatus;
	}
	public void setCompareStatus(String compareStatus) {
		this.compareStatus = compareStatus;
	}
	public String getCltCompareUser() {
		return cltCompareUser;
	}
	public void setCltCompareUser(String cltCompareUser) {
		this.cltCompareUser = cltCompareUser;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	public List<CompareMapQuoteTo> getCompareMapQuoteList() {
		return compareMapQuoteList;
	}
	public void setCompareMapQuoteList(List<CompareMapQuoteTo> compareMapQuoteList) {
		this.compareMapQuoteList = compareMapQuoteList;
	}
	public String getInquiryHeaderUid() {
		return inquiryHeaderUid;
	}
	public void setInquiryHeaderUid(String inquiryHeaderUid) {
		this.inquiryHeaderUid = inquiryHeaderUid;
	}
	
	
}
