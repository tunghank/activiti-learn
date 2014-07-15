package com.clt.quotation.inquiry.action;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.clt.quotation.config.dao.QuoPaperVerDao;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import com.clt.quotation.erp.dao.ErpASLDao;
import com.clt.quotation.erp.dao.ErpBOMDao;
import com.clt.quotation.erp.dao.ErpCurrencyDao;
import com.clt.quotation.erp.dao.ErpDeliveryLocationDao;
import com.clt.quotation.erp.dao.ErpFreightTermsCodeDao;
import com.clt.quotation.erp.dao.ErpPartNumDao;
import com.clt.quotation.erp.dao.ErpTradeTermsCodeDao;
import com.clt.quotation.erp.dao.ErpVendorDao;
import com.clt.quotation.erp.to.ErpASLTo;
import com.clt.quotation.erp.to.ErpBOMTo;
import com.clt.quotation.erp.to.ErpCurrencyTo;
import com.clt.quotation.erp.to.ErpDeliveryLocationTo;
import com.clt.quotation.erp.to.ErpFreightTermsCodeTo;
import com.clt.quotation.erp.to.ErpPartNumTo;
import com.clt.quotation.erp.to.ErpTradeTermsCodeTo;
import com.clt.quotation.erp.to.ErpVendorTo;
import com.clt.quotation.erp.to.PartNumElementTo;
import com.clt.quotation.inquiry.dao.InquiryHeaderDao;
import com.clt.quotation.inquiry.dao.InquiryInformDao;
import com.clt.quotation.inquiry.dao.InquiryPartNumDao;
import com.clt.quotation.inquiry.dao.InquirySeqnumDao;
import com.clt.quotation.inquiry.dao.InquirySupplierContactDao;
import com.clt.quotation.inquiry.dao.InquirySupplierDao;
import com.clt.quotation.inquiry.to.InquiryConfirmTo;
import com.clt.quotation.inquiry.to.InquiryHeaderTo;
import com.clt.quotation.inquiry.to.InquiryManageQueryTo;
import com.clt.quotation.inquiry.to.InquiryPartNumTo;
import com.clt.quotation.inquiry.to.InquirySupplierContactTo;
import com.clt.quotation.inquiry.to.InquirySupplierTo;
import com.clt.quotation.mail.MailContents;
import com.clt.quotation.quote.dao.QuoteHeaderDao;
import com.clt.quotation.quote.dao.QuoteSeqnumDao;
import com.clt.quotation.quote.to.QuoteHeaderTo;
import com.clt.system.sys.dao.SysFunParameterDao;
import com.clt.system.to.SysFunParameterTo;
import com.clt.system.to.SysUserTo;
import com.clt.system.util.BaseAction;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.JsonUtil;

public class QuoPaperInquiryAction extends BaseAction {
	
	private String paperVerUid;
	private String partNumGridData;
	private String inquiryGridData;
	private String inquiryStatus;
	private String inquiryNum;
	
