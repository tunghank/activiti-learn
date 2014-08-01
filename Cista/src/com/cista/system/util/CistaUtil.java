package com.cista.system.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cista.framework.security.Encryptor;
import com.cista.system.to.SysUserTo;
//Spring
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
/**
 * <p>Title: LicenseEncryptor</p>
 * <p>Description: Encryptor of License</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Himax</p>
 * @author 900730
 * @version 1.0
 */

public class CistaUtil {
    protected final static Log logger = LogFactory.getLog(CistaUtil.class);
    public static final String GLOBAL_PROPERTIES_FILE = "cista.properties";
    private static Properties GLOBAL_PROPERTIES = null;
    
	/** Cista Site Code */
	public static final String CISTA_SITE = "CISTA";
	
    /** Spring */
    
    /** HLH Context configuration file path and name. */
    private static final String[] CONTEXT_CONFIGURATION = new String[] {
            "applicationContext.xml" };
    
    /** The Core ApplicationContext of springframework, instanced by initialize() once only. **/
    private static ApplicationContext context;

    /** Default locale for i18n message fetch. */
    private static final Locale DEFAULT_LOCALE = Locale.TAIWAN;
    
    
    /** Get Application Message File **/
    private static Locale locale = Locale.getDefault();
    private static ResourceBundle APPLICATION_MESSAGE_FILE =
    	ResourceBundle.getBundle("application", locale);
    
    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
    
    /** UserInfoTo key in session. */
    public static final String CUR_USERINFO = "CUR_USERINFO";
    
    /** UserInfoTo key in session. */
    public static final String USER_ACTIVE = "1";
    public static final String USER_INACTIVE = "0";
    
   
    // Report Page size setting
    public static final String PAGE_SIZE="REPORT_PAGE_SIZE";
    public static final String RESULT_SIZE="REPORT_RESULT_SIZE";
    public static final String PAGES="REPORT_PAGES";
    public static final String PAGENO="REPORT_PAGENO";
    public static final int REPORT_PAGE_SIZE = 20;
    
    // spilt char
    public static final String split = "%";
    
