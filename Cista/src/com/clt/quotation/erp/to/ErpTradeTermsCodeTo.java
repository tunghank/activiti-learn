package com.clt.quotation.erp.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;

public class ErpTradeTermsCodeTo extends BaseObject implements Serializable {

	// Fields
 	private String id;
 	private String description;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
