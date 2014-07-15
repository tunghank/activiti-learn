package com.clt.quotation.erp.to;

import java.io.Serializable;
import java.util.List;

import com.clt.system.util.BaseObject;

public class PartNumElementTo extends BaseObject implements Serializable {

	// Fields
	private String partNum;
	private String partNumDesc;
	
	private String elementValue;
	private String elementName;
	
	
	public String getPartNum() {
		return partNum;
	}
	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}
	public String getPartNumDesc() {
		return partNumDesc;
	}
	public void setPartNumDesc(String partNumDesc) {
		this.partNumDesc = partNumDesc;
	}
	public String getElementValue() {
		return elementValue;
	}
	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	

}
