package com.cista.report.to;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InWipCostTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ta001;
	private String product;
	private Double materialCost;
	private Double ngCost;

	
	// Constructors

	/** default constructor */
	public InWipCostTo() {
	}



	public String getTa001() {
		return ta001;
	}



	public void setTa001(String ta001) {
		this.ta001 = ta001;
	}



	public String getProduct() {
		return product;
	}



	public void setProduct(String product) {
		this.product = product;
	}



	public Double getMaterialCost() {
		return materialCost;
	}



	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}



	public Double getNgCost() {
		return ngCost;
	}



	public void setNgCost(Double ngCost) {
		this.ngCost = ngCost;
	}


}