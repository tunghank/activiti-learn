package com.clt.quotation.config.action;

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
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.clt.quotation.config.dao.QuoPaperDao;
import com.clt.quotation.config.dao.QuoPaperFieldDao;
import com.clt.quotation.config.dao.QuoPaperGroupDao;
import com.clt.quotation.config.dao.QuoPaperVerDao;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import com.clt.quotation.config.to.QuoPaperFieldTo;
import com.clt.quotation.config.to.QuoPaperGroupTo;
import com.clt.quotation.config.to.QuoPaperTo;
import com.clt.system.to.SysUserTo;
import com.clt.system.util.BaseAction;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.JsonUtil;

public class QuoPaperItemEditAction extends BaseAction {
	
	public String quoPaperItemsSave() throws Exception {

		String data = request.getParameter("data");
		data = null != data ? data : "";
		logger.debug("data " + data);
		
		String paperVerUid = request.getParameter("paperVerUid");
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid " + paperVerUid);
		
		final List<QuoPaperGroupTo> groupFieldsList= JsonUtil.getList4Json(data , QuoPaperGroupTo.class);
		logger.debug("fieldList.size " + groupFieldsList.size());
		logger.debug("fieldList.data " + groupFieldsList.toString());	
		
		final QuoPaperDao paperDao = new QuoPaperDao();
		final QuoPaperGroupDao paperGroupDao = new QuoPaperGroupDao();
		final QuoPaperFieldDao paperFieldDao = new QuoPaperFieldDao();
		final QuoPaperVerDao paperVerDao = new QuoPaperVerDao();
		
		SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		QuoCatalogPaperVerTo quoCatalogPaperVerTo = paperVerDao.getPaperByVer(paperVerUid);
		String paperUid = quoCatalogPaperVerTo.getPaperUid();
		logger.debug("paperUid " + paperUid);
			
		final List<QuoPaperGroupTo> paperGroupList = paperGroupDao.getGroupByPaperVer(paperVerUid);
		/*********************************************************************
		 * 1.Check 是否版本資料已經 Save , 如果沒有就自動存入
		 * 2.Check 是否版本資料已經 Save , 如果有就 Save 成新版
		 *********************************************************************/
		String newPaperVerUid = "";
	    if (!data.equals("")) {
	    	logger.debug("Data not null");
	    	//Check 是否 有版本已經 將版面配置存入
	    	
	    	double currVer = paperVerDao.getMaxVerByPaper(paperUid);
	    	NumberFormat nft = new DecimalFormat("###.#");
    		SimpleDateFormat ddf = new SimpleDateFormat( "yyyyMMdd" );
    		SimpleDateFormat dtdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
    		Calendar now = Calendar.getInstance();
    		final QuoCatalogPaperVerTo paperVerTo = new QuoCatalogPaperVerTo();
	    	if(paperGroupList != null ){
	    		//版本資料已經 有Save , 如果有就 進版本    		
	    		newPaperVerUid = UUID.randomUUID().toString().toUpperCase();
	    		
	    		paperVerTo.setPaperVerUid(newPaperVerUid);
	    		paperVerTo.setPaperUid(paperUid);
	    		
	    		double nextVer = currVer + 0.1;
				paperVerTo.setPaperVer(nft.format(nextVer));
				logger.debug("nextVer " + nft.format(nextVer));
				paperVerTo.setPaperVerApprove("0");
				paperVerTo.setVerCreateBy(curUser.getUserId());
				

	    	}else{
	    		//版本資料 沒 有Save , 取得現有版本v1.0
	    		newPaperVerUid = paperVerUid;
	    		//logger.debug("nextVer " + nft.format(currVer));
	    	}
	    	//logger.debug("paperVerUid " + paperVerUid);
	    	//logger.debug("newPaperVerUid " + newPaperVerUid);
	    	
	    	String paperGroupUid = "";
	    	
    		for(int i=0;i < groupFieldsList.size(); i++){
    			QuoPaperGroupTo quoPaperGroupTo = groupFieldsList.get(i);
    			paperGroupUid = UUID.randomUUID().toString().toUpperCase();
    			logger.debug("paperGroupUid " + paperGroupUid);
    	    	//Set Group Object
    			quoPaperGroupTo.setPaperGroupUid(paperGroupUid);
    			quoPaperGroupTo.setPaperGroupName(quoPaperGroupTo.getPaperGroupName());
    			quoPaperGroupTo.setPaperVerUid(newPaperVerUid);
    			quoPaperGroupTo.setPaperCreateBy(curUser.getUdt());
    			quoPaperGroupTo.setPaperGroupSeq(quoPaperGroupTo.getPaperGroupSeq());
    	    	
    	    	//Set Item Object
    	    	List<QuoPaperFieldTo> fieldList = quoPaperGroupTo.getFieldList();
    	    	   	    	
    	    	//logger.debug(fieldList.size());
    	    	String paperFieldUid = "";
    	    	for(int j = 0; j < fieldList.size() ; j++ ){
    	    		JSONObject jsonObject = JSONObject.fromObject(fieldList.get(j)); 
    	    		QuoPaperFieldTo fieldTo = (QuoPaperFieldTo) JSONObject.toBean(jsonObject, QuoPaperFieldTo.class); 

    	    		paperFieldUid = UUID.randomUUID().toString().toUpperCase();
    	    		fieldTo.setPaperGroupUid(paperGroupUid);
    	    		fieldTo.setPaperFieldUid(paperFieldUid);
    	    		fieldList.set(j, fieldTo);
    	    		//logger.debug(fieldTo.toString());
    	    	}
    	    	quoPaperGroupTo.setFieldList(fieldList);
    	    	groupFieldsList.set(i,quoPaperGroupTo);
    	    	
    		}
	    	
    		//logger.debug(groupFieldsList.toString());
    		
			TransactionCallbackWithoutResult callback = new TransactionCallbackWithoutResult() {
				public void doInTransactionWithoutResult(
						final TransactionStatus status) {	
					if(paperGroupList != null ){
						paperVerDao.instPaperVerByKey(paperVerTo);
					}
					for(int i=0;i < groupFieldsList.size(); i++){
						QuoPaperGroupTo quoPaperGroupTo = groupFieldsList.get(i);
						paperGroupDao.instPaperGroup(quoPaperGroupTo);
						paperFieldDao.batchInstPaperFields(quoPaperGroupTo.getFieldList());
					}

				}
			};
			
			try {
				new QuoPaperGroupDao().doInTransaction(callback);
				
				//取回儲存完的Data Quo -> Ver -> Group -> Fileds
				QuoPaperTo quoPaperTo = paperDao.getPapersByKey(paperUid);
				List<QuoCatalogPaperVerTo> quoVersionList = paperVerDao.getVerByPaper(paperUid);
				/*for(int i=0;i <quoVersionList.size(); i++){
					QuoCatalogPaperVerTo quoPaperVerTo = quoVersionList.get(i);
					//只取到 現有版本的 ITEM LIST
					if(paperVerTo.getPaperVerUid().equals(quoPaperVerTo.getPaperVerUid())){
						
						logger.debug(paperVerTo.getPaperVer());
						logger.debug(paperVerTo.getPaper());
						logger.debug(paperVerTo.getPaperVerUid());
						
						List<QuoPaperGroupTo> quoPaperGroupList = paperGroupDao.getGroupByPaperVer(quoPaperVerTo.getPaperVerUid());
						quoPaperGroupList = null != quoPaperGroupList ? quoPaperGroupList : new ArrayList<QuoPaperGroupTo>();
						
						for(int j=0;j <quoPaperGroupList.size(); j++){
							QuoPaperGroupTo quoPaperGroupTo = quoPaperGroupList.get(j);
							List<QuoPaperFieldTo> quoPaperFieldList = paperFieldDao.getFildByGroup(quoPaperGroupTo.getPaperGroupUid());
							quoPaperFieldList = null != quoPaperFieldList ? quoPaperFieldList : new ArrayList<QuoPaperFieldTo>();
							
							quoPaperGroupTo.setFieldList(quoPaperFieldList);
							quoPaperGroupList.set(j, quoPaperGroupTo);
						}
						quoPaperVerTo.setGroupList(quoPaperGroupList);
						quoVersionList.set(i, quoPaperVerTo);
					}
				}*/
				quoPaperTo.setVersionList(quoVersionList);
				// 1.3 Set AJAX response
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("UTF-8");
				StringBuilder sb = new StringBuilder();
				PrintWriter out = response.getWriter();
				
				
				JSONArray jsonObject = JSONArray.fromObject(quoPaperTo);
				sb.append(jsonObject.toString());
				logger.debug(sb);
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
				return NONE;
			}
			
			
	    }else{
	    	logger.debug("Data is null");
	    }
		
		return NONE;
	}


