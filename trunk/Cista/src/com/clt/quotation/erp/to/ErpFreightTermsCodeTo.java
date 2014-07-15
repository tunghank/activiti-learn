package com.clt.quotation.erp.to;

import java.io.Serializable;

import com.clt.system.util.BaseObject;

public class ErpFreightTermsCodeTo extends BaseObject implements Serializable {

	// Fields
 	private String lookupCode;
 	private String meaning;
	public String getLookupCode() {
		return lookupCode;
	}
	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}	

}
