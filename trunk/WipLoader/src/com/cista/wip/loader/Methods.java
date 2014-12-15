package com.cista.wip.loader;

import java.io.File;
import java.util.Vector;
import java.util.List;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cista.job.SystemContext;
import com.cista.mail.MailAlert;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *--
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Methods {
	
	protected static Log logger = LogFactory.getLog(Methods.class);
	private static MailAlert alert = new MailAlert();
	
    public Methods() {
		// TODO Auto-generated constructor stub
		alert.setSmtpIP(SystemContext.getConfig("config.himax.mail.ip"));
		alert.setSenderName(SystemContext.getConfig("config.himax.mail.sender.name"));
		alert.setSenderAddr(SystemContext.getConfig("config.himax.mail.sender.mail"));
		alert.setToMail(SystemContext.getConfig("config.himax.mail.admin.mail"));
		alert.setSubject(SystemContext.getConfig("config.himax.mail.subject"));
    }


    public static List<File> getFiles(File fileDir) {
        File file = fileDir;
        File afile[] = file.listFiles();
        List<File> fileLIst = new Vector();
        for (int i = 0; i < afile.length; i++) {
        	fileLIst.add(afile[i]);
        }

        return fileLIst;
    }
    /**
     * �ɮ׫���
     * @param in File
     * @param out File
     * @throws Exception
     */
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


}
