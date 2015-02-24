package com.cista.job;

import com.cista.cpYield.parser.CpYieldParser;

public class ParserJob extends ImportProcessor {
    private static Integer lock = 0;

    //private static final String IMPORT_STG_STATUS = "New";

    /**
     * Goods movement import process.
     */
    @Override
    public void run() {
        synchronized (lock) {

            long startTime = System.currentTimeMillis();
            logger.info("CpYieldParser Process start ...");
            CpYieldParser cpYieldParser =  new CpYieldParser();
            cpYieldParser.GetCpYieldFiles();
            
            logger.info("CpYieldParser Process End ...");
            long endTime = System.currentTimeMillis();
            logger.info("Total Time : " + (endTime - startTime)); 
            
            try{
            	this.wait(30000);
            }catch(Exception ex){
            	
            }
            

            
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
