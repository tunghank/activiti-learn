package com.clt.quotation.quote.action;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CheckQuoteStatus {
    protected final static Log logger = LogFactory.getLog(CheckQuoteStatus.class);
 
	//Check Status
	public static String checkQuoteStatusDesc(String status) {
		String statusDesc="";
		if( status == null ){
			statusDesc = "未報價";
		}else if( status.equals("S")){
			statusDesc="已報價";
		}
		return statusDesc;
	}
}
