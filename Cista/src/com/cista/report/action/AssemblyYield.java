package com.cista.report.action;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;


import com.cista.report.dao.AssemblyYieldDao;
import com.cista.report.dao.StandardCostDao;

import com.cista.system.to.ExtJSGridTo;

import com.cista.report.to.AssemblyYieldIssueTo;
import com.cista.report.to.AssemblyYieldQueryTo;
import com.cista.report.to.AssemblyYieldReceiveTo;
import com.cista.report.to.StandardCostTo;


import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sun.java_cup.internal.internal_error;
//JExcel
import jxl.CellView;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class AssemblyYield extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	
	public String AssemblyYieldPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}

	public String AssemblyYield() throws Exception{
		
		try {
			request= ServletActionContext.getRequest();
			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart;//分頁。。。每頁開始數據
	        int iLimit;//分頁。。。每一頁數據
	        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
	        
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
			
	        AssemblyYieldQueryTo queryTo = gson.fromJson(queryEl.toString(), AssemblyYieldQueryTo.class);
			logger.debug("queryTo " + queryTo.toString());		
			
	        String project = queryTo.getProject();
	        project = null != project ? project : "";
	        
            String edate = queryTo.getEdate();
            edate = null != edate ? edate : "";
            if(!edate.equals("")){
            	Date dEdate = df1.parse(edate);
            	edate = df2.format(dEdate);
            }
            
            logger.debug("project: " + project);
            logger.debug("edate: " + edate);
			
			
			AssemblyYieldDao assemblyYieldDao = new AssemblyYieldDao();
			StandardCostDao standardCostDao = new StandardCostDao();
			List<StandardCostTo> standardCostList = standardCostDao.getStandardCostByProject(project);
			
			
			//logger.debug("userList Size " + userList.size());
			
			//分頁
			String product;
			if( standardCostList!= null){
				
				for(int i=0;i < standardCostList.size(); i ++){
					StandardCostTo standardCostTo = standardCostList.get(i);
					product = standardCostTo.getProduct();
					List<AssemblyYieldIssueTo> issueList = assemblyYieldDao.getAssemblyPOIssuedQty(edate, product);
					logger.debug(product + " issueList size " + issueList.size() );
					List<AssemblyYieldReceiveTo> receiveList =  assemblyYieldDao.getAssemblyReceiveQty(edate, product);
					logger.debug(product + " receiveList size " + receiveList.size() );
				}
				
				
				
/*				total=wipList.size();
				logger.debug("total " + total);
				
				int end=iStart+iLimit;
				if(end > total){//不能總數
					end = total;
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
				CistaUtil.ajaxResponseData(response, jsonData);*/
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
