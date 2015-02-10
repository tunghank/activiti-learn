package com.cista.cpYield.to;

import java.util.Date;
import java.util.List;

import com.cista.cpYield.to.CpYieldLotBinTo;

public class CpYieldLotTo extends com.cista.system.util.BaseObject 
implements	java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private  String fileName;
	private  String fileMimeType;
	private  String ftpFlag;
	private  Date ftpSendTime;
	
	private  List<CpYieldLotBinTo> cpYieldLotBins;
	
	
	public CpYieldLotTo() {

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

	public List<CpYieldLotBinTo> getCpYieldLotBins() {
		return cpYieldLotBins;
	}

	public void setCpYieldLotBins(List<CpYieldLotBinTo> cpYieldLotBins) {
		this.cpYieldLotBins = cpYieldLotBins;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileMimeType() {
		return fileMimeType;
	}

	public void setFileMimeType(String fileMimeType) {
		this.fileMimeType = fileMimeType;
	}

	public String getFtpFlag() {
		return ftpFlag;
	}

	public void setFtpFlag(String ftpFlag) {
		this.ftpFlag = ftpFlag;
	}

	public Date getFtpSendTime() {
		return ftpSendTime;
	}

	public void setFtpSendTime(Date ftpSendTime) {
		this.ftpSendTime = ftpSendTime;
	}


}
