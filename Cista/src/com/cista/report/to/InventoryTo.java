package com.cista.report.to;

/**
 * SysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InventoryTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ma002;
	private String ma003;
	private String mb001;
	private String mb064;

	
	// Constructors

	/** default constructor */
	public InventoryTo() {
	}


	public String getMa002() {
		return ma002;
	}


	public void setMa002(String ma002) {
		this.ma002 = ma002;
	}


	public String getMa003() {
		return ma003;
	}


	public void setMa003(String ma003) {
		this.ma003 = ma003;
	}


	public String getMb001() {
		return mb001;
	}


	public void setMb001(String mb001) {
		this.mb001 = mb001;
	}


	public String getMb064() {
		return mb064;
	}


	public void setMb064(String mb064) {
		this.mb064 = mb064;
	}



}