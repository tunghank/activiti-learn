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

public class QuoPaperQuoteAction extends BaseAction {
	

	
	public String quoPaperQuotePre() throws Exception {
		
		String quoteHeaderUid = (String) request.getParameter("quoteHeaderUid");
		quoteHeaderUid = null != quoteHeaderUid ? quoteHeaderUid : "" ;
		logger.debug("quoteHeaderUid " + quoteHeaderUid);
		//Mail Link 時沒有Login 時, 取到的Quote Number
		if( quoteHeaderUid.equals("") ){
			quoteHeaderUid = (String)request.getAttribute("quoteHeaderUid");
			quoteHeaderUid = null != quoteHeaderUid ? quoteHeaderUid : "" ;
		}
		//1.0 Check 這張 Quotation 是否這一個廠商可以填寫.
		//1.0.1 抓取廠商Login 資訊
		//Get Currency User
        SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		String company = curUser.getCompany();
		logger.debug("company " + company);
		QuoteHeaderDao quoteHeaderDao = new QuoteHeaderDao();
		QuoteHeaderTo quoteHeaderTo = quoteHeaderDao.getQuoteHeaderByKey(quoteHeaderUid);
		logger.debug("quoteHeaderTo.getQuoteSupplierCode() " + quoteHeaderTo.getQuoteSupplierCode());
		
		if(quoteHeaderTo != null ){
			if(!company.equals(quoteHeaderTo.getQuoteSupplierCode())){
				addActionMessage(CLTUtil.getMessage("Quote.error.not.your"));
				return ERROR;
			}
		}else{
			addActionMessage("ERROR");
			return ERROR;
		}
		//1.1 Prepare Data
		String inquiryHeaderUid = quoteHeaderTo.getInquiryHeaderUid();
		
		InquiryHeaderDao inquiryHeaderDao = new InquiryHeaderDao();
		InquiryPartNumDao InquiryPartNumDao = new InquiryPartNumDao();
				
		InquiryHeaderTo inquiryHeaderTo = inquiryHeaderDao.getInquiryHeaderByKey(inquiryHeaderUid);
		
		String inquirySupplierCode = quoteHeaderTo.getQuoteSupplierCode();
		
		InquirySupplierDao inquirySupplierDao = new InquirySupplierDao();
		InquirySupplierTo inquirySupplierTo = inquirySupplierDao.getInquirySupplier(inquiryHeaderUid, inquirySupplierCode);
		
		QuoPaperVerDao paperVerDao = new QuoPaperVerDao();
		QuoCatalogPaperVerTo catalogPaperVerTo= paperVerDao.getPaperByVer(inquiryHeaderTo.getPaperVerUid());
		catalogPaperVerTo = null != catalogPaperVerTo ? catalogPaperVerTo : new QuoCatalogPaperVerTo();
		String catalog = catalogPaperVerTo.getCatalog();
		String paper = catalogPaperVerTo.getPaper();
		String paperVer = catalogPaperVerTo.getPaperVer();
		String paperVerUid = catalogPaperVerTo.getPaperVerUid();
		
		String quotPaperDesc = catalog + "-" +  paper + " Ver " + paperVer;
		inquiryHeaderTo.setQuotPaperDesc(quotPaperDesc);

		//Get Delivery Location
		ErpDeliveryLocationDao deliveryLocationDao = new ErpDeliveryLocationDao();
		ErpDeliveryLocationTo deliveryLocationTo = deliveryLocationDao.getErpDeliveryLocation(inquirySupplierTo.getInquiryDeliveryLocation());
		inquirySupplierTo.setInquiryDeliveryLocationDesc(deliveryLocationTo.getLocationCode());
		//Get 奇菱聯絡人
		String cltContactUserID = inquiryHeaderTo.getCltInquiryUser();
		UserDao userDao = new UserDao();
		SysUserTo cltContactUser = userDao.getUserDetail(cltContactUserID);
		
		//Tax List
		ErpTaxDao erpTaxDao = new ErpTaxDao();
		List<ErpTaxTo> erpTaxRateList = erpTaxDao.getAllTaxRate();
		
		
		request.setAttribute("quoteNum", quoteHeaderTo.getQuoteNum());
		request.setAttribute("quoteHeaderUid", quoteHeaderTo.getQuoteHeaderUid());
		request.setAttribute("inquiryHeaderTo", inquiryHeaderTo);
		request.setAttribute("inquirySupplierTo", inquirySupplierTo);
		request.setAttribute("quoteHeaderTo", quoteHeaderTo);
		request.setAttribute("cltContactUser", cltContactUser);
		request.setAttribute("erpTaxRateList", erpTaxRateList);
		
		//取出先前存入的舊Data
		final QuoteRecordDao quoteRecordDao = new QuoteRecordDao();
		final QuoteRecordTotalDao quoteRecordTotalDao = new QuoteRecordTotalDao();
		
		QuoPaperGroupDao quoPaperGroupDao =  new QuoPaperGroupDao();
		//1.0 List All Paper Ver's All Group (大項)
		List<QuoPaperGroupTo> quoPaperGroupList = quoPaperGroupDao.getGroupByPaperVer(paperVerUid);
		for(int i=0; i < quoPaperGroupList.size(); i ++ ){
			QuoPaperGroupTo quoPaperGroupTo = quoPaperGroupList.get(i);
			String paperGroupUid = quoPaperGroupTo.getPaperGroupUid();
			//1.1取出Recored Total.
			QuoteRecordTotalTo quoteRecordTotalTo = 
				quoteRecordTotalDao.getQuoteRecordTotalByGroup(quoteHeaderUid, paperVerUid, paperGroupUid);
			//1.1.1 Set Group Total
			if( quoteRecordTotalTo != null ){
				quoPaperGroupTo.setPaperRecordTotal(quoteRecordTotalTo.getRecordTotal());
			}
			
			//1.2取出Recored
			List<QuoteRecordTo> savedRecordCountList = 
				quoteRecordDao.getQuoteRecordCountByGroup(quoteHeaderUid, paperVerUid, paperGroupUid);
			
			List<String> saveFieldValueList = new ArrayList<String>();
			
			if(savedRecordCountList != null ){
				for (int k=0; k <savedRecordCountList.size(); k ++){
					QuoteRecordTo quoteRecordRowTo = savedRecordCountList.get(k);
					int recordRowSeq = quoteRecordRowTo.getRecordRowSeq();
					//1.3 取出每一列的Value Set to HashMap
					List<QuoteRecordTo> savedRecordList = 
						quoteRecordDao.getQuoteRecordByGroup(quoteHeaderUid, paperVerUid, paperGroupUid, recordRowSeq);
					HashMap savedFieldValueMap = new HashMap();
					for(int j=0;j <savedRecordList.size(); j++ ){
						QuoteRecordTo quoteRecordTo = savedRecordList.get(j);
						String key = "item" + String.valueOf( quoteRecordTo.getFieldSeq() );
						String value = quoteRecordTo.getRecordValue();
						savedFieldValueMap.put(key, value);
					}
					//Map 轉Json
					
					String josnString = JsonUtil.getJsonString4JavaPOJO(savedFieldValueMap);
					saveFieldValueList.add(josnString);
																	
					logger.debug("saveFieldValueList " + saveFieldValueList.toString());
				}
			}
			quoPaperGroupTo.setFieldValueList(saveFieldValueList);
			
			quoPaperGroupList.set(i, quoPaperGroupTo);
		}
		
		// 1.3 Set response Value

		StringBuilder returnJosnString = new StringBuilder();
		JSONArray jsonObject = JSONArray.fromObject(quoPaperGroupList);
		logger.debug("returnJosnString " + jsonObject.toString());
		returnJosnString.append(jsonObject.toString());
		
		request.setAttribute("returnJosnString", returnJosnString);
		
		return SUCCESS;
	}
	
