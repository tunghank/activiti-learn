package com.cista.report.to;

import java.util.Date;

/**
 * SysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProductYieldTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;
	private String project;
	private Double waferYield;
	private Double cpYield;
	private Double cfYield;
	private Double cspYield;
	private Double ftYield;
	private Date createDate;

	
	// Constructors

	/** default constructor */
	public ProductYieldTo() {
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


	public Double getWaferYield() {
		return waferYield;
	}


	public void setWaferYield(Double waferYield) {
		this.waferYield = waferYield;
	}


	public Double getCpYield() {
		return cpYield;
	}


	public void setCpYield(Double cpYield) {
		this.cpYield = cpYield;
	}


	public Double getCfYield() {
		return cfYield;
	}


	public void setCfYield(Double cfYield) {
		this.cfYield = cfYield;
	}


	public Double getCspYield() {
		return cspYield;
	}


	public void setCspYield(Double cspYield) {
		this.cspYield = cspYield;
	}


	public Double getFtYield() {
		return ftYield;
	}


	public void setFtYield(Double ftYield) {
		this.ftYield = ftYield;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



}