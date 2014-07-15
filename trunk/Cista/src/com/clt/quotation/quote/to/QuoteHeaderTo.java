package com.clt.quotation.quote.to;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.clt.quotation.config.to.QuoPaperGroupTo;
import com.clt.system.util.BaseObject;

public class QuoteHeaderTo extends BaseObject implements Serializable , Cloneable {

	/**** Header Fields ****/
	
	private String quoteHeaderUid;
	private String quoteNum;
	private String inquiryHeaderUid;
	private String inquiryPartNumUid;
	private String inquirySupplierUid;
	private String quoteSupplierCode;
	private String cltContactUser;
	private String quoteOfferPeople;
	private String quotePartNum;
	private String quotePartNumDesc;
	private Double quoteTotal;
	private Double quoteRealTotal;
	private Double quoteTftTotal;
	private String quoteTax;
	private String quoteNotes;
	private String cdt;
	private String quoteStatus;
	
	private Date firstQuoteDate;
	private Date lastQuoteDate;
	private Integer quoteCount;
	
	
	private List<QuoteRecordTo> QuoteRecordList;
	//For Show
	private String paperVerUid;
	private List<QuoPaperGroupTo> quoPaperGroupList;
	private String quoteStatusDesc;
	
	public Object clone() throws CloneNotSupportedException {
		  return super.clone();
	}

	/**** Header Fields ****/
	
	public String getQuoteHeaderUid() {
		return quoteHeaderUid;
	}


	public void setQuoteHeaderUid(String quoteHeaderUid) {
		this.quoteHeaderUid = quoteHeaderUid;
	}


	public String getQuoteNum() {
		return quoteNum;
	}


	public void setQuoteNum(String quoteNum) {
		this.quoteNum = quoteNum;
	}


	public String getInquiryHeaderUid() {
		return inquiryHeaderUid;
	}

	public void setInquiryHeaderUid(String inquiryHeaderUid) {
		this.inquiryHeaderUid = inquiryHeaderUid;
	}

	public String getCltContactUser() {
		return cltContactUser;
	}


	public void setCltContactUser(String cltContactUser) {
		this.cltContactUser = cltContactUser;
	}


	public String getQuoteOfferPeople() {
		return quoteOfferPeople;
	}


	public void setQuoteOfferPeople(String quoteOfferPeople) {
		this.quoteOfferPeople = quoteOfferPeople;
	}


	public String getQuotePartNum() {
		return quotePartNum;
	}


	public void setQuotePartNum(String quotePartNum) {
		this.quotePartNum = quotePartNum;
	}


	public String getQuotePartNumDesc() {
		return quotePartNumDesc;
	}


	public void setQuotePartNumDesc(String quotePartNumDesc) {
		this.quotePartNumDesc = quotePartNumDesc;
	}


	public Double getQuoteTotal() {
		return quoteTotal;
	}


	public void setQuoteTotal(Double quoteTotal) {
		this.quoteTotal = quoteTotal;
	}


	public Double getQuoteRealTotal() {
		return quoteRealTotal;
	}


	public void setQuoteRealTotal(Double quoteRealTotal) {
		this.quoteRealTotal = quoteRealTotal;
	}


	public Double getQuoteTftTotal() {
		return quoteTftTotal;
	}


	public void setQuoteTftTotal(Double quoteTftTotal) {
		this.quoteTftTotal = quoteTftTotal;
	}


	public String getQuoteTax() {
		return quoteTax;
	}


	public void setQuoteTax(String quoteTax) {
		this.quoteTax = quoteTax;
	}


	public String getQuoteNotes() {
		return quoteNotes;
	}


	public void setQuoteNotes(String quoteNotes) {
		this.quoteNotes = quoteNotes;
	}


	public String getCdt() {
		return cdt;
	}


	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

	public List<QuoteRecordTo> getQuoteRecordList() {
		return QuoteRecordList;
	}

	public void setQuoteRecordList(List<QuoteRecordTo> quoteRecordList) {
		QuoteRecordList = quoteRecordList;
	}

	public String getQuoteSupplierCode() {
		return quoteSupplierCode;
	}

	public void setQuoteSupplierCode(String quoteSupplierCode) {
		this.quoteSupplierCode = quoteSupplierCode;
	}

	public String getPaperVerUid() {
		return paperVerUid;
	}

	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}

	public List<QuoPaperGroupTo> getQuoPaperGroupList() {
		return quoPaperGroupList;
	}

	public void setQuoPaperGroupList(List<QuoPaperGroupTo> quoPaperGroupList) {
		this.quoPaperGroupList = quoPaperGroupList;
	}

	public String getInquiryPartNumUid() {
		return inquiryPartNumUid;
	}

	public void setInquiryPartNumUid(String inquiryPartNumUid) {
		this.inquiryPartNumUid = inquiryPartNumUid;
	}

	public String getInquirySupplierUid() {
		return inquirySupplierUid;
	}

	public void setInquirySupplierUid(String inquirySupplierUid) {
		this.inquirySupplierUid = inquirySupplierUid;
	}

	public String getQuoteStatus() {
		return quoteStatus;
	}

	public void setQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

	public Date getFirstQuoteDate() {
		return firstQuoteDate;
	}

	public void setFirstQuoteDate(Date firstQuoteDate) {
		this.firstQuoteDate = firstQuoteDate;
	}

	public Date getLastQuoteDate() {
		return lastQuoteDate;
	}

	public void setLastQuoteDate(Date lastQuoteDate) {
		this.lastQuoteDate = lastQuoteDate;
	}

	public Integer getQuoteCount() {
		return quoteCount;
	}

	public void setQuoteCount(Integer quoteCount) {
		this.quoteCount = quoteCount;
	}

	public String getQuoteStatusDesc() {
		return quoteStatusDesc;
	}

	public void setQuoteStatusDesc(String quoteStatusDesc) {
		this.quoteStatusDesc = quoteStatusDesc;
	}

	
	
}
