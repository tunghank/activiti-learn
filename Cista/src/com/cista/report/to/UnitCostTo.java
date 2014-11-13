package com.cista.report.to;

import java.util.Date;

/**
 * SysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UnitCostTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;
	private String project;
	private double wafer;
	private double cp;
	private double cf;
	private double csp;
	private double ft;
	private Date createDate;

	
	// Constructors

	/** default constructor */
	public UnitCostTo() {
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


	public double getWafer() {
		return wafer;
	}


	public void setWafer(double wafer) {
		this.wafer = wafer;
	}


	public double getCp() {
		return cp;
	}


	public void setCp(double cp) {
		this.cp = cp;
	}


	public double getCf() {
		return cf;
	}


	public void setCf(double cf) {
		this.cf = cf;
	}


	public double getCsp() {
		return csp;
	}


	public void setCsp(double csp) {
		this.csp = csp;
	}


	public double getFt() {
		return ft;
	}


	public void setFt(double ft) {
		this.ft = ft;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}






}