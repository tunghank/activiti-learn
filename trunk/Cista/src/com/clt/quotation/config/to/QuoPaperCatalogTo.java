package com.clt.quotation.config.to;

import java.io.Serializable;
import com.clt.system.util.BaseObject;

public class QuoPaperCatalogTo extends BaseObject implements Serializable {

	// Fields
	private String paperCatalogUid;
	private String catalogUid;
	private String paperUid;
	
	public String getCatalogUid() {
		return catalogUid;
	}
	public void setCatalogUid(String catalogUid) {
		this.catalogUid = catalogUid;
	}
	public String getPaperCatalogUid() {
		return paperCatalogUid;
	}
	public void setPaperCatalogUid(String paperCatalogUid) {
		this.paperCatalogUid = paperCatalogUid;
	}
	public String getPaperUid() {
		return paperUid;
	}
	public void setPaperUid(String paperUid) {
		this.paperUid = paperUid;
	}


	
	
}
