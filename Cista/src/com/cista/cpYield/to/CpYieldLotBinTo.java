package com.cista.cpYield.to;

import java.util.Date;

public class CpYieldLotBinTo {
	
	private  String cpYieldBinUuid;
	private  String cpYieldUuid;
	private  String bin;
	private  Integer die;
	private  String percentage;


	public CpYieldLotBinTo() {

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


}
