package com.cista.job;

import com.cista.ftp.edaFTPGet;
import com.cista.ftp.edaFTPPut;

public class JobProcessor extends ImportProcessor {
    private static Integer lock = 0;

    private static final String IMPORT_STG_STATUS = "New";

    /**
     * Goods movement import process.
     */
    @Override
    public void run() {
        synchronized (lock) {
            logger.info("FTP Process start ..."); 
            long startTime = System.currentTimeMillis();
            
            edaFTPGet ftpGet = new edaFTPGet();
            //ftpGet.getDataFiles("10.240.73.72", "hank", "abc816", "/Temp/FTP_OUT", "D:\\Temp\\FTP_IN");
            logger.info("Chipbond..."); 
            ftpGet.getDataFiles("ftp.himax.com.tw", "chipbond", "3822chipbond", "/TSKMergeIN", "D:\\TSKMergeAgent\\work\\bump\\CHIPBOND");
            logger.info("IST..."); 
            ftpGet.getDataFiles("ftp.himax.com.tw", "ist", "3825ist", "/TSKMergeIN", "D:\\TSKMergeAgent\\work\\bump\\IST");
            logger.info("SPIL..."); 
            ftpGet.getDataFiles("ftp.himax.com.tw", "spil", "3820spil", "/TSKMergeIN", "D:\\TSKMergeAgent\\work\\bump\\SPIL");                
            logger.info("CHIPMOS..."); 
            ftpGet.getDataFiles("ftp.himax.com.tw", "chipmos", "3823chipmos", "/TSKMergeIN", "D:\\TSKMergeAgent\\work\\bump\\CHIPMOS");
            logger.info("CHIPMORE..."); 
            ftpGet.getDataFiles("ftp.chipmore.com.cn", "Himax", "qOAO76bin", "/", "D:\\TSKMergeAgent\\work\\bump\\CHIPMORE");
            
            edaFTPPut ftpPut = new edaFTPPut();
            logger.info("Upload CHIPMOS..."); 
            ftpPut.putDataFiles("ftp02.chipmos.com", "himax01", "1sis6w", "D:\\TSKMergeAgent\\work\\tmb\\CHIPMOS","/", "D:\\TSKMergeAgent\\work\\backup\\tmb\\CHIPMOS");
            
            long endTime = System.currentTimeMillis();
            logger.info("Total Time " + (endTime - startTime) + "ms");
            logger.info("FTP Process end ..."); 
        }
    }

  
 

    /**
     * Find next batch number with last batch number.
     * 
     * @param lastBatchNum
     *            the last batch number like 'W000000003'
     * @param prefix
     *            the batch number prefix, like 'W'
     * @return the next batch number
     */
    private String nextJOb(final String as, final String ass) {
        return "";
    }
}
