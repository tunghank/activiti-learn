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

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.clt.quotation.config.dao.QuoCatalogDao;
import com.clt.quotation.config.dao.QuoPaperDao;
import com.clt.quotation.config.dao.QuoPaperVerDao;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import com.clt.quotation.config.to.QuoCatalogTo;
import com.clt.quotation.config.to.QuoPaperCatalogTo;
import com.clt.quotation.config.to.QuoPaperTo;
import com.clt.system.to.SysUserTo;
import com.clt.system.util.BaseAction;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.JsonUtil;

public class QuoPaperEditAction extends BaseAction {
	
	private String paperUid;
	//Edit Version Form
	private String catalog;
	private String paper;
	private String paperVer;
	private String paperVerApprove;
	private String verStartDt;
	private String verEndDt;
	private String catalogUid;
	private String paperVerUid;
	private boolean success; 
	
	public String quoPaperEditPre() throws Exception {
		
		QuoCatalogDao catalDao = new QuoCatalogDao();
		List<QuoCatalogTo> catalogList = catalDao.getAllCatalog();
		
		request.setAttribute("catalogList", catalogList);

		return SUCCESS;
	}
	
	public String ajaxCatalogList() throws Exception {
		
		QuoCatalogDao catalDao = new QuoCatalogDao();
		List<QuoCatalogTo> catalogList = catalDao.getAllCatalog();
		
		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		
		String resultString="";
		sb.append( "[ " ) ;
		for ( int i =0; i < catalogList.size() ; i ++ ){
			QuoCatalogTo catalogTo = catalogList.get(i);
			if ( i != catalogList.size() - 1 ){
				resultString = "['" + 
				catalogTo.getCatalogUid() + "','" +
				catalogTo.getCatalog() + "'],";
			}else{
				resultString = "['" + 
				catalogTo.getCatalogUid() + "','" +
				catalogTo.getCatalog() + "']";
			}
			sb.append( resultString );
		}
		sb.append( " ]" ) ;	
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}
	
	public String fetchPapers() throws Exception {
		
		String catalogUid = request.getParameter("catalogUid");
		catalogUid = null != catalogUid ? catalogUid : "";
		logger.debug("catalog " + catalogUid );
		
		QuoPaperDao paperDao = new QuoPaperDao();
		List<QuoPaperTo> paperList = paperDao.getPapersByCatalog(catalogUid);
		paperList = null != paperList ? paperList : new ArrayList<QuoPaperTo>();
		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();

		for (int i=0; i< paperList.size() ; i++){
			QuoPaperTo paperTo = (QuoPaperTo)paperList.get(i);	
			if( i == paperList.size() - 1 ){
				sb.append( JsonUtil.getJsonString4JavaPOJO(paperTo) );
			}else{
				sb.append( JsonUtil.getJsonString4JavaPOJO(paperTo) + "|" );
			}
			
		}
		
		logger.debug(sb);
		out.println(sb);
		out.close();
		
		return NONE;
	}
	

