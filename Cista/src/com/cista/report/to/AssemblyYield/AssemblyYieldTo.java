package com.cista.report.to.AssemblyYield;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AssemblyYieldTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String product;
	private Long grossDie;
	private Long issueQty;
	private String unit;
	private Long issueDieQty;
	private Long receiveDieQty;
	private Double assemblyYield;
	private String strAssemblyYield;	
	// Constructors

	/** default constructor */
	public AssemblyYieldTo() {
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




	public Long getIssueQty() {
		return issueQty;
	}




	public void setIssueQty(Long issueQty) {
		this.issueQty = issueQty;
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




	public Double getAssemblyYield() {
		return assemblyYield;
	}




	public void setAssemblyYield(Double assemblyYield) {
		this.assemblyYield = assemblyYield;
	}




	public String getStrAssemblyYield() {
		return strAssemblyYield;
	}




	public void setStrAssemblyYield(String strAssemblyYield) {
		this.strAssemblyYield = strAssemblyYield;
	}



}