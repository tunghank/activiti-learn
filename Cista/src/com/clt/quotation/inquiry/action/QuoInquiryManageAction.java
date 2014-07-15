package com.clt.quotation.inquiry.action;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.clt.quotation.compare.action.CheckCompareStatus;
import com.clt.quotation.config.dao.QuoPaperVerDao;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import com.clt.quotation.erp.dao.ErpDeliveryLocationDao;
import com.clt.quotation.erp.to.ErpDeliveryLocationTo;
import com.clt.quotation.inquiry.dao.InquiryHeaderDao;
import com.clt.quotation.inquiry.dao.InquirySupplierContactDao;
import com.clt.quotation.inquiry.to.InquiryConfirmTo;
import com.clt.quotation.inquiry.to.InquiryManageQueryTo;
import com.clt.quotation.inquiry.to.InquirySupplierContactTo;
import com.clt.quotation.quote.action.CheckQuoteStatus;
import com.clt.quotation.quote.dao.QuoteHeaderDao;
import com.clt.quotation.quote.to.QuoteHeaderTo;
import com.clt.system.util.BaseAction;

public class QuoInquiryManageAction extends BaseAction {
	
	public String quoInquiryManagePre() throws Exception {
		QuoPaperVerDao paperVerDao = new QuoPaperVerDao();
		List<QuoCatalogPaperVerTo> catalogPaperVerList = paperVerDao.getActivePaperList();
		catalogPaperVerList = null != catalogPaperVerList ? catalogPaperVerList : new ArrayList<QuoCatalogPaperVerTo>();
		
		NumberFormat nft = new DecimalFormat("###.#");
		
		for(int i=0;i < catalogPaperVerList.size(); i ++){
			QuoCatalogPaperVerTo catalogPaperVerTo = catalogPaperVerList.get(i);
			String paperVer = nft.format(Double.parseDouble(catalogPaperVerTo.getPaperVer()));
			catalogPaperVerTo.setPaperVer(paperVer);
			
			catalogPaperVerList.set(i, catalogPaperVerTo);
		}

		
		request.setAttribute("catalogPaperVerList", catalogPaperVerList);
		
		return SUCCESS;
	}

	public String ajaxQueryInquiryList() throws Exception {
		
		String inquiryNum = request.getParameter("inquiryNum");
		inquiryNum = null != inquiryNum ? inquiryNum : "";
		
		String inquiryScdt = request.getParameter("inquiryScdt");
		inquiryScdt = null != inquiryScdt ? inquiryScdt : "";
		
		String inquiryEcdt = request.getParameter("inquiryEcdt");
		inquiryEcdt = null != inquiryEcdt ? inquiryEcdt : "";
		
		String recoverStime = request.getParameter("recoverStime");
		recoverStime = null != recoverStime ? recoverStime : "";
		
		String recoverEtime = request.getParameter("recoverEtime");
		recoverEtime = null != recoverEtime ? recoverEtime : "";
		
		String inquiryPartNum = request.getParameter("inquiryPartNum");
		inquiryPartNum = null != inquiryPartNum ? inquiryPartNum : "";
		
		String inquirySupplierCode = request.getParameter("inquirySupplierCode");
		inquirySupplierCode = null != inquirySupplierCode ? inquirySupplierCode : "";
		
		String cltInquiryUser = request.getParameter("cltInquiryUser");
		cltInquiryUser = null != cltInquiryUser ? cltInquiryUser : "";
		

		InquiryManageQueryTo inquiryManageQueryTo = new InquiryManageQueryTo();
		inquiryManageQueryTo.setInquiryNum(inquiryNum);
		inquiryManageQueryTo.setInquiryScdt(inquiryScdt);
		inquiryManageQueryTo.setInquiryEcdt(inquiryEcdt);
		inquiryManageQueryTo.setRecoverStime(recoverStime);
		inquiryManageQueryTo.setRecoverEtime(recoverEtime);
		inquiryManageQueryTo.setInquiryPartNum(inquiryPartNum);
		inquiryManageQueryTo.setInquirySupplierCode(inquirySupplierCode);
		inquiryManageQueryTo.setCltInquiryUser(cltInquiryUser);
			
		InquiryHeaderDao inquiryHeaderDao = new InquiryHeaderDao();
		
		List<InquiryConfirmTo> inquiryConfirmList = inquiryHeaderDao.getInquiry(inquiryManageQueryTo);
		inquiryConfirmList = null != inquiryConfirmList ? inquiryConfirmList : new ArrayList<InquiryConfirmTo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//Change Format
		for(int i=0; i < inquiryConfirmList.size(); i ++){
			InquiryConfirmTo inquiryConfirmTo = inquiryConfirmList.get(i);
			
			String inquiryStatus = inquiryConfirmTo.getInquiryStatus();
			inquiryConfirmTo.setInquiryStatusDesc(CheckInquiryStatus.checkInquiryStatusDesc(inquiryStatus));
			
			String compareStatus = inquiryConfirmTo.getCompareStatus();
			inquiryConfirmTo.setCompareStatusDesc(CheckCompareStatus.checkCompareStatusDesc(compareStatus));
			
			String inquiryCdt = inquiryConfirmTo.getInquiryCdt();
			if(inquiryCdt != null ){
				inquiryCdt = sdf2.format( sdf.parse(inquiryCdt) ) ;
				inquiryConfirmTo.setInquiryCdt(inquiryCdt);
			}
			
			inquiryConfirmList.set(i, inquiryConfirmTo);
		}
		
		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		
		
		JSONArray jsonObject = JSONArray.fromObject(inquiryConfirmList);
		logger.debug("returnJosnString " + jsonObject.toString());
		
		sb.append(jsonObject.toString());
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}

