package com.cista.report.action;


import java.io.File;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
import com.cista.report.dao.ERPInventoryDao;
import com.cista.report.to.InventoryTo;
//JExcel
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class InventoryReport extends BaseAction{
	

	private static final long serialVersionUID = 1L;

	public String WeeklyInventoryPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}
	
	public String WeeklyInventory() throws Exception{
		
		request= ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		
		ERPInventoryDao erpInventoryDao = new ERPInventoryDao();
		List<InventoryTo> inventoryList = erpInventoryDao.getInventory();
		
		logger.debug("inventoryList Size " + inventoryList.size() );
		logger.debug("inventoryList " + inventoryList.toString());
		
		//1.0 to read a file from Web Content
		ServletContext servletContext = ServletActionContext.getServletContext();
		String contextPath = servletContext.getRealPath(File.separator);
		
		logger.debug("<br/>File system context path (in TestServlet): " + contextPath);
		String templateFile = contextPath + File.separator + "Report\\Excel" + File.separator
                + "Weekly inventory Report.xls";

        File excelTemplate = new File(templateFile);
        logger.debug("excelTemplate Size " +  excelTemplate.length() );
		
        createExcel(response, excelTemplate);
		return NONE;
	}
	
	
    private void createExcel(HttpServletResponse response , File excelTemplate) {

        int row = 2;

        //HSSFWorkbook wb = new HSSFWorkbook();
        try {
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
            WritableSheet sheet = outWorkbook.getSheet(0);
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
            sheet.addCell(new Label(0, 0, "ECOA List", headFormat));
            sheet.mergeCells(0, 0, 3, 0);

            // �إߦC���� row
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat.setWrap(false);
            cellFormat.setBackground(Colour.WHITE);

            //Report Time
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            String rptDT = dateFormat.format(calendar.getTime()).toString();
            sheet.addCell(new Label(0, 1, "Report Time: " + rptDT, cellFormat));
            sheet.mergeCells(0, 1, 3, 1);



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
        }
    }
}
