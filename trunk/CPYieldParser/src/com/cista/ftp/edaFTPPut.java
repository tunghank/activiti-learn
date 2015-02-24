package com.cista.ftp;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Hank (c) 2005</p>
 *
 * <p>Company: Himax</p>
 *
 * @author not attributable
 * @version 1.0
 */
import org.apache.commons.net.ftp.*;
import java.net.SocketTimeoutException;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cista.cpYield.dao.CpYieldParserDao;
import com.cista.cpYield.to.CpYieldLotTo;

import com.cista.job.SystemContext;

public class edaFTPPut {

	protected final Log logger = LogFactory.getLog(getClass());

    public edaFTPPut() {
    }

    public void putDataFiles(){
    	
    	String server = SystemContext.getConfig("config.ftp.server");
    	String username = SystemContext.getConfig("config.ftp.username");
    	String password = SystemContext.getConfig("config.ftp.password");
        String folder = SystemContext.getConfig("config.ftp.folder");
        String destinationFolder = SystemContext.getConfig("config.ftp.destinationFolder");
        String backupFolder  = SystemContext.getConfig("config.ftp.backupFolder");
    	
        FTPClient ftpClient = new FTPClient();//FTPClient物件擁有用戶端的各種方法
        // We want to timeout if a response takes longer than 30 seconds
        int timeout = 30000;
        ftpClient.setDefaultTimeout(timeout);
        ftpClient.setConnectTimeout(3000);
        ftpClient.setBufferSize(1024 * 2);
                
      try{
    	  ftpClient.setDataTimeout(timeout);
          // Connect and logon to FTP Server
    	  int reply;
    	  //ftpClient.setControlEncoding("UTF-8");
    	  
    	  ftpClient.connect( server );
    	  ftpClient.setSoTimeout(30000);
    	  ftpClient.login( username, password );

          ftpClient.enterLocalPassiveMode();
          ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
          reply = ftpClient.getReplyCode();

          if (!FTPReply.isPositiveCompletion(reply)) {
          	ftpClient.disconnect();
          	logger.info("FTP server refused connection.");
          	logger.info(1);
          }
          logger.info("Connected to " + server );
          logger.info(ftpClient.getReplyString());

          // List the files in the directory
          ftpClient.changeWorkingDirectory( destinationFolder );

          //File rawPath = new File( folder );

          //1.1 Get need send back files
          CpYieldParserDao cpYieldParserDao = new CpYieldParserDao();
          List<CpYieldLotTo> backLotFiles = cpYieldParserDao.getNeedSendFtpFiles();
          
          //File[] rawFile = rawPath.listFiles();
          
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
          Calendar calendar = Calendar.getInstance();
          String uuid, fileName;
          String ftpFlag;
          if( backLotFiles != null ){
	          for( int i=0; i<backLotFiles.size(); i++ ){
	        	  CpYieldLotTo backLotFile = backLotFiles.get(i);
	        	  fileName = backLotFile.getFileName();
	        	  uuid = backLotFile.getCpYieldUuid();
	        	  
	        	  File rawFile = new File(folder + File.separator + fileName);
	        	  
	              if( !rawFile.getName().trim().equals(".") &&
	                  !rawFile.getName().trim().equals("..") &&
	                  !rawFile.isDirectory() && rawFile.canWrite() && rawFile.canRead() ) {
	                  // Download a file from the FTP Server
	                  //System.out.println( df.format( files[ i ].getTimestamp().getTime() ) );
	                  //System.out.println( files[ i ].getName() );
	            	  logger.info(rawFile.getName());
	
	                  FileInputStream fis = new FileInputStream(rawFile);
	                  boolean flag = ftpClient.storeFile(fileName.replaceAll("_" + uuid , ""), fis);
	                  fis.close();
	                  
	                  //Delete File
	                  if ( flag ){
	                	  File bkFolder = new File(backupFolder + File.separator + 
	                			  dateFormat.format(calendar.getTime()).toString() );
	                	  
	                	  bkFolder.mkdir();
	                	  File bkFile = new File(bkFolder.getPath().toString() + File.separator + fileName);
	                	  
	                	  copyFile(rawFile ,bkFile);
	                	  logger.debug( rawFile.delete() ) ;
	                	  ftpFlag = "S";
	                	  //1.2 Update DataBase FTP Send Time
	                	  cpYieldParserDao.updateFtpSendTime(ftpFlag, backLotFile);
	                  }else{
	                	  logger.info("Put Fail: " + rawFile.getName());
	                	  //1.2 Update DataBase FTP Send Time
	                	  ftpFlag = "F";
	                	  cpYieldParserDao.updateFtpSendTime(ftpFlag, backLotFile);
	                  }
	
	              }
	          }
          }

          // Logout from the FTP Server and disconnect
          ftpClient.logout();
          ftpClient.disconnect();
      	  }catch (SocketTimeoutException socketTimeoutException) {
      		  logger.info(socketTimeoutException.toString());
      		  socketTimeoutException.printStackTrace();
          }catch( Exception e ){
        	  logger.info(e.toString());
              e.printStackTrace();
          }finally {
              if (ftpClient != null && ftpClient.isConnected()) {
                  try {
                	  ftpClient.logout();
                	  ftpClient.disconnect();
                  }catch (IOException e) {
                	  logger.info(e.toString());
                	  e.printStackTrace();
                  }
              }
              logger.info("Connection Close");
              logger.info("--------------------------------------------------");
          }

      }

    public void copyFile(File in, File out) throws Exception {
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

	public static void main(String[] args) {

            edaFTPPut edaftp = new edaFTPPut();
            edaftp.putDataFiles();
    }
}
