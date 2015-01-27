package com.cista.report.action;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.struts2.ServletActionContext;
//Dao
import com.cista.report.dao.StandardCostDao;
import com.cista.report.dao.AssemblyYieldDao;
//To
import com.cista.system.to.ExtJSGridTo;
import com.cista.report.to.StandardCostTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldIssueTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldQueryTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldReceiveTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldTo;
//Base
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
//Json
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
	private String project;
	private String edate;
	
	
	public String AssemblyYieldPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}

	public String AssemblyYieldReport() throws Exception{
		
		try {

			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart=0;//分頁。。。每頁開始數據
	        int iLimit=10;//分頁。。。每一頁數據
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
	
			
	        String project = queryTo.getProject();
	        project = null != project ? project : "";
	        
            String edate = queryTo.getEdate();
            edate = null != edate ? edate : "";
            if(!edate.equals("")){
            	Date dEdate = df1.parse(edate);
            	edate = df2.format(dEdate);
            }

            List<AssemblyYieldTo> assemblyYieldList = this.AssemblyYieldDatas(edate, project);
			if(assemblyYieldList != null){

				total=assemblyYieldList.size();
				logger.debug("total " + total);
				
				int end=iStart+iLimit;
				if(end > total){//不能總數
					end = total;
				}		
				
				List<AssemblyYieldTo> resultList = new ArrayList<AssemblyYieldTo>();
				for(int i=iStart;i<end;i++){//只加載當前頁面數據
					//logger.debug(userList.get(i).toString());
					resultList.add(assemblyYieldList.get(i));
				}
				
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
	
	private List<AssemblyYieldTo> AssemblyYieldDatas(String edate, String project) throws Exception{
		try {
			AssemblyYieldDao assemblyYieldDao = new AssemblyYieldDao();
			StandardCostDao standardCostDao = new StandardCostDao();
			List<StandardCostTo> standardCostList = standardCostDao.getStandardCostByProject(project);
			List<AssemblyYieldTo> assemblyYieldList = new ArrayList<AssemblyYieldTo>();
			//分頁
			String product;
			
			if( standardCostList!= null){
				
				for(int i=0;i < standardCostList.size(); i ++){
					StandardCostTo standardCostTo = standardCostList.get(i);
					AssemblyYieldTo assemblyYieldTo = new AssemblyYieldTo();
					product = standardCostTo.getProduct();
					assemblyYieldTo.setProduct(product);
					
					
					List<AssemblyYieldIssueTo> issueList = assemblyYieldDao.getAssemblyPOIssuedQty(edate, product);
					long issueQty =0, grossDie=0, issueDieQty=0;
					String unit="";
					if( issueList != null){
						for(int j=0; j< issueList.size(); j++){
							AssemblyYieldIssueTo issueTo = issueList.get(j);
							grossDie = issueTo.getGrossDie();
							issueQty = issueQty + issueTo.getIssueQty();
							issueDieQty = issueDieQty + issueTo.getIssueDieQty();
							unit = issueTo.getUnit();
						}
						logger.debug(product + " issueList size " + issueList.size() );
					}
					
					List<AssemblyYieldReceiveTo> receiveList =  assemblyYieldDao.getAssemblyReceiveQty(edate, product);
					long receiveDieQty=0;
					if( receiveList != null){
						for(int k=0; k< receiveList.size(); k++){
							AssemblyYieldReceiveTo receiveTo = receiveList.get(k);
							receiveDieQty = receiveDieQty + receiveTo.getReceiveQty();
						}
						logger.debug(product + " receiveList size " + receiveList.size() );
					}
					double assemblyYield=0.0;
					String strAssemblyYield="";
					if( receiveList != null & issueList != null){
						 /**
						  * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指
						  * 定精度，以後的數字四捨五入。
						  * @param v1 被除數
						  * @param v2 除數
						  * @param scale 表示表示需要精確到小數點以後幾位。
						  * @return 兩個參數的商
						 */
						BigDecimal b1 = new BigDecimal(Double.toString(receiveDieQty));
						BigDecimal b2 = new BigDecimal(Double.toString(issueDieQty));
						assemblyYield = b1.divide(b2,4,BigDecimal.ROUND_HALF_UP).doubleValue();
						
						strAssemblyYield = String.valueOf(assemblyYield * 100) + "%";
					}
					
					assemblyYieldTo.setAssemblyYield(assemblyYield);
					assemblyYieldTo.setGrossDie(grossDie);
					assemblyYieldTo.setIssueDieQty(issueDieQty);
					assemblyYieldTo.setIssueQty(issueQty);
					assemblyYieldTo.setUnit(unit);
					assemblyYieldTo.setReceiveDieQty(receiveDieQty);
					assemblyYieldTo.setStrAssemblyYield(strAssemblyYield);
					assemblyYieldList.add(assemblyYieldTo);
				}
				
				return assemblyYieldList;
			}else{
				return null;
			}
		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
			return null;
		}
	}
	
	
	public String AssemblyYieldExcel() throws Exception{
		try {
			request= ServletActionContext.getRequest();
			response= ServletActionContext.getResponse();

	        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
	        
			String filename = request.getParameter("filename");
			filename = null != filename ? filename : "";
			logger.debug("filename " + filename);
								
	        String project = this.project;
	        project = null != project ? project : "";
	        
            String edate = this.edate;
            edate = null != edate ? edate : "";
            logger.debug("edate " + edate);
            if(!edate.equals("")){
            	Date dEdate = df1.parse(edate);
            	edate = df2.format(dEdate);
            }
			
            List<AssemblyYieldTo> assemblyYieldList = this.AssemblyYieldDatas(edate, project);
			if(assemblyYieldList != null){
				createExcel(response, filename, assemblyYieldList);
			}
	        
	        
	        return SUCCESS;
		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
			return ERROR;
		}
		
	}

	 private void createExcel(HttpServletResponse response , String excelFile, List<AssemblyYieldTo> assemblyYieldList) {

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
	            
	            WritableSheet assemblyYieldSheet = outWorkbook.createSheet("Assembly Yield", 0);
	            
	            CellView autoSizeCellView = new CellView();
	            autoSizeCellView.setAutosize(true);
	                   
	            
	            
	            //Header
	            assemblyYieldSheet.addCell(new Label(0, 1, "Assembly Yield ", headerF2ormat));
	            //Write to Cell
	            //Product
	            assemblyYieldSheet.addCell(new Label(0, 2, "Product", headerFormat));
	            //Gross Die
	            assemblyYieldSheet.addCell(new Label(1, 2, "Gross Die", headerFormat));
	            //PO Issue Qty
	            assemblyYieldSheet.addCell(new Label(2, 2, "PO Issue Qty", headerFormat));	            
	            //Unit
	            assemblyYieldSheet.addCell(new Label(3, 2, "Unit", headerFormat));
	            //Issue Die Qty
	            assemblyYieldSheet.addCell(new Label(4, 2, "Issue Die Qty", headerFormat));
	            //Receive Die Qty
	            assemblyYieldSheet.addCell(new Label(5, 2, "Receive Die Qty", headerFormat));	            	            
	            //Assembly Yield
	            assemblyYieldSheet.addCell(new Label(6, 2, "Assembly Yield", headerFormat));

	            //2.0 DATA

				for(int i =0; i< assemblyYieldList.size(); i ++){
	    			
					AssemblyYieldTo yieldTo = assemblyYieldList.get(i);
		            //Write to Cell
	                //Product
					assemblyYieldSheet.addCell(new Label(0, 3+i, yieldTo.getProduct(), cellFormat));
		            //Gross Die
					assemblyYieldSheet.addCell(new Number(1, 3+i, yieldTo.getGrossDie(), cellFormat));
		            //PO Issue Qty
					assemblyYieldSheet.addCell(new Number(2, 3+i, yieldTo.getIssueQty(), cellFormat));            
	                //Unit
					assemblyYieldSheet.addCell(new Label(3, 3+i, yieldTo.getUnit(), cellFormat));
		            //Issue Die Qty
					assemblyYieldSheet.addCell(new Number(4, 3+i, yieldTo.getIssueDieQty(), cellFormat)); 
		            //Receive Die Qty
					assemblyYieldSheet.addCell(new Number(5, 3+i, yieldTo.getReceiveDieQty(), cellFormat));            	            
	                //Assembly Yield
					assemblyYieldSheet.addCell(new Label(6, 3+i, yieldTo.getStrAssemblyYield(), cellFormat));
					
				}
				
				//行高自動擴展
				for(int r=0; r<=18; r++){
					assemblyYieldSheet.setColumnView(r, autoSizeCellView);//行高自動擴展
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
	
	
	
	
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}
	
	
	
}