	public String ajaxCatalogPaperVerList() throws Exception {

		this.paperUid = null != this.paperUid ? this.paperUid : "";
		logger.debug("paperUid " + this.paperUid );
		
		QuoPaperVerDao verDao = new QuoPaperVerDao();
		List<QuoCatalogPaperVerTo> paperVerList = verDao.getVerByPaper(this.paperUid);


		// 1.3 Set AJAX response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		
		
		JSONArray jsonObject = JSONArray.fromObject(paperVerList);
		sb.append(jsonObject.toString());
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;

	}
	//Paper Version Edit
	public String quoPaperVerEdit() throws Exception {
		logger.debug("catalog " + this.catalog );
		
		SimpleDateFormat ddf = new SimpleDateFormat( "yyyyMMdd" );
		SimpleDateFormat dtdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
		Calendar now = Calendar.getInstance();
		SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		
		QuoCatalogPaperVerTo paperVerTo = new QuoCatalogPaperVerTo();
		QuoPaperVerDao verDao = new QuoPaperVerDao();
		
		//Set Form to Object	
		
		//Set Version Active Date
		if(this.paperVerApprove.equals("Yes")){
			paperVerTo.setPaperVerApprove("1");
			paperVerTo.setVerApproveDt(ddf.format(now.getTime()));
		}else{
			paperVerTo.setPaperVerApprove("0");
		}
		
		String verStartDt = this.verStartDt;
		verStartDt =null!=verStartDt?verStartDt:"";
		verStartDt = verStartDt.replaceAll("\\/", "");
		logger.debug("verStartDt " + verStartDt);
		
		String verEndDt = this.verEndDt;
		verEndDt =null!=verEndDt?verEndDt:"";
		verEndDt = verEndDt.replaceAll("\\/", "");
		logger.debug("verEndDt " + verEndDt);
		
		if(!verStartDt.equals("") && !verEndDt.equals("")){
			paperVerTo.setVerStartDt(verStartDt);
			paperVerTo.setVerEndDt(verEndDt);
		}
		paperVerTo.setVerUpdateBy(curUser.getUserId());
		paperVerTo.setUdt(dtdf.format(now.getTime()));
		paperVerTo.setPaperVerUid(this.paperVerUid);
		paperVerTo.setPaperUid(this.paperUid);
		
		logger.debug("paperVerTo " + paperVerTo.toString());
		
		String paperVerApprove = paperVerTo.getPaperVerApprove();
		String paperUid = paperVerTo.getPaperUid();
		// 只能有一個 版本Active
		logger.debug("paperVerApprove " + paperVerApprove);
		logger.debug("paperUid " + paperUid);
		if( paperVerApprove.equals("1")){
			verDao.cleanPaperVerActive(paperUid);
		}
		verDao.updPaperVerByKey(paperVerTo);

		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		//EXT JS response JSON
		sb.append("{success: true,data: {}}");


		
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}
	
	public String quoPaperVerAdd() throws Exception {
		
		logger.debug("catalog " + this.catalog );
		
		SimpleDateFormat ddf = new SimpleDateFormat( "yyyyMMdd" );
		SimpleDateFormat dtdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
		NumberFormat nft = new DecimalFormat("###.#");
		Calendar now = Calendar.getInstance();
		SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		
		QuoCatalogPaperVerTo paperVerTo = new QuoCatalogPaperVerTo();
		QuoPaperVerDao verDao = new QuoPaperVerDao();
		
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();
		//EXT JS response JSON
		String flag="";
		
		
		//Set Form to Object	
		paperVerTo.setPaperVerUid(UUID.randomUUID().toString().toUpperCase());
		paperVerTo.setPaperUid(this.paperUid);
		double nextVer = verDao.getMaxVerByPaper(this.paperUid);
		if( nextVer <= 0.0 ){
			flag = "{success: false,data: {}}";
		}else{
			nextVer = nextVer + 0.1;
			paperVerTo.setPaperVer(nft.format(nextVer));
			logger.debug("nextVer " + nft.format(nextVer));
			paperVerTo.setPaperVerApprove("0");
			paperVerTo.setVerCreateBy(curUser.getUserId());
			verDao.instPaperVerByKey(paperVerTo);
			flag = "{success: true,data: {}}";
		}

		logger.debug("paperVerTo " + paperVerTo.toString());

		sb.append(flag);
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}
	
