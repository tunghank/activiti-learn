package com.clt.quotation.erp.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;

public class ErpTaxTo extends BaseObject implements Serializable {

	// Fields
 	private String name;
 	private String taxId;
 	private String taxType;
 	private String description;
 	private String taxRate;
 	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
 	
 	
	
	

}
