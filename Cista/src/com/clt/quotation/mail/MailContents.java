package com.clt.quotation.mail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.clt.system.to.SysUserTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.SendMailThread;
import com.clt.quotation.config.dao.QuoPaperVerDao;
import com.clt.quotation.config.to.QuoCatalogPaperVerTo;
import com.clt.quotation.inquiry.dao.InquiryInformDao;
import com.clt.quotation.inquiry.to.InquiryHeaderTo;
import com.clt.quotation.inquiry.to.InquiryInformTo;
import com.clt.quotation.inquiry.to.InquirySupplierContactTo;
import com.clt.quotation.inquiry.to.InquirySupplierTo;
import com.clt.quotation.quote.to.QuoteHeaderTo;

public class MailContents {
	protected final static Log logger = LogFactory
			.getLog(MailContents.class);

	public static void sendInquiryMail(HttpServletRequest request,
			HttpServletResponse response, 
			List<QuoteHeaderTo> quoteHeaderList,
			InquirySupplierTo supplierTo,
			List<InquirySupplierContactTo> mailInquirySupplierContactList,
			String inquiryGroupUid ) {

		/*************************************************
		 * 	1.6 Mail to Inform 
		 ************************************************/
		//Get Currency User
        SysUserTo curUser = (SysUserTo) request.getSession().getAttribute(CLTUtil.CUR_USERINFO);
		String sourcer = curUser.getRealName() + " " + curUser.getPhoneNum();
		
		String contentsStr = "";
		String subjectStr = "";
		String sysAdminMail = CLTUtil.getMessage("System.sysadmin.email");
		logger.debug("Save URL " + request.getRequestURL());
		String reqUrl = request.getRequestURL().toString();
		String baseUrl = "";
		String toUrl = "";
		int baseFrom = reqUrl.indexOf("//");
		int baseTo = reqUrl.indexOf("/", baseFrom + 2);

		// mail smtp server defined in the configure file , himax.property
		String smtpSvr = CLTUtil.getConfig("SMTPServer");
		
		// ArrayList mailTo = new ArrayList();

		try {

			
			subjectStr = CLTUtil.getMessage("Inquiry.email.subject");
			baseUrl = request.getRequestURL().substring(0, baseTo);
			toUrl = baseUrl + request.getContextPath()
					+ "/QuoteMailLink.action?quoteHeaderUid=";

			String message = CLTUtil.getMessage("Inquiry.email.quote.link.html");
			String linkContent = CLTUtil
					.getMessage("Inquiry.email.quote.link");

			List<InquiryInformTo> informList = new ArrayList<InquiryInformTo>();
			

			ArrayList<String> toMailList = new ArrayList<String>();
			for(int i =0; i < mailInquirySupplierContactList.size(); i ++){
				InquirySupplierContactTo inquirySupplierContactTo = mailInquirySupplierContactList.get(i);
				
				String informUid = UUID.randomUUID().toString().toUpperCase();
				InquiryInformTo informTo = new InquiryInformTo();	
				//Inform To
				informTo.setInquiryInformUid(informUid);
				informTo.setInquiryHeaderUid(supplierTo.getInquiryHeaderUid());
				informTo.setInquirySupplierUid(supplierTo.getInquirySupplierUid());
				
				informTo.setMailName(inquirySupplierContactTo.getInquirySupplierContact());
				informTo.setMailTo(inquirySupplierContactTo.getInquirySupplierEmail());
				informList.add(informTo);
					
				// 1.6.1.1 Insert Mail TO List
				//UAT toMailList.add(inquirySupplierContactTo.getInquirySupplierEmail());
				 toMailList.add("hank_tang@chilintech.com.tw");
				logger.debug("Mail To : " + inquirySupplierContactTo.getInquirySupplierEmail());
			}

				
			// Send Mail
			contentsStr = MailContents.initialCaseSubmitContent(message,
						toUrl, linkContent, inquiryGroupUid, 
						quoteHeaderList, supplierTo, sourcer);
				
			SendMailThread sendMail = new SendMailThread(smtpSvr, sysAdminMail,
						toMailList, null, null, subjectStr, contentsStr);
			sendMail.start();
			sendMail.interrupt();

			
			// 1.6.1.2 Insert Inform Data to DB
			InquiryInformDao informDao = new InquiryInformDao();
			informDao.batchInstInquiryInform(informList);
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public static String initialCaseSubmitContent(String message, String toUrl,
			String linkContent, String inquiryGroupUid,
			List<QuoteHeaderTo> quoteHeaderList, 
			InquirySupplierTo supplierTo, String sourcer) {



        
		String background = "#CCDCF7";
		String contentsStr = "<html>"
				+ "<head>"
				+ "<meta http-equiv='Content-Type'content='text/html; charset=UTF-8'>"
				+ "</head>" + "<body>"
				+ "<b><font face='Arial' size='4' color='#000099'>"
				+ message
				+ "</font></b>"
				+ "<P></P><P></P> "

				+ "<P></P><P></P> "
				+ "<br>"
				+ "	<table width=\"99%\"  border=1 cellpadding=0 cellspacing=0 bordercolordark=#004080 >"
				+ "<thead>"
				+ "<tr>"
				+ "<th width=10%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">點選Link前往報價</th>"
				+ "<th width=10%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">供應商</th>"
				+ "<th width=15%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">報價單號</th>"
				+ "<th width=10%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">詢價料號</th>"
				+ "<th width=10%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">品名</th>"
				+ "<th width=10%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">報價單格式</th>"
				+ "<th width=10%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">回收期限</th>"
				+ "<th width=10%  align=\"left\" nowrap bgcolor="
				+ background
				+ ">Sourcer</th>"


				+ "</tr>" + "</thead>" + "<tbody>";
		String className = "";
		QuoPaperVerDao paperVerDao = new QuoPaperVerDao();
		SimpleDateFormat sdtf = new SimpleDateFormat("yyyy:MM:dd HH:mm");
		for (int i = 0; i < quoteHeaderList.size(); i++) {
			QuoteHeaderTo headerTo = quoteHeaderList.get(i);
						
			QuoCatalogPaperVerTo catalogPaperVerTo = paperVerDao.getPaperByVer(headerTo.getPaperVerUid());
			String catalog = catalogPaperVerTo.getCatalog();
			String paper = catalogPaperVerTo.getPaper();
			String paperVer = catalogPaperVerTo.getPaperVer();
			String paperDesc = catalog + "-" +  paper + " Ver " + paperVer;
			
				className = "#FFFFCC";
				if (i % 2 == 0) {
					className = "#F0FFCE";
				}
				// ==============================================
				contentsStr += "<tr>"

						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ "<a target='mainFrame' href='"
						+ toUrl + headerTo.getQuoteHeaderUid()
						+ "' >"
						+ "請點選報價"
						+ "</a>"
						+ "</td>"
					
						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ supplierTo.getInquirySupplierCode() + " " + supplierTo.getInquirySupplierName()
						+ "</td>"
						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ headerTo.getQuoteNum()
						+ "</td>"
						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ headerTo.getQuotePartNum()
						+ "</td>"
						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ headerTo.getQuotePartNumDesc()
						+ "</td>"
						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ paperDesc
						+ "</td>"
						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ sdtf.format(supplierTo.getQuotationRecoverTime())
						+ "</td>"
						+ "<td width=\"100%/13>\"  align=\"left\" nowrap bgcolor="
						+ className
						+ ">&nbsp;"
						+ sourcer
						+ "</td>"


						+ "</tr>";

				// ==================================================
			}
	
		return contentsStr + "</tbody></table></body></html>";
	}

}
