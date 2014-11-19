package com.cista.report.to;


/**
 * InWipCostTo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FgReceiveBinTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mg001;
	private String mg006;
	private Double fgGooddie;
	
	// Constructors

	/** default constructor */
	public FgReceiveBinTo() {
	}

	public String getMg001() {
		return mg001;
	}

	public void setMg001(String mg001) {
		this.mg001 = mg001;
	}

	public String getMg006() {
		return mg006;
	}

	public void setMg006(String mg006) {
		this.mg006 = mg006;
	}

	public Double getFgGooddie() {
		return fgGooddie;
	}

	public void setFgGooddie(Double fgGooddie) {
		this.fgGooddie = fgGooddie;
	}

	
}