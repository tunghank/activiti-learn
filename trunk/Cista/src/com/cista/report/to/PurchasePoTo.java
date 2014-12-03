package com.cista.report.to;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PurchasePoTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  private String productSpec;
	  private Long purchaseQty;
	  private Long receiveQty;
	  private Long notReceiveQty;
	  
	  private String vendor;
	  private String vendorName;
	  private String poNum;
	  private String partNum;
	  private String product;
	  private String unit;
	  private Double unitPrice;
	  private Double poAmount;
	  
	
	// Constructors

	/** default constructor */
	public PurchasePoTo() {
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public Long getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(Long purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public Long getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Long receiveQty) {
		this.receiveQty = receiveQty;
	}

	public Long getNotReceiveQty() {
		return notReceiveQty;
	}

	public void setNotReceiveQty(Long notReceiveQty) {
		this.notReceiveQty = notReceiveQty;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
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

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getPoAmount() {
		return poAmount;
	}

	public void setPoAmount(Double poAmount) {
		this.poAmount = poAmount;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPoNum() {
		return poNum;
	}

	public void setPoNum(String poNum) {
		this.poNum = poNum;
	}



	
}