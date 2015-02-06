package com.cista.cpYield.to;

import java.util.Date;

public class cpYieldLotBinTo {
	
	private  String cpYieldBinUuid;
	private  String cpYieldUuid;
	private  String bin;
	private  Integer die;
	private  String percentage;
	private  Date cdt;

	public cpYieldLotBinTo() {

	}

	public String getCpYieldBinUuid() {
		return cpYieldBinUuid;
	}

	public void setCpYieldBinUuid(String cpYieldBinUuid) {
		this.cpYieldBinUuid = cpYieldBinUuid;
	}

	public String getCpYieldUuid() {
		return cpYieldUuid;
	}

	public void setCpYieldUuid(String cpYieldUuid) {
		this.cpYieldUuid = cpYieldUuid;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public Integer getDie() {
		return die;
	}

	public void setDie(Integer die) {
		this.die = die;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public Date getCdt() {
		return cdt;
	}

	public void setCdt(Date cdt) {
		this.cdt = cdt;
	}

	
	
}
