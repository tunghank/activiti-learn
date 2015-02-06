package com.cista.mail;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import java.io.*;
import java.util.Date;


public class MailAlert {

    private Properties props;
    private Session session;
    private MimeMessage msg;
    private Multipart mp;
    private MimeBodyPart mbp1, mbp2;
    public String smtpIP, senderAddr, senderName, subject, content, toMail, ccMail;


    public MailAlert() {
    	
    }

	public void sendWithFile(String message, String attchTmpFile) {

        File attchFile = new File(attchTmpFile);
        this.content = message;
        try {

            //設定 SMTP 伺服器位址---Hashtable
            props = System.getProperties();
            props.put("mail.smtp.host", smtpIP);

            //建立 Session
            session = Session.getDefaultInstance(props, null);
            session.setDebug(false);

            //建立新的 MimeMessage物件
            msg = new MimeMessage(session);

            //加入自訂的標頭
            msg.addHeader("DAI-Lab", "FreeJavaMan");

            //設定送件者資料
            msg.setFrom(new InternetAddress(senderAddr, senderName, "ISO8859-1"));

            //設定收件者資料
            msg.setRecipients(Message.RecipientType.TO, (String) toMail);
            msg.setRecipients(Message.RecipientType.CC, (String) ccMail);
            //設定郵件標題
            msg.setSubject(subject, "ISO8859-1");

            //設定發送時間
            msg.setSentDate(new java.util.Date());

            //建立多內容郵件物件
            //For example, to create a "multipart/alternative" object,
            //use new MimeMultipart("alternative").
            mp = new MimeMultipart();

            //建立郵件內文物件
            mbp1 = new MimeBodyPart();
            mbp1.setText(content, "ISO8859-1");
            mp.addBodyPart(mbp1);

            //建立夾檔物件
            mbp2 = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attchFile);
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName((String) attchFile.getName());
            mp.addBodyPart(mbp2);

            //將MimeMultipart物件加入MimeMessage物件之中
            msg.setContent(mp);

            //儲存設定值
            msg.saveChanges();

            //發送電子郵件
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void sendNoFile(String message) {

        try {

            this.content = message;
            //設定 SMTP 伺服器位址---Hashtable
            props = System.getProperties();
            props.put("mail.smtp.host", smtpIP);

            //建立 Session
            session = Session.getDefaultInstance(props, null);
            session.setDebug(false);

            //建立新的 MimeMessage物件
            msg = new MimeMessage(session);

            //加入自訂的標頭
            msg.addHeader("DAI-Lab", "FreeJavaMan");

            //設定送件者資料
            msg.setFrom(new InternetAddress(senderAddr, senderName, "ISO8859-1"));

            //設定收件者資料
            msg.setRecipients(Message.RecipientType.TO, (String) toMail);
            if( ccMail != null ){
            	msg.setRecipients(Message.RecipientType.CC, (String) ccMail);
            }

            //設定郵件標題
            msg.setSubject(subject, "ISO8859-1");

            //設定發送時間
            msg.setSentDate(new java.util.Date());

            //建立多內容郵件物件
            //For example, to create a "multipart/alternative" object,
            //use new MimeMultipart("alternative").
            mp = new MimeMultipart();

            //建立郵件內文物件
            mbp1 = new MimeBodyPart();
            mbp1.setText(content, "Big5");
            mp.addBodyPart(mbp1);

            //建立夾檔物件

            //將MimeMultipart物件加入MimeMessage物件之中
            msg.setContent(mp);

            //儲存設定值
            msg.saveChanges();

            //發送電子郵件
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendWithFileList(String message, File[] attchFile) {

        String sFileName;
        this.content = message;
        try {

            //設定 SMTP 伺服器位址---Hashtable
            props = System.getProperties();
            props.put("mail.smtp.host", smtpIP);

            //建立 Session
            session = Session.getDefaultInstance(props, null);
            session.setDebug(false);

            //建立新的 MimeMessage物件
            msg = new MimeMessage(session);

            //加入自訂的標頭
            msg.addHeader("DAI-Lab", "FreeJavaMan");

            //設定送件者資料
            msg.setFrom(new InternetAddress(senderAddr, senderName, "ISO8859-1"));

            //設定收件者資料
            msg.setRecipients(Message.RecipientType.TO, (String) toMail);
            msg.setRecipients(Message.RecipientType.CC, (String) ccMail);
            //設定郵件標題
            msg.setSubject(subject, "ISO8859-1");

            //設定發送時間
            msg.setSentDate(new java.util.Date());

            //建立多內容郵件物件
            //For example, to create a "multipart/alternative" object,
            //use new MimeMultipart("alternative").
            mp = new MimeMultipart();

            //建立郵件內文物件
            mbp1 = new MimeBodyPart();
            mbp1.setText(content, "ISO8859-1");
            mp.addBodyPart(mbp1);

            //建立夾檔物件
            for (int i = 0; i < attchFile.length; i++) {
                sFileName = attchFile[i].getName().toString();
                mbp2 = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(attchFile[i]);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(sFileName);
                mp.addBodyPart(mbp2);
            }

            //將MimeMultipart物件加入MimeMessage物件之中
            msg.setContent(mp);

            //儲存設定值
            msg.saveChanges();

            //發送電子郵件
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
	 * @param smtpIP the smtpIP to set
	 */
	public void setSmtpIP(String smtpIP) {
		this.smtpIP = smtpIP;
	}



	/**
	 * @param senderAddr the senderAddr to set
	 */
	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}



	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}



	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}



	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}



	/**
	 * @param toMail the toMail to set
	 */
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}



	/**
	 * @param ccMail the ccMail to set
	 */
	public void setCcMail(String ccMail) {
		this.ccMail = ccMail;
	}
}
