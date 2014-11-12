package com.cista.report.to;

import java.util.Date;

/**
 * SysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StandardCostTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;
	private String project;
	private float grossDie;
	private float waferCost;
	private float cpCost;
	private float cpYield;
	private float cfCost;
	private float cspCost;
	private float cspYield;
	private float cspDie;
	private float ftUnitCost;
	private float ftFee;
	private float ftYield;
	private float totalCost;
	private float goodPart;
	private float unitCost;
	private Date createDate;
	private String creator;
	private String updateBy;
	private Date updateDate;

	
	// Constructors

	/** default constructor */
	public StandardCostTo() {
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


	public float getGrossDie() {
		return grossDie;
	}


	public void setGrossDie(float grossDie) {
		this.grossDie = grossDie;
	}


	public float getWaferCost() {
		return waferCost;
	}


	public void setWaferCost(float waferCost) {
		this.waferCost = waferCost;
	}


	public float getCpCost() {
		return cpCost;
	}


	public void setCpCost(float cpCost) {
		this.cpCost = cpCost;
	}


	public float getCpYield() {
		return cpYield;
	}


	public void setCpYield(float cpYield) {
		this.cpYield = cpYield;
	}


	public float getCfCost() {
		return cfCost;
	}


	public void setCfCost(float cfCost) {
		this.cfCost = cfCost;
	}


	public float getCspCost() {
		return cspCost;
	}


	public void setCspCost(float cspCost) {
		this.cspCost = cspCost;
	}


	public float getCspYield() {
		return cspYield;
	}


	public void setCspYield(float cspYield) {
		this.cspYield = cspYield;
	}


	public float getCspDie() {
		return cspDie;
	}


	public void setCspDie(float cspDie) {
		this.cspDie = cspDie;
	}


	public float getFtUnitCost() {
		return ftUnitCost;
	}


	public void setFtUnitCost(float ftUnitCost) {
		this.ftUnitCost = ftUnitCost;
	}


	public float getFtFee() {
		return ftFee;
	}


	public void setFtFee(float ftFee) {
		this.ftFee = ftFee;
	}


	public float getFtYield() {
		return ftYield;
	}


	public void setFtYield(float ftYield) {
		this.ftYield = ftYield;
	}


	public float getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}


	public float getGoodPart() {
		return goodPart;
	}


	public void setGoodPart(float goodPart) {
		this.goodPart = goodPart;
	}


	public float getUnitCost() {
		return unitCost;
	}


	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



}