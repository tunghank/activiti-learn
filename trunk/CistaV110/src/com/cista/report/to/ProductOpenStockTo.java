package com.cista.report.to;

import java.util.Date;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProductOpenStockTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;
	private String project;
	private String process;
	private Double inAmount;
	private Double outAmount;
	private Date createDate;
	
	// Constructors

	/** default constructor */
	public ProductOpenStockTo() {
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public Double getInAmount() {
		return inAmount;
	}

	public void setInAmount(Double inAmount) {
		this.inAmount = inAmount;
	}

	public Double getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Double outAmount) {
		this.outAmount = outAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



}