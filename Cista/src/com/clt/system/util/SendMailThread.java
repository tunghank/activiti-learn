package com.clt.system.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import com.clt.system.util.CLTUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class SendMailThread extends Thread {
	
	protected final Log logger = LogFactory.getLog(getClass());
    String smtpSvr = "";
    String from = "";
    ArrayList inToList = new ArrayList();
    ArrayList inCcList = new ArrayList();
    ArrayList inBccList = new ArrayList();
    String inSubject = "";
    String inContents = "";


    public SendMailThread(String smtpSvr, String from, ArrayList inToList,
			ArrayList inCcList, ArrayList inBccList, String inSubject,
			String inContents ) {
		this.smtpSvr = smtpSvr;
		this.from = from;
		this.inToList = inToList;
		this.inCcList = inCcList;
		this.inBccList = inBccList;
		this.inSubject = inSubject;
		this.inContents = inContents;
	}
    
    public void run(){
        try{

            	CLTUtil.sendHTMLMail(this.smtpSvr, this.from,
                        this.inToList, this.inCcList,this.inBccList,
                        this.inSubject, this.inContents);
            	logger.debug("-----------Mail Send----------");       	

        }catch( MessagingException ex ){
        	logger.debug(ex.toString());
            ex.printStackTrace();
        }catch( Exception ex ){
        	logger.debug(ex.toString());
            ex.printStackTrace();
        }
	}
}
