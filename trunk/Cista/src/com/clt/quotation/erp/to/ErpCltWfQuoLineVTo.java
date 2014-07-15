package com.clt.quotation.erp.to;

import java.io.Serializable;
import java.util.Date;

import com.clt.system.util.BaseObject;

public class ErpCltWfQuoLineVTo extends BaseObject implements Serializable {

	// Fields
 	private String orgId;
 	private String orgName;
 	private String qNo;
 	private String poLineId;
 	private String vendorId;
 	private String vendorName;
 	private String vendorSiteId;
 	private String currencyCode;
 	private Date startDate;
 	private Date endDate;
 	private String fobLookupCode;
 	private String shipToLocationId;
 	private String location;
 	private String itemId;
 	private String itemNumber;
 	private String freightTermsLookupCode;
 	private String freightTerm;
 	private String termsId;
 	private String paymentTerm;
 	private String priceOverride;
 	
 	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getqNo() {
		return qNo;
	}
	public void setqNo(String qNo) {
		this.qNo = qNo;
	}
	public String getPoLineId() {
		return poLineId;
	}
	public void setPoLineId(String poLineId) {
		this.poLineId = poLineId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorSiteId() {
		return vendorSiteId;
	}
	public void setVendorSiteId(String vendorSiteId) {
		this.vendorSiteId = vendorSiteId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFobLookupCode() {
		return fobLookupCode;
	}
	public void setFobLookupCode(String fobLookupCode) {
		this.fobLookupCode = fobLookupCode;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getFreightTermsLookupCode() {
		return freightTermsLookupCode;
	}
	public void setFreightTermsLookupCode(String freightTermsLookupCode) {
		this.freightTermsLookupCode = freightTermsLookupCode;
	}
	public String getFreightTerm() {
		return freightTerm;
	}
	public void setFreightTerm(String freightTerm) {
		this.freightTerm = freightTerm;
	}
	public String getTermsId() {
		return termsId;
	}
	public void setTermsId(String termsId) {
		this.termsId = termsId;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public String getPriceOverride() {
		return priceOverride;
	}
	public void setPriceOverride(String priceOverride) {
		this.priceOverride = priceOverride;
	}
 	
 	
	
	

}
