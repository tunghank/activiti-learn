package com.clt.quotation.config.to;

import java.io.Serializable;
import java.util.List;

import com.clt.system.util.BaseObject;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;

public class QuoPaperTo extends BaseObject implements Serializable {

	// Fields
	private String catalog;
	private String paper;
	private String paperUid;
	private String paperRemark;
	
	private List<QuoCatalogPaperVerTo> versionList;
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public String getPaperRemark() {
		return paperRemark;
	}
	public void setPaperRemark(String paperRemark) {
		this.paperRemark = paperRemark;
	}
	public String getPaperUid() {
		return paperUid;
	}
	public void setPaperUid(String paperUid) {
		this.paperUid = paperUid;
	}
	public List<QuoCatalogPaperVerTo> getVersionList() {
		return versionList;
	}
	public void setVersionList(List<QuoCatalogPaperVerTo> versionList) {
		this.versionList = versionList;
	}

	
	
}
