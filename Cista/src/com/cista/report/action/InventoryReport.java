package com.cista.report.action;


import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.cista.system.util.BaseAction;
import com.cista.report.dao.ERPInventoryDao;
import com.cista.report.dao.InWipCostDao;
import com.cista.report.dao.ProductYieldDao;
import com.cista.report.dao.StandardCostDao;
import com.cista.report.dao.UnitCostDao;
import com.cista.report.to.InWipCostTo;
import com.cista.report.to.InventoryTo;
import com.cista.report.to.ProductYieldTo;
import com.cista.report.to.StandardCostTo;
import com.cista.report.to.UnitCostTo;
import com.sun.java_cup.internal.internal_error;
import com.cista.system.util.CistaUtil;
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

public class InventoryReport extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	private String project;
	private String edate;

	public String WeeklyInventoryPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}
	
	public String WeeklyInventory() throws Exception{
		try {
			request= ServletActionContext.getRequest();
			response= ServletActionContext.getResponse();
			
			logger.debug("project " + this.project );
			logger.debug("edate " + this.edate );
			//1.0 to read a file from Web Content
			ServletContext servletContext = ServletActionContext.getServletContext();
			String contextPath = servletContext.getRealPath(File.separator);
			
			logger.debug("<br/>File system context path (in TestServlet): " + contextPath);
			String templateFile = contextPath + File.separator + "Report\\Excel" + File.separator
	                + "Weekly inventory Report.xls";
	
	        File excelTemplate = new File(templateFile);
	        logger.debug("excelTemplate Size " +  excelTemplate.length() );
			
	        createExcel(response, excelTemplate);
	        
	        return SUCCESS;
		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
			return ERROR;
		}
		
	}
	
	
    private void createExcel(HttpServletResponse response , File excelTemplate) {

        try {
        	String tMonth, sdate, edate;
        	tMonth = this.edate.substring(0,6);
        	sdate = tMonth + "01";
        	edate = this.edate;
        	
        	logger.debug(tMonth + " " + sdate + " " + edate);
        	
            SimpleDateFormat dateFormat2 = new SimpleDateFormat(
                "yyyyMMddHHmmss");
            Calendar calendar1 = Calendar.getInstance();
            String rptFile = dateFormat2.format(calendar1.getTime()).toString();

            //Output to Web
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                               "attachment; filename=Weekly inventory Report_" + rptFile
                               + ".xls");
            OutputStream httpOut = response.getOutputStream();
            Workbook workbook = Workbook.getWorkbook(excelTemplate);

            WritableWorkbook outWorkbook = Workbook.createWorkbook(httpOut,
                workbook);
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
            WritableFont f = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
            //Row Format
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat.setWrap(false);
            cellFormat.setBackground(Colour.WHITE);
            cellFormat.setFont(f);
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
            
            //Yield Format
            DisplayFormat percentFormat = NumberFormats.PERCENT_FLOAT;
            WritableCellFormat yieldFormat = new WritableCellFormat(percentFormat);
            yieldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            yieldFormat.setWrap(false);
            yieldFormat.setBackground(Colour.WHITE);
            yieldFormat.setFont(f);
            //Report Time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            String rptDT = dateFormat.format(calendar.getTime()).toString();
            //sheet.addCell(new Label(0, 1, "Report Time: " + rptDT, cellFormat));
            //sheet.mergeCells(0, 1, 3, 1);

  		
    		/*******************************************************
    		 * 1.1 Standard Cost Page
    		 *******************************************************/
    		WritableSheet standardCostSheet = outWorkbook.getSheet(1);
    		StandardCostDao standardCostDao = new StandardCostDao();
    		ERPInventoryDao erpInventoryDao = new ERPInventoryDao();
    		InWipCostDao inWipCostDao = new InWipCostDao();
    		
    		UnitCostDao unitCostDao = new UnitCostDao();
    		ProductYieldDao productYieldDao = new ProductYieldDao();
    		
    		List<StandardCostTo> standardCostList = standardCostDao.getStandardCostByProject(this.project);
    		
    		for(int i =0; i< standardCostList.size(); i ++){
    			
    			StandardCostTo standardCostTo = standardCostList.get(i);
    			
                //Write to Cell
                //Product
                standardCostSheet.addCell(new Label(0, 4+i, standardCostTo.getProduct(), cellFormat));
                //Project
                standardCostSheet.addCell(new Label(1, 4+i, standardCostTo.getProject(), cellFormat));
                //GROSS_DIE
                standardCostSheet.addCell(new Number(2, 4+i, standardCostTo.getGrossDie(), cellFormat));
                //WAFER_COST
                standardCostSheet.addCell(new Number(3, 4+i, standardCostTo.getWaferCost(), cellFormat));
                //CP_COST
                standardCostSheet.addCell(new Number(4, 4+i, standardCostTo.getCpCost(), cellFormat));
                //CP_YIELD
                standardCostSheet.addCell(new Number(5, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getCpYield())),4),yieldFormat));
                //CF_COST
                standardCostSheet.addCell(new Number(6, 4+i, standardCostTo.getCfCost(), cellFormat));
                //CSP_COST
                standardCostSheet.addCell(new Number(7, 4+i, standardCostTo.getCspCost(), cellFormat));
                //CSP_YIELD
                standardCostSheet.addCell(new Number(8, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getCspYield())),4), yieldFormat));
                //CSP_DIE
                standardCostSheet.addCell(new Number(9, 4+i, standardCostTo.getCspDie(), cellFormat));
                //FT_UNIT_COST
                standardCostSheet.addCell(new Number(10, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getFtUnitCost())),4), cellFormat));
                //FT_FEE
                standardCostSheet.addCell(new Number(11, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getFtFee())),4), cellFormat));
                //FT_YIELD
                standardCostSheet.addCell(new Number(12, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getFtYield())),4), yieldFormat));
                //TOTAL_COST
                standardCostSheet.addCell(new Number(13, 4+i, standardCostTo.getTotalCost(), cellFormat));
                //GOOD_PART
                standardCostSheet.addCell(new Number(14, 4+i, standardCostTo.getGoodPart(), cellFormat));
                //UNIT_COST
                standardCostSheet.addCell(new Number(15, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getUnitCost())),4), cellFormat));
    		}
    		
    		/*********************************************************
    		 * 1.2 Inventory Report
    		 *********************************************************/
    		WritableSheet inventorySheet = outWorkbook.getSheet(0);
    		
    		String product , project;
    		int openOrder=0, foundry=0, cp=0 , cf=0, csp=0, ft=0 , totalWafer=0, totalDie =0;
    		double foundryCost=0, cpCost=0 , cfCost=0, cspCost=0, ftCost=0, amountTotal=0;
    		double foundryWipCost=0, cpWipCost=0 , cfWipCost=0, cspWipCost=0, ftWipCost=0;
    		
    		int fg=0;
    		double fgCost=0, shipCost=0;
    		//Cost & Qty
    		int  summaryFoundry=0, summaryCp=0 , summaryCf=0, summaryCsp=0, summaryFt=0;
    		double summaryFoundryCost=0, summaryCpCost=0 , summaryCfCost=0, summaryCspCost=0, summaryFtCost=0 ,summaryAmountTotal=0;
    		double unitCost=0, summaryUnitCost=0;
    		
    		//Future Amount
    		double fFoundryCost=0, fCpCost=0 , fCfCost=0, fCspCost=0, fFtCost=0, fAmountTotal=0 ;
    		double fSummayFoundryCost=0, fSummayCpCost=0 , fSummayCfCost=0, fSummayCspCost=0, fSummayFtCost=0, fSummayAmountTotal=0 ;
    		double fUnitCost=0, fSummaryUnitCost=0;
    		//Total Final Cost
    		double tFoundryCost=0, tCpCost=0 , tCfCost=0, tCspCost=0, tFtCost=0, tAmountTotal=0 ;
    		double tSummayFoundryCost=0, tSummayCpCost=0 , tSummayCfCost=0, tSummayCspCost=0, tSummayFtCost=0, tSummayAmountTotal=0 ;
    		double tUnitCost=0, tSummaryUnitCost=0;
    		//Expected Yield
    		double expectedYield=0, summaryExpectedYield=0;
    		//Good Die
    		long goodDie=0, summaryGoodDie=0;
    		
    		double inventoryCost =0.0;
			long expectedFG=0;
    		for(int i =0; i< standardCostList.size(); i ++){
    			        		
    			StandardCostTo standardCostTo = standardCostList.get(i);
    			product = standardCostTo.getProduct();
    			project = standardCostTo.getProject();
    			
    			List<InventoryTo> inventoryList = erpInventoryDao.getInventoryByProduct(tMonth, sdate, edate, product);

    			if( inventoryList != null ){
    				logger.debug("inventoryList Size " + inventoryList.size() );
            		logger.debug("inventoryList " + inventoryList.toString());
            		for(int j=0; j<inventoryList.size(); j ++ ){
            			InventoryTo inventoryTo = inventoryList.get(j);
            			//CP
            			if(inventoryTo.getMb001().indexOf("1WS") >=0){
            				cp = inventoryTo.getMb064();
            			}
            			//CF
            			if(inventoryTo.getMb001().indexOf("3PS") >=0){
            				cf = inventoryTo.getMb064();
            			}
            			//AS
            			if(inventoryTo.getMb001().indexOf("3CS") >=0){
            				csp = inventoryTo.getMb064();
            			}
            			//FT
            			if(inventoryTo.getMb001().indexOf("3AS") >=0){
            				ft = inventoryTo.getMb064();
            			}
            		}
            		
                    //Product
            		inventorySheet.addCell(new Label(0, 4+i, standardCostTo.getProduct(), cellFormat));
                    //Project
            		inventorySheet.addCell(new Label(1, 4+i, standardCostTo.getProject(), cellFormat));
                    //GROSS_DIE
            		inventorySheet.addCell(new Number(2, 4+i, standardCostTo.getGrossDie(), cellFormat));
                    //Open Order
                    
                    //Foundry
                    
                    //CP
            		inventorySheet.addCell(new Number(5, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cp)),4),cellFormat));
                    //CP_YIELD
            		inventorySheet.addCell(new Number(6, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getCpYield())),4),yieldFormat));
                    //CF
            		inventorySheet.addCell(new Number(7, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cf)),4),cellFormat));
                    //CSP
            		inventorySheet.addCell(new Number(8, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(csp)),4),cellFormat));
                    //CSP Yield
            		inventorySheet.addCell(new Number(9, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getCspYield())),4),yieldFormat));
                    //FT
            		inventorySheet.addCell(new Number(10, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(ft)),4),cellFormat));
                    //CSP Yield
            		inventorySheet.addCell(new Number(11, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getFtYield())),4),yieldFormat));
                    //Expected FG
            		totalWafer = openOrder+foundry+cp+cf+csp;
            		logger.debug("openOrder+foundry+cp+cf+csp "  + totalWafer);
            		totalDie =Math.round(totalWafer * standardCostTo.getGrossDie() * standardCostTo.getCspYield() ) ;
            		totalDie = totalDie + ft;
            		logger.debug("total die "  + totalDie);
            		logger.debug("FT die "  + totalDie );
            		
            		Double tmpExpectedFG = totalDie * CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getFtYield())),4);
            		expectedFG = (Math.round(tmpExpectedFG))  ;
            		inventorySheet.addCell(new Number(12, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(expectedFG)),4),cellFormat));
                    //UNIT_COST
            		inventorySheet.addCell(new Number(13, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getUnitCost())),4), cellFormat));
                    //Iventory Cost
            		inventoryCost = expectedFG * CistaUtil.NumScale(Double.parseDouble(String.valueOf(standardCostTo.getUnitCost())),4);
            		inventorySheet.addCell(new Number(14, 4+i, CistaUtil.NumScale(Double.parseDouble(String.valueOf(inventoryCost)),4),USCurrencyFormat));
    			}
    			        		
    		}

    		/********************************************
    		 * 1.3 Product List Sheet
    		 ********************************************/
    		
    		UnitCostTo unitCostTo = new UnitCostTo();
    		for(int i =0; i< standardCostList.size(); i ++){
    			
    			StandardCostTo standardCostTo = standardCostList.get(i);
    			//Copy Sheet
    			outWorkbook.copySheet("Template", standardCostTo.getProduct(), 2+i);
    			WritableSheet productSheet = outWorkbook.getSheet(2+i);
    			
    			product = standardCostTo.getProduct();
    			project = standardCostTo.getProject();
    			
    			List<InventoryTo> inventoryList = erpInventoryDao.getInventoryByProduct(tMonth, sdate, edate, product);
    			unitCostTo = unitCostDao.getUnitCostByProduct(product);
    			ProductYieldTo productYieldTo = productYieldDao.getProductYield(product);
    			
    			//Cost 值歸0
    			fFoundryCost=0; fCpCost=0; fCfCost=0; fCspCost=0; fFtCost=0;
    			foundryWipCost=0; cpWipCost=0 ; cfWipCost=0; cspWipCost=0; ftWipCost=0;
    			//Wip Cost
    			List<InWipCostTo> inWipCostList = inWipCostDao.getInWipCostByProduct(tMonth, product);
    			
    			if( inWipCostList != null ){
    				logger.debug("Wip List Size " + inWipCostList.size());
    				for(int k=0; k<inWipCostList.size(); k ++ ){
    					InWipCostTo inWipCostTo = inWipCostList.get(k);
						//CP
						if(inWipCostTo.getTa001().indexOf("1PS") >=0){
							cpWipCost = inWipCostTo.getMaterialCost();
						}
						//CF
						if(inWipCostTo.getTa001().indexOf("3CS") >=0){
							cfWipCost = inWipCostTo.getMaterialCost();
						}
						//AS
						if(inWipCostTo.getTa001().indexOf("3AS") >=0){
							cspWipCost = inWipCostTo.getMaterialCost();
							logger.debug("cspWipCost " + cspWipCost);
						}
						//FT
						if(inWipCostTo.getTa001().indexOf("3FT") >=0){
							ftWipCost = inWipCostTo.getMaterialCost();
						}
					}
    			}
    			if( inventoryList != null ){
    				logger.debug("inventoryList Size " + inventoryList.size() );
            		logger.debug("inventoryList " + inventoryList.toString());
            		for(int j=0; j<inventoryList.size(); j ++ ){
            			InventoryTo inventoryTo = inventoryList.get(j);
            			//CP
            			if(inventoryTo.getMb001().indexOf("1WS") >=0){
            				cp = inventoryTo.getMb064();
            				cpCost = inventoryTo.getInventoryCost();
            				summaryCp = summaryCp + cp;
            				
            			}
            			//CF
            			if(inventoryTo.getMb001().indexOf("3PS") >=0){
            				cf =inventoryTo.getMb064();
            				cfCost = inventoryTo.getInventoryCost();
            				summaryCf = summaryCf + cf;
            				
            			}
            			//AS
            			if(inventoryTo.getMb001().indexOf("3CS") >=0){
            				csp = inventoryTo.getMb064();
            				cspCost = inventoryTo.getInventoryCost();
            				summaryCsp = summaryCsp + csp;
            				
            			}
            			//FT
            			if(inventoryTo.getMb001().indexOf("3AS") >=0){
            				ft = inventoryTo.getMb064();
            				ftCost = inventoryTo.getInventoryCost();
            				summaryFt = summaryFt + ft;
            				
            			}
            			//FG
            			if(inventoryTo.getMb001().indexOf("3FS") >=0){
            				fg=0;
            				fgCost = inventoryTo.getInventoryCost();

            				
            			}
            		}

    			}
    			amountTotal = cpCost + cfCost + cspCost + ftCost +
    					cpWipCost + cfWipCost + cspWipCost + ftWipCost;
    			// 未來加工費
    			//Future Amount 清空0
        		fFoundryCost=0; fCpCost=0 ; fCfCost=0;fCspCost=0;fFtCost=0;fAmountTotal=0 ;
        		
    			fCpCost = (foundry+cp)*unitCostTo.getCp();
    			fCfCost = (foundry+cp+cf)*unitCostTo.getCf();
    			fCspCost = (foundry+cp+cf+csp)*unitCostTo.getCsp();
    			double cspDie = ( ( (foundry+cp+cf+csp)* standardCostTo.getGrossDie()*productYieldTo.getCspYield() ) + ft );
    			cspDie = CistaUtil.NumScale(cspDie,0);
    			fFtCost = cspDie * unitCostTo.getFt();
    			
    			fAmountTotal = fCpCost + fCfCost + fCspCost + fFtCost;
    			logger.debug(product + " CSP DIE QTY " + cspDie);
    			
    			tAmountTotal = fAmountTotal + amountTotal;
    			//Expected Yield
    			expectedYield = 0;
    			expectedYield = productYieldTo.getCspYield() * productYieldTo.getFtYield();
    			summaryExpectedYield = summaryExpectedYield  + expectedYield;
    			//Good Die
    			goodDie = 0;
    			double tmpGoodDie = ( (foundry+cp+cf+csp) * standardCostTo.getGrossDie() * expectedYield ) + 
    					ft * productYieldTo.getFtYield();
    			goodDie =Math.round(tmpGoodDie);
    			summaryGoodDie= summaryGoodDie + goodDie;
    			//Unit Cost
    			unitCost =0;fUnitCost=0;tUnitCost=0;
    			unitCost = amountTotal / goodDie;
    			fUnitCost = fAmountTotal / goodDie;
    			tUnitCost = tAmountTotal / goodDie;
    			/**************************************************
    			 * Summary 
    			 **************************************************/
    			//Cost & Qty
    			summaryCpCost = summaryCpCost + cpCost + cpWipCost;
    			summaryCfCost = summaryCfCost + cfCost + cfWipCost;
    			summaryCspCost = summaryCspCost + cspCost + cspWipCost;
    			summaryFtCost = summaryFtCost + ftCost + ftWipCost;
    			
    			summaryAmountTotal = summaryAmountTotal + amountTotal;
    			
    			//Funture
    			fSummayFoundryCost = fSummayFoundryCost + fFoundryCost;
    			fSummayCpCost = fSummayCpCost + fCpCost;
    			fSummayCfCost = fSummayCfCost + fCfCost;
    			fSummayCspCost = fSummayCspCost + fCspCost;
    			fSummayFtCost = fSummayFtCost + fFtCost;
    			fSummayAmountTotal = fSummayAmountTotal + fAmountTotal;
    			
    			
    			//Write to Cell
    			//到九月的成本
    			productSheet.addCell(new Label(2, 1, "到" + tMonth + "的成本", cellFormat));
        		//Quantity
        		//Wafer
    			productSheet.addCell(new Number(2, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(foundry)),4),cellFormat));
        		//CP
    			productSheet.addCell(new Number(2, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cp)),4),cellFormat));
    			productSheet.addCell(new Number(6, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cp)),4),cellFormat));
    			
        		//CF
    			productSheet.addCell(new Number(2, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cf)),4),cellFormat));
    			productSheet.addCell(new Number(6, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cf)),4),cellFormat));
        		//CSP
    			productSheet.addCell(new Number(2, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(csp)),4),cellFormat));
    			productSheet.addCell(new Number(6, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(csp)),4),cellFormat));
        		//FT
    			productSheet.addCell(new Number(2, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(ft)),4),cellFormat));
    			productSheet.addCell(new Number(6, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(ft)),4),cellFormat));
    			
        		//Amount
        		//Wafer
    			productSheet.addCell(new Number(3, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(foundryCost)),4),cellFormat));
        		//CP
    			productSheet.addCell(new Number(3, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cpCost + cpWipCost)),4),cellFormat));
        		//CF
    			productSheet.addCell(new Number(3, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cfCost + cfWipCost)),4),cellFormat));
        		//CSP
    			productSheet.addCell(new Number(3, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(cspCost + cspWipCost)),4),cellFormat));
        		//FT
    			productSheet.addCell(new Number(3, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(ftCost + ftWipCost)),4),cellFormat));
    			//Amount Total
    			productSheet.addCell(new Number(3, 8, CistaUtil.NumScale(Double.parseDouble(String.valueOf(amountTotal)),4),cellFormat));			
    			
        		//Unit Cost
        		//CP
    			productSheet.addCell(new Number(5, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getCp())),4),USCurrencyFormat4));
        		//CF
    			productSheet.addCell(new Number(5, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getCf())),4),USCurrencyFormat4));
        		//CSP
    			productSheet.addCell(new Number(5, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getCsp())),4),USCurrencyFormat4));
        		//FT
    			productSheet.addCell(new Number(5, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getFt())),4),USCurrencyFormat4));
    			
        		//Future Amount
        		//Wafer
    			productSheet.addCell(new Number(8, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fFoundryCost)),4),USCurrencyFormat4));
        		//CP
    			productSheet.addCell(new Number(8, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fCpCost)),4),USCurrencyFormat4));
        		//CF
    			productSheet.addCell(new Number(8, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fCfCost)),4),USCurrencyFormat4));
        		//CSP
    			productSheet.addCell(new Number(8, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fCspCost)),4),USCurrencyFormat4));
        		//FT
    			productSheet.addCell(new Number(8, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fFtCost)),4),USCurrencyFormat4));
    			//Total Future Amount
    			productSheet.addCell(new Number(8, 8, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fAmountTotal)),4),USCurrencyFormat4));
    			
        		//Total Final Cost
        		//Wafer
    			tFoundryCost = fFoundryCost + foundryCost + foundryWipCost;
    			productSheet.addCell(new Number(10, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tFoundryCost)),4),USCurrencyFormat4));
        		//CP
    			tCpCost = fCpCost + cpCost+ cpWipCost;
    			productSheet.addCell(new Number(10, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tCpCost)),4),USCurrencyFormat4));
        		//CF
    			tCfCost = fCfCost + cfCost + cfWipCost;
    			productSheet.addCell(new Number(10, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tCfCost)),4),USCurrencyFormat4));
        		//CSP
    			tCspCost = fCspCost + cspCost + cspWipCost;
    			productSheet.addCell(new Number(10, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tCspCost)),4),USCurrencyFormat4));
        		//FT
    			tFtCost = fFtCost + ftCost + ftWipCost;
    			productSheet.addCell(new Number(10, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tFtCost)),4),USCurrencyFormat4));
    			//Total Future Amount
    			
    			productSheet.addCell(new Number(10, 8, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tAmountTotal)),4),USCurrencyFormat4));
    			
    			//Expected Yield
    			productSheet.addCell(new Number(10, 10, CistaUtil.NumScale(Double.parseDouble(String.valueOf(expectedYield)),4),yieldFormat));
       			//Good Die
    			productSheet.addCell(new Number(10, 11, goodDie,cellFormat));
    			//Unit Cost
    			productSheet.addCell(new Number(3, 12, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCost)),4),USCurrencyFormat4));
    			//Funture Unit Cost
    			productSheet.addCell(new Number(8, 12, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fUnitCost)),4),USCurrencyFormat4));
    			//Total Unit Cost
    			productSheet.addCell(new Number(10, 12, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tUnitCost)),4),USCurrencyFormat4));
    		}
    		//Summary Sheet
			outWorkbook.copySheet("Template", this.project + "_Summary", 2 + standardCostList.size());
			WritableSheet summarySheet = outWorkbook.getSheet(2 + standardCostList.size());
			//Write to Cell
			//到九月的成本
			summarySheet.addCell(new Label(2, 1, "到" + tMonth + "的成本", cellFormat));
    		//Quantity
    		//Wafer
			summarySheet.addCell(new Number(2, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryFoundry)),4),cellFormat));
    		//CP
			summarySheet.addCell(new Number(2, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCp)),4),cellFormat));
			summarySheet.addCell(new Number(6, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCp)),4),cellFormat));
    		//CF
			summarySheet.addCell(new Number(2, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCf)),4),cellFormat));
			summarySheet.addCell(new Number(6, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCf)),4),cellFormat));
    		//CSP
			summarySheet.addCell(new Number(2, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCsp)),4),cellFormat));
			summarySheet.addCell(new Number(6, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCsp)),4),cellFormat));
    		//FT
			summarySheet.addCell(new Number(2, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryFt)),4),cellFormat));
			summarySheet.addCell(new Number(6, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryFt)),4),cellFormat));
    		
    		//Amount
    		//Wafer
			summarySheet.addCell(new Number(3, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryFoundryCost)),4),cellFormat));
    		//CP
			summarySheet.addCell(new Number(3, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCpCost)),4),cellFormat));
    		//CF
			summarySheet.addCell(new Number(3, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCfCost)),4),cellFormat));
    		//CSP
			summarySheet.addCell(new Number(3, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryCspCost)),4),cellFormat));
    		//FT
			summarySheet.addCell(new Number(3, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryFtCost)),4),cellFormat));	
			//Total Amount
			summarySheet.addCell(new Number(3, 8, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryAmountTotal)),4),cellFormat));
			
			//Unit Cost
    		//CP
			summarySheet.addCell(new Number(5, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getCp())),4),USCurrencyFormat4));
    		//CF
			summarySheet.addCell(new Number(5, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getCf())),4),USCurrencyFormat4));
    		//CSP
			summarySheet.addCell(new Number(5, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getCsp())),4),USCurrencyFormat4));
    		//FT
			summarySheet.addCell(new Number(5, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(unitCostTo.getFt())),4),USCurrencyFormat4));
			
    		//Future Amount
    		//Wafer
			summarySheet.addCell(new Number(8, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fSummayFoundryCost)),4),USCurrencyFormat4));
    		//CP
			summarySheet.addCell(new Number(8, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fSummayCpCost)),4),USCurrencyFormat4));
    		//CF
			summarySheet.addCell(new Number(8, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fSummayCfCost)),4),USCurrencyFormat4));
    		//CSP
			summarySheet.addCell(new Number(8, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fSummayCspCost)),4),USCurrencyFormat4));
    		//FT
			summarySheet.addCell(new Number(8, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fSummayFtCost)),4),USCurrencyFormat4));
			//Total Future Amount
			summarySheet.addCell(new Number(8, 8, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fSummayAmountTotal)),4),USCurrencyFormat4));
			
			
    		//Total Final Cost
    		//Wafer
			tSummayFoundryCost = fSummayFoundryCost + summaryFoundryCost;
			summarySheet.addCell(new Number(10, 3, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tSummayFoundryCost)),4),USCurrencyFormat4));
    		//CP
			tSummayCpCost = fSummayCpCost + summaryCpCost;
			summarySheet.addCell(new Number(10, 4, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tSummayCpCost)),4),USCurrencyFormat4));
    		//CF
			tSummayCfCost = fSummayCfCost + summaryCfCost;
			summarySheet.addCell(new Number(10, 5, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tSummayCfCost)),4),USCurrencyFormat4));
    		//CSP
			tSummayCspCost = fSummayCspCost + summaryCspCost;
			summarySheet.addCell(new Number(10, 6, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tSummayCspCost)),4),USCurrencyFormat4));
    		//FT
			tSummayFtCost = fSummayFtCost + summaryFtCost;
			summarySheet.addCell(new Number(10, 7, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tSummayFtCost)),4),USCurrencyFormat4));
			//Total Future Amount
			tSummayAmountTotal = fSummayAmountTotal + summaryAmountTotal;
			summarySheet.addCell(new Number(10, 8, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tSummayAmountTotal)),4),USCurrencyFormat4));
			
			//Expected Yield
			summarySheet.addCell(new Number(10, 10, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryExpectedYield/standardCostList.size())),4),yieldFormat));
   			//Good Die
			summarySheet.addCell(new Number(10, 11, summaryGoodDie,cellFormat));
			
			//Unit Cost
			summaryUnitCost = summaryAmountTotal / summaryGoodDie;
			summarySheet.addCell(new Number(3, 12, CistaUtil.NumScale(Double.parseDouble(String.valueOf(summaryUnitCost)),4),USCurrencyFormat4));
			//Funture Unit Cost
			fSummaryUnitCost = fSummayAmountTotal / summaryGoodDie;
			summarySheet.addCell(new Number(8, 12, CistaUtil.NumScale(Double.parseDouble(String.valueOf(fSummaryUnitCost)),4),USCurrencyFormat4));
			//Total Unit Cost
			tSummaryUnitCost = tSummayAmountTotal / summaryGoodDie;
			summarySheet.addCell(new Number(10, 12, CistaUtil.NumScale(Double.parseDouble(String.valueOf(tSummaryUnitCost)),4),USCurrencyFormat4));
			
            outWorkbook.write();
            workbook.close();
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