	public String ajaxQueryQuoteList() throws Exception {
		
		String inquiryHeaderUid = request.getParameter("inquiryHeaderUid");
		inquiryHeaderUid = null != inquiryHeaderUid ? inquiryHeaderUid : "";
		logger.debug("inquiryHeaderUid " + inquiryHeaderUid);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		InquiryHeaderDao inquiryHeaderDao = new InquiryHeaderDao();
		QuoteHeaderDao quoteHeaderDao = new QuoteHeaderDao();
		List<InquiryConfirmTo> inquiryConfirmList = inquiryHeaderDao.getInquiryByKey(inquiryHeaderUid);
		ErpDeliveryLocationDao erpDeliveryLocationDao = new ErpDeliveryLocationDao();
		for(int i=0; i< inquiryConfirmList.size(); i ++){
			InquiryConfirmTo inquiryConfirmTo = inquiryConfirmList.get(i);
			
			//送貨地點
			String inquiryDeliveryLocation = inquiryConfirmTo.getInquiryDeliveryLocation();
			ErpDeliveryLocationTo erpDeliveryLocationTo = erpDeliveryLocationDao.getErpDeliveryLocation(inquiryDeliveryLocation);
			erpDeliveryLocationTo = null != erpDeliveryLocationTo ? erpDeliveryLocationTo : new ErpDeliveryLocationTo();
			String inquiryDeliveryLocationDesc = erpDeliveryLocationTo.getLocationCode();
			inquiryConfirmTo.setInquiryDeliveryLocationDesc(inquiryDeliveryLocationDesc);
			
			
			String inquiryPartNumUid = inquiryConfirmTo.getInquiryPartNumUid();
			String inquirySupplierUid = inquiryConfirmTo.getInquirySupplierUid();
			String inquiryCdt = inquiryConfirmTo.getInquiryCdt();
			inquiryCdt = inquiryCdt.substring(0,8);
			Date inquiryCdtDate = sdf.parse(inquiryCdt);
			inquiryCdt = sdf2.format(inquiryCdtDate);
			
			//Inquiry Supplier Contact 
			InquirySupplierContactDao supplierContactDao =  new InquirySupplierContactDao();
			InquirySupplierContactTo supplierContactTo = supplierContactDao.getSupplierContactBySupplierUid(inquirySupplierUid, inquiryHeaderUid);
			supplierContactTo = null != supplierContactTo ? supplierContactTo : new InquirySupplierContactTo();
			
			inquiryConfirmTo.setInquirySupplierContact(supplierContactTo.getInquirySupplierContact());
			
			QuoteHeaderTo quoteHeaderTo = quoteHeaderDao.getQuoteHeaderByInquiry(inquiryHeaderUid, inquiryPartNumUid, inquirySupplierUid);
			if( quoteHeaderTo!=null ){
				inquiryConfirmTo.setQuoteNum(quoteHeaderTo.getQuoteNum());
				inquiryConfirmTo.setQuoteHeaderUid(quoteHeaderTo.getQuoteHeaderUid());
				inquiryConfirmTo.setQuoteNotes(quoteHeaderTo.getQuoteNotes());
				inquiryConfirmTo.setQuoteStatus(quoteHeaderTo.getQuoteStatus());
				
				inquiryConfirmTo.setQuoteStatusDesc(CheckQuoteStatus.checkQuoteStatusDesc(quoteHeaderTo.getQuoteStatus()));
				
				inquiryConfirmTo.setInquiryCdt(inquiryCdt);
			}
			
			inquiryConfirmList.set(i, inquiryConfirmTo);
		}
		
		for(int i=0; i < inquiryConfirmList.size(); i ++){
			InquiryConfirmTo inquiryConfirmTo = inquiryConfirmList.get(i);
			String inquiryStatus = inquiryConfirmTo.getInquiryStatus();
			inquiryConfirmTo.setInquiryStatusDesc(CheckInquiryStatus.checkInquiryStatusDesc(inquiryStatus));
			
			String compareStatus = inquiryConfirmTo.getCompareStatus();
			inquiryConfirmTo.setCompareStatusDesc(CheckCompareStatus.checkCompareStatusDesc(compareStatus));
		}
		
		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		
		
		JSONArray jsonObject = JSONArray.fromObject(inquiryConfirmList);
		logger.debug("returnJosnString " + jsonObject.toString());
		
		sb.append(jsonObject.toString());
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}
	
	
}
