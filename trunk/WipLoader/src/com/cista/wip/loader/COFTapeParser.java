package com.cista.wip.loader;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.cista.job.SystemContext;
import com.cista.mail.MailAlert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.cista.wip.dao.EcoaDao;
import com.cista.wip.to.COFTapeTo;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class COFTapeParser extends Thread {

	protected final Log logger = LogFactory.getLog(getClass());
	private MailAlert alert = new MailAlert();
	
    PreparedStatement pstmtMT;
    Methods mod = new Methods();
    
	public COFTapeParser() {
		// TODO Auto-generated constructor stub
		alert.setSmtpIP(SystemContext.getConfig("config.himax.mail.ip"));
		alert.setSenderName(SystemContext.getConfig("config.himax.mail.sender.name"));
		alert.setSenderAddr(SystemContext.getConfig("config.himax.mail.sender.mail"));
		alert.setToMail(SystemContext.getConfig("config.himax.mail.admin.mail"));
		alert.setSubject(SystemContext.getConfig("config.himax.mail.subject"));
	}

    public boolean cofTapeParserCSV(File csvFile) throws Exception{
    	FileInputStream fIn = null;
    	File fileErrorUrl = new File("D:\\ECOA\\ERROR\\COF_TAPE\\");
        try {
            //建立資料流
            List<COFTapeTo> tapeList = new ArrayList<COFTapeTo>();
            EcoaDao ecoaDao = new EcoaDao();
        	
            fIn = new FileInputStream(csvFile);
            //FileInputStream fIn2 = new FileInputStream(csvFile);
            InputStreamReader isr = null;
            BufferedReader br = null;
            isr = new InputStreamReader(fIn, "UTF-8");
            br = new BufferedReader(isr);

            //byte[] byteArray = new byte[new Long(csvFile.length()).intValue()];
            //讀入File Data Byte.....
            //fIn2.read(byteArray);
            logger.debug(csvFile.getName() + "<--讀入資料檔...成功");
            //Using get sign  "\n" 抓行數...
            
            //略過行數
            int l = 1;
            List<String> lineDataList = new ArrayList<String>();
            for (int i = 0; i <= l; i++) {
                br.readLine();
            } while (br.ready()) {
                String line = br.readLine();
                line = null != line ? line : "";
                if (!line.trim().equals("")) {
                	lineDataList.add(line);
                }
            }

    		//Using Find Target "|=124" or ",=44" Count
            for( int i = 0; i < lineDataList.size(); i++ ){
            	String[] tmpStr = lineDataList.get(i).split(",");
            	logger.info("Line " + i);
            	COFTapeTo tapeTo = new COFTapeTo();
            	UUID uid = UUID.randomUUID();
            	tapeTo.setTapeId(uid.toString());
            	logger.info(tmpStr.length);
            	if( tmpStr.length == 26 ){
           		
            		tapeTo.setProductId(tmpStr[0]);
            		tapeTo.setTapeLot(tmpStr[1]);
            		tapeTo.setOlbUptol(tmpStr[2]);
            		tapeTo.setOlbLowtol(tmpStr[3]);
            		tapeTo.setOlbUsl(tmpStr[4]);
            		tapeTo.setOlbCsl(tmpStr[5]);
            		tapeTo.setOlbLsl(tmpStr[6]);
            		tapeTo.setOlbAvg(tmpStr[7]);
            		tapeTo.setOlbMax(tmpStr[8]);
            		tapeTo.setOlbMin(tmpStr[9]);
            		tapeTo.setOlbData1(tmpStr[10]);
            		tapeTo.setOlbData2(tmpStr[11]);
            		tapeTo.setOlbData3(tmpStr[12]);
            		tapeTo.setOlbData4(tmpStr[13]);

            		tapeTo.setSrUptol(tmpStr[14]);
            		tapeTo.setSrLowtol(tmpStr[15]);
            		tapeTo.setSrUsl(tmpStr[16]);
            		tapeTo.setSrCsl(tmpStr[17]);
            		tapeTo.setSrLsl(tmpStr[18]);
            		tapeTo.setSrAvg(tmpStr[19]);
            		tapeTo.setSrMax(tmpStr[20]);
            		tapeTo.setSrMin(tmpStr[21]);
            		tapeTo.setSrData1(tmpStr[22]);
            		tapeTo.setSrData2(tmpStr[23]);
            		tapeTo.setSrData3(tmpStr[24]);
            		tapeTo.setSrData4(tmpStr[25]);
            		
            		tapeTo.setFileName(csvFile.getName());
            		
            		tapeList.add(tapeTo);
            	}else{
            		return false;
            	}
            	for(int j = 0; j < tmpStr.length; j++){
            		logger.debug(tmpStr[j]);           		
            		
            	}
            	
            	logger.debug("------------------------------");
            }
    		
            fIn.close();
            br.close();
            
            if( tapeList == null || tapeList.size() < 1){
            	logger.info("No Data " + csvFile.getName());
            }else{
            	ecoaDao.insertCOFTape(tapeList);
            }
            logger.debug(tapeList.size());
            logger.info("---------------------------------");
        } catch (Exception e) {
    		if(fIn != null){
    			fIn.close();
    		}
    		logger.info("ERROR MOVE FILE");
    		mod.copyFile(csvFile, new File(fileErrorUrl + "\\" + csvFile.getName()));
    		csvFile.delete();
        	
        	StackTraceElement[] messages = e.getStackTrace();
        	Exception ex = new Exception(csvFile.getName());
        	ex.setStackTrace(messages);
        	ex.printStackTrace();
        	throw ex ;
        }finally{
        	try{
        		if(fIn != null){
        			fIn.close();
        		}
        	}catch (IOException ie) {
    			StackTraceElement[] messages = ie.getStackTrace();
    			int length = messages.length;
    			String error = "";
        		for (int i = 0; i < length; i++) {
    				error = error + "toString:" + messages[i].toString() + "\r\n";
    			}
    			ie.printStackTrace();
    			logger.error(error);
    			return false;
        	}
        }
        return true;
    }


    
    /**
     * Bumpping ���ɵ{��
     * @throws IOException
     */


    public void cofParserGetFiles(){

        Methods mod = new Methods();
        File fileInUrl = new File("D:\\ECOA\\RAW\\SPC\\COF_TAPE\\");
        File fileOutUrl = new File("D:\\ECOA\\BACKUP\\SPC\\COF_TAPE\\");
        File fileErrorUrl = new File("D:\\ECOA\\ERROR\\COF_TAPE\\");

        try {

            List fileList = mod.getFiles(fileInUrl);
            fileList =null!=fileList?fileList:new ArrayList();

            for (int i = 0; i < fileList.size(); i++) {
            	File excelFile = (File)fileList.get(i);
                String file_name = excelFile.getName();

                //if (file_name.substring(4, 5).equals("B")) {
                     //System.out.println(file_name+" is Bump's File");
                   if (this.cofTapeParserCSV(excelFile) == false) {
                	   	logger.info(file_name + " is Parser false");
                	   	alert.setSubject(SystemContext.getConfig("config.himax.mail.subject") + " - " + file_name + " is Parser false");
                	   	alert.sendNoFile("ECOA BumppingParser fail:" + excelFile);
                        //mod.copyFile(excelFile, new File(fileErrorUrl + "\\" + file_name));
                        //excelFile.delete();
                    } else {

                    	logger.info(file_name + " is Parser complete");
                        mod.copyFile(excelFile, new File(fileOutUrl + "\\" + file_name));
                        excelFile.delete();
                    }
                /*} else {
                    logger.info(file_name + " is not Bump's File");
                    alert.sendNoFile("ECOA This File is not Bump's File:" + excelFile);
                    mod.copyFile(excelFile, new File(fileErrorUrl + "\\" + file_name));
                    excelFile.delete();
                    
                    logger.info(file_name+" is not BUMP's File");
                }*/
            }
        } catch (Exception e) {
			StackTraceElement[] messages = e.getStackTrace();
			
			int length = messages.length;
			String error = "";
			alert.setSubject(SystemContext.getConfig("config.himax.mail.subject") + " - " + e.getMessage());
			for (int i = 0; i < length; i++) {
				error = error + "toString:" + messages[i].toString() + "\r\n";
			}
			alert.sendNoFile(error);
			e.printStackTrace();
			logger.error(error);


        }

    }

    public static void main(String[] args) {
    	COFTapeParser app = new COFTapeParser();
    	app.cofParserGetFiles();
    }


}
