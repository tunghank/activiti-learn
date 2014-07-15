package com.clt.quotation.compare.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;


public class CompareMapQuoteTo extends BaseObject implements Serializable {

	// Fields
	private String compareMapQuoteUid;
	private String compareHeaderUid;
	private String quoteHeaderUid;
	private String cdt;
	private String compareRatio;
	
	
	public String getCompareMapQuoteUid() {
		return compareMapQuoteUid;
	}
	public void setCompareMapQuoteUid(String compareMapQuoteUid) {
		this.compareMapQuoteUid = compareMapQuoteUid;
	}
	public String getCompareHeaderUid() {
		return compareHeaderUid;
	}
	public void setCompareHeaderUid(String compareHeaderUid) {
		this.compareHeaderUid = compareHeaderUid;
	}
	public String getQuoteHeaderUid() {
		return quoteHeaderUid;
	}
	public void setQuoteHeaderUid(String quoteHeaderUid) {
		this.quoteHeaderUid = quoteHeaderUid;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	public String getCompareRatio() {
		return compareRatio;
	}
	public void setCompareRatio(String compareRatio) {
		this.compareRatio = compareRatio;
	}
	
	
}
