package com.clt.quotation.config.to;

import java.io.Serializable;
import java.util.List;

import com.clt.quotation.config.to.QuoPaperGroupTo;
import com.clt.system.util.BaseObject;

public class QuoCatalogPaperVerTo extends BaseObject implements Serializable {

	// Fields
	private String catalog;
	private String paper;
	private String paperVer;
	private String paperVerApprove;
	private String verStartDt;
	private String verEndDt;
	private String udt;
	private String catalogUid;
	private String paperUid;
	private String paperVerUid;
	private String verCreateBy;
	private String cdt;
	private String verUpdateBy;
	private String verApproveDt;
	
	private List<QuoPaperGroupTo> groupList;
	
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getCatalogUid() {
		return catalogUid;
	}
	public void setCatalogUid(String catalogUid) {
		this.catalogUid = catalogUid;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public String getPaperVerUid() {
		return paperVerUid;
	}
	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}
	public String getPaperUid() {
		return paperUid;
	}
	public void setPaperUid(String paperUid) {
		this.paperUid = paperUid;
	}
	public String getPaperVer() {
		return paperVer;
	}
	public void setPaperVer(String paperVer) {
		this.paperVer = paperVer;
	}
	public String getPaperVerApprove() {
		return paperVerApprove;
	}
	public void setPaperVerApprove(String paperVerApprove) {
		this.paperVerApprove = paperVerApprove;
	}
	public String getVerCreateBy() {
		return verCreateBy;
	}
	public void setVerCreateBy(String verCreateBy) {
		this.verCreateBy = verCreateBy;
	}
	public String getCdt() {
		return cdt;
	}
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	public String getVerUpdateBy() {
		return verUpdateBy;
	}
	public void setVerUpdateBy(String verUpdateBy) {
		this.verUpdateBy = verUpdateBy;
	}
	public String getUdt() {
		return udt;
	}
	public void setUdt(String udt) {
		this.udt = udt;
	}
	public String getVerApproveDt() {
		return verApproveDt;
	}
	public void setVerApproveDt(String verApproveDt) {
		this.verApproveDt = verApproveDt;
	}
	public String getVerStartDt() {
		return verStartDt;
	}
	public void setVerStartDt(String verStartDt) {
		this.verStartDt = verStartDt;
	}
	public String getVerEndDt() {
		return verEndDt;
	}
	public void setVerEndDt(String verEndDt) {
		this.verEndDt = verEndDt;
	}
	
	public List<QuoPaperGroupTo> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<QuoPaperGroupTo> groupList) {
		this.groupList = groupList;
	}

	
	
}
