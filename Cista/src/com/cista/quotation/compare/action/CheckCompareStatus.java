package com.cista.quotation.compare.action;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CheckCompareStatus {
    protected final static Log logger = LogFactory.getLog(CheckCompareStatus.class);
 
	//Check Status
	public static String checkCompareStatusDesc(String status) {
		String statusDesc="";
		if( status == null ){
			statusDesc ="";
		}else if( status.equals("F")){
			statusDesc="比價完成";
		}
		return statusDesc;
	}
}
