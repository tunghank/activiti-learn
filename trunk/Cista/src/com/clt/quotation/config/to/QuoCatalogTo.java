package com.clt.quotation.config.to;

import java.io.Serializable;
import com.clt.system.util.BaseObject;

public class QuoCatalogTo extends BaseObject implements Serializable {

	// Fields
	private String catalogUid;
	private String catalog;
	
	public String getCatalogUid() {
		return catalogUid;
	}
	public void setCatalogUid(String catalogUid) {
		this.catalogUid = catalogUid;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	
	
}
