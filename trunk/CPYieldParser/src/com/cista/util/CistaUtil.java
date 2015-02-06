package com.cista.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


//Spring
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.cista.job.DataImportEngine;
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
    public static final String GLOBAL_PROPERTIES_FILE = "himax.properties";
    private static Properties GLOBAL_PROPERTIES = null;
    

    /** Spring */
    
    /** HLH Context configuration file path and name. */
    private static final String[] CONTEXT_CONFIGURATION = new String[] {
            "SystemContext.xml", "JobContextImport.xml"};
    
    /** The Core ApplicationContext of springframework, instanced by initialize() once only. **/
    private static ApplicationContext context;

    /** Default locale for i18n message fetch. */
    private static final Locale DEFAULT_LOCALE = Locale.TAIWAN;
    
    
    /** Get Application Message File **/

    
    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
      


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
                System.out.println("config file " + GLOBAL_PROPERTIES_FILE
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

    
    public static void main(String[] args) {
//        List ff = new ArrayList();
//        ff.add("c:/dscwebasp.rar");
//        List to = new ArrayList();
//        to.add("matrix@cavell.com.tw");
//        List cc = new ArrayList();
//        //System.out.println(sendMailWithAttachment("211.75.40.150",
//                                                  "allen@cavell.com.tw", to, cc,
//                                                  "test", "��撚嚙踐��蕭嚙踝蕭", ff));
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
                logger.info("ApplicationContext "
                        + CONTEXT_CONFIGURATION[0] + 
                        CONTEXT_CONFIGURATION[1] + " loaded success.");
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
        return (DataSource) context.getBean("ETLDataSource");
    }

    /**
     * Return a TransactionTemplate.
     * @return TransactionTemplate.
     */
    public static TransactionTemplate getTransactionTemplate() {
        initialize();
        return (TransactionTemplate) context.getBean("ETLTransactionTemplate");
    }
    
    
    /**
     * Return a TransactionTemplate.
     * @return TransactionTemplate.
     */
    public static DataSourceTransactionManager getTransactionManager() {
        initialize();
        return (DataSourceTransactionManager) context.getBean("ETLTransactionManager");
    }
    
    /**
     * Return a DataImportEngine.
     * @return DataImportEngine.
     */
    public static DataImportEngine getDataImportEngine() {
        initialize();
        return (DataImportEngine) context.getBean("dataImportEngine");
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



	// 嚙踝�蕭謒rrayList���蕭��蕭�嚙踝嚙踝蕭
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
	
	//嚙踝�蕭謒rrayList���蕭��蕭�嚙踝嚙踐漕嚙踝嚙踐�蕭蹓選蕭��蕭嚙�
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
