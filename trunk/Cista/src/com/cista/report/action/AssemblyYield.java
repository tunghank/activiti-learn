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

public class AssemblyYield extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	private String project;
	private String edate;
	
	public String AssemblyYieldPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
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
