package com.clt.quotation.compare.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;


public class CompareSeqnumTo extends BaseObject implements Serializable {

	// Fields
	private String seqYear;
	private String seqMonth;
	private int seqNum;
	
	
	public String getSeqYear() {
		return seqYear;
	}
	public void setSeqYear(String seqYear) {
		this.seqYear = seqYear;
	}
	public String getSeqMonth() {
		return seqMonth;
	}
	public void setSeqMonth(String seqMonth) {
		this.seqMonth = seqMonth;
	}
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	
}
