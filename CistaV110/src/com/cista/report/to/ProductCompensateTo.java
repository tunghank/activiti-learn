package com.cista.report.to;

import java.util.Date;

/**
 * SysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProductCompensateTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;
	private String project;
	private String month;
	private Double compensation;
	private Date create_date;

	
	// Constructors

	/** default constructor */
	public ProductCompensateTo() {
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


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}




	public Date getCreate_date() {
		return create_date;
	}


	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}


	public Double getCompensation() {
		return compensation;
	}


	public void setCompensation(Double compensation) {
		this.compensation = compensation;
	}



}