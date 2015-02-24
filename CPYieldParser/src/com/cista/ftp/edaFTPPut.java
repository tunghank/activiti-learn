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
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class edaFTPPut {

	protected final Log logger = LogFactory.getLog(getClass());

    public edaFTPPut() {
    }

    public void putDataFiles( String server,String username,String password,
                              String folder,String destinationFolder, String backupFolder ){
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

          File rawPath = new File( folder );

          File[] rawFile = rawPath.listFiles();
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
          Calendar calendar = Calendar.getInstance();

          for( int i=0; i<rawFile.length; i++ ){

              if( !rawFile[i].getName().trim().equals(".") &&
                  !rawFile[i].getName().trim().equals("..") &&
                  !rawFile[i].isDirectory() && rawFile[i].canWrite() ) {
                  // Download a file from the FTP Server
                  //System.out.println( df.format( files[ i ].getTimestamp().getTime() ) );
                  //System.out.println( files[ i ].getName() );
            	  logger.info(rawFile[i].getName());
                  File file = new File( folder + File.separator +
                                       rawFile[i].getName());

                  FileInputStream fis = new FileInputStream(file);
                  boolean flag = ftpClient.storeFile(rawFile[i].getName(), fis);
                  fis.close();

                  //Delete File
                  if ( flag ){
                	  File bkFolder = new File(backupFolder + File.separator + 
                			  dateFormat.format(calendar.getTime()).toString() );
                	  
                	  bkFolder.mkdir();
                	  File bkFile = new File(bkFolder.getPath().toString()+ File.separator + file.getName());
                	  
                	  copyFile(file,bkFile);
                      file.delete();
                  }else{
                	  logger.info("Put Fail: " + file.getName());
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
            String server = args[0];
            String username = args[1];
            String password = args[2];
            String folder = args[3];
            String destinationFolder = args[4];
            String backupFolder = args[5];

            edaFTPPut edaftp = new edaFTPPut();
            edaftp.putDataFiles(server, username, password, folder, destinationFolder, backupFolder);
    }
}
