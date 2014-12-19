package com.cista.wip.loader;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import com.cista.job.SystemContext;
import com.cista.mail.MailAlert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.cista.wip.dao.FoundryWipDao;
import com.cista.wip.to.FoundryWipTo;


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
public class SMICWipParser extends Thread {

	protected final Log logger = LogFactory.getLog(getClass());
	private MailAlert alert = new MailAlert();
	
    PreparedStatement pstmtMT;
    Methods mod = new Methods();
    
    private File fileInUrl;
    private File fileOutUrl;
    private File fileErrorUrl;
    
	public SMICWipParser(String fileInUrlLocal , String fileOutUrlLocal, String fileErrorUrlLocal) {
		
    	File fileInLocal = new File(fileInUrlLocal);
        File fileOutLocal = new File(fileOutUrlLocal);
        File fileErrorLocal = new File(fileErrorUrlLocal);
    	
    	this.fileInUrl = fileInLocal;
    	this.fileOutUrl = fileOutLocal;
    	this.fileErrorUrl = fileErrorLocal;
    	
		// TODO Auto-generated constructor stub
		alert.setSmtpIP(SystemContext.getConfig("config.himax.mail.ip"));
		alert.setSenderName(SystemContext.getConfig("config.himax.mail.sender.name"));
		alert.setSenderAddr(SystemContext.getConfig("config.himax.mail.sender.mail"));
		alert.setToMail(SystemContext.getConfig("config.himax.mail.admin.mail"));
		alert.setSubject(SystemContext.getConfig("config.himax.mail.subject"));
	}