	public String quoPaperItemDelete() throws Exception {
		
		String paperGroupUid = request.getParameter("paperGroupUid");
		paperGroupUid = null != paperGroupUid ? paperGroupUid : "";
		
		logger.debug("paperGroupUid " + paperGroupUid);
		
		final QuoPaperGroupDao paperGroupDao = new QuoPaperGroupDao();
		final QuoPaperFieldDao paperFieldDao = new QuoPaperFieldDao();
		
    	final String fianalPaperGroupUid = paperGroupUid;
    	
		TransactionCallbackWithoutResult callback = new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(
					final TransactionStatus status) {
				paperFieldDao.delFieldByGroupUid(fianalPaperGroupUid);
				paperGroupDao.delPaperGroupByKey(fianalPaperGroupUid);
					
			}
		};
		
		try {
			new QuoPaperGroupDao().doInTransaction(callback);
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
			return NONE;
		}
				
		return NONE;
	}

	public String quoPaperItemPreview() throws Exception {
		
		String paperVerUid = request.getParameter("paperVerUid");
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		
		logger.debug("paperVerUid " + paperVerUid);

		request.setAttribute("paperVerUid", paperVerUid);
		return SUCCESS;
	}
	
	public String ajaxPaperFildByPaperVer() throws Exception {
		
		String paperVerUid = request.getParameter("paperVerUid");
		paperVerUid = null != paperVerUid ? paperVerUid : "";
		logger.debug("paperVerUid " + paperVerUid);
		
		QuoPaperGroupDao paperGroupDao = new QuoPaperGroupDao();
		QuoPaperFieldDao paperFieldDao = new QuoPaperFieldDao();
		
		List<QuoPaperGroupTo> paperGroupList = paperGroupDao.getGroupByPaperVer(paperVerUid);
		paperGroupList = null != paperGroupList ? paperGroupList : new ArrayList<QuoPaperGroupTo>();
		
		for(int i=0; i <paperGroupList.size(); i++ ){
			QuoPaperGroupTo groupTo = paperGroupList.get(i);
			List<QuoPaperFieldTo> fieldNewList = paperFieldDao.getFildByGroup(groupTo.getPaperGroupUid());
			fieldNewList = null != fieldNewList ? fieldNewList : new ArrayList<QuoPaperFieldTo>();
			
			groupTo.setFieldList(fieldNewList);
			paperGroupList.set(i, groupTo);
		}
		
		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		
		
		JSONArray jsonObject = JSONArray.fromObject(paperGroupList);
		sb.append(jsonObject.toString());
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}
	
}
