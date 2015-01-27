package com.cista.report.to.AssemblyYield;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AssemblyYieldIssueTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String issueSheet;
	private String po;
	private String partNum;
	private String product;
	private String sepc;
	private Long grossDie;
	private Long issueQty;
	private Long issueDieQty;
	private String unit;
	private String lot;
	
	
	
	
	// Constructors

	/** default constructor */
	public AssemblyYieldIssueTo() {
	}




	public String getIssueSheet() {
		return issueSheet;
	}




	public void setIssueSheet(String issueSheet) {
		this.issueSheet = issueSheet;
	}




	public String getPo() {
		return po;
	}




	public void setPo(String po) {
		this.po = po;
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




	public String getSepc() {
		return sepc;
	}




	public void setSepc(String sepc) {
		this.sepc = sepc;
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




	public String getLot() {
		return lot;
	}




	public void setLot(String lot) {
		this.lot = lot;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public Long getGrossDie() {
		return grossDie;
	}




	public void setGrossDie(Long grossDie) {
		this.grossDie = grossDie;
	}




	public Long getIssueDieQty() {
		return issueDieQty;
	}




	public void setIssueDieQty(Long issueDieQty) {
		this.issueDieQty = issueDieQty;
	}



	
}