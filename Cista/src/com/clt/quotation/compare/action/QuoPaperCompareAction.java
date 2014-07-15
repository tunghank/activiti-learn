package com.clt.quotation.compare.action;


import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.clt.quotation.compare.dao.CompareHeaderDao;
import com.clt.quotation.compare.dao.CompareMapQuoteDao;
import com.clt.quotation.compare.dao.CompareSeqnumDao;
import com.clt.quotation.compare.to.ApplyCompareTo;
import com.clt.quotation.compare.to.CompareHeaderTo;
import com.clt.quotation.compare.to.CompareMapQuoteTo;
import com.clt.quotation.compare.to.QuotationCompareTo;
import com.clt.quotation.config.dao.QuoPaperVerDao;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import com.clt.quotation.erp.dao.ErpCltWfQuoLineVDao;
import com.clt.quotation.erp.dao.ErpDeliveryLocationDao;
import com.clt.quotation.erp.dao.ErpFreightTermsCodeDao;
import com.clt.quotation.erp.dao.ErpPaymentDao;
import com.clt.quotation.erp.dao.ErpTaxDao;
import com.clt.quotation.erp.dao.ErpVendorDao;
import com.clt.quotation.erp.to.ErpCltWfQuoLineVTo;
import com.clt.quotation.erp.to.ErpDeliveryLocationTo;
import com.clt.quotation.erp.to.ErpFreightTermsCodeTo;
import com.clt.quotation.erp.to.ErpPaymentTo;
import com.clt.quotation.erp.to.ErpTaxTo;
import com.clt.quotation.erp.to.PoVendorSitesAllTo;
import com.clt.quotation.inquiry.dao.InquiryHeaderDao;
import com.clt.quotation.inquiry.dao.InquiryPartNumDao;
import com.clt.quotation.inquiry.dao.InquirySupplierDao;
import com.clt.quotation.inquiry.to.InquiryConfirmTo;
import com.clt.quotation.inquiry.to.InquiryHeaderTo;
import com.clt.quotation.inquiry.to.InquiryPartNumTo;
import com.clt.quotation.inquiry.to.InquirySupplierTo;
import com.clt.quotation.quote.action.CheckQuoteStatus;
import com.clt.quotation.quote.dao.QuoteHeaderDao;
import com.clt.quotation.quote.dao.QuoteRecordTotalDao;
import com.clt.quotation.quote.to.QuoteHeaderTo;
import com.clt.quotation.quote.to.QuoteRecordTotalTo;
import com.clt.quotation.wfInterface.dao.PQSWfInterfaceHDao;
import com.clt.quotation.wfInterface.dao.PQSWfInterfaceLineDao;
import com.clt.quotation.wfInterface.to.PQSWfInterfaceHTo;
import com.clt.quotation.wfInterface.to.PQSWfInterfaceLineTo;
import com.clt.system.to.SysUserTo;
import com.clt.system.util.BaseAction;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.JsonUtil;

public class QuoPaperCompareAction extends BaseAction {
	
	private String nextFormInquiryNum;
	private String nextFormPaperVerUid;
	
	private String queryQuoteFormInquiryNum;
	private String queryQuoteFormPaperVerUid;
	
