package com.cista.cpYield.action;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;


import com.cista.cpYield.dao.CpYieldLotDao;
import com.cista.system.to.ExtJSGridTo;
import com.cista.cpYield.to.CpYieldLotTo;
import com.cista.cpYield.to.CpYieldQueryTo;
import com.cista.cpYield.to.CpYieldReportTo;


import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import com.google.gson.JsonParser;


public class CpYieldBackToSMIC extends BaseAction{
	

	private static final long serialVersionUID = 1L;

	public String CpYieldBackToSMICPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}
	
	public String QueryCpYieldBackToSMIC() throws Exception{
		
		try {
			request= ServletActionContext.getRequest();

			String query = request.getParameter("query");
			query = null != query ? query : "";
			logger.debug("query " + query);

			Gson gson = new Gson();		

	        // 創建一個JsonParser  
	        JsonParser parser = new JsonParser();  
	        JsonElement jsonEl = parser.parse(query);            
	        JsonObject jsonObj = null;  
	        jsonObj = jsonEl.getAsJsonObject();//轉換成Json對象
	        
	        JsonElement queryEl =jsonObj.get("query");//query 節點
			
	        CpYieldQueryTo queryTo = gson.fromJson(queryEl.toString(), CpYieldQueryTo.class);
	        
			this.QueryData(queryTo);

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
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

	public String UpdateCpYieldBackToSmicFlag() throws Exception{
		
		try {
			request= ServletActionContext.getRequest();

	        
			String query = request.getParameter("query");
			query = null != query ? query : "";
			logger.debug("query " + query);

			Gson gson = new Gson();		

	        // 創建一個JsonParser  
	        JsonParser parser = new JsonParser();  
	        JsonElement jsonEl = parser.parse(query);            
	        JsonObject jsonObj = null;  
	        jsonObj = jsonEl.getAsJsonObject();//轉換成Json對象
	        
	        JsonElement queryEl =jsonObj.get("query");//query 節點
			
	        CpYieldQueryTo queryTo = gson.fromJson(queryEl.toString(), CpYieldQueryTo.class);
			logger.debug("queryTo " + queryTo.toString());		
			
			//1.0 處理Query 字串
	        String cpYieldUuid = queryTo.getCpYieldUuid();
	        cpYieldUuid = null != cpYieldUuid ? cpYieldUuid : "";
	        logger.debug("cpYieldUuid " + cpYieldUuid);
            
	        String cpYieldUuidArray[]=cpYieldUuid.split(",");
	        List<String> cpYieldUuidList = new ArrayList<String>();
	        //1.1 Update Send Flag
	        if(cpYieldUuidArray.length > 0 ){
	        	for(int i=0; i < cpYieldUuidArray.length; i ++){
	        		cpYieldUuidList.add(cpYieldUuidArray[i]);
	        	}
	        	CpYieldLotDao cpYieldLotDao = new CpYieldLotDao();
	        	cpYieldLotDao.updateCpYieldLotSendFlg(cpYieldUuidList);
	        }
	        queryTo.setFtpFlag("");
	        
            this.QueryData(queryTo);

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
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
    
	public String QueryData(CpYieldQueryTo queryTo) throws Exception{
		
		try {
			request= ServletActionContext.getRequest();
			
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			
			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart;//分頁。。。每頁開始數據
	        int iLimit;//分頁。。。每一頁數據
	        

			logger.debug("queryTo " + queryTo.toString());		
			
			//1.0 處理Query 字串
	        String start = queryTo.getStart();
            String limit = queryTo.getLimit();
            iStart = Integer.parseInt(start);
            iLimit = Integer.parseInt(limit);
            
            logger.debug("start: " + Integer.parseInt(start));
            logger.debug("limit: " + limit);
			
            
			String lot = queryTo.getLot();
			lot = null != lot ? lot : "";
			logger.debug("lot " + lot);
			lot = lot.replaceAll("\\'", "");
			lot = lot.trim();
			logger.debug("lot " + lot);
			
			String ftpFlag = queryTo.getFtpFlag();
			ftpFlag = null != ftpFlag ? ftpFlag : "";
			logger.debug("ftpFlag " + ftpFlag);
			ftpFlag = ftpFlag.replaceAll("\\'", "");
			ftpFlag = ftpFlag.trim();
			logger.debug("ftpFlag " + ftpFlag);
			
			String sCdt = queryTo.getsCdt();
			sCdt = null != sCdt ? sCdt : "";
			logger.debug("sCdt " + sCdt);
			sCdt = sCdt.replaceAll("\\'", "");
			sCdt = sCdt.trim();
			if(!sCdt.equals("")){
				df1.parse(sCdt);
				sCdt = df2.format(df1.parse(sCdt)).toString();
			}
			
			String sFtpSendTime = queryTo.getsFtpSendTime();
			sFtpSendTime = null != sFtpSendTime ? sFtpSendTime : "";
			logger.debug("sFtpSendTime " + sFtpSendTime);
			sFtpSendTime = sFtpSendTime.replaceAll("\\'", "");
			sFtpSendTime = sFtpSendTime.replaceAll("null", "");
			sFtpSendTime = sFtpSendTime.trim();
			if(!sFtpSendTime.equals("")){
				df1.parse(sFtpSendTime);
				sFtpSendTime = df2.format(df1.parse(sFtpSendTime)).toString();
			}
			
			logger.debug("sFtpSendTime " + sFtpSendTime);
			queryTo.setFtpFlag(ftpFlag);
			queryTo.setsCdt(sCdt);
			queryTo.setsFtpSendTime(sFtpSendTime);
			queryTo.setLot(lot);

			logger.debug("queryTo " + queryTo.toString());
			
            CpYieldLotDao cpYieldLotDao = new CpYieldLotDao();
			List<CpYieldReportTo> cpYieldList = cpYieldLotDao.getBins(queryTo);
			
			
			//logger.debug("userList Size " + userList.size());
			
			//分頁
			if( cpYieldList!= null){
				total=cpYieldList.size();
				logger.debug("total " + total);
				
				int end=iStart+iLimit;
				if(end > total){//不能總數
					end = total;
				}		
				
				List<CpYieldReportTo> resultList = new ArrayList<CpYieldReportTo>();
				for(int i=iStart;i<end;i++){//只加載當前頁面數據
					//logger.debug(userList.get(i).toString());
					resultList.add(cpYieldList.get(i));
				}
				
				
				//logger.debug(resultList.toString());
				
				ExtJSGridTo extJSGridTo = new ExtJSGridTo();
				extJSGridTo.setTotal(total);
				extJSGridTo.setRoot(resultList);
				Gson gson = new Gson();
				String jsonData = gson.toJson(extJSGridTo);
				//logger.debug(jsonData);
				
				// 1.5 Set AJAX response
				CistaUtil.ajaxResponseData(response, jsonData);
			}else{
				CistaUtil.ajaxResponseData(response, "");
			}

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
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
	
	
	public String OpenCpYiledFile() throws Exception{
		try {


			String cpYieldUuid = request.getParameter("cpYieldUuid");
			cpYieldUuid = null != cpYieldUuid ? cpYieldUuid : "";
			logger.debug("cpYieldUuid " + cpYieldUuid);
			
			CpYieldLotDao cpYieldLotDao = new CpYieldLotDao();
			CpYieldLotTo cpYieldLot = cpYieldLotDao.getCpYiledLotFile(cpYieldUuid);
			String fileName , fileFullName , contentType, ftpFolder , uuid;
			ftpFolder = CistaUtil.getConfig("config.ftp.folder");
						
			if( cpYieldLot != null ){
				uuid = cpYieldLot.getCpYieldUuid();
				contentType = cpYieldLot.getFileMimeType();
				fileFullName = ftpFolder +  File.separator + cpYieldLot.getFileName().replaceAll("\\\\","\\\\\\\\");
				File rawFile = new File(fileFullName);
				fileName = rawFile.getName().replaceAll("_" + uuid , "");
				CistaUtil.downLoadFile(request, response, fileName, fileFullName, contentType);
			}
	        return NONE;
		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
			return ERROR;
		}
		
	}
}
