package com.cista.report.action;


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

import com.cista.report.dao.FoundryWipDao;
import com.cista.system.to.ExtJSGridTo;
import com.cista.report.to.FoundryWipQueryTo;
import com.cista.report.to.FoundryWipTo;
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
//JExcel
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class FoundryWip extends BaseAction{
	

	private static final long serialVersionUID = 1L;

	public String FoundryWipPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}
	
	public String QueryFoundryWip() throws Exception{
		
		try {
			request= ServletActionContext.getRequest();
			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart;//分頁。。。每頁開始數據
	        int iLimit;//分頁。。。每一頁數據
	        
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
			
			FoundryWipQueryTo queryTo = gson.fromJson(queryEl.toString(), FoundryWipQueryTo.class);
			logger.debug("queryTo " + queryTo.toString());		
			
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
			
			String cistaProject = queryTo.getCistaProject();
			cistaProject = null != cistaProject ? cistaProject : "";
			logger.debug("cistaProject " + cistaProject);
			cistaProject = cistaProject.replaceAll("\\'", "");
			cistaProject = cistaProject.trim();
			logger.debug("cistaProject " + cistaProject);
			
			String vendorCode = "10001";
			
			FoundryWipDao foundryWipDao = new FoundryWipDao();		
	
			List<FoundryWipTo> wipList = foundryWipDao.getFountryWip(vendorCode, lot, cistaProject);
			
			
			//logger.debug("userList Size " + userList.size());
			//分頁
			if( wipList!= null){
				total=wipList.size();
				logger.debug("total " + total);
				
				int end=iStart+iLimit;
				if(end>total){//不能總數
					end=total;
				}		
				
				List<FoundryWipTo> resultList = new ArrayList<FoundryWipTo>();
				for(int i=iStart;i<end;i++){//只加載當前頁面數據
					//logger.debug(userList.get(i).toString());
					resultList.add(wipList.get(i));
				}
				
				
				//logger.debug(resultList.toString());
				
				ExtJSGridTo extJSGridTo = new ExtJSGridTo();
				extJSGridTo.setTotal(total);
				extJSGridTo.setRoot(resultList);
				
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
	
	


    
}