	public String quoPaperQuoteSave() throws Exception {
		
		String data = request.getParameter("data");
		data = null != data ? data : "";
		logger.debug("data " + data);
		
		final List<QuoPaperGroupTo> groupFieldsList= JsonUtil.getList4Json(data , QuoPaperGroupTo.class);
		logger.debug(groupFieldsList.toString());	
		
		String paperVerUid = request.getParameter("paperVerUid");
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid " + paperVerUid);
		
		String quoteHeaderUid = request.getParameter("quoteHeaderUid");
		quoteHeaderUid = null != quoteHeaderUid ? quoteHeaderUid : "";
		logger.debug("quoteHeaderUid " + quoteHeaderUid);
		
		final QuoteHeaderDao quoteHeaderDao = new QuoteHeaderDao();
		final QuoteRecordDao quoteRecordDao = new QuoteRecordDao();
		final QuoteRecordTotalDao quoteRecordTotalDao = new QuoteRecordTotalDao();
		final QuoteHeaderTo quoteHeader = quoteHeaderDao.getQuoteHeaderByKey(quoteHeaderUid);
		
		QuoPaperFieldDao quoPaperFieldDao =  new QuoPaperFieldDao();
		
		/*********************************************************************
		 * 					Save Total Form Data
		 *********************************************************************/
		String totalFormQuoTotal = request.getParameter("totalFormQuoTotal");
		totalFormQuoTotal = null != totalFormQuoTotal ? totalFormQuoTotal : "0";
		logger.debug("totalFormQuoTotal " + totalFormQuoTotal);
		if(totalFormQuoTotal.equals("")){
			totalFormQuoTotal = "0.0";
		}
		
		String totalFormQuoRealTotal = request.getParameter("totalFormQuoRealTotal");
		totalFormQuoRealTotal = null != totalFormQuoRealTotal ? totalFormQuoRealTotal : "0";
		logger.debug("totalFormQuoRealTotal " + totalFormQuoRealTotal);
		if(totalFormQuoRealTotal.equals("")){
			totalFormQuoRealTotal = "0.0";
		}
		
		String totalFormQuoTFTTotal = request.getParameter("totalFormQuoTFTTotal");
		totalFormQuoTFTTotal = null != totalFormQuoTFTTotal ? totalFormQuoTFTTotal : "0";
		if(totalFormQuoTFTTotal.equals("")){
			totalFormQuoTFTTotal = "0.0";
		}
		logger.debug("totalFormQuoTFTTotal " + totalFormQuoTFTTotal);
		
		String totalFormQuoTax = request.getParameter("totalFormQuoTax");
		
		String totalFormQuoNote = request.getParameter("totalFormQuoNote");
		// Get Current User
		SysUserTo curUser =
	            (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		
		quoteHeader.setQuoteOfferPeople(curUser.getUserId());
		quoteHeader.setQuoteTotal(Double.parseDouble(totalFormQuoTotal));
		quoteHeader.setQuoteRealTotal(Double.parseDouble(totalFormQuoRealTotal));
		quoteHeader.setQuoteTftTotal(Double.parseDouble(totalFormQuoTFTTotal));
		quoteHeader.setQuoteTax(totalFormQuoTax);
		quoteHeader.setQuoteNotes(totalFormQuoNote);
		quoteHeader.setQuoteStatus("S");
		
		Date firstQuoteDate = quoteHeader.getFirstQuoteDate();
		Calendar calendar = Calendar.getInstance();
		if( firstQuoteDate == null  ){
			quoteHeader.setFirstQuoteDate(calendar.getTime());
			quoteHeader.setLastQuoteDate(null);
		}else{
			quoteHeader.setFirstQuoteDate(firstQuoteDate);
			quoteHeader.setLastQuoteDate(calendar.getTime());
		}
		
		/********************************************************
		 * 0. 先清掉舊Data
		 * 1. SET Quote Record List for Insert DB
		 * 2. SET Quote Record Total List for Insert DB
		 ********************************************************/
		
		final List<QuoteRecordTo> quoteRecordList = new ArrayList<QuoteRecordTo>();
		final List<QuoteRecordTotalTo> quoteRecordTotalList = new ArrayList<QuoteRecordTotalTo>();
		
		for(int i=0; i < groupFieldsList.size(); i++){
			
			QuoPaperGroupTo quoPaperGroupTo = groupFieldsList.get(i);
			String paperGroupUid = quoPaperGroupTo.getPaperGroupUid();
			double paperGroupTotal = quoPaperGroupTo.getPaperRecordTotal();
			logger.debug("paperGroupTotal " + paperGroupTotal);		
			
			List<QuoPaperFieldTo> quoPaperFieldList = quoPaperFieldDao.getFildByGroup(paperGroupUid);
			List<String> fieldValueList = quoPaperGroupTo.getFieldValueList();
			logger.debug("fieldValue " + fieldValueList.size());
			logger.debug("fieldValue " + fieldValueList.toString());
			
			//此Tab Grid有資料才儲存
			if( fieldValueList.size() > 0 ){
				//SET Quote Record Total object
				String quoteRecordTotalUid = UUID.randomUUID().toString().toUpperCase();
				QuoteRecordTotalTo quoteRecordTotalTo = new QuoteRecordTotalTo();
				quoteRecordTotalTo.setQuoteRecordTotalUid(quoteRecordTotalUid);
				quoteRecordTotalTo.setQuoteHeaderUid(quoteHeaderUid);
				quoteRecordTotalTo.setPaperVerUid(paperVerUid);
				quoteRecordTotalTo.setPaperGroupUid(paperGroupUid);
				quoteRecordTotalTo.setRecordTotal(paperGroupTotal);
				quoteRecordTotalList.add(quoteRecordTotalTo);
				
				for(int k=0; k< fieldValueList.size(); k ++){
					String jsonSring =  JSONObject.fromObject(fieldValueList.get(k)).toString();
					logger.debug(jsonSring);
					HashMap fieldValueMap = (HashMap)JsonUtil.getMap4Json(jsonSring);
					logger.debug(fieldValueMap.toString());
				
					for(int j=0; j <quoPaperFieldList.size() ; j++ ){
						QuoteRecordTo recordTo = new QuoteRecordTo();
						
						QuoPaperFieldTo quoPaperFieldTo = quoPaperFieldList.get(j);
						String hashKey = "item" + quoPaperFieldTo.getFieldSeq();
						String hashValue;
						if( fieldValueMap.get(hashKey) == null ){
							hashValue = null;
						}else{
							hashValue = String.valueOf( fieldValueMap.get(hashKey) ) ;
							hashValue = hashValue.trim();
							
							if( !hashValue.equals("") ){
								//SET Value to Object
								String quoteRecordUid = UUID.randomUUID().toString().toUpperCase();
								recordTo.setQuoteRecordUid(quoteRecordUid);
								recordTo.setQuoteHeaderUid(quoteHeaderUid);
								recordTo.setPaperVerUid(paperVerUid);
								recordTo.setPaperGroupUid(paperGroupUid);
								recordTo.setPaperFieldUid(quoPaperFieldTo.getPaperFieldUid());
								recordTo.setFieldSeq(j);
								recordTo.setRecordRowSeq(k);
								recordTo.setRecordValue(hashValue);
								logger.debug("recordTo " + recordTo);
								quoteRecordList.add(recordTo);
							}
						}
						logger.debug("hashKey -" + hashKey + " - hashValue -" + hashValue);
	
					}//for(int j=0; j <quoPaperFieldList.size() ; j++ ){
					
				}//for(int k=0; k< fieldValueList.size(); k ++){
			}
			
		}//for(int i=0; i < groupFieldsList.size(); i++){
		
		logger.debug("quoteRecordList.size() " + quoteRecordList.size());
		logger.debug("quoteRecordTotalList() " + quoteRecordTotalList.size());
		
		final String fPaperVerUid = paperVerUid;
		final String fQuoteHeaderUid = quoteHeaderUid;
		
		//Insert DB
		TransactionCallbackWithoutResult callback = new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(
					final TransactionStatus status) {
				//Delete Old Data
				quoteRecordDao.deleteQuoteRecord(fQuoteHeaderUid, fPaperVerUid);
				quoteRecordTotalDao.deleteQuoteRecordTotal(fQuoteHeaderUid, fPaperVerUid);
				//Insert DB
				quoteHeaderDao.updateQuoteHeader(quoteHeader);
				quoteRecordDao.batchInstQuoteRecord(quoteRecordList);
				quoteRecordTotalDao.batchInstQuoteRecordTotal(quoteRecordTotalList);
			}
		};
		
		try {
			new QuoteHeaderDao().doInTransaction(callback);
			//Return Save Data.
			QuoteHeaderTo quoteHeaderTo = quoteHeaderDao.getQuoteHeaderByKey(quoteHeaderUid);
			
			QuoPaperGroupDao quoPaperGroupDao =  new QuoPaperGroupDao();
			//1.0 List All Paper Ver's All Group (大項)
			List<QuoPaperGroupTo> quoPaperGroupList = quoPaperGroupDao.getGroupByPaperVer(paperVerUid);
			for(int i=0; i < quoPaperGroupList.size(); i ++ ){
				QuoPaperGroupTo quoPaperGroupTo = quoPaperGroupList.get(i);
				String paperGroupUid = quoPaperGroupTo.getPaperGroupUid();
				//1.1取出Recored Total.
				QuoteRecordTotalTo quoteRecordTotalTo = 
					quoteRecordTotalDao.getQuoteRecordTotalByGroup(quoteHeaderUid, paperVerUid, paperGroupUid);
				//1.1.1 Set Group Total
				if( quoteRecordTotalTo != null ){
					quoPaperGroupTo.setPaperRecordTotal(quoteRecordTotalTo.getRecordTotal());
				}
				
				//1.2取出Recored
				List<QuoteRecordTo> savedRecordCountList = 
					quoteRecordDao.getQuoteRecordCountByGroup(quoteHeaderUid, paperVerUid, paperGroupUid);
				
				List<String> saveFieldValueList = new ArrayList<String>();
				
				if(savedRecordCountList != null ){
					for (int k=0; k <savedRecordCountList.size(); k ++){
						QuoteRecordTo quoteRecordRowTo = savedRecordCountList.get(k);
						int recordRowSeq = quoteRecordRowTo.getRecordRowSeq();
						//1.3 取出每一列的Value Set to HashMap
						List<QuoteRecordTo> savedRecordList = 
							quoteRecordDao.getQuoteRecordByGroup(quoteHeaderUid, paperVerUid, paperGroupUid, recordRowSeq);
						HashMap savedFieldValueMap = new HashMap();
						for(int j=0;j <savedRecordList.size(); j++ ){
							QuoteRecordTo quoteRecordTo = savedRecordList.get(j);
							String key = "item" + String.valueOf( quoteRecordTo.getFieldSeq() );
							String value = quoteRecordTo.getRecordValue();
							savedFieldValueMap.put(key, value);
						}
						//Map 轉Json
						
						String josnString = JsonUtil.getJsonString4JavaPOJO(savedFieldValueMap);
						saveFieldValueList.add(josnString);
																		
						logger.debug("saveFieldValueList " + saveFieldValueList.toString());
					}
				}
				quoPaperGroupTo.setFieldValueList(saveFieldValueList);
				
				quoPaperGroupList.set(i, quoPaperGroupTo);
			}
			quoteHeaderTo.setQuoPaperGroupList(quoPaperGroupList);
			
			// 1.3 Set AJAX response
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			StringBuilder sb = new StringBuilder();
			PrintWriter out = response.getWriter();
			
			
			JSONArray jsonObject = JSONArray.fromObject(quoteHeaderTo);
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
}
