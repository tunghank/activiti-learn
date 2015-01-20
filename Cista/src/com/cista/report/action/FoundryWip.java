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

public class FoundryWip extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	private String cistaProject;
	private String lot;
	
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
	
	public String FoundryWipExcel() throws Exception{
		try {
			request= ServletActionContext.getRequest();
			response= ServletActionContext.getResponse();

			String filename = request.getParameter("filename");
			filename = null != filename ? filename : "";
			logger.debug("filename " + filename);
			
			logger.debug("lot " + this.lot );
			logger.debug("cistaProject " + this.cistaProject );

            String vendorCode = "10001";
			
			String cistaProject = this.cistaProject;
			cistaProject = null != cistaProject ? cistaProject : "";
			logger.debug("cistaProject " + cistaProject);
			
			String lot = this.lot;
			lot = null != lot ? lot : "";
			logger.debug("lot " + lot);
            
			FoundryWipDao foundryWipDao = new FoundryWipDao();
			List<FoundryWipTo> wipList = foundryWipDao.getFountryWip(vendorCode, lot, cistaProject);
	        createExcel(response, filename, wipList);
	        
	        
	        return SUCCESS;
		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
			return ERROR;
		}
		
	}

	
	 private void createExcel(HttpServletResponse response , String excelFile, List<FoundryWipTo> wipList) {

	        try {

	        	DateFormat dateFormat = new DateFormat("yyyy-MM-dd");
	            //Output to Web
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition",
	                               "attachment; filename=" + excelFile);
	            
	            OutputStream httpOut = response.getOutputStream();

	            WritableWorkbook outWorkbook = Workbook.createWorkbook(httpOut);
	            //WritableSheet sheet = outWorkbook.getSheet(0);
	            
	            //Print Excel Hearer
	            WritableFont arial16font = new WritableFont(WritableFont.ARIAL, 16,
	                WritableFont.BOLD);
	            WritableCellFormat headFormat = new WritableCellFormat();
	            headFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	            headFormat.setAlignment(Alignment.LEFT);
	            headFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
	            headFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
	            //headFormat.setBackground(Colour.TURQUOISE);
	            headFormat.setFont(arial16font);
	            //Report Title
	            //sheet.addCell(new Label(0, 0, "ECOA List", headFormat));
	            //sheet.mergeCells(0, 0, 3, 0);
	            //Font
	            WritableFont f = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
	            		UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
	            //Row Format
	            WritableCellFormat cellFormat = new WritableCellFormat();
	            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	            cellFormat.setWrap(false);
	            cellFormat.setBackground(Colour.WHITE);
	            cellFormat.setFont(f);
	            
	            WritableCellFormat cellDateFormat = new WritableCellFormat(dateFormat);
	            cellDateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	            cellDateFormat.setWrap(false);
	            cellDateFormat.setBackground(Colour.WHITE);
	            cellDateFormat.setFont(f);
	            
	            //Header
	            WritableFont headerF = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
	            		UnderlineStyle.NO_UNDERLINE,Colour.WHITE);
	            //Row Format
	            WritableCellFormat headerFormat = new WritableCellFormat();
	            headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	            headerFormat.setWrap(false);
	            headerFormat.setBackground(Colour.BLUE);
	            // 置中對齊
	            headerFormat.setAlignment(Alignment.CENTRE);
	            // 垂直置中
	            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	            // 上方框粗線
	            headerFormat.setBorder(Border.TOP, BorderLineStyle.MEDIUM, Colour.BLACK); 
	            headerFormat.setFont(headerF);
	            
	            //Header
	            WritableFont headerF2 = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
	            		UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
	            //Row Format
	            WritableCellFormat headerF2ormat = new WritableCellFormat();
	            headerF2ormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	            headerF2ormat.setWrap(false);
	            //headerF2ormat.setBackground(Colour.WHITE);
	            // 置中對齊
	            headerF2ormat.setAlignment(Alignment.CENTRE);
	            // 垂直置中
	            headerF2ormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	            // 下方框粗線
	            headerF2ormat.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM, Colour.BLACK); 
	            headerF2ormat.setFont(headerF2);
	            
	            //存儲格樣式,保留兩位小數
	            NumberFormat scale2format = new NumberFormat("#.0000");  
	            WritableCellFormat numbercellformat_scale2 = new WritableCellFormat(scale2format);              
	            numbercellformat_scale2.setAlignment(Alignment.RIGHT);
	            numbercellformat_scale2.setBorder(Border.ALL, BorderLineStyle.THIN);
	            numbercellformat_scale2.setWrap(false);
	            numbercellformat_scale2.setBackground(Colour.WHITE);
	            numbercellformat_scale2.setFont(f);
	            
	            //Money
	            NumberFormat currencyFormat = new NumberFormat(NumberFormat.CURRENCY_DOLLAR + "#", NumberFormat.COMPLEX_FORMAT); 
	            WritableCellFormat USCurrencyFormat = new WritableCellFormat(currencyFormat);
	            USCurrencyFormat.setAlignment(Alignment.RIGHT);
	            USCurrencyFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	            USCurrencyFormat.setWrap(false);
	            USCurrencyFormat.setBackground(Colour.WHITE);
	            USCurrencyFormat.setFont(f);
	            
	            //Money4
	            NumberFormat currencyFormat4 = new NumberFormat(NumberFormat.CURRENCY_DOLLAR + "0.00##", NumberFormat.COMPLEX_FORMAT); 
	            WritableCellFormat USCurrencyFormat4 = new WritableCellFormat(currencyFormat4);
	            USCurrencyFormat4.setAlignment(Alignment.RIGHT);
	            USCurrencyFormat4.setBorder(Border.ALL, BorderLineStyle.THIN);
	            USCurrencyFormat4.setWrap(false);
	            USCurrencyFormat4.setBackground(Colour.WHITE);
	            USCurrencyFormat4.setFont(f);
	            
	            WritableSheet foundryWipSheet = outWorkbook.createSheet("Foundry Wip", 0);
	            
	            CellView autoSizeCellView = new CellView();
	            autoSizeCellView.setAutosize(true);
	                   
	            
	            
	            //Header
	            foundryWipSheet.addCell(new Label(0, 1, "Foundry Wip ", headerF2ormat));
	            //Write to Cell
                //Vendor
	            foundryWipSheet.addCell(new Label(0, 2, "Vendor", headerFormat));
	            //Site
	            foundryWipSheet.addCell(new Label(1, 2, "Site", headerFormat));
	            //Process
	            foundryWipSheet.addCell(new Label(2, 2, "Process", headerFormat));	            
                //PO
	            foundryWipSheet.addCell(new Label(3, 2, "PO", headerFormat));
	            //Vendor Product
	            foundryWipSheet.addCell(new Label(4, 2, "Vendor Product", headerFormat));
	            //Cista Project
	            foundryWipSheet.addCell(new Label(5, 2, "Cista Project", headerFormat));	            	            
                //Lot
	            foundryWipSheet.addCell(new Label(6, 2, "Lot", headerFormat));
	            //Qty
	            foundryWipSheet.addCell(new Label(7, 2, "Qty", headerFormat));
	            //Lot Type
	            foundryWipSheet.addCell(new Label(8, 2, "Lot Type", headerFormat));
	            
                //Total Layer
	            foundryWipSheet.addCell(new Label(9, 2, "Total Layer", headerFormat));
	            //Remain Layer
	            foundryWipSheet.addCell(new Label(10, 2, "Remain Layer", headerFormat));
	            //Lot Status
	            foundryWipSheet.addCell(new Label(11, 2, "CLot Status", headerFormat));	            	            
                //Curr Stage
	            foundryWipSheet.addCell(new Label(12, 2, "Curr Stage", headerFormat));
	            //Hold Days
	            foundryWipSheet.addCell(new Label(13, 2, "Hold Days", headerFormat));
	            //Wafer Start
	            foundryWipSheet.addCell(new Label(14, 2, "Wafer Start", headerFormat));	  	            
	            
                //Stg In Date
	            foundryWipSheet.addCell(new Label(15, 2, "Stg In Date", headerFormat));
	            //SOD
	            foundryWipSheet.addCell(new Label(16, 2, "SOD", headerFormat));
	            //RSOD
	            foundryWipSheet.addCell(new Label(17, 2, "RSOD", headerFormat));	            	            
                //Report Date
	            foundryWipSheet.addCell(new Label(18, 2, "Report Date", headerFormat));

	            //2.0 DATA

				for(int i =0; i< wipList.size(); i ++){
	    			
					FoundryWipTo foundryWipTo = wipList.get(i);
		            //Write to Cell
	                //Vendor
		            foundryWipSheet.addCell(new Label(0, 3+i, foundryWipTo.getVendor(), cellFormat));
		            //Site
		            foundryWipSheet.addCell(new Label(1, 3+i, foundryWipTo.getVendorSiteNum(), cellFormat));
		            //Process
		            foundryWipSheet.addCell(new Label(2, 3+i, foundryWipTo.getProcess(), cellFormat));	            
	                //PO
		            foundryWipSheet.addCell(new Label(3, 3+i, foundryWipTo.getCistaPo(), cellFormat));
		            //Vendor Product
		            foundryWipSheet.addCell(new Label(4, 3+i, foundryWipTo.getVendorProd(), cellFormat));
		            //Cista Project
		            foundryWipSheet.addCell(new Label(5, 3+i, foundryWipTo.getCistaProject(), cellFormat));	            	            
	                //Lot
		            foundryWipSheet.addCell(new Label(6, 3+i, foundryWipTo.getWaferLotId(), cellFormat));
		            //Qty
		            foundryWipSheet.addCell(new Number(7, 3+i, foundryWipTo.getWaferQty(), cellFormat));
		            //Lot Type
		            foundryWipSheet.addCell(new Label(8, 3+i, foundryWipTo.getLotType(), cellFormat));
		            
	                //Total Layer
		            foundryWipSheet.addCell(new Number(9, 3+i, foundryWipTo.getTotalLayer(), cellFormat));
		            //Remain Layer
		            foundryWipSheet.addCell(new Number(10, 3+i, foundryWipTo.getRemainLayer(), cellFormat));
		            //Lot Status
		            foundryWipSheet.addCell(new Label(11, 3+i, foundryWipTo.getLotStatus(), cellFormat));	            	            
	                //Curr Stage
		            foundryWipSheet.addCell(new Label(12, 3+i, foundryWipTo.getCurrStage(), cellFormat));
		            //Hold Days
		            if ( foundryWipTo.getCurrHoldDay() !=null ){
		            	foundryWipSheet.addCell(new Number(13, 3+i, foundryWipTo.getCurrHoldDay(), cellFormat));
		            }else{
		            	foundryWipSheet.addCell(new Label(13, 3+i, "", cellFormat));
		            }
		            //Wafer Start
		            if( foundryWipTo.getWaferStart() != null){
		            	foundryWipSheet.addCell(new DateTime(14, 3+i, foundryWipTo.getWaferStart(), cellDateFormat));
		            }else{
		            	foundryWipSheet.addCell(new Label(14, 3+i, "", cellFormat));
		            }
		            
	                //Stg In Date
		            if( foundryWipTo.getStgInDate() != null){
		            	foundryWipSheet.addCell(new DateTime(15, 3+i, foundryWipTo.getStgInDate(), cellDateFormat));
		            }else{
		            	foundryWipSheet.addCell(new Label(15, 3+i, "", cellFormat));
		            }
		              
		            //SOD
		            if( foundryWipTo.getSod() != null){
		            	foundryWipSheet.addCell(new DateTime(16, 3+i, foundryWipTo.getSod(), cellDateFormat));	
		            }else{
		            	foundryWipSheet.addCell(new Label(16, 3+i, "", cellFormat));
		            }		            
		             
		            //RSOD
		            if( foundryWipTo.getRsod() != null){
		            	foundryWipSheet.addCell(new DateTime(17, 3+i, foundryWipTo.getRsod(), cellDateFormat));   	
		            }else{
		            	foundryWipSheet.addCell(new Label(17, 3+i, "", cellFormat));
		            }		            
		                  	            
	                //Report Date
		            if(  foundryWipTo.getRptDate() != null){
		            	foundryWipSheet.addCell(new DateTime(18, 3+i,  foundryWipTo.getRptDate(), cellDateFormat));   	
		            }else{
		            	foundryWipSheet.addCell(new Label(18, 3+i, "", cellFormat));
		            }		
 
					
				}
				
				//行高自動擴展
				for(int r=0; r<=18; r++){
					foundryWipSheet.setColumnView(r, autoSizeCellView);//行高自動擴展
				}

				
	            outWorkbook.write();
	            
	            httpOut.flush();
	            outWorkbook.close();
	            httpOut.close();

	        } catch (Exception e) {
				this.addActionMessage("ERROR");
				e.printStackTrace();
				logger.error(e.toString());
				addActionMessage(e.toString());
				return;
	        }
	    }

	
	
	
	
	public String getCistaProject() {
		return cistaProject;
	}

	public void setCistaProject(String cistaProject) {
		this.cistaProject = cistaProject;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}



    
}
