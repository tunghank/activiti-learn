package com.clt.quotation.config.to;

import java.util.List;

import java.io.Serializable;
import com.clt.system.util.BaseObject;
import com.clt.quotation.config.to.QuoPaperFieldTo;

public class QuoPaperGroupTo extends BaseObject implements Serializable {

	// Fields
	private String paperGroupUid;
	private String paperVerUid;
	private String paperGroupName;
	private String cdt;
	private String paperCreateBy;
	private String paperGroupSeq;
	
	private List<QuoPaperFieldTo> fieldList;

	//For Quote Use
	private double paperRecordTotal;
	private List<String> fieldValueList;

	
	public String getPaperGroupUid() {
		return paperGroupUid;
	}
	public void setPaperGroupUid(String paperGroupUid) {
		this.paperGroupUid = paperGroupUid;
	}
	public String getPaperVerUid() {
		return paperVerUid;
	}
	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}
	public String getPaperGroupName() {
		return paperGroupName;
	}
	public void setPaperGroupName(String paperGroupName) {
		this.paperGroupName = paperGroupName;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	public List<QuoPaperFieldTo> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<QuoPaperFieldTo> fieldList) {
		this.fieldList = fieldList;
	}
	public String getPaperCreateBy() {
		return paperCreateBy;
	}
	public void setPaperCreateBy(String paperCreateBy) {
		this.paperCreateBy = paperCreateBy;
	}
	public String getPaperGroupSeq() {
		return paperGroupSeq;
	}
	public void setPaperGroupSeq(String paperGroupSeq) {
		this.paperGroupSeq = paperGroupSeq;
	}
	public List<String> getFieldValueList() {
		return fieldValueList;
	}
	public void setFieldValueList(List<String> fieldValueList) {
		this.fieldValueList = fieldValueList;
	}
	public double getPaperRecordTotal() {
		return paperRecordTotal;
	}
	public void setPaperRecordTotal(double paperRecordTotal) {
		this.paperRecordTotal = paperRecordTotal;
	}



}
