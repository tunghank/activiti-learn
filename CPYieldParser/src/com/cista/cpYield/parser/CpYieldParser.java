package com.cista.cpYield.parser;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.activation.MimetypesFileTypeMap;

import com.cista.job.SystemContext;
import com.cista.mail.MailAlert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.FilenameUtils;
import sun.util.logging.resources.logging;

import com.cista.cpYield.dao.CpYieldParserDao;
import com.cista.cpYield.to.CpYieldLotTo;
import com.cista.cpYield.to.CpYieldLotBinTo;
import com.cista.util.Methods;

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
public class CpYieldParser extends Thread {

	protected final Log logger = LogFactory.getLog(getClass());
	private MailAlert alert = new MailAlert();
	

    
    private File fileInUrl;
    private File fileOutUrl;
    private File fileErrorUrl;
    
	public CpYieldParser() {
		
		String fileInUrlLocal = SystemContext.getConfig("config.parser.path");
		String fileOutUrlLocal = SystemContext.getConfig("config.parser.finish.path");
		String fileErrorUrlLocal = SystemContext.getConfig("config.parser.error.path");
		
		logger.debug(fileInUrlLocal);
		logger.debug(fileOutUrlLocal);
		logger.debug(fileErrorUrlLocal);
		
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

    public boolean parserCpYieldTxt(File txtFile) throws Exception{
    	FileInputStream fIn = null;
    	String fileNameUid="";
        try {
        	//Using Find Target "|=124" or ",=44" Count
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        	
        	logger.debug("File " + txtFile.getAbsolutePath() + "  " + txtFile.getName() 
        			+ "  " + new MimetypesFileTypeMap().getContentType(txtFile));
        	
            //1.0 讀入檔案, 建立資料流
            fIn = new FileInputStream(txtFile);
            //FileInputStream fIn2 = new FileInputStream(csvFile);
            InputStreamReader isr = null;
            BufferedReader br = null;
            isr = new InputStreamReader(fIn, "UTF-8");
            br = new BufferedReader(isr);

            //byte[] byteArray = new byte[new Long(csvFile.length()).intValue()];
            //讀入File Data Byte.....
            //fIn2.read(byteArray);
            logger.debug(txtFile.getName() + "<--讀入資料檔...成功");
            //Using get sign  "\n" 抓行數...
            
            
            //1.1 讀入行數資料, 略過行數
            int l = -1;
            List<String> lineDataList = new ArrayList<String>();
            for (int i = 0; i <= l; i++) {
                br.readLine();
            } while (br.ready()) {
                String line = br.readLine();
                line = null != line ? line : "";
                //logger.debug(line);
                if (!line.trim().equals("")) {
                	lineDataList.add(line);
                }
            }
            
            
            //1.2 確認有資料開使處理
            if( lineDataList !=null ){
            	CpYieldParserDao cpYieldParserDao = new CpYieldParserDao();
            	
            	CpYieldLotTo cpYieldLotTo = new CpYieldLotTo();
            	
            	String cpYieldUuid = UUID.randomUUID().toString().toUpperCase();
                logger.debug("lineDataList.size() " +lineDataList.size());
                
                fileNameUid = FilenameUtils.getBaseName(txtFile.getName())  + "_"  + cpYieldUuid+ "." 
                			 + FilenameUtils.getExtension(txtFile.getName());
                //1.2.1 處理每行資料
                String tmpWaferID = lineDataList.get(1);
                String arrayWafer[]=tmpWaferID.split("=")[1].trim().split("-");
                
                //logger.debug("arrayWafer[] " + arrayWafer.length);
                
                //1.3 Prepare Data
                String cpLot = arrayWafer[0].trim();
                String waferId = arrayWafer[1].trim();
                String machineId = arrayWafer[2].trim();
                Integer cpTestTimes = cpYieldParserDao.getMaxCpTestTimes(cpLot, waferId);
                
            	String xMaxCoor = lineDataList.get(2).split("=")[1].trim();
            	String yMaxCoor = lineDataList.get(3).split("=")[1].trim();
            	String flat = lineDataList.get(4).split("=")[1].trim();
            	
            	logger.debug("xMaxCoor " + xMaxCoor);
            	logger.debug("yMaxCoor " + yMaxCoor);
            	logger.debug("flat " + flat);
            	
            	//1.3 Find Bin Data
            	int sb = 0 , eb=0;
                for( int i = 0; i < lineDataList.size(); i++ ){               	
                	if(lineDataList.get(i).indexOf("Wafer Bin Summary") >= 0 ){
                		sb = i + 1;
                		break;
                	}
                }
                for( int i = sb; i < lineDataList.size(); i++ ){               	
                	if(lineDataList.get(i).indexOf("bin") < 0 ){
                		eb = i - 1;
                		break;
                	}
                }
                
                logger.debug("sb " + sb);
            	logger.debug(lineDataList.get(sb).trim());
            	logger.debug("eb " + eb);
            	logger.debug(lineDataList.get(eb).trim());
            	//1.3.1 Get Bin Data
            	List<CpYieldLotBinTo> cpYieldLotBins = new ArrayList<CpYieldLotBinTo>();
            	String cpYieldBinUuid;
            	
            	String bin;
            	Integer die;
            	String percentage;
            	String binString;
            	

            	for(int j=sb; j <= eb; j++ ){
            		cpYieldBinUuid = UUID.randomUUID().toString().toUpperCase();
            		CpYieldLotBinTo cpYieldLotBinTo = new CpYieldLotBinTo();
            		
            		cpYieldLotBinTo.setCpYieldBinUuid(cpYieldBinUuid);
            		cpYieldLotBinTo.setCpYieldUuid(cpYieldUuid);
            		
            		binString=lineDataList.get(j).trim();
            		binString = binString.replaceAll("bin", "").trim();
            		//Get Bin
            		bin = binString.substring(0, binString.indexOf(" "));
            		logger.debug("bin " + bin);
            		//Get Die
            		bin = binString.substring(0, binString.indexOf(" "));
            		binString = binString.replaceAll(bin, "").trim();
            		die = Integer.parseInt( binString.substring(0, binString.indexOf(" ")) );
            		logger.debug("die " + die);
            		//Get Percentage
            		binString = binString.replaceAll(die.toString(), "").trim();
            		percentage = binString.substring(0, binString.length()-1);
            		logger.debug("percentage " + percentage);
            		
            		cpYieldLotBinTo.setBin(bin);
            		cpYieldLotBinTo.setDie(die);
            		cpYieldLotBinTo.setPercentage(percentage);
            		
            		cpYieldLotBins.add(cpYieldLotBinTo);
            		
            	}
            	
            	
            	//1.4 Die Data
            	Integer passDie;
            	Integer failDie;
            	Integer totelDie;
            	for( int i = eb+1; i < lineDataList.size(); i++ ){ 
            		//pass die
            		if(lineDataList.get(i).trim().indexOf("pass die")>=0){
            			passDie = Integer.parseInt( lineDataList.get(i).trim().split(":")[1].trim() );
            			logger.debug("passDie " + passDie);
            			cpYieldLotTo.setPassDie(passDie);
            			continue;
            		}
            		//fail die
            		if(lineDataList.get(i).trim().indexOf("fail die")>=0){
            			failDie = Integer.parseInt( lineDataList.get(i).trim().split(":")[1].trim() );
            			logger.debug("failDie " + failDie);
            			cpYieldLotTo.setFailDie(failDie);
            			continue;
            		}
            		//totel die
            		if(lineDataList.get(i).trim().indexOf("totel die")>=0){
            			totelDie = Integer.parseInt( lineDataList.get(i).trim().split(":")[1].trim() );
            			logger.debug("totelDie " + totelDie);
            			cpYieldLotTo.setTotelDie(totelDie);
            			continue;
            		}
            	}
            	
            	//1.5 Set data in To 
            	cpYieldLotTo.setCpYieldUuid(cpYieldUuid);
            	cpYieldLotTo.setCpTestTimes(cpTestTimes);
            	cpYieldLotTo.setCpLot(cpLot);
            	cpYieldLotTo.setWaferId(waferId);
            	cpYieldLotTo.setMachineId(machineId);
            	cpYieldLotTo.setxMaxCoor(xMaxCoor);
            	cpYieldLotTo.setyMaxCoor(yMaxCoor);
            	cpYieldLotTo.setFlat(flat);
            	
            	String fileMimeType = new MimetypesFileTypeMap().getContentType(txtFile);
            	cpYieldLotTo.setFileName(fileNameUid);
            	cpYieldLotTo.setFileMimeType(fileMimeType);
            	cpYieldLotTo.setFtpFlag("N");
            	
            	fIn.close();
                br.close();
            	//1.6 DataBasse
                //1.6.1 Insert CP Lot Table
                cpYieldParserDao.insertCpYieldLot(cpYieldLotTo);
                cpYieldParserDao.insertCpYieldLotBin(cpYieldLotBins);
            }
             		
            fIn.close();
            br.close();
            
            
            logger.info(txtFile.getName() + " is Parser complete");
            logger.info( fileNameUid  + " is Parser complete");
        	Methods.copyFile(txtFile, new File(fileOutUrl + "\\" + fileNameUid));
        	txtFile.delete();
        	
            //logger.debug(tapeList.size());
            logger.info("---------------------------------");
        } catch (Exception e) {
    		if(fIn != null){
    			fIn.close();
    		}
    		logger.info("ERROR MOVE FILE");
    		Methods.copyFile(txtFile, new File(fileErrorUrl + "\\" + txtFile.getName()));
    		txtFile.delete();
        	
        	StackTraceElement[] messages = e.getStackTrace();
        	Exception ex = new Exception(txtFile.getName());
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


    public void GetCpYieldFiles(){


        try {
       	    logger.debug("fileInUrl " + fileInUrl.getPath());
            List fileList = Methods.getFiles(fileInUrl);
            fileList =null!=fileList?fileList:new ArrayList();

            for (int i = 0; i < fileList.size(); i++) {
            	File txtFile = (File)fileList.get(i);
                String fileName = txtFile.getName();
                //1.0 Check File Status can read, write
                if(txtFile.canRead() && txtFile.canWrite() ){
                	
                }
                //if (file_name.substring(4, 5).equals("B")) {
                     //System.out.println(file_name+" is Bump's File");
                   if (this.parserCpYieldTxt(txtFile) == false) {
                	   	logger.info(fileName + " is Parser false");
                	   	//alert.setSubject(SystemContext.getConfig("config.himax.mail.subject") + " - " + fileName + " is Parser false");
                	   	//alert.sendNoFile("CP Yield Parser fail:" + txtFile);
                	   	Methods.copyFile(txtFile, new File(fileErrorUrl + "\\" + fileName));
                        txtFile.delete();
                    } else {

                    	//logger.info(fileName + " is Parser complete");
                    	//Methods.copyFile(txtFile, new File(fileOutUrl + "\\" + fileName));
                    	//txtFile.delete();
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
    	CpYieldParser app = new CpYieldParser();
    	app.GetCpYieldFiles();
    }


}
