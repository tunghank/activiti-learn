package com.clt.quotation.wfInterface.to;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.clt.system.util.BaseObject;


public class PQSWfInterfaceHTo extends BaseObject implements Serializable , Cloneable {

	private String pqsNo;
	private String orgSiteId;
	private String orgOuId;
	private String fillInUserId;
	private String wfNo;
	private String wfStatus;
	private Date wfUpdateDatetime;
	private String wfInsid;
	
	public String getPqsNo() {
		return pqsNo;
	}
	public void setPqsNo(String pqsNo) {
		this.pqsNo = pqsNo;
	}
	public String getOrgSiteId() {
		return orgSiteId;
	}
	public void setOrgSiteId(String orgSiteId) {
		this.orgSiteId = orgSiteId;
	}
	public String getOrgOuId() {
		return orgOuId;
	}
	public void setOrgOuId(String orgOuId) {
		this.orgOuId = orgOuId;
	}
	public String getFillInUserId() {
		return fillInUserId;
	}
	public void setFillInUserId(String fillInUserId) {
		this.fillInUserId = fillInUserId;
	}
	public String getWfNo() {
		return wfNo;
	}
	public void setWfNo(String wfNo) {
		this.wfNo = wfNo;
	}
	public String getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	public Date getWfUpdateDatetime() {
		return wfUpdateDatetime;
	}
	public void setWfUpdateDatetime(Date wfUpdateDatetime) {
		this.wfUpdateDatetime = wfUpdateDatetime;
	}
	public String getWfInsid() {
		return wfInsid;
	}
	public void setWfInsid(String wfInsid) {
		this.wfInsid = wfInsid;
	}

	
}
