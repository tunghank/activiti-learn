package com.clt.quotation.quote.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.clt.quotation.config.dao.QuoPaperFieldDao;
import com.clt.quotation.config.dao.QuoPaperGroupDao;
import com.clt.quotation.config.dao.QuoPaperVerDao;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import com.clt.quotation.config.to.QuoPaperFieldTo;
import com.clt.quotation.config.to.QuoPaperGroupTo;
import com.clt.quotation.erp.dao.ErpDeliveryLocationDao;
import com.clt.quotation.erp.dao.ErpTaxDao;
import com.clt.quotation.erp.to.ErpDeliveryLocationTo;
import com.clt.quotation.erp.to.ErpTaxTo;
import com.clt.quotation.inquiry.dao.InquiryHeaderDao;
import com.clt.quotation.inquiry.dao.InquiryPartNumDao;
import com.clt.quotation.inquiry.dao.InquirySupplierDao;
import com.clt.quotation.inquiry.to.InquiryHeaderTo;
import com.clt.quotation.inquiry.to.InquirySupplierTo;
import com.clt.quotation.quote.dao.QuoteHeaderDao;
import com.clt.quotation.quote.dao.QuoteRecordDao;
import com.clt.quotation.quote.dao.QuoteRecordTotalDao;
import com.clt.quotation.quote.to.QuoteHeaderTo;
import com.clt.quotation.quote.to.QuoteRecordTo;
import com.clt.quotation.quote.to.QuoteRecordTotalTo;
import com.clt.system.account.dao.UserDao;
import com.clt.system.to.SysUserTo;
import com.clt.system.util.BaseAction;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.JsonUtil;

public class QuoPaperQuoteQueryAction extends BaseAction {
	
	public String quoPaperQuoteQueryPre() throws Exception {
		
		return this.SUCCESS;
	}
}