    // Account common setting
    /*
     * Role
     */
    public static final String CISTA_ROLE = "1";
    public static final String CUSTOMER_ROLE = "2";
    public static final String VENDOR_ROLE = "3";
    

    
    public static final String FUNCTION_FILE = "file";
    public static final String FUNCTION_FOLDER = "folder";
    public static final String[] FUNCTION_CLS = {"file","folder"};
    public static final String[][] MAIL_BOX = {
    		{"cistadesign.com"			,"cistadesign.com(CISTA)"}										
	};

    
    /**
     * Send email.
     * @param smtpSvr stmp server
     * @param from mail_from
     * @param inToList list of mail_to
     * @param inCcList list of mail_cc
     * @param inSubject mail_subject
     * @param inContents mail_contents
     * @return true, if send mail success, otherwise return false.
     * @throws MessagingException
     */
    public static boolean sendMail(String smtpSvr, String from,
                                   ArrayList<String> inToList, ArrayList<String> inCcList,
                                   String inSubject, String inContents)
        throws MessagingException {

        if (smtpSvr == null || from == null) {
            return false;
        }
        ArrayList<String> toList = (inToList == null) ? new ArrayList<String>() : inToList;
        ArrayList<String> ccList = (inCcList == null) ? new ArrayList<String>() : inCcList;
        String subject = (inSubject == null) ? "" : inSubject;
        String contents = (inContents == null) ? "" : inContents;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpSvr);
        Session session = Session.getInstance(props);
        MimeMessage message = null;
        try {
            message = new MimeMessage(session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        InternetAddress fromAdr = new InternetAddress(from);
        message.setFrom(fromAdr);
        //add TO List
        String toStr, ccStr;
        for (int i = 0; i < toList.size(); i++) {
            toStr = (String) toList.get(i);
            InternetAddress toAdr = new InternetAddress(toStr);
            message.addRecipient(Message.RecipientType.TO, toAdr);
        }
        //add CC list
        for (int j = 0; j < ccList.size(); j++) {
            ccStr = (String) ccList.get(j);
            InternetAddress ccAdr = new InternetAddress(ccStr);
            message.addRecipient(Message.RecipientType.CC, ccAdr);
        }

        message.setSubject(subject);
        message.setText(contents);
        Transport.send(message);

        return true;
    }

    /**
     * Send email.
     * @param smtpSvr stmp server
     * @param from mail_from
     * @param inToList list of mail_to
     * @param inCcList list of mail_cc
     * @param inSubject mail_subject
     * @param inContents mail_contents
     * @return true, if send mail success, otherwise return false.
     * @throws MessagingException
     */
    public static boolean sendHTMLMail(String smtpSvr, String from,
                                   ArrayList<String> inToList, ArrayList<String> inCcList, ArrayList<String> inBccList ,
                                   String inSubject, String inContents)
        throws MessagingException {

        if (smtpSvr == null || from == null) {
            return false;
        }
        ArrayList<String> toList = (inToList == null) ? new ArrayList<String>() : inToList;
        ArrayList<String> ccList = (inCcList == null) ? new ArrayList<String>() : inCcList;
        ArrayList<String> bccList = (inBccList == null) ? new ArrayList<String>() : inBccList;
        String subject = (inSubject == null) ? "" : inSubject;
        String contents = (inContents == null) ? "" : inContents;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpSvr);
        Session session = Session.getInstance(props);
        MimeMessage message = null;

        try {
            message = new MimeMessage(session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        InternetAddress fromAdr = new InternetAddress(from);
        message.setFrom(fromAdr);
        //add TO List
        String toStr, ccStr , bccStr;
        for (int i = 0; i < toList.size(); i++) {
            toStr = (String) toList.get(i);
            InternetAddress toAdr = new InternetAddress(toStr);
            message.addRecipient(Message.RecipientType.TO, toAdr);
        }
        //add CC list
        for (int j = 0; j < ccList.size(); j++) {
            ccStr = (String) ccList.get(j);
            InternetAddress ccAdr = new InternetAddress(ccStr);
            message.addRecipient(Message.RecipientType.CC, ccAdr);           
        }
        //add BCC list
        for (int j = 0; j < bccList.size(); j++) {
            bccStr = (String) bccList.get(j);
            InternetAddress bccAdr = new InternetAddress(bccStr);
            message.addRecipient(Message.RecipientType.BCC, bccAdr);           
        }
        message.setSubject(subject, "UTF-8");
        //message.setSubject(subject);
        // mail 的格式(內容) html
        message.setContent(contents,"text/html;charset=UTF-8");
        //message.setText(contents);
        Transport.send(message);

        return true;
    }


    
    /**
     * Send email.
     * @param smtpSvr stmp server
     * @param from mail_from
     * @param inToList list of mail_to
     * @param inCcList list of mail_cc
     * @param inBccList list of mail_bcc
     * @param inSubject mail_subject
     * @param inContents mail_contents
     * @return true, if send mail success, otherwise return false.
     * @throws MessagingException
     */
    public static boolean sendHTMLBccMail(String smtpSvr, String from,
                                   ArrayList<String> inToList, ArrayList<String> inCcList,ArrayList<String> inBccList,
                                   String inSubject, String inContents)
        throws MessagingException {

        if (smtpSvr == null || from == null) {
            return false;
        }
        ArrayList<String> toList = (inToList == null) ? new ArrayList<String>() : inToList;
        ArrayList<String> ccList = (inCcList == null) ? new ArrayList<String>() : inCcList;
        ArrayList<String> bccList = (inBccList == null) ? new ArrayList<String>() : inBccList;
        String subject = (inSubject == null) ? "" : inSubject;
        String contents = (inContents == null) ? "" : inContents;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpSvr);
        Session session = Session.getInstance(props);
        MimeMessage message = null;

        try {
            message = new MimeMessage(session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        InternetAddress fromAdr = new InternetAddress(from);
        message.setFrom(fromAdr);
        //add TO List
        String toStr, ccStr , bccStr;
        for (int i = 0; i < toList.size(); i++) {
            toStr = (String) toList.get(i);
            InternetAddress toAdr = new InternetAddress(toStr);
            message.addRecipient(Message.RecipientType.TO, toAdr);
        }
        //add CC list
        for (int j = 0; j < ccList.size(); j++) {
            ccStr = (String) ccList.get(j);
            InternetAddress ccAdr = new InternetAddress(ccStr);
            message.addRecipient(Message.RecipientType.CC, ccAdr);           
        }
        //add BCC list
        for (int j = 0; j < bccList.size(); j++) {
            bccStr = (String) bccList.get(j);
            logger.debug(bccStr);
            InternetAddress bccAdr = new InternetAddress(bccStr);
            message.addRecipient(Message.RecipientType.BCC, bccAdr);           
        }        
        message.setSubject(subject, "UTF-8");       
        // mail 的格式(內容) html
        message.setContent(contents,"text/html;charset=UTF-8");
        //message.setText(contents);
        Transport.send(message);

        return true;
    }

    /**
     * Send email with attachment.
     * @param smtpSvr stmp server
     * @param from mail_from
     * @param inToList list of mail_to
     * @param inCcList list of mail_cc
     * @param inSubject mail_subject
     * @param inContents mail_contents
     * @param files mail_attachment
     * @return  true, if send mail success, otherwise return false.
     */
    public static boolean sendMailWithAttachment(String smtpSvr, String from,
                                                 List inToList,
                                                 List inCcList,
                                                 String inSubject,
                                                 String inContents, List files) {
        if (smtpSvr == null || from == null || inToList == null
            || inToList.size() == 0) {
            return false;
        }
        String subject = (inSubject == null) ? "" : inSubject;
        String contents = (inContents == null) ? "" : inContents;

        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpSvr);
        Session session = Session.getDefaultInstance(props);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));

            InternetAddress[] toAdd = new InternetAddress[inToList.size()];
            for (int i = 0; i < inToList.size(); i++) {
                toAdd[i] = new InternetAddress((String) inToList.get(i));
            }
            msg.setRecipients(Message.RecipientType.TO, toAdd);
            if (inCcList != null && inCcList.size() > 0) {
                InternetAddress[] ccAdd = new InternetAddress[inCcList.size()];
                for (int i = 0; i < inCcList.size(); i++) {
                    ccAdd[i] = new InternetAddress((String) inCcList.get(i));
                }
                msg.setRecipients(Message.RecipientType.TO, ccAdd);
            }

            msg.setSubject(subject);

            if (files != null && files.size() > 0) {
                Multipart mp = new MimeMultipart();
                for (int i = 0; i < files.size(); i++) {
                    MimeBodyPart mbp = new MimeBodyPart();
                    String filename = (String) files.get(i);
                    FileDataSource fds = new FileDataSource(filename);
                    mbp.setDataHandler(new DataHandler(fds));
                    mbp.setFileName(fds.getName());
                    mp.addBodyPart(mbp);
                }
                //set text
                MimeBodyPart mbp = new MimeBodyPart();
                mbp.setText(contents, "BIG5");
                mp.addBodyPart(mbp);

                msg.setContent(mp);
            } else {
                msg.setText(contents, "BIG5");
            }
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException mex) {
            logger.error("Error while send email with attachment.", mex);
            return false;
        }
        return true;
    }

    public static void sendInitialUserMail(
    		HttpServletRequest request,	HttpServletResponse response,
			String mailSubject, SysUserTo user,String senderMail) {    	
    	    	
		// 1.6 Send Alert Mail
		request.getRequestURI();

		ArrayList<String> mailTo = new ArrayList<String>();
		ArrayList<String> bccTo = new ArrayList<String>();
		mailTo.add(user.getEmail());
		bccTo.add(senderMail);
		
		String serverName = request.getServerName();
		String serverPort = String.valueOf(request.getServerPort());
		String reqUrl = "http://" + serverName + ":" + serverPort + "/";		
		String sysAdminMail = CistaUtil.getMessage("IE.sysadmin.email");
		String contentsStr="";

		// 1.6.1 mail smtp server defined in the configure file , himax.property
		String smtpSvr = CistaUtil.getConfig("SMTPServer");
		contentsStr = CistaUtil.insertUserComment(reqUrl,user);
        
		// 1.6.4 Send Mail
		// Send Mail
		SendMailThread sendMail = new SendMailThread(smtpSvr,
				sysAdminMail,mailTo, null,bccTo, mailSubject, contentsStr);        
        sendMail.start();
        sendMail.interrupt();
	}
    
    public static String insertUserComment(String reqUrl ,SysUserTo user) {

    	String contentsStr = "";
    	String baseUrl = "";
    	String toUrl = "";
    	int baseFrom = reqUrl.indexOf("//");
    	int baseTo = reqUrl.indexOf("/", baseFrom + 2);
    	baseUrl = reqUrl.substring(0, baseTo);
    	toUrl = baseUrl;
    	String strPassword = (user.getCompany().equals(CistaUtil.CISTA_ROLE)?"(Please use Windows Login Password)":CistaUtil.decodePasswd(user.getPassword()));    	
    	
    	contentsStr =  "<html><head><meta http-equiv='Content-Type' "
    		+ "content='text/html; charset=UTF8'></head><body>" +"<P></P> " +"<P></P> "     		
    		+ "<b><font color='#7D9EC0'>" 
    		+ " Hi   </font><font color = '#CD853F'>" + user.getRealName()+ "</font><font color='#7D9EC0'> ,</font><br><br>"
    		+ "  &nbsp&nbsp&nbsp&nbsp <font color = '#7D9EC0'>Welcome , Your Account was be created by administator. <br>"
    		+ "  If you want to modify your personal detail , you can find the function in the top menu with name </font><font color='#CD853F'> myProfile </font>.<br>"
    		+ "  <br><table border ='1' width = 50%>"
    		+ "	 <tr><td bgcolor='#B9D3EE'>Account</td><td>" + user.getUserId() +"</td>"
    		+ "  <tr><td bgcolor='#B9D3EE'>Password</td><td>" +strPassword+"</td>"
    		+ "  </table>"    	
    		+ "<br>"
    		
    		+ "</font></b><br>"
    		+ "<a target='_blank' href='" + toUrl+ "' > Go To Himax IE System</a>" ;
    	
    	contentsStr = contentsStr + "</body></html>";
    	return contentsStr;
    }
    /**
     * Util method to check target string whether is in target list.
     * @param list target List.
     * @param tar  target string.
         * @return true, if target string is in target list; otherwise return false.
     */
    public static boolean isInList(ArrayList list, String tar) {
        boolean ret = false;

        for (int i = 0; i < list.size(); i++) {
            if (tar.equals((String) list.get(i))) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    // password encoder.
    private static Encryptor ENCTOR = new Encryptor();
    /**
     * Encode the password.
     * @param password password string.
     * @return encoded password
     */
    public static String encodePasswd(String password) {
        return ENCTOR.encodeStrOf(password);
    }

    /**
     * Decode the password.
     * @param password  encoded password string.
     * @return decoded password
     */
    public static String decodePasswd(String password) {
        return ENCTOR.decodeStrOf(password);

    }

    /**
     * Util method is to cut email address string with charactor '@'
     * @param email email address.
     * @return front part of email address.
     */
    public static String cutEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    // date time format for dash
    private static final String DATE_TIME_FORMAT_DASH = "yyyy-MM-dd HH:mm:ss";
    /**
     * Util mathod is to format date time.
     * @param date long format date time
     * @return string format date time.
     */
    public static String formatDateTime(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT_DASH);
        Date dt = new Date(date);
        String formattedDate = formatter.format(dt);
        return formattedDate;
    }
    // date time format for news part
    public static final String DATE_TIME_FORMAT_NEWS = "yyyy/MM/dd";
    /**
     * Util method is to format date time.
     * @param dt date format date time
     * @param pattern format pattern
     * @return formated date time.
     */
    public static String formatDateTime(Date dt, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(dt);
    }
    /**
     * Util method is to get configration information.
     * @param key config key
     * @return config value
     */
    public synchronized static String getConfig(String key) {
        if (GLOBAL_PROPERTIES == null) {
            GLOBAL_PROPERTIES = new Properties();
            try {
            	ClassLoader classLoader = CistaUtil.class.getClassLoader();
               
                GLOBAL_PROPERTIES.load(classLoader.getResourceAsStream(
                    GLOBAL_PROPERTIES_FILE));
            } catch (IOException ex) {
                logger.error("config file " + GLOBAL_PROPERTIES_FILE
                                   + " not find, config file fail.");
            }
        }
        return (String) GLOBAL_PROPERTIES.get(key);
    }
    
        
    /**
     * Util method is to get date of month.
     * @return date of month
     */
    public static String getFirstDateOfMonthStr() {
        Date d = new Date();
        d.setDate(1);
        return SDF.format(d);
    }
    /**
     * Util method is to get todate string.
     * @return todate string.
     */
    public static String getTodayStr() {
        Date d = new Date();
        return SDF.format(d);
    }

    public static void swap(int[] number, int i, int j) {
        int temp;

        temp = number[j];
        number[j] = number[i];
        number[i] = temp;
    }

    public static int bubbleSort(List dataList) {
        Object[] tmp =  dataList.toArray();
        int[] data = new int[dataList.size()];

        //List2Array
        for (int i = 0; i <= dataList.size() - 1; i++) {
            data[i] = Integer.parseInt( tmp[i].toString().trim() );
        }

        if ( data.length > 1 ){
            for (int i = 0; i <= data.length - 1; i++) {
                for (int j = 0; j < data.length - 1 - i; j++) {
                    if (data[j] > data[j + 1]) {
                        swap(data, j, j + 1);
                    }
                }
            }
            return data[data.length - 1];
        }else{
            return data[data.length - 1];
        }
    }

    /**
     * Get message from properties file.
     * @param key property key
     * @return property value.
     */
    public synchronized static String getMessage(String key) {
    	return APPLICATION_MESSAGE_FILE.getString(key);
    }
    
    /**
     * Get message from properties file.
     * @param key  property key
     * @param value0 replaced part
     * @return property value.
     */
    public static String getMessage(String key, String value0) {
        String v = getMessage(key);
        if (value0 != null && v.indexOf("{0}") >= 0) {
            v = StringUtils.replace(v, "{0}", value0);
        }
        return v;
    }
    /**
      * Get message from properties file.
      * @param key  property key
      * @param value0 replaced part 1
      * @param value1 replaced part 2
      * @return property value.
      */
    public static String getMessage(String key, String value0, String value1) {
        String v = getMessage(key);
        if (value0 != null && v.indexOf("{0}") >= 0) {
            v = StringUtils.replace(v, "{0}", value0);
        }
        if (value1 != null && v.indexOf("{1}") >= 0) {
            v = StringUtils.replace(v, "{1}", value1);
        }
        return v;
    }
    
    public static void main(String[] args) {
//        List ff = new ArrayList();
//        ff.add("c:/dscwebasp.rar");
//        List to = new ArrayList();
//        to.add("matrix@cavell.com.tw");
//        List cc = new ArrayList();
//        //System.out.println(sendMailWithAttachment("211.75.40.150",
//                                                  "allen@cavell.com.tw", to, cc,
//                                                  "test", "繁體中文", ff));
    }
    
    /**
     * Spring Util
     */
    
    /**
     * BeanFactory initialize internal only.
     *
     */
    private static synchronized void initialize() {
        try {
            if (context == null) {
                //init application context.
                context = new ClassPathXmlApplicationContext(CONTEXT_CONFIGURATION);
            }
        } catch (Exception e) {
        	logger.error("ApplicationContext "
                    + CONTEXT_CONFIGURATION[0] + " load failure.", e);
        }
    }

    /**
     * Return JDBC DataSource.
     * @return DataSource.
     */
    public static DataSource getDataSource() {
        initialize();
        return (DataSource) context.getBean("SystemDataSource");
    }

    /**
     * Return a TransactionTemplate.
     * @return TransactionTemplate.
     */
    public static TransactionTemplate getTransactionTemplate() {
        initialize();
        return (TransactionTemplate) context.getBean("SystemTransactionTemplate");
    }
    
    
    /**
     * Return a TransactionTemplate.
     * @return TransactionTemplate.
     */
    public static DataSourceTransactionManager getTransactionManager() {
        initialize();
        return (DataSourceTransactionManager) context.getBean("SystemTransactionManager");
    }
    
    

    
    public static int calcPages(int resultSize, int pageSize) {
        int pages = resultSize / pageSize;
        if (resultSize % pageSize != 0) {
            pages += 1;
        }
        return pages;
    }
    public static List cutResult(List list, int pageNo, int pageSize) {
    	
    	if (list == null || list.size() == 0) {
			return null;
		} else {
			int cacheCount = (pageNo - 1);
			int fromIdx = cacheCount * pageSize + 1;
			int toIdx = (cacheCount + 1) * pageSize;

			if (toIdx > list.size()) {
				toIdx = list.size();
			}

			if (fromIdx > toIdx) {
				fromIdx = 1;
			}
			return list.subList(fromIdx - 1, toIdx);
		}
    }

    public static int getWorkNoString(int weekToMove, int y, int m, int d) {
        Calendar cal = new GregorianCalendar(TimeZone.getDefault());
        cal.set( Calendar.YEAR,  y);
        cal.set( Calendar.MONTH, m );
        cal.set( Calendar.DAY_OF_MONTH, d );
        // cal = 01.Jan.2002

        cal.add(Calendar.WEEK_OF_YEAR,weekToMove);

        int week = cal.get(Calendar.WEEK_OF_YEAR);

        return week;
    }
 
    public static void copyFile(File in, File out) throws Exception {
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[1024];
        int i = 0;
        while ((i = fis.read(buf)) != -1) {
            fos.write(buf, 0, i);

        }
        fis.close();
        fos.close();
    }

    public static void copyFile(FileInputStream fis, File out) throws Exception {
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[1024];
        int i = 0;
        while ((i = fis.read(buf)) != -1) {
            fos.write(buf, 0, i);

        }
        fis.close();
        fos.close();
    }

	public static void downLoadFile(HttpServletRequest request,
			HttpServletResponse response, String fileName, String fileFullName,
			String contentType) throws Exception {

		File file = new File(fileFullName);
		FileInputStream fIn = new FileInputStream(file);
		request.setCharacterEncoding("UTF-8");

		response.reset(); // Reset the response
		response.setCharacterEncoding("UTF-8");
		response.setContentType("" + contentType + ";charset=utf-8");

		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ java.net.URLEncoder.encode(fileName, "UTF-8") + "\"");
		byte[] buf = new byte[1024];
		ServletOutputStream out = response.getOutputStream();
		try {
			int i = 0;
			while ((i = fIn.read(buf)) != -1) {
				out.write(buf, 0, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		out.close();
		fIn.close();
		fIn = null;
		file = null;

	}

	// 刪除ArrayList中重複元素
	public static List removeDuplicate(List list) {
	  HashSet h = new HashSet(list);
	  list.clear();
	  list.addAll(h);
	  //System.out.println(list);
	  return list;
	  
	}
	
	public static List removeDuplicateObj(List list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		//System.out.println(list);
		return list;
	} 
	
	//刪除ArrayList中重複元素，保持順序
	public static void removeDuplicateWithOrder(List list) {
	  Set set = new HashSet();
	  List newList = new ArrayList();
	  for (Iterator iter = list.iterator(); iter.hasNext();) {
	   Object element = iter.next();
	   if (set.add(element))
	    newList.add(element);
	  }
	  list.clear();
	  list.addAll(newList);
	  //System.out.println("remove duplicate" + list);
	}
}
