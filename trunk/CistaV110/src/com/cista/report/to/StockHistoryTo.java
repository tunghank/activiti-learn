package com.cista.report.to;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockHistoryTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mb001;
	private String mb002;
	private String mb003;
	private String mb004; 
	private String la005;
	private Double outCost;
	
	// Constructors

	/** default constructor */
	public StockHistoryTo() {
	}

	public String getMb001() {
		return mb001;
	}

	public void setMb001(String mb001) {
		this.mb001 = mb001;
	}

	public String getMb002() {
		return mb002;
	}

	public void setMb002(String mb002) {
		this.mb002 = mb002;
	}

	public String getMb003() {
		return mb003;
	}

	public void setMb003(String mb003) {
		this.mb003 = mb003;
	}

	public String getMb004() {
		return mb004;
	}

	public void setMb004(String mb004) {
		this.mb004 = mb004;
	}

	public String getLa005() {
		return la005;
	}

	public void setLa005(String la005) {
		this.la005 = la005;
	}

	public Double getOutCost() {
		return outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}


}