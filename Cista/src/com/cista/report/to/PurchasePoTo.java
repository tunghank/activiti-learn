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



	
}