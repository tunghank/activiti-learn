package com.clt.quotation.config.to;

import java.io.Serializable;
import com.clt.system.util.BaseObject;

public class QuoPaperFieldTo extends BaseObject implements Serializable {

	// Fields
	private String paperFieldUid;
	private String paperGroupUid;
	private String field;
	private int fieldSeq;
	private String fieldAttr;
	private String fieldNecess;
	private String fieldValue;	
	private double numberStart;
	private double numberEnd;
	private String cdt;
	
	
	
	public String getPaperFieldUid() {
		return paperFieldUid;
	}
	public void setPaperFieldUid(String paperFieldUid) {
		this.paperFieldUid = paperFieldUid;
	}
	public String getPaperGroupUid() {
		return paperGroupUid;
	}
	public void setPaperGroupUid(String paperGroupUid) {
		this.paperGroupUid = paperGroupUid;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public int getFieldSeq() {
		return fieldSeq;
	}
	public void setFieldSeq(int fieldSeq) {
		this.fieldSeq = fieldSeq;
	}
	public String getFieldAttr() {
		return fieldAttr;
	}
	public void setFieldAttr(String fieldAttr) {
		this.fieldAttr = fieldAttr;
	}
	public String getFieldNecess() {
		return fieldNecess;
	}
	public void setFieldNecess(String fieldNecess) {
		this.fieldNecess = fieldNecess;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public double getNumberStart() {
		return numberStart;
	}
	public void setNumberStart(double numberStart) {
		this.numberStart = numberStart;
	}
	public double getNumberEnd() {
		return numberEnd;
	}
	public void setNumberEnd(double numberEnd) {
		this.numberEnd = numberEnd;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	
	

	
}
