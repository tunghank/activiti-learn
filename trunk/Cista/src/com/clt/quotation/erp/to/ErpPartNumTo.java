package com.clt.quotation.erp.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;

public class ErpPartNumTo extends BaseObject implements Serializable {

	// Fields
	private String inventoryItemId;
	private String partNum;
	private String partNumDesc;
	private String partNumUnit;
	
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
	public String getPartNumUnit() {
		return partNumUnit;
	}
	public void setPartNumUnit(String partNumUnit) {
		this.partNumUnit = partNumUnit;
	}
	public String getInventoryItemId() {
		return inventoryItemId;
	}
	public void setInventoryItemId(String inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}
	

	
	
	

}
