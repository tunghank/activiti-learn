package com.cista.report.to.FTYield;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FTYieldTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String product;
	private Long grossDie;
	private String unit;
	private Long issueDieQty;
	private Long receiveDieQty;
	private Double ftYield;
	private String strFtYield;	
	// Constructors

	/** default constructor */
	public FTYieldTo() {
	}




	public String getProduct() {
		return product;
	}




	public void setProduct(String product) {
		this.product = product;
	}




	public Long getGrossDie() {
		return grossDie;
	}




	public void setGrossDie(Long grossDie) {
		this.grossDie = grossDie;
	}



	public String getUnit() {
		return unit;
	}




	public void setUnit(String unit) {
		this.unit = unit;
	}




	public Long getIssueDieQty() {
		return issueDieQty;
	}




	public void setIssueDieQty(Long issueDieQty) {
		this.issueDieQty = issueDieQty;
	}




	public Long getReceiveDieQty() {
		return receiveDieQty;
	}




	public void setReceiveDieQty(Long receiveDieQty) {
		this.receiveDieQty = receiveDieQty;
	}




	public Double getFtYield() {
		return ftYield;
	}




	public void setFtYield(Double ftYield) {
		this.ftYield = ftYield;
	}




	public String getStrFtYield() {
		return strFtYield;
	}




	public void setStrFtYield(String strFtYield) {
		this.strFtYield = strFtYield;
	}






}