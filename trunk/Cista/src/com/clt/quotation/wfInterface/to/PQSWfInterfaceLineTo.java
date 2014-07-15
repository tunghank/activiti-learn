package com.clt.quotation.wfInterface.to;

import java.io.Serializable;
import java.util.Date;

import com.clt.system.util.BaseObject;


public class PQSWfInterfaceLineTo extends BaseObject implements Serializable , Cloneable {

	private String pqsNo;
	private String wfNo;
	private String wfInsid;
	private int seqNo;
	private int erpQuoId;
	private int erpQuoLineNo;
	private String partsNo;
	private String partsDesc;
	private String unit;
	private int vendorId;
	private String vendorName;
	private int vendorSiteId;
	private String vendorSiteCode;
	private Double allocation;
	private String currency;
	private Double priceOld;
	private Double priceNew;
	private Double cpPriceQld;
	private Double cpPriceNew;
	private int taxId;
	private String taxName;
	private Date quoStartDateOld;
	private Date quoEndDateOld;
	private Date quoStartDateNew;
	private Date quoEndDateNew;
	private String paymentMethodOld;
	private int paymentMethodNewId;
	private String paymentMethodNew;
	private String conditionsTermOld;
	private String conditionsTermNew;
	private int shipLocationId;
	private String shipLocation;
	private int billToLocId;
	private String freightTermsCode;
	private String freightTerms;
	private String comments;
	private String erpMsg;
	public String getPqsNo() {
		return pqsNo;
	}
	public void setPqsNo(String pqsNo) {
		this.pqsNo = pqsNo;
	}
	public String getWfNo() {
		return wfNo;
	}
	public void setWfNo(String wfNo) {
		this.wfNo = wfNo;
	}
	public String getWfInsid() {
		return wfInsid;
	}
	public void setWfInsid(String wfInsid) {
		this.wfInsid = wfInsid;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public int getErpQuoId() {
		return erpQuoId;
	}
	public void setErpQuoId(int erpQuoId) {
		this.erpQuoId = erpQuoId;
	}
	public int getErpQuoLineNo() {
		return erpQuoLineNo;
	}
	public void setErpQuoLineNo(int erpQuoLineNo) {
		this.erpQuoLineNo = erpQuoLineNo;
	}
	public String getPartsNo() {
		return partsNo;
	}
	public void setPartsNo(String partsNo) {
		this.partsNo = partsNo;
	}
	public String getPartsDesc() {
		return partsDesc;
	}
	public void setPartsDesc(String partsDesc) {
		this.partsDesc = partsDesc;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public int getVendorSiteId() {
		return vendorSiteId;
	}
	public void setVendorSiteId(int vendorSiteId) {
		this.vendorSiteId = vendorSiteId;
	}
	public String getVendorSiteCode() {
		return vendorSiteCode;
	}
	public void setVendorSiteCode(String vendorSiteCode) {
		this.vendorSiteCode = vendorSiteCode;
	}
	public Double getAllocation() {
		return allocation;
	}
	public void setAllocation(Double allocation) {
		this.allocation = allocation;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getPriceOld() {
		return priceOld;
	}
	public void setPriceOld(Double priceOld) {
		this.priceOld = priceOld;
	}
	public Double getPriceNew() {
		return priceNew;
	}
	public void setPriceNew(Double priceNew) {
		this.priceNew = priceNew;
	}
	public Double getCpPriceQld() {
		return cpPriceQld;
	}
	public void setCpPriceQld(Double cpPriceQld) {
		this.cpPriceQld = cpPriceQld;
	}
	public Double getCpPriceNew() {
		return cpPriceNew;
	}
	public void setCpPriceNew(Double cpPriceNew) {
		this.cpPriceNew = cpPriceNew;
	}
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public Date getQuoStartDateOld() {
		return quoStartDateOld;
	}
	public void setQuoStartDateOld(Date quoStartDateOld) {
		this.quoStartDateOld = quoStartDateOld;
	}
	public Date getQuoEndDateOld() {
		return quoEndDateOld;
	}
	public void setQuoEndDateOld(Date quoEndDateOld) {
		this.quoEndDateOld = quoEndDateOld;
	}
	public Date getQuoStartDateNew() {
		return quoStartDateNew;
	}
	public void setQuoStartDateNew(Date quoStartDateNew) {
		this.quoStartDateNew = quoStartDateNew;
	}
	public Date getQuoEndDateNew() {
		return quoEndDateNew;
	}
	public void setQuoEndDateNew(Date quoEndDateNew) {
		this.quoEndDateNew = quoEndDateNew;
	}
	public String getPaymentMethodOld() {
		return paymentMethodOld;
	}
	public void setPaymentMethodOld(String paymentMethodOld) {
		this.paymentMethodOld = paymentMethodOld;
	}
	public int getPaymentMethodNewId() {
		return paymentMethodNewId;
	}
	public void setPaymentMethodNewId(int paymentMethodNewId) {
		this.paymentMethodNewId = paymentMethodNewId;
	}
	public String getPaymentMethodNew() {
		return paymentMethodNew;
	}
	public void setPaymentMethodNew(String paymentMethodNew) {
		this.paymentMethodNew = paymentMethodNew;
	}
	public String getConditionsTermOld() {
		return conditionsTermOld;
	}
	public void setConditionsTermOld(String conditionsTermOld) {
		this.conditionsTermOld = conditionsTermOld;
	}
	public String getConditionsTermNew() {
		return conditionsTermNew;
	}
	public void setConditionsTermNew(String conditionsTermNew) {
		this.conditionsTermNew = conditionsTermNew;
	}
	public int getShipLocationId() {
		return shipLocationId;
	}
	public void setShipLocationId(int shipLocationId) {
		this.shipLocationId = shipLocationId;
	}
	public String getShipLocation() {
		return shipLocation;
	}
	public void setShipLocation(String shipLocation) {
		this.shipLocation = shipLocation;
	}
	public int getBillToLocId() {
		return billToLocId;
	}
	public void setBillToLocId(int billToLocId) {
		this.billToLocId = billToLocId;
	}
	public String getFreightTermsCode() {
		return freightTermsCode;
	}
	public void setFreightTermsCode(String freightTermsCode) {
		this.freightTermsCode = freightTermsCode;
	}
	public String getFreightTerms() {
		return freightTerms;
	}
	public void setFreightTerms(String freightTerms) {
		this.freightTerms = freightTerms;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getErpMsg() {
		return erpMsg;
	}
	public void setErpMsg(String erpMsg) {
		this.erpMsg = erpMsg;
	}
	
}