	public String quoInquiryPartNumPre() throws Exception {
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
	
	public String instQuoInquiryPartNum() throws Exception {
		
		String inquiryKeyPartNum = request.getParameter("inquiryKeyPartNum");
		inquiryKeyPartNum = null != inquiryKeyPartNum ? inquiryKeyPartNum : "";
		logger.debug("inquiryKeyPartNum  " + inquiryKeyPartNum);
		inquiryKeyPartNum = inquiryKeyPartNum.trim()+",";
		String inquiryKeyPartNumArray[]= inquiryKeyPartNum.split(",");
		
		String partNumFormQty = request.getParameter("partNumFormQty");
		//partNumFormQty = null != partNumFormQty ? partNumFormQty : "0";
		logger.debug("partNumFormQty " + partNumFormQty);
		

		
		/********************************************************************
		 * 	1. 拿料號找出BOM.
		 *  	1.1.0  如果沒找到, 判定此料為替代料.
		 *  	1.1.1  拿替代料找出主料.
		 *  	1.1.2 再展出 所有的替代料
		 *  
		 *  	1.2.0  找到, 判定為主料.
		 *  	1.2.1 拿主料找替代料
		 *
		 ********************************************************************/
		
		ErpBOMDao erpBOMDao = new ErpBOMDao();
		ErpPartNumDao erpPartNumDao = new ErpPartNumDao();
		
		List<InquiryPartNumTo> inquiryPartNumList = new ArrayList<InquiryPartNumTo>();
		
		String inquiryPartNum = "";
		
		for(int k=0; k<inquiryKeyPartNumArray.length; k++ ){
			inquiryPartNum = "";
			inquiryPartNum = inquiryKeyPartNumArray[k];
			logger.debug("inquiryPartNum " + inquiryPartNum);
			//Query Part Num 
			ErpPartNumTo erpPartNumTo = erpPartNumDao.getPartNum(inquiryPartNum);
			
			if( erpPartNumTo != null ){
			
				List<ErpBOMTo> erpBOMList = erpBOMDao.getBOMbyItem(inquiryPartNum);
				List<PartNumElementTo> partNumList;
				if( erpBOMList == null ){
					logger.debug("erpBOMList null " );
					//1.1.0  如果沒找到, 判定此料為替代料.
					//1.1.1  拿替代料找出主料.
					List<PartNumElementTo> mainPartNumList = erpBOMDao.getMainByAltermate(inquiryPartNum);
					if( mainPartNumList != null ){
						//主料
						PartNumElementTo mainPartNumTo = mainPartNumList.get(0);
						partNumList = erpBOMDao.getAltermateByMain(mainPartNumTo.getPartNum());
						partNumList = null != partNumList ? partNumList : new ArrayList<PartNumElementTo>();
						for(int i=0; i < partNumList.size(); i ++){
							PartNumElementTo partNumTo = partNumList.get(i);
							InquiryHeaderTo inquiryHeaderTo = new InquiryHeaderTo();
							InquiryPartNumTo inquiryPartNumTo = new InquiryPartNumTo();
							//詢價料號
							
							inquiryPartNumTo.setInquiryPartNumDiffer("替");
							inquiryPartNumTo.setInquiryKeyPartNum(inquiryPartNum);
							inquiryPartNumTo.setInquiryPartNum(partNumTo.getPartNum());
							inquiryPartNumTo.setInquiryPartNumDesc(partNumTo.getPartNumDesc());
							//只要把詢價料號帶入必要資訊
							if(inquiryPartNum.equals(partNumTo.getPartNum()) ){
								inquiryPartNumTo.setInquiryQty( partNumFormQty );
								inquiryPartNumTo.setInquiryUnit(erpPartNumTo.getPartNumUnit());
							}
							inquiryPartNumList.add(inquiryPartNumTo);
						}
						
						//Set 主料資訊
						InquiryPartNumTo inquiryPartNumTo = new InquiryPartNumTo();
						//詢價料號
		
						inquiryPartNumTo.setInquiryPartNumDiffer("主");
						inquiryPartNumTo.setInquiryKeyPartNum(inquiryPartNum);
						inquiryPartNumTo.setInquiryPartNum(mainPartNumTo.getPartNum());
						inquiryPartNumTo.setInquiryPartNumDesc(mainPartNumTo.getPartNumDesc());
						
						//只要把詢價料號帶入必要資訊
						inquiryPartNumList.add(inquiryPartNumTo);
					}else{
						logger.debug("NO BOM . NO 主料");
						ErpPartNumTo partNumTo = erpPartNumDao.getPartNum(inquiryPartNum);
						//Set 主料資訊
						InquiryPartNumTo inquiryPartNumTo = new InquiryPartNumTo();
						//詢價料號
		
						inquiryPartNumTo.setInquiryPartNumDiffer("主");
						inquiryPartNumTo.setInquiryKeyPartNum(inquiryPartNum);
						inquiryPartNumTo.setInquiryPartNum(inquiryPartNum);
						inquiryPartNumTo.setInquiryPartNumDesc(partNumTo.getPartNumDesc());
						inquiryPartNumTo.setInquiryUnit(partNumTo.getPartNumUnit());
						//只要把詢價料號帶入必要資訊
						inquiryPartNumList.add(inquiryPartNumTo);
					}
					
				}else{
					logger.debug("erpBOMList !null " );
					//1.2.0 找到, 判定為主料.
					//1.2.1 拿主料找替代料
					partNumList = erpBOMDao.getAltermateByMain(inquiryPartNum);
					partNumList = null != partNumList ? partNumList : new ArrayList<PartNumElementTo>();
					
					for(int i=0; i < partNumList.size(); i ++){
						PartNumElementTo partNumTo = partNumList.get(i);
						InquiryHeaderTo inquiryHeaderTo = new InquiryHeaderTo();
						InquiryPartNumTo inquiryPartNumTo = new InquiryPartNumTo();
						//Get 機種
						logger.debug("替 " + partNumTo.getPartNum());
						
						//詢價料號
						inquiryPartNumTo.setInquiryPartNumDiffer("替");
						inquiryPartNumTo.setInquiryKeyPartNum(inquiryPartNum);
						inquiryPartNumTo.setInquiryPartNum(partNumTo.getPartNum());
						inquiryPartNumTo.setInquiryPartNumDesc(partNumTo.getPartNumDesc());
									
						inquiryPartNumList.add(inquiryPartNumTo);
					}
					logger.debug("inquiryPartNumList Size : " + inquiryPartNumList.size());
					//Set 主料資訊
					InquiryPartNumTo inquiryPartNumTo = new InquiryPartNumTo();
					//詢價料號
					
					inquiryPartNumTo.setInquiryPartNumDiffer("主");
					inquiryPartNumTo.setInquiryKeyPartNum(inquiryPartNum);
					inquiryPartNumTo.setInquiryPartNum(inquiryPartNum);
					inquiryPartNumTo.setInquiryPartNumDesc(erpPartNumTo.getPartNumDesc());
					
		
					inquiryPartNumTo.setInquiryQty( partNumFormQty );
					inquiryPartNumTo.setInquiryUnit(erpPartNumTo.getPartNumUnit());
					inquiryPartNumList.add(inquiryPartNumTo);
				}
			}
		}
		logger.debug("inquiryPartNumList Size : " + inquiryPartNumList.size());

		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();

		JSONArray jsonObject = JSONArray.fromObject(inquiryPartNumList);
		sb.append(jsonObject.toString());
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;

	}
	
	public String saveInquiryPartNum() throws Exception {

		ErpCurrencyDao erpCurrencyDao = new ErpCurrencyDao();
		ErpFreightTermsCodeDao erpFreightTermsCodeDao = new ErpFreightTermsCodeDao();
		ErpTradeTermsCodeDao erpTradeTermsCodeDao = new ErpTradeTermsCodeDao();
		
		SysFunParameterDao funParameterDao = new SysFunParameterDao();
		
		String paperVerUid = this.paperVerUid;
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid  " + paperVerUid);
		
		//String partNumGrid = request.getParameter("partNumGrid");
		String partNumGrid = this.partNumGridData;
		partNumGrid = null != partNumGrid ? partNumGrid : "";
		logger.debug("partNumGrid  " + partNumGrid);
		
		
		List<ErpCurrencyTo> waersList = erpCurrencyDao.getErpCurrency();
		List<ErpFreightTermsCodeTo> shippedByList = erpFreightTermsCodeDao.getAllFreightTermsCode();
		List<ErpTradeTermsCodeTo> termsTransactionList = erpTradeTermsCodeDao.getAllTradeTermsCode();
		List<SysFunParameterTo> deliveryLocationList = funParameterDao.getParameterList("INQUIRY", "DELIVERY_LOCATION");
		
		ErpPartNumDao erpPartNumDao = new ErpPartNumDao();
		ErpASLDao erpASLDao = new ErpASLDao();
		ErpVendorDao erpVendorDao =  new ErpVendorDao();
		
		//抓出ASL Vendor
		final List<InquiryPartNumTo> inquiryPartNumList= JsonUtil.getList4Json(partNumGrid , InquiryPartNumTo.class);
		List<InquiryConfirmTo> inquirySupplierList = new ArrayList<InquiryConfirmTo>();
		for(int i=0; i < inquiryPartNumList.size(); i ++ ){
			InquiryPartNumTo partNumTo = inquiryPartNumList.get(i);
			ErpPartNumTo erpPartNumTo = erpPartNumDao.getPartNum(partNumTo.getInquiryPartNum());
			List<ErpASLTo> erpASLList = erpASLDao.getASLVendorList(erpPartNumTo.getInventoryItemId());
			if( erpASLList != null ){
				
				for(int j=0; j < erpASLList.size(); j++){
					InquiryConfirmTo inquiryConfirmTo = new InquiryConfirmTo();
					ErpASLTo erpASLTo = erpASLList.get(j);
					ErpVendorTo erpVendorTo = erpVendorDao.getErpVendor(erpASLTo.getVendorId());
					if( erpVendorTo != null ){
						inquiryConfirmTo.setInquirySupplierCode(erpVendorTo.getSupplierCode());
						inquiryConfirmTo.setInquirySupplierName(erpVendorTo.getSupplierName());
						inquiryConfirmTo.setInquirySupplierSite(erpVendorTo.getSupplierSite());
						inquiryConfirmTo.setInquirySupplierSiteCode(erpVendorTo.getSupplierSiteCode());
						logger.debug("Vendor Site " + erpVendorTo.getSupplierSite() + "  " + erpVendorTo.getSupplierSiteCode());
						inquiryConfirmTo.setInquirySupplierContact(erpVendorTo.getContact());
						inquiryConfirmTo.setInquirySupplierEmail(erpVendorTo.getContactEmail());
						inquiryConfirmTo.setInquiryCurrency(erpVendorTo.getPaymentCurrency());
						
						inquirySupplierList.add(inquiryConfirmTo);
					}
				}
				
			}
		}
		
		inquirySupplierList = CLTUtil.removeDuplicateObj(inquirySupplierList);
		logger.debug("ASL List " + inquirySupplierList.size() );
		StringBuilder returnASL = new StringBuilder();
		JSONArray jsonObject = JSONArray.fromObject(inquirySupplierList);
		logger.debug("returnJosnString " + jsonObject.toString());
		returnASL.append(jsonObject.toString());
		
		request.setAttribute("returnASL", returnASL);
		
		
		request.setAttribute("partNumGrid", partNumGrid);
		request.setAttribute("paperVerUid", paperVerUid);

		request.setAttribute("waersList", waersList);
		request.setAttribute("shippedByList", shippedByList);
		request.setAttribute("termsTransactionList", termsTransactionList);
		request.setAttribute("deliveryLocationList", deliveryLocationList);
		
		return SUCCESS;
	}
	
	public String saveInquirySupplier() throws Exception {

		ErpCurrencyDao erpCurrencyDao = new ErpCurrencyDao();
		ErpFreightTermsCodeDao erpFreightTermsCodeDao = new ErpFreightTermsCodeDao();
		ErpTradeTermsCodeDao erpTradeTermsCodeDao = new ErpTradeTermsCodeDao();
		SysFunParameterDao funParameterDao = new SysFunParameterDao();
		
		String paperVerUid = this.paperVerUid;
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid  " + paperVerUid);
		
		String partNumGrid = this.partNumGridData;
		partNumGrid = null != partNumGrid ? partNumGrid : "";
		logger.debug("partNumGrid  " + partNumGrid);

		
		String supplierGrid = request.getParameter("supplierGrid");
		supplierGrid = null != supplierGrid ? supplierGrid : "";
		logger.debug("supplierGrid  " + supplierGrid);
		
		String recoverDate = request.getParameter("recoverDate");
		recoverDate = null != recoverDate ? recoverDate : "";
		logger.debug("recoverDate  " + recoverDate);
		
		String recoverTime = request.getParameter("recoverTime");
		recoverTime = null != recoverTime ? recoverTime : "";
		logger.debug("recoverTime  " + recoverTime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/ddHH:mm");
		Date recoverDateTime = sdf.parse(recoverDate + recoverTime);
			
		
		final List<InquiryConfirmTo> inquiryConfirmList = new ArrayList<InquiryConfirmTo>();
		
		final List<InquiryPartNumTo> inquiryPartNumList= JsonUtil.getList4Json(partNumGrid , InquiryPartNumTo.class);
		final List<InquirySupplierTo> inquirySupplierList= JsonUtil.getList4Json(supplierGrid , InquirySupplierTo.class);
		final List<InquirySupplierContactTo> inquirySupplierContactList= JsonUtil.getList4Json(supplierGrid , InquirySupplierContactTo.class);
		
		logger.debug("inquiryHeaderList.size() " + inquiryPartNumList.size());
		logger.debug("inquirySupplierList.size() " + inquirySupplierList.size());
		logger.debug(inquirySupplierList.toString());
		
		QuoPaperVerDao paperVerDao = new QuoPaperVerDao();
		List<QuoCatalogPaperVerTo> catalogPaperVerList = paperVerDao.getActivePaperList();
		catalogPaperVerList = null != catalogPaperVerList ? catalogPaperVerList : new ArrayList<QuoCatalogPaperVerTo>();
		
		NumberFormat nft = new DecimalFormat("###.#");
		String quotPaperDesc = "" , paperVer = "", catalog = "", paper="";

		for(int i=0;i < catalogPaperVerList.size(); i ++){
			QuoCatalogPaperVerTo catalogPaperVerTo = catalogPaperVerList.get(i);
			paperVer = nft.format(Double.parseDouble(catalogPaperVerTo.getPaperVer()));
			catalog = catalogPaperVerTo.getCatalog();
			paper = catalogPaperVerTo.getPaper();
			
			catalogPaperVerTo.setPaperVer(paperVer);			
			if (catalogPaperVerTo.getPaperVerUid().equals(paperVerUid) ){
				quotPaperDesc = catalogPaperVerTo.getCatalog() + "-" +  catalogPaperVerTo.getPaper() + " Ver " + paperVer ;
			}
			catalogPaperVerList.set(i, catalogPaperVerTo);
		}	
		
		
		for(int i=0; i < inquiryPartNumList.size(); i ++ ){
			InquiryPartNumTo partNumTo = inquiryPartNumList.get(i);
			//詢價料號
			String inquiryKeyPartNum = partNumTo.getInquiryKeyPartNum();
			if( inquiryKeyPartNum.equals(partNumTo.getInquiryPartNum())){
				InquiryConfirmTo confirmTo = new InquiryConfirmTo();
				//Part Number
				confirmTo.setInquiryKeyPartNum(inquiryKeyPartNum);
				confirmTo.setInquiryPartNum(partNumTo.getInquiryPartNum());
				confirmTo.setInquiryPartNumDesc(partNumTo.getInquiryPartNumDesc());
				confirmTo.setInquiryPartNumDiffer(partNumTo.getInquiryPartNumDiffer());
				confirmTo.setInquiryModel(partNumTo.getInquiryModel());
				confirmTo.setInquiryModelDesc(partNumTo.getInquiryModelDesc());
				confirmTo.setPaperVerUid(paperVerUid);

				confirmTo.setInquiryQty(partNumTo.getInquiryQty());
				confirmTo.setInquiryUnit(partNumTo.getInquiryUnit());
				confirmTo.setQuotPaperDesc(quotPaperDesc);
		
				//Supplier
				for(int j=0; j < inquirySupplierList.size(); j ++ ){
					InquirySupplierTo supplierTo =  inquirySupplierList.get(j);
					for(int k=0; k < inquirySupplierContactList.size(); k ++ ){
						InquirySupplierContactTo supplierContactTo = inquirySupplierContactList.get(k);
						 
						if(supplierTo.getInquirySupplierCode().equals( supplierContactTo.getInquirySupplierCode() )){
							
							InquiryConfirmTo confirmWithPartNumTo = new InquiryConfirmTo();
							//Copy Object
							confirmWithPartNumTo = (InquiryConfirmTo)confirmTo.clone();
							
							confirmWithPartNumTo.setInquirySupplierCode(supplierTo.getInquirySupplierCode());
							confirmWithPartNumTo.setInquirySupplierName(supplierTo.getInquirySupplierName());
							confirmWithPartNumTo.setInquirySupplierEmail(supplierContactTo.getInquirySupplierEmail());
							
							logger.debug("1 " + supplierContactTo.getInquirySupplierContact());
							
							confirmWithPartNumTo.setInquirySupplierContact(supplierContactTo.getInquirySupplierContact());
							logger.debug("2 " + confirmWithPartNumTo.getInquirySupplierContact());
							
							confirmWithPartNumTo.setInquirySupplierSite(supplierTo.getInquirySupplierSite());
							confirmWithPartNumTo.setInquirySupplierSiteCode(supplierTo.getInquirySupplierSiteCode());
							
							confirmWithPartNumTo.setInquiryCurrency(supplierTo.getInquiryCurrency());
							confirmWithPartNumTo.setInquiryPaymentMethod(supplierTo.getInquiryPaymentMethod());
							confirmWithPartNumTo.setInquiryDeliveryLocation(supplierTo.getInquiryDeliveryLocation());
							confirmWithPartNumTo.setInquiryDeliveryLocationDesc(supplierTo.getInquiryDeliveryLocationDesc());
							confirmWithPartNumTo.setInquiryShippedBy(supplierTo.getInquiryShippedBy());
							confirmWithPartNumTo.setQuotationRecoverTime(recoverDate +" " + recoverTime);
							
							supplierTo.setQuotationRecoverTime(recoverDateTime);
							inquirySupplierList.set(j, supplierTo);
							
							inquiryConfirmList.add(confirmWithPartNumTo);
						}
					}
					

				}
				
				logger.debug("inquiryConfirmList " +inquiryConfirmList.toString());
				logger.debug("inquiryKeyPartNum " + inquiryKeyPartNum);
				logger.debug("InquiryPartNum " + partNumTo.getInquiryPartNum());
				
				
				continue;
			}
		}
		
		request.setAttribute("inquiryConfirmList", inquiryConfirmList);
		request.setAttribute("paperVerUid", paperVerUid);
		
		//Return List		
		List<ErpCurrencyTo> waersList = erpCurrencyDao.getErpCurrency();
		List<ErpFreightTermsCodeTo> shippedByList = erpFreightTermsCodeDao.getAllFreightTermsCode();
		List<ErpTradeTermsCodeTo> termsTransactionList = erpTradeTermsCodeDao.getAllTradeTermsCode();
		List<SysFunParameterTo> deliveryLocationList = funParameterDao.getParameterList("INQUIRY", "DELIVERY_LOCATION");
			
				
		request.setAttribute("catalogPaperVerList", catalogPaperVerList);
		request.setAttribute("waersList", waersList);
		request.setAttribute("shippedByList", shippedByList);
		request.setAttribute("termsTransactionList", termsTransactionList);
		request.setAttribute("deliveryLocationList", deliveryLocationList);
		
		return SUCCESS;
	}

	public String ajaxInquiryConfirmAddQuery() throws Exception {
		
		String partNum = request.getParameter("partNum");
		partNum = null != partNum ? partNum : "";
		logger.debug("partNum  " + partNum);
		
		String paperVerUid = request.getParameter("paperVerUid");
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid  " + paperVerUid);
		
		ErpBOMDao erpBOMDao = new ErpBOMDao();	
		List<ErpBOMTo> erpBOMList = erpBOMDao.getBOMbyItem(partNum);
		String partNumDiffer="", verStartDt = "", verEndDt="";
		if( erpBOMList == null ){
			logger.debug("erpBOMList null " );
			//1.1.0  如果沒找到, 判定此料為替代料.
			//1.1.1  拿替代料找出主料.
			partNumDiffer = "替";
		}else{
			partNumDiffer = "主";
		}
		
		QuoPaperVerDao paperVerDao = new QuoPaperVerDao();
		QuoCatalogPaperVerTo catalogPaperVerTo= paperVerDao.getPaperByVer(paperVerUid);
		catalogPaperVerTo = null != catalogPaperVerTo ? catalogPaperVerTo : new QuoCatalogPaperVerTo();
		
		verStartDt = catalogPaperVerTo.getVerStartDt();
		verStartDt = null != verStartDt ? verStartDt : "";
		
		verEndDt = catalogPaperVerTo.getVerEndDt();
		verEndDt = null != verEndDt ? verEndDt : "";
		
		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		sb.append("|");
		sb.append(partNumDiffer);
		sb.append("|");
		sb.append(verStartDt);
		sb.append("|");
		sb.append(verEndDt);
		sb.append("|");
		
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}
	
	
	public String saveInquiry() throws Exception {
		
		String befofeInquiryNum = this.inquiryNum;
		befofeInquiryNum = null != befofeInquiryNum ? befofeInquiryNum : "";
		logger.debug("befofeInquiryNum " + befofeInquiryNum);
		
		String inquiryGridData = this.inquiryGridData;
		inquiryGridData = null != inquiryGridData ? inquiryGridData : "";
		logger.debug("inquiryGridData  " + inquiryGridData);
		
		final InquiryHeaderDao headerDao = new InquiryHeaderDao();
		final InquiryPartNumDao partNumDao = new InquiryPartNumDao();
		final InquirySupplierDao supplierDao = new InquirySupplierDao();
		final InquirySupplierContactDao supplierContactDao = new InquirySupplierContactDao();
		final InquiryInformDao inquiryInformDao = new InquiryInformDao();
		
		final QuoteHeaderDao quoteHeaderDao = new QuoteHeaderDao();
		InquirySeqnumDao seqnumDao = new InquirySeqnumDao();
		QuoteSeqnumDao quoteSeqnumDao = new QuoteSeqnumDao();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdtf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Calendar today = Calendar.getInstance();
		DecimalFormat df = new DecimalFormat("000000");
		
		//處理資料
		final List<InquiryConfirmTo> inquiryConfirmList = JsonUtil.getList4Json(inquiryGridData , InquiryConfirmTo.class);
		List<InquiryHeaderTo> inquiryHeaderList = JsonUtil.getList4Json(inquiryGridData , InquiryHeaderTo.class);
		List<InquiryPartNumTo> inquiryPartNumList = JsonUtil.getList4Json(inquiryGridData , InquiryPartNumTo.class);
		List<InquirySupplierTo> inquirySupplierList = JsonUtil.getList4Json(inquiryGridData , InquirySupplierTo.class);
		List<InquirySupplierContactTo> inquirySupplierContactList = JsonUtil.getList4Json(inquiryGridData , InquirySupplierContactTo.class);
		
		//logger.debug("inquiryConfirmList  " + inquiryConfirmList.size());
		logger.debug("inquiryHeaderList  " + inquiryHeaderList.size());
		logger.debug("inquiryHeaderList  " + inquiryHeaderList.toString());
		logger.debug("-----------------------------------------------");
		logger.debug("inquiryPartNumList  " + inquiryPartNumList.size());
		logger.debug("inquiryPartNumList  " + inquiryPartNumList.toString());
		logger.debug("-----------------------------------------------");
		logger.debug("inquirySupplierList  " + inquirySupplierList.size());
		logger.debug("inquirySupplierList  " + inquirySupplierList.toString());
		logger.debug("-----------------------------------------------");
		logger.debug("inquirySupplierContactList  " + inquirySupplierContactList.size());
		logger.debug("inquirySupplierContactList  " + inquirySupplierContactList.toString());
		logger.debug("-----------------------------------------------");
		for (int i=0; i<inquirySupplierList.size(); i ++){
			InquirySupplierTo inquirySupplierTo = inquirySupplierList.get(i);
			inquirySupplierTo.setInquirySupplierUid(null);
			inquirySupplierTo.setQuotationRecoverTime(null);
			inquirySupplierTo.setCdt(null);
			inquirySupplierList.set(i, inquirySupplierTo);
		}
		
		for (int i=0; i<inquiryPartNumList.size(); i ++){
			InquiryPartNumTo inquiryPartNumTo = inquiryPartNumList.get(i);
			inquiryPartNumTo.setInquiryPartNumUid(null);
			inquiryPartNumTo.setCdt(null);
			inquiryPartNumTo.setInquiryModelDesc("");
			inquiryPartNumList.set(i, inquiryPartNumTo);
		}
		
		for (int i=0; i<inquirySupplierContactList.size(); i ++){
			InquirySupplierContactTo supplierContactTo = inquirySupplierContactList.get(i);
			supplierContactTo.setInquirySupplierContactUid(null);
			inquirySupplierContactList.set(i, supplierContactTo);
		}
		
		inquiryHeaderList = CLTUtil.removeDuplicateObj(inquiryHeaderList);
		inquiryPartNumList = CLTUtil.removeDuplicateObj(inquiryPartNumList);
		inquirySupplierList = CLTUtil.removeDuplicateObj(inquirySupplierList);
		inquirySupplierContactList = CLTUtil.removeDuplicateObj(inquirySupplierContactList);
		
		logger.debug("inquiryHeaderList  " + inquiryHeaderList.size());
		logger.debug("inquiryHeaderList  " + inquiryHeaderList.toString());
		logger.debug("-----------------------------------------------");
		logger.debug("inquiryPartNumList  " + inquiryPartNumList.size());
		logger.debug("inquiryPartNumList  " + inquiryPartNumList.toString());
		logger.debug("-----------------------------------------------");
		logger.debug("inquirySupplierList  " + inquirySupplierList.size());
		logger.debug("inquirySupplierList  " + inquirySupplierList.toString());
		logger.debug("-----------------------------------------------");
		logger.debug("inquirySupplierContactList  " + inquirySupplierContactList.size());
		logger.debug("inquirySupplierContactList  " + inquirySupplierContactList.toString());
		logger.debug("-----------------------------------------------");
		
		logger.debug("inquiryHeaderList  " + inquiryHeaderList.size());
		//List<InquiryHeaderTo> inquiryHeaderList= new ArrayList<InquiryHeaderTo>();
		//List<InquirySupplierTo> inquirySupplierList= new ArrayList<InquirySupplierTo>();
		

			
		//Generate Sequence Number
	    //1.1.1GET DATE		
	    String issDate = sdf.format(today.getTime());
	    String inquiryNum = "";
	    String inquiryHeaderUid = "";
		//新增詢價單
		if( befofeInquiryNum.equals("") ){
			logger.debug("新增詢價單 ************************************** " +  befofeInquiryNum);
		    //1.2 Set Inquiry Number 詢價單號設定
		    inquiryNum = "I" + CLTUtil.getMessage("System.system.site.code");
		    String issYear = issDate.substring(0, 4);
		    String issMonth = issDate.substring(4, 6);
		    		
		    String seqNum = "";
		    if (!seqnumDao.isSeqNumExisted(issYear, issMonth)) {
		    	seqnumDao.insertSeqNum(issYear, issMonth, 1);
		    	int nextSeqNum = seqnumDao.getSeqNum(issYear, issMonth);
		        seqNum = df.format(nextSeqNum).toString();
		    } else {
		        int prevSeqNum = seqnumDao.getSeqNum(issYear, issMonth);
		        seqnumDao.updateSeqNum(issYear, issMonth, prevSeqNum);
		        int nextSeqNum = seqnumDao.getSeqNum(issYear, issMonth);
		        seqNum = df.format(nextSeqNum).toString();
		    }
		    
		    inquiryNum = inquiryNum + issDate + seqNum;	
		}else{
			
			//Update 詢價單
			logger.debug("Update 詢價單 ************************************** " +  befofeInquiryNum);
			inquiryNum = befofeInquiryNum;
		}
       	    
	    //Get Currency User
	    SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
	
	
	    String paperVerUid = "";      
	     QuoPaperVerDao quoPaperVerDao = new QuoPaperVerDao();
	    //Header
		for(int i=0; i< inquiryHeaderList.size(); i ++){
			if( befofeInquiryNum.equals("") ){
				inquiryHeaderUid = UUID.randomUUID().toString().toUpperCase();
			}else{
				InquiryHeaderTo inquiryHeaderTo = headerDao.getInquiryHeaderByInquiryNum(befofeInquiryNum);
				inquiryHeaderUid = inquiryHeaderTo.getInquiryHeaderUid();
			}
			InquiryHeaderTo inquiryHeaderTo = inquiryHeaderList.get(i);
			paperVerUid = inquiryHeaderTo.getPaperVerUid();
			
			inquiryHeaderTo.setInquiryHeaderUid(inquiryHeaderUid);
			inquiryHeaderTo.setInquiryNum(inquiryNum);
			inquiryHeaderTo.setPaperVerUid(paperVerUid);
			QuoCatalogPaperVerTo quoCatalogPaperVerTo = quoPaperVerDao.getPaperByVer(paperVerUid);
			inquiryHeaderTo.setPaperVerStartDt(quoCatalogPaperVerTo.getVerStartDt());
			inquiryHeaderTo.setPaperVerEndDt(quoCatalogPaperVerTo.getVerEndDt());
			inquiryHeaderTo.setCltInquiryUser(curUser.getUserId());
			inquiryHeaderTo.setInquiryStatus(this.inquiryStatus);		
			inquiryHeaderList.set(i,inquiryHeaderTo);
			
			//Part Num
			for(int j=0; j< inquiryPartNumList.size(); j++){
				String inquiryPartNumUid = UUID.randomUUID().toString().toUpperCase();
				InquiryPartNumTo inquiryPartNumTo = inquiryPartNumList.get(j);
				inquiryPartNumTo.setInquiryPartNumUid(inquiryPartNumUid);
				inquiryPartNumTo.setInquiryHeaderUid(inquiryHeaderUid);
				
				inquiryPartNumList.set(j, inquiryPartNumTo);
				
				//Supplier
				String inquirySupplierUid = "";
	
				for(int k=0; k< inquirySupplierList.size(); k++){
					inquirySupplierUid = UUID.randomUUID().toString().toUpperCase();
					InquirySupplierTo inquirySupplierTo = inquirySupplierList.get(k);
					inquirySupplierTo.setInquirySupplierUid(inquirySupplierUid);
					inquirySupplierTo.setInquiryHeaderUid(inquiryHeaderUid);
					String inquirySupplierCode = inquirySupplierTo.getInquirySupplierCode();
					String recoverTime = "";
					
					for(int m=0; m< inquiryConfirmList.size(); m++){
						InquiryConfirmTo inquiryConfirmTo = inquiryConfirmList.get(m);
						if(inquiryConfirmTo.getInquirySupplierCode().equals(inquirySupplierCode)){
							recoverTime = inquiryConfirmTo.getQuotationRecoverTime();
							logger.debug("1 " + recoverTime);
							break;
						}
					}
					logger.debug("2 " + recoverTime);
					
					Date recoverDateTime = sdtf.parse(recoverTime);
					inquirySupplierTo.setQuotationRecoverTime(recoverDateTime);
					
					inquirySupplierList.set(k, inquirySupplierTo);
					
					String inquirySupplierContactUid="";
					//Supplier Contact
					for(int n=0; n< inquirySupplierContactList.size(); n++){
						inquirySupplierContactUid = UUID.randomUUID().toString().toUpperCase();
						InquirySupplierContactTo inquirySupplierContactTo = inquirySupplierContactList.get(n);
						if( inquirySupplierContactTo.getInquirySupplierCode().equals(inquirySupplierCode)){
							inquirySupplierContactTo.setInquirySupplierContactUid(inquirySupplierContactUid);
							inquirySupplierContactTo.setInquirySupplierUid(inquirySupplierUid);
							inquirySupplierContactTo.setInquiryHeaderUid(inquiryHeaderUid);
							inquirySupplierContactList.set(n,inquirySupplierContactTo);
						}
					}					
				}
			}
		}
		
		final List<InquiryHeaderTo> fInquiryHeaderList = inquiryHeaderList;
		final List<InquiryPartNumTo> fInquiryPartNumList = inquiryPartNumList;
		final List<InquirySupplierTo> fInquirySupplierList = inquirySupplierList;
		final List<InquirySupplierContactTo> fInquirySupplierContactList = inquirySupplierContactList;
		
		//暫存的 , 不發Mail, 不產生 Quotation 單號
		final List<QuoteHeaderTo> quoteHeaderList = new ArrayList<QuoteHeaderTo>();
		
		if(this.inquiryStatus.equals("F")){
			for(int i=0; i < fInquirySupplierList.size(); i++){
				InquirySupplierTo supplierTo = fInquirySupplierList.get(i);
				
				InquiryHeaderTo inquiryHeaderTo = new InquiryHeaderTo();
									
				for (int k=0; k < fInquiryHeaderList.size(); k++){
					inquiryHeaderTo = fInquiryHeaderList.get(k);
					if(inquiryHeaderTo.getInquiryHeaderUid().equals(inquiryHeaderUid) ){
						break;
					}		
				}
				
				for (int j=0; j < fInquiryPartNumList.size(); j++){
					InquiryPartNumTo inquiryPartNumTo = fInquiryPartNumList.get(j);
					
					
					//Create Quote Header
					//1.0 Set Quote Number
			        //1.2 Set Inquiry Number 詢價單號設定
			        String quoteDate = sdf.format(today.getTime());
			        
			        String quoteNum = "Q" + CLTUtil.getMessage("System.system.site.code");
			        String quoteYear = quoteDate.substring(0, 4);
			        String quoteMonth = quoteDate.substring(4, 6);
			        
			        String quoteSeqNum = "";
			        if (!quoteSeqnumDao.isSeqNumExisted(quoteYear, quoteMonth)) {
			        	quoteSeqnumDao.insertSeqNum(quoteYear, quoteMonth, 1);
			        	int nextSeqNum = quoteSeqnumDao.getSeqNum(quoteYear, quoteMonth);
			        	quoteSeqNum = df.format(nextSeqNum).toString();
			        } else {
			            int prevSeqNum = quoteSeqnumDao.getSeqNum(quoteYear, quoteMonth);
			            quoteSeqnumDao.updateSeqNum(quoteYear, quoteMonth, prevSeqNum);
			            int nextSeqNum = quoteSeqnumDao.getSeqNum(quoteYear, quoteMonth);
			            quoteSeqNum = df.format(nextSeqNum).toString();
			        }
			        quoteNum = quoteNum + quoteDate + quoteSeqNum;
			        
			        //Set QuoteHeder for insert DB
			        QuoteHeaderTo quoteHeaderTo = new QuoteHeaderTo();
			        String quoteHeaderUid = UUID.randomUUID().toString().toUpperCase();
			        quoteHeaderTo.setQuoteHeaderUid(quoteHeaderUid);
			        quoteHeaderTo.setQuoteNum(quoteNum);
			        quoteHeaderTo.setInquiryHeaderUid(inquiryHeaderTo.getInquiryHeaderUid());
			        quoteHeaderTo.setInquiryPartNumUid(inquiryPartNumTo.getInquiryPartNumUid());
			        quoteHeaderTo.setInquirySupplierUid(supplierTo.getInquirySupplierUid());
			        quoteHeaderTo.setQuoteSupplierCode(supplierTo.getInquirySupplierCode());
			        quoteHeaderTo.setCltContactUser(inquiryHeaderTo.getCltInquiryUser());
			        quoteHeaderTo.setQuotePartNum(inquiryPartNumTo.getInquiryPartNum());
			        quoteHeaderTo.setQuotePartNumDesc(inquiryPartNumTo.getInquiryPartNumDesc());
			        quoteHeaderTo.setPaperVerUid(inquiryHeaderTo.getPaperVerUid());
			        quoteHeaderList.add(quoteHeaderTo);				
	    		        
				}
				
			}
	
		}
		
		logger.debug("quoteHeaderList  " + quoteHeaderList.size());
		logger.debug("quoteHeaderList  " + quoteHeaderList.toString());
		logger.debug("-----------------------------------------------");
		
		final String fInquiryStatus = this.inquiryStatus;
		//新增詢價單
		TransactionCallbackWithoutResult callback;
		if( befofeInquiryNum.equals("") ){
			logger.debug("新增詢價單 ************************************** " +  befofeInquiryNum);
			//Insert DB
			 callback = new TransactionCallbackWithoutResult() {
				public void doInTransactionWithoutResult(
						final TransactionStatus status) {
					headerDao.batchInstInquiryHeader(fInquiryHeaderList);
					partNumDao.batchInstInquiryPartNum(fInquiryPartNumList);
					supplierDao.batchInstInquirySupplier(fInquirySupplierList);
					supplierContactDao.batchInstInquirySupplierContact(fInquirySupplierContactList);
					
					quoteHeaderDao.batchInstQuoteHeader(quoteHeaderList);
				}
			};
		}else{
			//Update 詢價單
			logger.debug("Update 詢價單 ************************************** " +  inquiryNum);
			
			final String fInquiryHeaderUid = inquiryHeaderUid;
			//Update 詢價單  DB
			 callback = new TransactionCallbackWithoutResult() {
				public void doInTransactionWithoutResult(
						final TransactionStatus status) {
					inquiryInformDao.delInquiryInform(fInquiryHeaderUid);
					partNumDao.delInquiryPartNum(fInquiryHeaderUid);
					supplierContactDao.delSupplierContact(fInquiryHeaderUid);
					supplierDao.delInquirySupplier(fInquiryHeaderUid);
					partNumDao.batchInstInquiryPartNum(fInquiryPartNumList);
					supplierDao.batchInstInquirySupplier(fInquirySupplierList);
					supplierContactDao.batchInstInquirySupplierContact(fInquirySupplierContactList);
					
					headerDao.updateInquiryStatus(fInquiryHeaderUid, fInquiryStatus);
					quoteHeaderDao.batchInstQuoteHeader(quoteHeaderList);
				}
			};
		}		

		
		try {
			new InquiryHeaderDao().doInTransaction(callback);
	
		    /******************************************************
		     * 	1.4 Send Mail to Supplier
		     ******************************************************/
			//暫存的 , 不發Mail, 不產生 Quotation 單號
			
			if(this.inquiryStatus.equals("F")){
				for(int i=0; i < inquirySupplierList.size(); i++){
					InquirySupplierTo supplierTo = inquirySupplierList.get(i);
					List<QuoteHeaderTo> mailQuoteHeaderList = new ArrayList<QuoteHeaderTo>();
					List<InquirySupplierContactTo> mailInquirySupplierContactList = new ArrayList<InquirySupplierContactTo>();
					String inquirySupplierCode = supplierTo.getInquirySupplierCode();
					for(int j=0; j < quoteHeaderList.size(); j++){
						QuoteHeaderTo quoteHeaderTo = quoteHeaderList.get(j);
						if(quoteHeaderTo.getQuoteSupplierCode().equals(inquirySupplierCode)){
							mailQuoteHeaderList.add(quoteHeaderTo);
						}
					}
					for(int k=0; k<inquirySupplierContactList.size(); k++){
						InquirySupplierContactTo inquirySupplierContactTo = inquirySupplierContactList.get(k);
						if(inquirySupplierContactTo.getInquirySupplierCode().equals(inquirySupplierCode)){
							mailInquirySupplierContactList.add(inquirySupplierContactTo);
						}
					}
					MailContents.sendInquiryMail(request , response , mailQuoteHeaderList
								, supplierTo, mailInquirySupplierContactList , inquiryNum);
				}
	
			}
			
		} catch (Exception e) {
			this.addActionMessage("Save ERROR");
			e.printStackTrace();
			logger.debug(e.toString());
			addActionMessage(e.toString());
	      	//AJAX
	      	try{
	  	    	response.setContentType("text/html; charset=UTF-8");
	  			PrintWriter out = response.getWriter();
	  			String returnResult = "ERROR" ;
	
	  			logger.debug(returnResult);
	  			logger.debug("Create Fail");
	  			out.print(returnResult);
	  			out.close();
	      	}catch(Exception ex){
	      		ex.printStackTrace();
	            logger.error(ex.toString());
	            return NONE;
	      	}
			return ERROR;
		}
		
		request.setAttribute("inquiryNum", inquiryNum);
		request.setAttribute("inquiryConfirmList", inquiryConfirmList);
		
		return SUCCESS;
	}
	
	
	
	//Update Inquiry
	public String updateInquiryPre() throws Exception {

		ErpCurrencyDao erpCurrencyDao = new ErpCurrencyDao();
		ErpFreightTermsCodeDao erpFreightTermsCodeDao = new ErpFreightTermsCodeDao();
		ErpTradeTermsCodeDao erpTradeTermsCodeDao = new ErpTradeTermsCodeDao();
		SysFunParameterDao funParameterDao = new SysFunParameterDao();
		
		String inquiryNum = this.inquiryNum;
		inquiryNum = null != inquiryNum ? inquiryNum : "";
		logger.debug("inquiryNum " + inquiryNum);
		
		String paperVerUid = this.paperVerUid;
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid " + paperVerUid);
		
		InquiryManageQueryTo inquiryManageQueryTo = new InquiryManageQueryTo();
		inquiryManageQueryTo.setInquiryNum(inquiryNum);
		inquiryManageQueryTo.setInquiryScdt("");
		inquiryManageQueryTo.setInquiryEcdt("");
		inquiryManageQueryTo.setRecoverStime("");
		inquiryManageQueryTo.setRecoverEtime("");
		inquiryManageQueryTo.setInquiryPartNum("");
		inquiryManageQueryTo.setInquirySupplierCode("");
		inquiryManageQueryTo.setCltInquiryUser("");
		
		InquiryHeaderDao inquiryHeaderDao = new InquiryHeaderDao();
		InquirySupplierContactDao supplierContactDao = new InquirySupplierContactDao();
		ErpDeliveryLocationDao erpDeliveryLocationDao = new ErpDeliveryLocationDao();
		
		List<InquiryConfirmTo> inquiryConfirmList = inquiryHeaderDao.getInquiry(inquiryManageQueryTo);
		for(int i=0; i < inquiryConfirmList.size(); i ++){
			InquiryConfirmTo inquiryConfirmTo = inquiryConfirmList.get(i);
			inquiryConfirmTo.setInquiryKeyPartNum(inquiryConfirmTo.getInquiryPartNum());
			ErpDeliveryLocationTo erpDeliveryLocationTo = erpDeliveryLocationDao.getErpDeliveryLocation(inquiryConfirmTo.getInquiryDeliveryLocation());
			inquiryConfirmTo.setInquiryDeliveryLocationDesc(erpDeliveryLocationTo.getLocationCode());
			
			String inquiryStatus = inquiryConfirmTo.getInquiryStatus();
			
			inquiryConfirmTo.setInquiryStatusDesc(CheckInquiryStatus.checkInquiryStatusDesc(inquiryStatus));
			
			List<InquirySupplierContactTo> inquirySupplierContactList = supplierContactDao.getSupplierContactBySupplierUid(inquiryConfirmTo.getInquirySupplierUid());
			if( inquirySupplierContactList != null ){
				InquirySupplierContactTo inquirySupplierContactTo = inquirySupplierContactList.get(0);
				inquiryConfirmTo.setInquirySupplierContact(inquirySupplierContactTo.getInquirySupplierContact());
				inquiryConfirmTo.setInquirySupplierEmail(inquirySupplierContactTo.getInquirySupplierEmail());
				//inquiryConfirmTo.setInquirySupplierPhone(inquirySupplierPhone);
				
			}

		}

		request.setAttribute("inquiryConfirmList", inquiryConfirmList);
		request.setAttribute("paperVerUid", paperVerUid);
		
		QuoPaperVerDao paperVerDao = new QuoPaperVerDao();
		List<QuoCatalogPaperVerTo> catalogPaperVerList = paperVerDao.getActivePaperList();
		catalogPaperVerList = null != catalogPaperVerList ? catalogPaperVerList : new ArrayList<QuoCatalogPaperVerTo>();
		
		NumberFormat nft = new DecimalFormat("###.#");
		String quotPaperDesc = "" , paperVer = "", catalog = "", paper="";

		for(int i=0;i < catalogPaperVerList.size(); i ++){
			QuoCatalogPaperVerTo catalogPaperVerTo = catalogPaperVerList.get(i);
			paperVer = nft.format(Double.parseDouble(catalogPaperVerTo.getPaperVer()));
			catalog = catalogPaperVerTo.getCatalog();
			paper = catalogPaperVerTo.getPaper();
			
			catalogPaperVerTo.setPaperVer(paperVer);			
			if (catalogPaperVerTo.getPaperVerUid().equals(paperVerUid) ){
				quotPaperDesc = catalogPaperVerTo.getCatalog() + "-" +  catalogPaperVerTo.getPaper() + " Ver " + paperVer ;
			}
			catalogPaperVerList.set(i, catalogPaperVerTo);
		}	

		//Return List		
		List<ErpCurrencyTo> waersList = erpCurrencyDao.getErpCurrency();
		List<ErpFreightTermsCodeTo> shippedByList = erpFreightTermsCodeDao.getAllFreightTermsCode();
		List<ErpTradeTermsCodeTo> termsTransactionList = erpTradeTermsCodeDao.getAllTradeTermsCode();
		List<SysFunParameterTo> deliveryLocationList = funParameterDao.getParameterList("INQUIRY", "DELIVERY_LOCATION");
			
				
		request.setAttribute("catalogPaperVerList", catalogPaperVerList);
		request.setAttribute("waersList", waersList);
		request.setAttribute("shippedByList", shippedByList);
		request.setAttribute("termsTransactionList", termsTransactionList);
		request.setAttribute("deliveryLocationList", deliveryLocationList);
		request.setAttribute("inquiryNum", inquiryNum);
		
		return SUCCESS;
	}
	
	public String getPartNumGridData() {
		return partNumGridData;
	}

	public void setPartNumGridData(String partNumGridData) {
		this.partNumGridData = partNumGridData;
	}

	public String getInquiryGridData() {
		return inquiryGridData;
	}

	public void setInquiryGridData(String inquiryGridData) {
		this.inquiryGridData = inquiryGridData;
	}

	public String getInquiryStatus() {
		return inquiryStatus;
	}

	public void setInquiryStatus(String inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}

	public String getPaperVerUid() {
		return paperVerUid;
	}

	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}

	public String getInquiryNum() {
		return inquiryNum;
	}

	public void setInquiryNum(String inquiryNum) {
		this.inquiryNum = inquiryNum;
	}


	
	
}