	public String quoPaperAdd() throws Exception {
		
		logger.debug("catalog " + this.catalog );
		logger.debug("this.catalogUid " + this.catalogUid );
		
		SimpleDateFormat ddf = new SimpleDateFormat( "yyyyMMdd" );
		SimpleDateFormat dtdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
		NumberFormat nft = new DecimalFormat("###.#");
		Calendar now = Calendar.getInstance();
		SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		
		final QuoPaperTo paperTo = new QuoPaperTo();
		final QuoCatalogPaperVerTo paperVerTo = new QuoCatalogPaperVerTo();
		final QuoPaperCatalogTo paperCatalogTo = new QuoPaperCatalogTo();
		
		final QuoPaperDao paperDao = new QuoPaperDao();
		final QuoPaperVerDao verDao = new QuoPaperVerDao();
		final QuoCatalogDao  catalogDao = new QuoCatalogDao();
		
		StringBuilder sb = new StringBuilder();
		PrintWriter out = response.getWriter();	
		
		//Set Form to Object
		String uuid = UUID.randomUUID().toString().toUpperCase();
		String paperUid = UUID.randomUUID().toString().toUpperCase();
		//1.0 By Paper 
		paperTo.setPaperUid(paperUid);
		paperTo.setPaper(this.paper);
		//1.0 By Paper Catalog
		paperCatalogTo.setPaperCatalogUid(uuid);
		paperCatalogTo.setCatalogUid(this.catalogUid);
		paperCatalogTo.setPaperUid(paperUid);
		//1.1 By Paper Version
		paperVerTo.setPaperVerUid(uuid);
		paperVerTo.setPaperUid(paperUid);
		paperVerTo.setPaperVer("1");
		paperVerTo.setVerCreateBy(curUser.getUserId());
		//Set Version Active Date
		if(this.paperVerApprove.equals("Yes")){
			paperVerTo.setPaperVerApprove("1");
			paperVerTo.setVerApproveDt(ddf.format(now.getTime()));
		}else{
			paperVerTo.setPaperVerApprove("0");
		}
		
		String verStartDt = this.verStartDt;
		verStartDt =null!=verStartDt?verStartDt:"";
		verStartDt = verStartDt.replaceAll("\\/", "");
		logger.debug("verStartDt " + verStartDt);
		
		String verEndDt = this.verEndDt;
		verEndDt =null!=verEndDt?verEndDt:"";
		verEndDt = verEndDt.replaceAll("\\/", "");
		logger.debug("verEndDt " + verEndDt);
		
		if(!verStartDt.equals("") && !verEndDt.equals("")){
			paperVerTo.setVerStartDt(verStartDt);
			paperVerTo.setVerEndDt(verEndDt);
		}
		
		logger.debug("paperTo " + paperTo.toString());
		logger.debug("paperCatalogTo " + paperCatalogTo.toString());
		logger.debug("paperVerTo " + paperVerTo.toString());
		
		//EXT JS response JSON
		String flag = "{\"success\":\"true\",\"data\":{\"paperUid\":\""+ paperUid + "\"" +
				", \"catalogUid\":\""+ this.catalogUid + "\"" +
				", \"paper\":\""+ this.paper + "\"" +
				"}}";
		
		
		TransactionCallbackWithoutResult callback = new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(
					final TransactionStatus status) {
				
				paperDao.instPaperByKey(paperTo);
				catalogDao.instPaperCatalog(paperCatalogTo);
				verDao.instPaperVerByKey(paperVerTo);
			}
		};

		new QuoPaperVerDao().doInTransaction(callback);
		


		sb.append(flag);
		logger.debug(sb);
		out.println(sb.toString());
		out.close();
		
		return NONE;
	}
	
	//FORM
	public String getPaperUid() {
		return paperUid;
	}

	public void setPaperUid(String paperUid) {
		this.paperUid = paperUid;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getPaperVer() {
		return paperVer;
	}

	public void setPaperVer(String paperVer) {
		this.paperVer = paperVer;
	}

	public String getPaperVerApprove() {
		return paperVerApprove;
	}

	public void setPaperVerApprove(String paperVerApprove) {
		this.paperVerApprove = paperVerApprove;
	}

	public String getVerStartDt() {
		return verStartDt;
	}

	public void setVerStartDt(String verStartDt) {
		this.verStartDt = verStartDt;
	}

	public String getVerEndDt() {
		return verEndDt;
	}

	public void setVerEndDt(String verEndDt) {
		this.verEndDt = verEndDt;
	}

	public String getCatalogUid() {
		return catalogUid;
	}

	public void setCatalogUid(String catalogUid) {
		this.catalogUid = catalogUid;
	}

	public String getPaperVerUid() {
		return paperVerUid;
	}

	public void setPaperVerUid(String paperVerUid) {
		this.paperVerUid = paperVerUid;
	}


	//Success Flag
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
