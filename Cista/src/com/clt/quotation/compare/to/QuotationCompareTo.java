package com.clt.quotation.compare.to;

import java.io.Serializable;
import java.util.List;

import com.clt.quotation.inquiry.to.InquiryHeaderTo;
import com.clt.quotation.inquiry.to.InquirySupplierTo;
import com.clt.quotation.inquiry.to.InquiryPartNumTo;
import com.clt.quotation.quote.to.QuoteHeaderTo;
import com.clt.quotation.quote.to.QuoteRecordTotalTo;
import com.clt.system.util.BaseObject;


public class QuotationCompareTo extends BaseObject implements Serializable {

	// Fields
	private InquiryHeaderTo inquiryHeaderTo;
	private QuoteHeaderTo quoteHeaderTo;
	private InquirySupplierTo inquirySupplierTo;
	private InquiryPartNumTo InquiryPartNumTo;
	private List<QuoteRecordTotalTo> quoteRecordTotalList;
	private ApplyCompareTo applyCompareTo;
	
	public InquiryHeaderTo getInquiryHeaderTo() {
		return inquiryHeaderTo;
	}
	public void setInquiryHeaderTo(InquiryHeaderTo inquiryHeaderTo) {
		this.inquiryHeaderTo = inquiryHeaderTo;
	}
	public QuoteHeaderTo getQuoteHeaderTo() {
		return quoteHeaderTo;
	}
	public void setQuoteHeaderTo(QuoteHeaderTo quoteHeaderTo) {
		this.quoteHeaderTo = quoteHeaderTo;
	}
	public InquirySupplierTo getInquirySupplierTo() {
		return inquirySupplierTo;
	}
	public void setInquirySupplierTo(InquirySupplierTo inquirySupplierTo) {
		this.inquirySupplierTo = inquirySupplierTo;
	}
	public List<QuoteRecordTotalTo> getQuoteRecordTotalList() {
		return quoteRecordTotalList;
	}
	public void setQuoteRecordTotalList(
			List<QuoteRecordTotalTo> quoteRecordTotalList) {
		this.quoteRecordTotalList = quoteRecordTotalList;
	}
	public ApplyCompareTo getApplyCompareTo() {
		return applyCompareTo;
	}
	public void setApplyCompareTo(ApplyCompareTo applyCompareTo) {
		this.applyCompareTo = applyCompareTo;
	}
	public InquiryPartNumTo getInquiryPartNumTo() {
		return InquiryPartNumTo;
	}
	public void setInquiryPartNumTo(InquiryPartNumTo inquiryPartNumTo) {
		InquiryPartNumTo = inquiryPartNumTo;
	}


}
