package com.clt.quotation.inquiry.action;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CheckInquiryStatus {
    protected final static Log logger = LogFactory.getLog(CheckInquiryStatus.class);
 
	//Check Status
	public static String checkInquiryStatusDesc(String status) {
		String statusDesc="";
		if( status == null ){
			statusDesc ="";
		}else if( status.equals("T")){
			statusDesc="暫存";
		}else if( status.equals("F")){
			statusDesc="詢價完成";
		}
		return statusDesc;
	}
}
