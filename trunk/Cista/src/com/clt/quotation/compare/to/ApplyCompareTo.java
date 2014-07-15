package com.clt.quotation.compare.to;

import java.io.Serializable;
import java.util.List;

import com.clt.quotation.inquiry.to.InquiryHeaderTo;
import com.clt.quotation.inquiry.to.InquirySupplierTo;
import com.clt.quotation.quote.to.QuoteHeaderTo;
import com.clt.quotation.quote.to.QuoteRecordTotalTo;
import com.clt.system.util.BaseObject;


public class ApplyCompareTo extends BaseObject implements Serializable {

	// Fields
	private String quoteNum;
	private String inquiryHeaderUid;
	private String inquiryPartNum;
	private String inquirySupplierCode;
	private String paperVerUid;
	private String inquiryGroupUid;
	private Double  ratio;
	
	
	
	public String getQuoteNum() {
		return quoteNum;
	}
	public void setQuoteNum(String quoteNum) {
		this.quoteNum = quoteNum;
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
	public String getPaperVerUid() {
		return paperVerUid;
	}
	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}
	public String getInquiryGroupUid() {
		return inquiryGroupUid;
	}
	public void setInquiryGroupUid(String inquiryGroupUid) {
		this.inquiryGroupUid = inquiryGroupUid;
	}

	public Double getRatio() {
		return ratio;
	}
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	public String getInquiryHeaderUid() {
		return inquiryHeaderUid;
	}
	public void setInquiryHeaderUid(String inquiryHeaderUid) {
		this.inquiryHeaderUid = inquiryHeaderUid;
	}
	
	
}
