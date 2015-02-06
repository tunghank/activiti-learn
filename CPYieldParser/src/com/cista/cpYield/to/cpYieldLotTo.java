package com.cista.cpYield.to;

import java.util.Date;
import java.util.List;

import com.cista.cpYield.to.cpYieldLotBinTo;

public class cpYieldLotTo {
	
	private  String cpYieldUuid;
	private  Integer cpTestTimes;
	private  String cpLot;
	private  String waferId;
	private  String machineId;
	private  String xMaxCoor;
	private  String yMaxCoor;
	private  String flat;
	private  Integer passDie;
	private  Integer failDie;
	private  Integer totelDie;
	private  Date cdt;
	private  List<cpYieldLotBinTo> cpYieldLotBins;
	
	
	public cpYieldLotTo() {

	}

	public String getCpYieldUuid() {
		return cpYieldUuid;
	}

	public void setCpYieldUuid(String cpYieldUuid) {
		this.cpYieldUuid = cpYieldUuid;
	}

	public Integer getCpTestTimes() {
		return cpTestTimes;
	}

	public void setCpTestTimes(Integer cpTestTimes) {
		this.cpTestTimes = cpTestTimes;
	}

	public String getCpLot() {
		return cpLot;
	}

	public void setCpLot(String cpLot) {
		this.cpLot = cpLot;
	}

	public String getWaferId() {
		return waferId;
	}

	public void setWaferId(String waferId) {
		this.waferId = waferId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getxMaxCoor() {
		return xMaxCoor;
	}

	public void setxMaxCoor(String xMaxCoor) {
		this.xMaxCoor = xMaxCoor;
	}

	public String getyMaxCoor() {
		return yMaxCoor;
	}

	public void setyMaxCoor(String yMaxCoor) {
		this.yMaxCoor = yMaxCoor;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public Integer getPassDie() {
		return passDie;
	}

	public void setPassDie(Integer passDie) {
		this.passDie = passDie;
	}

	public Integer getFailDie() {
		return failDie;
	}

	public void setFailDie(Integer failDie) {
		this.failDie = failDie;
	}

	public Integer getTotelDie() {
		return totelDie;
	}

	public void setTotelDie(Integer totelDie) {
		this.totelDie = totelDie;
	}

	public Date getCdt() {
		return cdt;
	}

	public void setCdt(Date cdt) {
		this.cdt = cdt;
	}

	public List<cpYieldLotBinTo> getCpYieldLotBins() {
		return cpYieldLotBins;
	}

	public void setCpYieldLotBins(List<cpYieldLotBinTo> cpYieldLotBins) {
		this.cpYieldLotBins = cpYieldLotBins;
	}


}
