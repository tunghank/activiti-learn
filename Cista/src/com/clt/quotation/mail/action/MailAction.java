package com.clt.quotation.mail.action;

import org.apache.struts2.ServletActionContext;

import com.clt.system.util.BaseAction;

public class MailAction extends BaseAction {
	
	private String quoteHeaderUid;
	
	
	public String quoteMailLink() throws Exception {
		
		// 1.0 Get Current session
		session = ServletActionContext.getRequest().getSession(true);
		// 1.1 Remove Session value.
		
		String quoteHeaderUid = this.getQuoteHeaderUid();
		quoteHeaderUid = null !=quoteHeaderUid ? quoteHeaderUid : "" ;
		
		logger.debug("Quote Mail " + quoteHeaderUid);	
        //1.1.0 Current user info
        // Current user info
		request.setAttribute("quoteHeaderUid", quoteHeaderUid);
	
		return SUCCESS;
	}


	public String getQuoteHeaderUid() {
		return quoteHeaderUid;
	}


	public void setQuoteHeaderUid(String quoteHeaderUid) {
		this.quoteHeaderUid = quoteHeaderUid;
	}



	
	
	
}
