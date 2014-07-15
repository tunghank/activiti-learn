package com.clt.quotation.quote.to;

import java.io.Serializable;


import com.clt.system.util.BaseObject;


/**
 * @author 1004251
 *
 */
public class QuoteRecordTo extends BaseObject implements Serializable , Cloneable {

	/**** Header Fields ****/
	private String quoteRecordUid;
	private String quoteHeaderUid;
	private String paperVerUid;
	private String paperGroupUid;
	private String paperFieldUid;
	private int recordRowSeq;
	private int fieldSeq;
	private String recordValue;
	private String cdt;

	/**** Header Fields ****/
	
	public Object clone() throws CloneNotSupportedException {
		  return super.clone();
	}

	public String getQuoteRecordUid() {
		return quoteRecordUid;
	}

	public void setQuoteRecordUid(String quoteRecordUid) {
		this.quoteRecordUid = quoteRecordUid;
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

	public String getPaperFieldUid() {
		return paperFieldUid;
	}

	public void setPaperFieldUid(String paperFieldUid) {
		this.paperFieldUid = paperFieldUid;
	}


	public int getFieldSeq() {
		return fieldSeq;
	}

	public void setFieldSeq(int fieldSeq) {
		this.fieldSeq = fieldSeq;
	}

	public int getRecordRowSeq() {
		return recordRowSeq;
	}

	public void setRecordRowSeq(int recordRowSeq) {
		this.recordRowSeq = recordRowSeq;
	}

	public String getRecordValue() {
		return recordValue;
	}

	public void setRecordValue(String recordValue) {
		this.recordValue = recordValue;
	}

	public String getCdt() {
		return cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}



}
