package com.clt.quotation.inquiry.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;


public class InquiryConfirmTo extends BaseObject implements Serializable , Cloneable {

	/**** Header Fields ****/
	/**** Header ****/
	private String inquiryHeaderUid;
	private String inquiryNum;
	private String paperVerUid;
	private String paperVerStartDt;
	private String paperVerEndDt;
	private String cltInquiryUser;
	private String inquiryStatus;
	private String inquiryStatusDesc;
	private String compareStatus;
	private String compareStatusDesc;
	//For Show
	private String quotPaperDesc;
	private String inquiryCdt;
	/**** Part Number ****/
	private String inquiryKeyPartNum;
	private String inquiryPartNumUid;
	private String inquiryModel;
	private String inquiryModelDesc;
	private String inquiryPartNum;
	private String inquiryPartNumDesc;
	private String inquiryPartNumDiffer;
	private String inquiryQty;
	private String inquiryUnit;
	/**** INQUIRY_SUPPLIER ****/
	private String inquirySupplierUid;
	private String inquirySupplierCode;
	private String inquirySupplierName;
	private String inquirySupplierSiteCode;
	private String inquirySupplierSite;
	private String inquirySupplierPartNum;
	private String inquiryCurrency;
	private String inquiryPaymentMethod;
	private String inquiryDeliveryLocation;
	private String inquiryShippedBy;
	private String quotationRecoverTime;
	//For Show
	private String inquiryDeliveryLocationDesc;
	private String inquiryShippedByDesc;
	
	/**** INQUIRY_SUPPLIER Contact ****/
	private String inquirySupplierContactUid;
	private String inquirySupplierContact;
	private String inquirySupplierEmail;
	private String inquirySupplierPhone;
	
	//Quote
	private String quoteHeaderUid;
	private String quoteNum;
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
	private String quoteStatus;
	private String quoteStatusDesc;
	//private String inquiryRemark;
		
	public Object clone() throws CloneNotSupportedException {
		  return super.clone();
	}



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



	public String getQuotPaperDesc() {
		return quotPaperDesc;
	}



	public void setQuotPaperDesc(String quotPaperDesc) {
		this.quotPaperDesc = quotPaperDesc;
	}



	public String getInquiryKeyPartNum() {
		return inquiryKeyPartNum;
	}



	public void setInquiryKeyPartNum(String inquiryKeyPartNum) {
		this.inquiryKeyPartNum = inquiryKeyPartNum;
	}



	public String getInquiryPartNumUid() {
		return inquiryPartNumUid;
	}



	public void setInquiryPartNumUid(String inquiryPartNumUid) {
		this.inquiryPartNumUid = inquiryPartNumUid;
	}



	public String getInquiryModel() {
		return inquiryModel;
	}



	public void setInquiryModel(String inquiryModel) {
		this.inquiryModel = inquiryModel;
	}



	public String getInquiryModelDesc() {
		return inquiryModelDesc;
	}



	public void setInquiryModelDesc(String inquiryModelDesc) {
		this.inquiryModelDesc = inquiryModelDesc;
	}



	public String getInquiryPartNum() {
		return inquiryPartNum;
	}



	public void setInquiryPartNum(String inquiryPartNum) {
		this.inquiryPartNum = inquiryPartNum;
	}



	public String getInquiryPartNumDesc() {
		return inquiryPartNumDesc;
	}



	public void setInquiryPartNumDesc(String inquiryPartNumDesc) {
		this.inquiryPartNumDesc = inquiryPartNumDesc;
	}



	public String getInquiryPartNumDiffer() {
		return inquiryPartNumDiffer;
	}



	public void setInquiryPartNumDiffer(String inquiryPartNumDiffer) {
		this.inquiryPartNumDiffer = inquiryPartNumDiffer;
	}



	public String getInquiryQty() {
		return inquiryQty;
	}



	public void setInquiryQty(String inquiryQty) {
		this.inquiryQty = inquiryQty;
	}



	public String getInquiryUnit() {
		return inquiryUnit;
	}



	public void setInquiryUnit(String inquiryUnit) {
		this.inquiryUnit = inquiryUnit;
	}



	public String getInquirySupplierUid() {
		return inquirySupplierUid;
	}



	public void setInquirySupplierUid(String inquirySupplierUid) {
		this.inquirySupplierUid = inquirySupplierUid;
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



	public String getQuotationRecoverTime() {
		return quotationRecoverTime;
	}



	public void setQuotationRecoverTime(String quotationRecoverTime) {
		this.quotationRecoverTime = quotationRecoverTime;
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



	public String getInquirySupplierContactUid() {
		return inquirySupplierContactUid;
	}



	public void setInquirySupplierContactUid(String inquirySupplierContactUid) {
		this.inquirySupplierContactUid = inquirySupplierContactUid;
	}



	public String getInquirySupplierContact() {
		return inquirySupplierContact;
	}



	public void setInquirySupplierContact(String inquirySupplierContact) {
		this.inquirySupplierContact = inquirySupplierContact;
	}



	public String getInquirySupplierEmail() {
		return inquirySupplierEmail;
	}



	public void setInquirySupplierEmail(String inquirySupplierEmail) {
		this.inquirySupplierEmail = inquirySupplierEmail;
	}



	public String getInquirySupplierPhone() {
		return inquirySupplierPhone;
	}



	public void setInquirySupplierPhone(String inquirySupplierPhone) {
		this.inquirySupplierPhone = inquirySupplierPhone;
	}



	public String getInquiryCdt() {
		return inquiryCdt;
	}



	public void setInquiryCdt(String inquiryCdt) {
		this.inquiryCdt = inquiryCdt;
	}



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



	public String getQuoteSupplierCode() {
		return quoteSupplierCode;
	}



	public void setQuoteSupplierCode(String quoteSupplierCode) {
		this.quoteSupplierCode = quoteSupplierCode;
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



	public String getQuoteStatus() {
		return quoteStatus;
	}



	public void setQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
	}



	public String getInquiryStatusDesc() {
		return inquiryStatusDesc;
	}



	public void setInquiryStatusDesc(String inquiryStatusDesc) {
		this.inquiryStatusDesc = inquiryStatusDesc;
	}



	public String getQuoteStatusDesc() {
		return quoteStatusDesc;
	}

	public void setQuoteStatusDesc(String quoteStatusDesc) {
		this.quoteStatusDesc = quoteStatusDesc;
	}



	public String getCompareStatusDesc() {
		return compareStatusDesc;
	}



	public void setCompareStatusDesc(String compareStatusDesc) {
		this.compareStatusDesc = compareStatusDesc;
	}
	
	/**** Header Fields ****/
	

	
}
