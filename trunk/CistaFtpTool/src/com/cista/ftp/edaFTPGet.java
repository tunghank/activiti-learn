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
import org.apache.commons.net.ftp.FTPFile;
import java.io.IOException;
import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.zip.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class edaFTPGet {
	
	protected final Log logger = LogFactory.getLog(getClass());
    private static String rootFolder;
    private static File root;

    public edaFTPGet() {
    }

    public void getDataFiles(String server, String username, String password,
                             String folder, String destinationFolder) {
    	   
        FTPClient ftpClient = new FTPClient(); //FTPClient物件擁有用戶端的各種方法
        // We want to timeout if a response takes longer than 30 seconds
        int timeout = 30000;
        ftpClient.setDefaultTimeout(timeout);
        ftpClient.setConnectTimeout(3000);
        ftpClient.setBufferSize(1024 * 2);
        try {
        	ftpClient.setDataTimeout(timeout);
            // Connect and logon to FTP Server
            int reply;
            //ftp.setControlEncoding("Big5");
            

            ftpClient.connect(server);
            ftpClient.login(username, password);

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
            	ftpClient.disconnect();
            	logger.info("FTP server refused connection.");
            	logger.info(1);
            }

            logger.info("Connected to " + server);
            logger.info(ftpClient.getReplyString());

            // List the files in the directory
            ftpClient.changeWorkingDirectory(folder);
            FTPFile[] ftpFiles = ftpClient.listFiles();

            //logger.info( "Number of files in dir: " + files.length );

            for (int i = 0; i < ftpFiles.length; i++) {

                if (!ftpFiles[i].getName().trim().equals(".") &&
                    !ftpFiles[i].getName().trim().equals("..") &&
                    !ftpFiles[i].getName().trim().substring(0, 1).equals(".") &&
                    !ftpFiles[i].isDirectory()) {
                    // Download a file from the FTP Server
 
                	logger.info(ftpFiles[i].getName() + "\t" + ftpFiles[i].getSize());
                    File localFile = new File(destinationFolder + File.separator +
                    		ftpFiles[i].getName());

                    FileOutputStream fos = new FileOutputStream(localFile);
                    boolean flag = ftpClient.retrieveFile(ftpFiles[i].getName(), fos);
                    fos.flush();
                    fos.close();
                    //Delete File
                    if (flag) {
                    	ftpClient.deleteFile(ftpFiles[i].getName());
                    } else {
                    	logger.info("Get Fail: " + localFile.getName());
                    }
                    //Delete File


                }else if(!ftpFiles[i].getName().trim().equals(".") && !ftpFiles[i].getName().trim().equals("..") &&
                    !ftpFiles[i].getName().trim().substring(0, 1).equals(".") && ftpFiles[i].isDirectory()){
                    ftpClient.changeWorkingDirectory(ftpFiles[i].getName());
                    
                    FTPFile[] ftp2Files = ftpClient.listFiles();
                    for (int j = 0; j < ftp2Files.length; j++) {
                    	
                        if (!ftp2Files[j].getName().trim().equals(".") &&
                                !ftp2Files[j].getName().trim().equals("..") &&
                                !ftp2Files[j].getName().trim().substring(0, 1).equals(".") &&
                                !ftp2Files[j].isDirectory()) {
                                // Download a file from the FTP Server
             
                            	logger.info(ftp2Files[j].getName() + "\t" + ftp2Files[j].getSize());
                            	
                            	File localPath = new File(destinationFolder + File.separator + ftpFiles[i].getName() );
                            	localPath.mkdir();
                                File localFile = new File(localPath.getPath() + File.separator + ftp2Files[j].getName());
                                
                                logger.info("FTP Path " + localFile.getPath());
                                FileOutputStream fos = new FileOutputStream(localFile);
                                boolean flag = ftpClient.retrieveFile(ftp2Files[j].getName(), fos);
                                
                                fos.flush();
                                fos.close();
                                
                                //Delete File
                                if (flag) {
                                	ftpClient.deleteFile(ftp2Files[j].getName());
                                } else {
                                	logger.info("Get Fail: " + localFile.getName());
                                }
                                //Delete File


                            }
                    }
                    ftpClient.changeWorkingDirectory(folder);
                    logger.info("DELE PATH " + ftpFiles[i].getName());
                    ftpClient.removeDirectory(ftpFiles[i].getName());
                }
            }

            // Logout from the FTP Server and disconnect
            ftpClient.logout();
            ftpClient.disconnect();
            logger.info("------------------FTP CLOSE--------------------------");
            ////////////////////////////////////
            //	Unzip Get File to 目的Folder
            //	And Delete zip file
            ////////////////////////////////////
            ArrayList zipFile = new ArrayList();
            zipFile = getFileName(destinationFolder, "zip");
            for (int i = 0; i < zipFile.size(); i++) {
                logger.info(zipFile.get(i).toString());
                if (unZip(zipFile.get(i).toString(), destinationFolder) == 1) {
                    File tmpZipFile = new File(destinationFolder +
                                               File.separator +
                                               zipFile.get(i).toString());
                    tmpZipFile.delete();
                }
            }
            //Copy Zip Folder Files to root Folder
            this.rootFolder = destinationFolder;
            this.root = new File(destinationFolder);
            //getFileName( destinationFolder );
        } catch (Exception e) {
        	logger.info(e.toString());
        	e.printStackTrace();
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                	ftpClient.logout();
                	ftpClient.disconnect();
                } catch (IOException e) {
                }
            }
            logger.info("Connection Close");
            logger.info("--------------------------------------------------");
        }
    }
    
    public int unZip(String zipFile, String outFolder) {

        int ret = -1;
        try {
            String rootstr = outFolder + File.separator;
            FileInputStream fins = new FileInputStream(rootstr + zipFile);
            ZipInputStream zins = new ZipInputStream(fins);
            ZipEntry ze = null;
            byte ch[] = new byte[256];
            //Extend file
            int endIndex = zipFile.lastIndexOf(46);
            String zipfileName = zipFile.substring(0, endIndex);
            
            while ((ze = zins.getNextEntry()) != null) {
                File zfile = new File(rootstr + ze.getName().substring(ze.getName().lastIndexOf(47) + 1, ze.getName().length()));
                File fpath = new File(zfile.getParentFile().getPath()+ File.separator + zipfileName);
                logger.info(ze.getName() + "\t已解壓完成");
                if (ze.isDirectory()) {
                	logger.info("ITEM 1" );
                    if (!zfile.exists()) {
                        zfile.mkdirs();
                    }
                    zins.closeEntry();
                } else {
                	logger.info("ITEM 2" );
                    if (!fpath.exists()) {
                        logger.info("222222 ---- " + fpath.mkdirs());
                    }
                    FileOutputStream fouts = new FileOutputStream(zfile);
                    int i;
                    while ((i = zins.read(ch)) != -1) {
                        fouts.write(ch, 0, i);
                    }
                    zins.closeEntry();
                    fouts.close();
                }
                File copyUnzipFile = new File(zfile.getParentFile().getPath()+ File.separator + zipfileName + File.separator + ze.getName().substring(ze.getName().lastIndexOf(47) + 1, ze.getName().length()));
                copyFile(zfile, copyUnzipFile);
                zfile.delete();
            }
            fins.close();
            zins.close();
            ret = 1;
        } catch (Exception e) {
            ret = 0;
            e.printStackTrace();
        }
        return ret;
    }


    private ArrayList getFileName(String pathName, String auxName) {
        String sFileName;
        String tmpAuxName;
        String mainName;

        File sPath = new File(pathName);
        int f = 0;
        ArrayList fileName = new ArrayList();

        try {
            if (sPath.exists()) {
                if (sPath.isDirectory()) {
                    File[] sFile = sPath.listFiles();
                    if (sFile.length >= 1) {
                        for (int i = 0; i < sFile.length; i++) {
                            if (sFile[i].isDirectory()) {
                                getFileName(sFile[i].getPath(), auxName);
                            } else {
                                //Get Data
                                sFileName = sFile[i].getName().toString();
                                if (sFileName.indexOf('.') != -1) {
                                    mainName = sFileName.substring(0,
                                            sFileName.lastIndexOf('.'));
                                    tmpAuxName = sFileName.toLowerCase().
                                                 substring(sFileName.lastIndexOf(
                                            '.') + 1, sFileName.length());
                                    if (auxName.equals(tmpAuxName.toLowerCase())) {
                                        fileName.add(f, sFileName);
                                        f = f + 1;
                                    }
                                }
                            } //if (sFile[i].isDirectory()){
                        } //for ( int i =0; i < sFile.length ; i++ ){
                    } //if ( sFile.length >= 1 ){
                } //if (sPath.isDirectory()){
            } //if (sPath.exists()){
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private void getFileName(String pathName) {
        try {
            String sFileName;
            String tmpAuxName;
            File sPath = new File(pathName);

            if (sPath.exists()) {
                if (sPath.isDirectory()) {
                    File[] sFile = sPath.listFiles();
                    if (sFile.length >= 1) {
                        for (int i = 0; i < sFile.length; i++) {
                            if (sFile[i].isDirectory()) {
                                getFileName(sFile[i].getPath());
                            } else {
                                //Copy Data
                                sFileName = sFile[i].getName().toString();
                                tmpAuxName = sFileName.toLowerCase().
                                             substring(sFileName.indexOf(
                                        '.') + 1, sFileName.length());
                                if (!tmpAuxName.toLowerCase().equals("zip") ) {
                                    File outPutFile = new File( rootFolder + File.separator + sFileName );

                                    if ( !sFile[i].equals(outPutFile) ){
                                        copyFile( sFile[i] , outPutFile );
                                        sFile[i].delete();
                                        logger.info( outPutFile.toString() + "--->Zip Copy" );
                                    }

                                }
                            } //if (sFile[i].isDirectory()){
                        } //for ( int i =0; i < sFile.length ; i++ ){
                        //Can't Delete Root Folder
                        if (!sPath.equals(root)) {
                            sPath.delete();
                        }
                    } //if ( sFile.length >= 1 ){
                } //if (sPath.isDirectory()){
            } //if (sPath.exists()){

        } catch (Exception e) {
            e.printStackTrace();
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

        edaFTPGet edaftp = new edaFTPGet();
        edaftp.getDataFiles(server, username, password, folder,
                            destinationFolder);
        System.exit(1);
    }
}
