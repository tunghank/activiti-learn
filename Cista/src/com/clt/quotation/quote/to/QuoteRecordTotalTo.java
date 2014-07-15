package com.clt.quotation.quote.to;

import java.io.Serializable;


import com.clt.system.util.BaseObject;


/**
 * @author 1004251
 *
 */
public class QuoteRecordTotalTo extends BaseObject implements Serializable , Cloneable {

	/**** Header Fields ****/
	private String quoteRecordTotalUid;
	private String quoteHeaderUid;
	private String paperVerUid;
	private String paperGroupUid;
	private double recordTotal;
	private String cdt;

	/**** Header Fields ****/
	
	public Object clone() throws CloneNotSupportedException {
		  return super.clone();
	}



	public String getQuoteRecordTotalUid() {
		return quoteRecordTotalUid;
	}



	public void setQuoteRecordTotalUid(String quoteRecordTotalUid) {
		this.quoteRecordTotalUid = quoteRecordTotalUid;
	}



	public String getQuoteHeaderUid() {
		return quoteHeaderUid;
	}

	public void setQuoteHeaderUid(String quoteHeaderUid) {
		this.quoteHeaderUid = quoteHeaderUid;
	}

	public String getPaperVerUid() {
		return paperVerUid;
	}

	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}

	public String getPaperGroupUid() {
		return paperGroupUid;
	}

	public void setPaperGroupUid(String paperGroupUid) {
		this.paperGroupUid = paperGroupUid;
	}



	public double getRecordTotal() {
		return recordTotal;
	}



	public void setRecordTotal(double recordTotal) {
		this.recordTotal = recordTotal;
	}



	public String getCdt() {
		return cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}




}
