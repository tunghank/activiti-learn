package com.clt.quotation.erp.to;

import java.io.Serializable;
import java.util.List;

import com.clt.system.util.BaseObject;

public class ErpBOMTo extends BaseObject implements Serializable {

	// Fields
	private String assembly;
	private String assemblyDescription;
	private String assemblyUom;
	private String itemNum;
	private String operationSeqNum;
	private String item;
	private String itemDescription;
	private String itemUom;

	
	
	
	public String getAssembly() {
		return assembly;
	}
	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}
	public String getAssemblyDescription() {
		return assemblyDescription;
	}
	public void setAssemblyDescription(String assemblyDescription) {
		this.assemblyDescription = assemblyDescription;
	}
	public String getAssemblyUom() {
		return assemblyUom;
	}
	public void setAssemblyUom(String assemblyUom) {
		this.assemblyUom = assemblyUom;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public String getOperationSeqNum() {
		return operationSeqNum;
	}
	public void setOperationSeqNum(String operationSeqNum) {
		this.operationSeqNum = operationSeqNum;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getItemUom() {
		return itemUom;
	}
	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}
	
}