	public String quoPaperComparePre() throws Exception {
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
		
		InquiryHeaderDao inquiryHeaderDao = new InquiryHeaderDao();
		InquirySupplierDao inquirySupplierDao = new InquirySupplierDao();
		InquiryPartNumDao inquiryPartNumDao = new InquiryPartNumDao();
		
		String inquiryNum = request.getParameter("inquiryNum");
		inquiryNum = null != inquiryNum ? inquiryNum : "";
		
				
		final List<InquiryConfirmTo> inquiryConfirmList = new ArrayList<InquiryConfirmTo>();
		
		InquiryHeaderTo inquiryHeaderTo =inquiryHeaderDao.getInquiryHeaderByInquiryNum(inquiryNum);
		
		String inquiryHeaderUid = inquiryHeaderTo.getInquiryHeaderUid();
		
		List<InquirySupplierTo> inquirySupplierList = inquirySupplierDao.getInquirySupplierByInquiryHeaderUid(inquiryHeaderUid);
		List<InquiryPartNumTo> iquiryPartNumList = inquiryPartNumDao.getInquiryPartNumList(inquiryHeaderUid);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		logger.debug("inquirySupplierList() " + inquirySupplierList.size());
		
		for(int i=0; i < iquiryPartNumList.size(); i ++ ){
			InquiryPartNumTo partNumTo = iquiryPartNumList.get(i);
			for(int j=0; j < inquirySupplierList.size() ; j ++){
				InquirySupplierTo supplierTo = inquirySupplierList.get(j);
				InquiryConfirmTo confirmTo = new InquiryConfirmTo();
				//Part Number
				confirmTo.setInquiryKeyPartNum(partNumTo.getInquiryPartNum());
				confirmTo.setInquiryPartNum(partNumTo.getInquiryPartNum());
				confirmTo.setInquiryPartNumDesc(partNumTo.getInquiryPartNumDesc());
				confirmTo.setInquiryPartNumDiffer(partNumTo.getInquiryPartNumDiffer());
				confirmTo.setInquiryModel(partNumTo.getInquiryModel());
				confirmTo.setInquiryModelDesc(partNumTo.getInquiryModelDesc());
				confirmTo.setPaperVerUid(inquiryHeaderTo.getPaperVerUid());
				confirmTo.setPaperVerStartDt(inquiryHeaderTo.getPaperVerStartDt());
				confirmTo.setPaperVerEndDt(inquiryHeaderTo.getPaperVerEndDt());
				confirmTo.setInquiryQty(partNumTo.getInquiryQty());
				confirmTo.setInquiryUnit(partNumTo.getInquiryUnit());
				confirmTo.setQuotPaperDesc(inquiryHeaderTo.getQuotPaperDesc());
				
				confirmTo.setInquirySupplierCode(supplierTo.getInquirySupplierCode());

				confirmTo.setInquirySupplierName(supplierTo.getInquirySupplierName());
				
				confirmTo.setInquirySupplierSite(supplierTo.getInquirySupplierSite());
				confirmTo.setInquiryCurrency(supplierTo.getInquiryCurrency());
				confirmTo.setInquiryPaymentMethod(supplierTo.getInquiryPaymentMethod());
				confirmTo.setInquiryDeliveryLocation(supplierTo.getInquiryDeliveryLocation());
				confirmTo.setInquiryDeliveryLocationDesc(supplierTo.getInquiryDeliveryLocationDesc());
				confirmTo.setInquiryShippedBy(supplierTo.getInquiryShippedBy());
				confirmTo.setQuotationRecoverTime(sdf.format(supplierTo.getQuotationRecoverTime()));
				confirmTo.setInquiryNum(inquiryHeaderTo.getInquiryNum());					
				inquiryConfirmList.add(confirmTo);
			}

			logger.debug("inquiryConfirmList " +inquiryConfirmList.toString());

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

	public String quoPaperCompare() throws Exception {
		
		String inquiryNum = this.nextFormInquiryNum;
		inquiryNum = null != inquiryNum ? inquiryNum : "";
		logger.debug("inquiryNum " + inquiryNum);
		
		String paperVerUid = this.nextFormPaperVerUid;
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid " + paperVerUid);
		
		if( this.queryQuoteFormInquiryNum != null ){
			if(!this.queryQuoteFormInquiryNum.equals("")){
				inquiryNum = this.queryQuoteFormInquiryNum;
			}
		}
		
		
		if( this.queryQuoteFormPaperVerUid != null ){
			if(!this.queryQuoteFormPaperVerUid.equals("")){
				paperVerUid = this.queryQuoteFormPaperVerUid;
			}
		}
		
		InquiryHeaderDao inquiryHeaderDao = new InquiryHeaderDao();
		InquiryPartNumDao inquiryPartNumDao = new InquiryPartNumDao();
		
		InquirySupplierDao inquirySupplierDao =  new InquirySupplierDao();
		QuoteHeaderDao quoteHeaderDao =  new QuoteHeaderDao();
		QuoteRecordTotalDao quoteRecordTotalDao =  new QuoteRecordTotalDao();
		ErpDeliveryLocationDao erpDeliveryLocationDao = new ErpDeliveryLocationDao();
		
		/******************************************************************
		 * `1.先找出 詢價 Header
		 *  2.找出報價廠商
		 *  3.找出報價單
		 ******************************************************************/
		InquiryHeaderTo inquiryHeaderTo = inquiryHeaderDao.getInquiryHeaderByInquiryNum(inquiryNum);
		List<QuotationCompareTo> quotationCompareList = new ArrayList();
		String inquiryHeaderUid = inquiryHeaderTo.getInquiryHeaderUid();
		String inquiryHeaderPaperVerUid = inquiryHeaderTo.getPaperVerUid();
		
		if( inquiryHeaderTo != null ){
			int h = 0;
			String compareStatus = inquiryHeaderTo.getCompareStatus();
			inquiryHeaderTo.setCompareStatusDesc(CheckCompareStatus.checkCompareStatusDesc(compareStatus));
			
				List<QuoteHeaderTo> quoteHeaderList  = quoteHeaderDao.getQuoteHeaderByInquiryUid(inquiryHeaderUid);
				for(int j=0; j< quoteHeaderList.size(); j++){
					QuoteHeaderTo quoteHeaderTo = quoteHeaderList.get(j);
					quoteHeaderTo.setQuoteStatusDesc(CheckQuoteStatus.checkQuoteStatusDesc(quoteHeaderTo.getQuoteStatus()));
					
					String quoteHeaderUid = quoteHeaderTo.getQuoteHeaderUid();
					String inquiryPartNumUid = quoteHeaderTo.getInquiryPartNumUid();
					String inquirySupplierUid = quoteHeaderTo.getInquirySupplierUid();
					
					InquirySupplierTo inquirySupplierTo = 
						inquirySupplierDao.getInquirySupplierByKey(inquirySupplierUid);
					String locationId = inquirySupplierTo.getInquiryDeliveryLocation();
					
					ErpDeliveryLocationTo deliveryLocationTo= erpDeliveryLocationDao.getErpDeliveryLocation(locationId);
					deliveryLocationTo = null != deliveryLocationTo ? deliveryLocationTo : new ErpDeliveryLocationTo();
					inquirySupplierTo.setInquiryDeliveryLocationDesc(deliveryLocationTo.getLocationCode());
					
					InquiryPartNumTo inquiryPartNumTo = inquiryPartNumDao.getInquiryPartNumByKey(inquiryPartNumUid);
					
					List<QuoteRecordTotalTo> quoteRecordTotalList = 
						quoteRecordTotalDao.getQuoteRecordTotalByQuoteHeaderUid(quoteHeaderUid, inquiryHeaderPaperVerUid);
					//Set Quotation CompareTo
					QuotationCompareTo quotCompareTo = new QuotationCompareTo();
					quotCompareTo.setInquiryHeaderTo(inquiryHeaderTo);
					quotCompareTo.setQuoteHeaderTo(quoteHeaderTo);
					quotCompareTo.setInquirySupplierTo(inquirySupplierTo);
					quotCompareTo.setQuoteRecordTotalList(quoteRecordTotalList);
					quotCompareTo.setInquiryPartNumTo(inquiryPartNumTo);
					quotationCompareList.add(quotCompareTo);
				}
		
			logger.debug("H " + quotationCompareList.size());
		}
		
		// 1.3 Set AJAX response	
		StringBuilder returnJosnString = new StringBuilder();
		JSONArray jsonObject = JSONArray.fromObject(quotationCompareList);
		logger.debug("returnJosnString " + jsonObject.toString());
		returnJosnString.append(jsonObject.toString());
		
		request.setAttribute("returnJosnString", returnJosnString);
		request.setAttribute("inquiryNum", inquiryNum);
		request.setAttribute("paperVerUid", paperVerUid);
		return SUCCESS;
	}

	public String applyPaperCompare() throws Exception {
		
		String inquiryNum = request.getParameter("inquiryNum");
		inquiryNum = null != inquiryNum ? inquiryNum : "";
		logger.debug("inquiryNum " + inquiryNum);
		
		String paperVerUid = request.getParameter("paperVerUid");
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid " + paperVerUid);
		
		final InquiryHeaderDao inquiryHeaderDao = new InquiryHeaderDao();
		InquiryPartNumDao inquiryPartNumDao = new InquiryPartNumDao();
		InquirySupplierDao inquirySupplierDao =  new InquirySupplierDao();
		
		QuoteHeaderDao quoteHeaderDao =  new QuoteHeaderDao();
		QuoteRecordTotalDao quoteRecordTotalDao =  new QuoteRecordTotalDao();
		
		String data = request.getParameter("data");
		data = null != data ? data : "";
		logger.debug("data " + data);
		
		final List<ApplyCompareTo> applyCompareList= JsonUtil.getList4Json(data , ApplyCompareTo.class);
		logger.debug(applyCompareList.toString());
		
		/******************************************************************
		 * `1.先找出 詢價 Header
		 *  2.找出報價廠商
		 *  3.找出報價單  "要產生 比價單的報價單"
		 ******************************************************************/
		
		List<QuotationCompareTo> quotationCompareList = new ArrayList();
		InquiryHeaderTo inquiryHeaderTo = inquiryHeaderDao.getInquiryHeaderByInquiryNum(inquiryNum);
		final String inquiryHeaderUid = inquiryHeaderTo.getInquiryHeaderUid();
		String inquiryHeaderPaperVerUid = inquiryHeaderTo.getPaperVerUid();
		
		if( applyCompareList != null ){
			int h = 0;
			for(int i=0; i< applyCompareList.size(); i ++){
				ApplyCompareTo applyCompareTo = applyCompareList.get(i);
				QuoteHeaderTo quoteHeaderTo  = quoteHeaderDao.getQuoteHeaderByQuote(applyCompareTo.getQuoteNum());
				
				String quoteHeaderUid = quoteHeaderTo.getQuoteHeaderUid();
				String inquiryPartNumUid = quoteHeaderTo.getInquiryPartNumUid();
				String inquirySupplierUid = quoteHeaderTo.getInquirySupplierUid();

							
				InquirySupplierTo inquirySupplierTo = 
					inquirySupplierDao.getInquirySupplierByKey(inquirySupplierUid);
				
				InquiryPartNumTo inquiryPartNumTo = inquiryPartNumDao.getInquiryPartNumByKey(inquiryPartNumUid);
				
				List<QuoteRecordTotalTo> quoteRecordTotalList = 
					quoteRecordTotalDao.getQuoteRecordTotalByQuoteHeaderUid(quoteHeaderUid, inquiryHeaderPaperVerUid);
				//Set Quotation CompareTo
				QuotationCompareTo quotCompareTo = new QuotationCompareTo();
				quotCompareTo.setInquiryHeaderTo(inquiryHeaderTo);
				quotCompareTo.setInquirySupplierTo(inquirySupplierTo);
				quotCompareTo.setInquiryPartNumTo(inquiryPartNumTo);
				quotCompareTo.setQuoteHeaderTo(quoteHeaderTo);
				quotCompareTo.setQuoteRecordTotalList(quoteRecordTotalList);
				quotCompareTo.setApplyCompareTo(applyCompareTo);
				quotationCompareList.add(quotCompareTo);
				
			}
			logger.debug("H " + quotationCompareList.size());
		}
		
		/*********************************************************************
		 *  Set DAO , TO Object
		 ********************************************************************/
		final CompareHeaderTo compareHeaderTo = new CompareHeaderTo();
		final List<CompareMapQuoteTo> compareMapQuoteList = new ArrayList<CompareMapQuoteTo>();
		
		//Work Flow used
		final PQSWfInterfaceHTo pqsWfInterfaceHTo = new PQSWfInterfaceHTo();
		final List<PQSWfInterfaceLineTo> pqsWfInterfaceLineList = new ArrayList<PQSWfInterfaceLineTo>();
		final PQSWfInterfaceLineDao pqsWfInterfaceLineDao = new PQSWfInterfaceLineDao();
		final PQSWfInterfaceHDao pqsWfInterfaceHDao = new PQSWfInterfaceHDao();
		
		ErpVendorDao erpVendorDao = new ErpVendorDao();
		ErpTaxDao erpTaxDao = new ErpTaxDao();
		ErpDeliveryLocationDao erpDeliveryLocationDao =  new ErpDeliveryLocationDao();
		ErpFreightTermsCodeDao erpFreightTermsCodeDao = new ErpFreightTermsCodeDao();
		ErpPaymentDao erpPaymentDao = new ErpPaymentDao();
		ErpCltWfQuoLineVDao erpCltWfQuoLineVDao = new ErpCltWfQuoLineVDao();
		
		final CompareHeaderDao compareHeaderDao = new CompareHeaderDao();
		final CompareMapQuoteDao compareMapQuoteDao = new CompareMapQuoteDao();
		
        //Get Currency User
        SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
        
		//Generate Sequence Number
        //1.1.1GET DATE
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar today = Calendar.getInstance();
        String issDate = sdf.format(today.getTime());
        
        //1.2 Set Compare Number 比價單號設定
        String compareNum = "C" + CLTUtil.getMessage("System.system.site.code");
        String issYear = issDate.substring(0, 4);
        String issMonth = issDate.substring(4, 6);
        
		DecimalFormat df = new DecimalFormat("000000");
		CompareSeqnumDao seqnumDao = new CompareSeqnumDao();
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
        
        compareNum = compareNum + issDate + seqNum;	
        String compareHeaderUid = UUID.randomUUID().toString().toUpperCase();
        final String compareStatus = "F";
        /************************************************************
         * Set Value to Compare Header To & PQS Interface Header
         ************************************************************/
        //1.0 Set Value to Compare Header
        compareHeaderTo.setCompareHeaderUid(compareHeaderUid);
        compareHeaderTo.setCompareNum(compareNum);
        compareHeaderTo.setInquiryHeaderUid(inquiryHeaderUid);
        compareHeaderTo.setCltCompareUser(curUser.getUserId());
        compareHeaderTo.setCompareStatus(compareStatus);
        //1.1 Set PQS Interface Header
        String orgSiteId = CLTUtil.getMessage("System.ERP.Org");
        String orgOuId = CLTUtil.getMessage("System.ERP.OU");
        
        pqsWfInterfaceHTo.setPqsNo(compareNum);
        pqsWfInterfaceHTo.setOrgSiteId(orgSiteId);
        pqsWfInterfaceHTo.setOrgOuId(orgOuId);
        pqsWfInterfaceHTo.setFillInUserId(curUser.getUserId());
        
        /************************************************************
         * Set Value to CompareMapQuoteTo & PQS Interface Line
         ************************************************************/
        String compareMapQuoteUid = "";
        for(int i=0; i < quotationCompareList.size(); i ++ ){
        	QuotationCompareTo quotationCompareTo = quotationCompareList.get(i);
        	QuoteHeaderTo quoteHeaderTo = quotationCompareTo.getQuoteHeaderTo();
        	
        	InquirySupplierTo inquirySupplierTo = quotationCompareTo.getInquirySupplierTo();
        	ApplyCompareTo applyCompareTo = quotationCompareTo.getApplyCompareTo();
        	InquiryPartNumTo inquiryPartNumTo = quotationCompareTo.getInquiryPartNumTo();
        	
        	PQSWfInterfaceLineTo pqsWfInterfaceLineTo = new PQSWfInterfaceLineTo();
        	CompareMapQuoteTo compareMapQuoteTo = new CompareMapQuoteTo();
        	compareMapQuoteUid = UUID.randomUUID().toString().toUpperCase();
        	//1.2 Set Value to CompareMapQuoteTo 
        	compareMapQuoteTo.setCompareHeaderUid(compareHeaderUid);
        	compareMapQuoteTo.setCompareMapQuoteUid(compareMapQuoteUid);
        	compareMapQuoteTo.setQuoteHeaderUid(quoteHeaderTo.getQuoteHeaderUid());
        	compareMapQuoteTo.setCompareRatio(String.valueOf(applyCompareTo.getRatio()));
        	
        	compareMapQuoteList.add(compareMapQuoteTo);
        	
        	//1.3 Set Value to PQS Interface Line
        	String partNum = inquiryPartNumTo.getInquiryPartNum();
        	
        	pqsWfInterfaceLineTo.setPqsNo(compareNum);
        	pqsWfInterfaceLineTo.setWfNo("");
        	pqsWfInterfaceLineTo.setWfInsid("");
        	pqsWfInterfaceLineTo.setSeqNo(i+1);
        	pqsWfInterfaceLineTo.setPartsNo(partNum);
        	pqsWfInterfaceLineTo.setPartsDesc(inquiryPartNumTo.getInquiryPartNumDesc());
        	pqsWfInterfaceLineTo.setUnit(inquiryPartNumTo.getInquiryUnit());
        	
        	String vendorId = inquirySupplierTo.getInquirySupplierCode();
        	
        	pqsWfInterfaceLineTo.setVendorId(Integer.parseInt(vendorId) );
        	pqsWfInterfaceLineTo.setVendorName(inquirySupplierTo.getInquirySupplierName());
        	String vendorSiteId =  inquirySupplierTo.getInquirySupplierSiteCode();
        	logger.debug( "inquirySupplierTo " + inquirySupplierTo.toString() );
        	logger.debug("vendorSiteId " + vendorSiteId);
        	
        	PoVendorSitesAllTo poVendorSitesAllTo = erpVendorDao.getPoVendorSites(vendorSiteId, vendorId);
        	
        	pqsWfInterfaceLineTo.setVendorSiteId(Integer.parseInt(vendorSiteId));
        	pqsWfInterfaceLineTo.setVendorSiteCode(poVendorSitesAllTo.getVendorSiteCode());
        	
        	double ratio = 0;
        	if( applyCompareTo.getRatio() != null ){
        		ratio = applyCompareTo.getRatio();
        	}
        	double allocation = (ratio);
        	
        	pqsWfInterfaceLineTo.setAllocation(allocation);
        	pqsWfInterfaceLineTo.setCurrency(inquirySupplierTo.getInquiryCurrency());
        	
        	double quoteRealTotal = 0;
        	if( quoteHeaderTo.getQuoteRealTotal() != null ){
        		quoteRealTotal = quoteHeaderTo.getQuoteRealTotal();
        	}
        	pqsWfInterfaceLineTo.setPriceNew(quoteRealTotal);

        	double quoteTftTotal = 0;
        	if( quoteHeaderTo.getQuoteTftTotal() != null ){
        		quoteTftTotal = quoteHeaderTo.getQuoteTftTotal();
        	}
        	
        	pqsWfInterfaceLineTo.setCpPriceNew(quoteTftTotal);
        	String taxRate = quoteHeaderTo.getQuoteTax();
        	logger.debug("taxRate " + taxRate );
        	ErpTaxTo erpTaxTo= erpTaxDao.getTaxByTaxRate(taxRate);
        	erpTaxTo = null != erpTaxTo ? erpTaxTo : new ErpTaxTo();
        	
        	pqsWfInterfaceLineTo.setTaxId(Integer.parseInt( erpTaxTo.getTaxId()) );
        	pqsWfInterfaceLineTo.setTaxName(erpTaxTo.getName());
        	
        	//Paper 有效日
        	String paperVerStartDt = inquiryHeaderTo.getPaperVerStartDt();
        	String paperVerEndDt = inquiryHeaderTo.getPaperVerEndDt();
        	
        	pqsWfInterfaceLineTo.setQuoStartDateNew(sdf2.parse(paperVerStartDt));
        	pqsWfInterfaceLineTo.setQuoEndDateNew(sdf2.parse(paperVerEndDt));
        	       	
        	pqsWfInterfaceLineTo.setConditionsTermNew(poVendorSitesAllTo.getFobLookupCode());
        	
        	String deliveryLocation = inquirySupplierTo.getInquiryDeliveryLocation();
        	ErpDeliveryLocationTo erpDeliveryLocationTo =  erpDeliveryLocationDao.getErpDeliveryLocation(deliveryLocation);
        	
        	pqsWfInterfaceLineTo.setShipLocationId(Integer.parseInt(deliveryLocation));
        	pqsWfInterfaceLineTo.setShipLocation(erpDeliveryLocationTo.getLocationCode());
        	pqsWfInterfaceLineTo.setBillToLocId(Integer.parseInt(deliveryLocation));
        	
        	String freightTermsCode = inquirySupplierTo.getInquiryShippedBy();
        	pqsWfInterfaceLineTo.setFreightTermsCode(freightTermsCode);
        	ErpFreightTermsCodeTo  erpFreightTermsCodeTo = erpFreightTermsCodeDao.getFreightTermsCode(freightTermsCode);
        	pqsWfInterfaceLineTo.setFreightTerms(erpFreightTermsCodeTo.getMeaning());
        	//pqsWfInterfaceLineTo.setComments(inquiryHeaderTo.getInquiryRemark());
        	pqsWfInterfaceLineTo.setComments("");
        	String termId = poVendorSitesAllTo.getTermsId();
        	
        	//Get ErpPayment
        	ErpPaymentTo erpPaymentTo = erpPaymentDao.getPayment(termId);
        	pqsWfInterfaceLineTo.setPaymentMethodNewId(Integer.parseInt(termId));
        	pqsWfInterfaceLineTo.setPaymentMethodNew(erpPaymentTo.getName());
        	
        	
        	/******************************************************************
        	 * 舊報價資料
        	 *****************************************************************/
        	ErpCltWfQuoLineVTo erpCltWfQuoLineVTo = erpCltWfQuoLineVDao.getCltWfQuoLine(vendorId, partNum);
        	if( erpCltWfQuoLineVTo != null){
        		pqsWfInterfaceLineTo.setErpQuoId(Integer.parseInt(erpCltWfQuoLineVTo.getqNo()) );
        		pqsWfInterfaceLineTo.setErpQuoLineNo(Integer.parseInt(erpCltWfQuoLineVTo.getPoLineId()) );
        		
            	pqsWfInterfaceLineTo.setQuoStartDateOld(erpCltWfQuoLineVTo.getStartDate());
            	pqsWfInterfaceLineTo.setQuoEndDateOld(erpCltWfQuoLineVTo.getEndDate());
            	pqsWfInterfaceLineTo.setPaymentMethodOld(erpCltWfQuoLineVTo.getPaymentTerm());
            	pqsWfInterfaceLineTo.setConditionsTermOld(erpCltWfQuoLineVTo.getFobLookupCode());
            	
            	double oldPrice = 0;
            	if( erpCltWfQuoLineVTo.getPriceOverride() != null ){
            		oldPrice =  Double.parseDouble(erpCltWfQuoLineVTo.getPriceOverride());
            	}
            	pqsWfInterfaceLineTo.setPriceOld(oldPrice);
            	pqsWfInterfaceLineTo.setCpPriceQld(oldPrice);
        	
            	
        	}
        	
        	pqsWfInterfaceLineList.add(pqsWfInterfaceLineTo);
        	logger.debug(pqsWfInterfaceLineTo.toString());
        	logger.debug("------------------------------------------");
        }
        logger.debug(pqsWfInterfaceLineList.toString());
        
		//Insert DB
		TransactionCallbackWithoutResult callback = new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(
					final TransactionStatus status) {

				//Insert DB
				compareHeaderDao.instCompareHeader(compareHeaderTo);
				compareMapQuoteDao.batchInstCompareMapQuote(compareMapQuoteList);
				inquiryHeaderDao.updateInquiryCompareStatus(inquiryHeaderUid, compareStatus);
				pqsWfInterfaceHDao.instPQSWfInterfaceH(pqsWfInterfaceHTo);
				pqsWfInterfaceLineDao.batchInstPQSWfInterfaceLine(pqsWfInterfaceLineList);
			}
		};
		
		try {
			new CompareHeaderDao().doInTransaction(callback);

			// 1.3 Set AJAX response
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			StringBuilder sb = new StringBuilder();
			PrintWriter out = response.getWriter();
						
			JSONArray jsonObject = JSONArray.fromObject(compareHeaderTo);
			logger.debug("returnJosnString " + jsonObject.toString());
			
			sb.append(jsonObject.toString());
			out.println(sb.toString());
			out.close();
			
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

		}
        
		return NONE;
	}
	
	
	


	public String getNextFormInquiryNum() {
		return nextFormInquiryNum;
	}

	public void setNextFormInquiryNum(String nextFormInquiryNum) {
		this.nextFormInquiryNum = nextFormInquiryNum;
	}

	public String getNextFormPaperVerUid() {
		return nextFormPaperVerUid;
	}

	public void setNextFormPaperVerUid(String nextFormPaperVerUid) {
		this.nextFormPaperVerUid = nextFormPaperVerUid;
	}

	public String getQueryQuoteFormInquiryNum() {
		return queryQuoteFormInquiryNum;
	}

	public void setQueryQuoteFormInquiryNum(String queryQuoteFormInquiryNum) {
		this.queryQuoteFormInquiryNum = queryQuoteFormInquiryNum;
	}

	public String getQueryQuoteFormPaperVerUid() {
		return queryQuoteFormPaperVerUid;
	}

	public void setQueryQuoteFormPaperVerUid(String queryQuoteFormPaperVerUid) {
		this.queryQuoteFormPaperVerUid = queryQuoteFormPaperVerUid;
	}
	
}
