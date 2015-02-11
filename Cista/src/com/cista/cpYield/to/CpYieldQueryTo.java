package com.cista.cpYield.to;


public class CpYieldQueryTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String start;
	private  String limit;
	private  String lot;
	private  String sCdt;
	private  String ftpFlag;
	private  String sFtpSendTime;

	
	public CpYieldQueryTo() {

	}


	public String getsCdt() {
		return sCdt;
	}


	public void setsCdt(String sCdt) {
		this.sCdt = sCdt;
	}


	public String getFtpFlag() {
		return ftpFlag;
	}


	public void setFtpFlag(String ftpFlag) {
		this.ftpFlag = ftpFlag;
	}


	public String getsFtpSendTime() {
		return sFtpSendTime;
	}


	public void setsFtpSendTime(String sFtpSendTime) {
		this.sFtpSendTime = sFtpSendTime;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getLimit() {
		return limit;
	}


	public void setLimit(String limit) {
		this.limit = limit;
	}





	public String getLot() {
		return lot;
	}





	public void setLot(String lot) {
		this.lot = lot;
	}


}