    public boolean smicWipParserCSV(File csvFile) throws Exception{
    	FileInputStream fIn = null;
    	
        try {
            
            List<FoundryWipTo> wipToList = new ArrayList<FoundryWipTo>();
            FoundryWipDao foundryWipDao = new FoundryWipDao();
            //建立資料流
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
            int l = 4;
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
            logger.debug("lineDataList.size() " +lineDataList.size());
            String vendorCode = csvFile.getName().split("-")[1];
            String rptDateString = csvFile.getName().split("-")[2].substring(3,13);
            logger.debug("rptDateString "  + rptDateString );
            
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat dfRpt = new SimpleDateFormat("yyyyMMddHH", Locale.ENGLISH);
    		//Using Find Target "|=124" or ",=44" Count
            for( int i = 0; i < lineDataList.size(); i++ ){
            	
            	//logger.debug(lineDataList.get(i));
            	String[] tmpStr = lineDataList.get(i).split(",");	
            	//logger.info("Line " + i);
            	
            	FoundryWipTo wipTo = new FoundryWipTo();         	
            	//logger.info(tmpStr.length);
            	
            	if( tmpStr.length == 15 || tmpStr.length == 14 ){
            		UUID foundryWipUuid = UUID.randomUUID();
                	wipTo.setFoundryWipUuid(foundryWipUuid.toString());
                	
            		wipTo.setVendorCode(vendorCode);
            		if(vendorCode.equals("10001")){
            			wipTo.setVendor("SMIC");
            		}else{
            			wipTo.setVendor("");
            		}
            		wipTo.setVendorSiteNum(tmpStr[13]);
            		wipTo.setProcess("WF");
            		wipTo.setCistaPo(tmpStr[2]);
            		wipTo.setVendorProd(tmpStr[0]);
            		wipTo.setCistaPartNum(tmpStr[1]);
            		wipTo.setCistaProject(tmpStr[1]);
            		wipTo.setWaferLotId(tmpStr[3]);
            		wipTo.setVendorLotId(tmpStr[3]);
            		wipTo.setWaferQty(Long.parseLong(tmpStr[7]));
            		
            		logger.debug(tmpStr[2]);
            		logger.debug("tmpStr[2].indexOf('3301') " +tmpStr[2].indexOf("3301"));
            		if(   
            				tmpStr[2].indexOf("3390") < 0 && 
            				tmpStr[2].indexOf("3301") < 0 ){
            			wipTo.setLotType("EN");
            		}else {
            			wipTo.setLotType("MP");
            		}
            		
            		wipTo.setTotalLayer(Long.parseLong(tmpStr[11]));
            		wipTo.setRemainLayer(Long.parseLong(tmpStr[11]));
            		wipTo.setLotStatus(tmpStr[4]);
            		//Hold
            		if(tmpStr[4].indexOf("Hold") >= 0){
            			//Hold day
            			//Today -  Date stgInDate =  df.parse(tmpStr[6]);
            			Date tDate = Calendar.getInstance().getTime();
            			Date stgInDate =  df.parse(tmpStr[6]);
            			long diff = ( tDate.getTime() - stgInDate.getTime() ) / (1000 * 60 * 60 * 24) ;
            			wipTo.setCurrHoldDay( Double.parseDouble( String.valueOf(diff)  ) );
            			logger.debug( "Current Hold Day : " + wipTo.getCurrHoldDay() );
            		}

            		wipTo.setHoldCode("");
            		wipTo.setHoldReas("");
            		wipTo.setPriority("");
            		if( !tmpStr[10].equals("") ){
            			Date waferStart =  df.parse(tmpStr[10]);
            			wipTo.setWaferStart(waferStart);
            		}
            		wipTo.setCurrStage(tmpStr[5]);
            		if( !tmpStr[6].equals("") ){
            			Date stgInDate =  df.parse(tmpStr[6]);
            			wipTo.setStgInDate(stgInDate);
            		}
            		if( !tmpStr[9].equals("") ){
            			Date sod =  df.parse(tmpStr[9]);
            			wipTo.setSod(sod);
            		}
            		if( !tmpStr[8].equals("") ){
            			Date rsod =  df.parse(tmpStr[8]);
            			wipTo.setRsod(rsod);
            		}
        			Date rprDate =  dfRpt.parse(rptDateString);
        			wipTo.setRptDate(rprDate);
            		
            		wipTo.setShipTo("");
            		
            		wipToList.add(wipTo);
            	}else{
            		return false;
            	}
            	
/*            	for(int j = 0; j < tmpStr.length; j++){
            		logger.debug(tmpStr[j]);           		
            		
            	}
            	*/
            	logger.debug("------------------------------");
            }
    		
            fIn.close();
            br.close();
            
            if( wipToList == null || wipToList.size() < 1){
            	logger.info("No Data " + csvFile.getName());
            }else{
            	foundryWipDao.backupFoundryWip();
            	foundryWipDao.deleteFoundryWip();
            	foundryWipDao.insertFoundryWip(wipToList);
            }
            //logger.debug(tapeList.size());
            logger.info("---------------------------------");
        } catch (Exception e) {
    		if(fIn != null){
    			fIn.close();
    		}
    		logger.info("ERROR MOVE FILE");
    		//mod.copyFile(csvFile, new File(fileErrorUrl + "\\" + csvFile.getName()));
    		//csvFile.delete();
        	
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
     * GET SMIC WIP FILEs
     * @throws IOException
     */


    public void GetSmicWipFiles(){

        Methods mod = new Methods();

        try {
       	        	
            List fileList = mod.getFiles(fileInUrl);
            fileList =null!=fileList?fileList:new ArrayList();

            for (int i = 0; i < fileList.size(); i++) {
            	File excelFile = (File)fileList.get(i);
                String file_name = excelFile.getName();

                //if (file_name.substring(4, 5).equals("B")) {
                     //System.out.println(file_name+" is Bump's File");
                   if (this.smicWipParserCSV(excelFile) == false) {
                	   	logger.info(file_name + " is Parser false");
                	   	alert.setSubject(SystemContext.getConfig("config.himax.mail.subject") + " - " + file_name + " is Parser false");
                	   	alert.sendNoFile("SMIC WIP Parser fail:" + excelFile);
                        //mod.copyFile(excelFile, new File(fileErrorUrl + "\\" + file_name));
                        //excelFile.delete();
                    } else {

                    	logger.info(file_name + " is Parser complete");
                        //mod.copyFile(excelFile, new File(fileOutUrl + "\\" + file_name));
                        //excelFile.delete();
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
    	SMICWipParser app = new SMICWipParser(args[0], args[1], args[2]);
    	app.GetSmicWipFiles();
    }


}
