package com.cista.report.action;


import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.cista.system.util.BaseAction;
import com.cista.report.dao.ERPInventoryDao;
import com.cista.report.dao.FgReceiveBinDao;
import com.cista.report.dao.InWipCostDao;
import com.cista.report.dao.ProductCompensateDao;
import com.cista.report.dao.ProductOpenStockDao;
import com.cista.report.dao.ProductYieldDao;
import com.cista.report.dao.PurchasePoDao;
import com.cista.report.dao.StandardCostDao;
import com.cista.report.dao.StockHistoryDao;
import com.cista.report.dao.UnitCostDao;
import com.cista.report.to.FgReceiveBinTo;
import com.cista.report.to.InWipCostTo;
import com.cista.report.to.InventoryTo;
import com.cista.report.to.ProductCompensateTo;
import com.cista.report.to.ProductOpenStockTo;
import com.cista.report.to.ProductYieldTo;
import com.cista.report.to.PurchasePoTo;
import com.cista.report.to.StandardCostTo;
import com.cista.report.to.StockHistoryTo;
import com.cista.report.to.UnitCostTo;
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

public class PurchasePo extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	private String project;
	private String edate;

	public String PurchasePoPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}
	
	public String PurchasePo() throws Exception{
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
	                + "PurchasePO.xls";
	
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
                               "attachment; filename=Purchase PO_" + rptFile
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
            
            //Header
            WritableFont headerF = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
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
            
            //Yield Format
            DisplayFormat percentFormat = NumberFormats.PERCENT_FLOAT;
            WritableCellFormat yieldFormat = new WritableCellFormat(percentFormat);
            yieldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            yieldFormat.setWrap(false);
            yieldFormat.setBackground(Colour.WHITE);
            yieldFormat.setFont(f);
            //Report Time
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            //Calendar calendar = Calendar.getInstance();
            
            //sheet.addCell(new Label(0, 1, "Report Time: " + rptDT, cellFormat));
            //sheet.mergeCells(0, 1, 3, 1);

  		
    		/*******************************************************
    		 * 1.1 Standard Cost Page
    		 *******************************************************/
    		WritableSheet poListSheet = outWorkbook.getSheet(0);
    		
    		StandardCostDao standardCostDao = new StandardCostDao();
    		PurchasePoDao purchasePoDao = new PurchasePoDao();
    		
    		
    		List<StandardCostTo> standardCostList = standardCostDao.getProductFormStandCost();
    		
    		logger.debug("standardCostList.size() " + standardCostList.size() );
    		
    		//Header
    		//poListSheet.addCell(new Label(0, 1, "For the week ended: " + edate, cellFormat));
            String poType = "";
            List<PurchasePoTo> purchasePoList = new ArrayList<PurchasePoTo>();
            int p=0;
    		for(int i =0; i< standardCostList.size(); i ++){
    			
    			StandardCostTo standardCostTo = standardCostList.get(i);
    			purchasePoList = purchasePoDao.getOpenPO3301ByProduct(standardCostTo.getProduct());
    			
    			if(purchasePoList !=null ){
    				
    				logger.debug("purchasePoList size() " + purchasePoList.size() );
    				logger.debug( purchasePoList.toString() );
    				for(int j =0; j< purchasePoList.size(); j ++) {
    					    					
    					PurchasePoTo purchasePoTo = purchasePoList.get(j);
                        //Write to Cell
    					if( purchasePoTo.getPoNum().indexOf("3301") >=0 ){
    						poType = "Normal";
    					}else{
    						poType = "Compensation";
    					}
    					
    					if( purchasePoTo.getPoNum().indexOf("3301-2014040001") >=0){
    						purchasePoTo.setPurchaseQty(Long.valueOf("200"));
    						purchasePoTo.setReceiveQty(Long.valueOf("186"));
    						purchasePoTo.setNotReceiveQty(Long.valueOf("14"));
    						purchasePoTo.setPoAmount(Double.valueOf(String.valueOf((200*600)))  );
    					}
    					logger.debug(standardCostTo.getProduct() + " - " + purchasePoTo.getPoNum() );
    					
                        //PO
        				poListSheet.addCell(new Label(1, 2 + p, poType , cellFormat));
                        //Project
        				poListSheet.addCell(new Label(2, 2 + p, standardCostTo.getProject() , cellFormat));
        				//PO
        				poListSheet.addCell(new Label(3, 2 + p, purchasePoTo.getPoNum() , cellFormat));
        				//PO QTY
        				poListSheet.addCell(new Number(4, 2 + p, purchasePoTo.getPurchaseQty() , cellFormat));
        				//Receive QTY
        				poListSheet.addCell(new Number(5, 2 + p, purchasePoTo.getReceiveQty() , cellFormat));
        				//Not Receive QTY
        				poListSheet.addCell(new Number(6, 2 + p, purchasePoTo.getNotReceiveQty() , cellFormat));
        				//Unit Price
        				poListSheet.addCell(new Number(7, 2 + p, purchasePoTo.getUnitPrice() , USCurrencyFormat4));
        				//PO Amount
        				poListSheet.addCell(new Number(8, 2 + p, purchasePoTo.getPoAmount() , USCurrencyFormat4));
        				
        				p = p + 1;
    				}
    				    				
    			}else{
    				logger.debug("purchasePoList size() = null " );
    			}

    		}
    		

			
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
