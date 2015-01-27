package com.cista.report.to.FTYield;

import java.util.Date;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FTYieldReceiveTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String receiveSheet;
	private Date receiveDate;
	private String vendor;
	private String receiveFlag;
	private String po;
	private String lot;
	private String partNum;
	private String product;
	private String spec;
	private Long ga;
	private Long gb;
	private Long gc;
	private Long gd;
	private String unit;
	private String itemFlag;

	
	
	
	// Constructors

	/** default constructor */
	public FTYieldReceiveTo() {
	}




	public String getReceiveSheet() {
		return receiveSheet;
	}




	public void setReceiveSheet(String receiveSheet) {
		this.receiveSheet = receiveSheet;
	}




	public Date getReceiveDate() {
		return receiveDate;
	}




	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}




	public String getVendor() {
		return vendor;
	}




	public void setVendor(String vendor) {
		this.vendor = vendor;
	}




	public String getReceiveFlag() {
		return receiveFlag;
	}




	public void setReceiveFlag(String receiveFlag) {
		this.receiveFlag = receiveFlag;
	}




	public String getPo() {
		return po;
	}




	public void setPo(String po) {
		this.po = po;
	}




	public String getLot() {
		return lot;
	}




	public void setLot(String lot) {
		this.lot = lot;
	}




	public String getPartNum() {
		return partNum;
	}




	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}




	public String getProduct() {
		return product;
	}




	public void setProduct(String product) {
		this.product = product;
	}




	public String getSpec() {
		return spec;
	}




	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getItemFlag() {
		return itemFlag;
	}

	public void setItemFlag(String itemFlag) {
		this.itemFlag = itemFlag;
	}




	public Long getGa() {
		return ga;
	}




	public void setGa(Long ga) {
		this.ga = ga;
	}




	public Long getGb() {
		return gb;
	}




	public void setGb(Long gb) {
		this.gb = gb;
	}




	public Long getGc() {
		return gc;
	}




	public void setGc(Long gc) {
		this.gc = gc;
	}




	public Long getGd() {
		return gd;
	}




	public void setGd(Long gd) {
		this.gd = gd;
	}




	
}