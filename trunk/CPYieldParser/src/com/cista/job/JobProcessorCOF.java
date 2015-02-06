package com.cista.job;

import com.cista.wip.loader.SMICWipParser;

public class JobProcessorCOF extends ImportProcessor {
    private static Integer lock = 0;

    //private static final String IMPORT_STG_STATUS = "New";

    /**
     * Goods movement import process.
     */
    @Override
    public void run() {
        synchronized (lock) {
            logger.info("Process start ..."); 
            long startTime = System.currentTimeMillis();
            logger.info("Assy Process start ...");
            //COFAssyParser assyParser = new COFAssyParser();
            //assyParser.cofParserGetFiles();
            
            try{
            	this.wait(30000);
            }catch(Exception ex){
            	
            }
            
            logger.info("Bump Process start ...");
            //SMICWipParser smicWipParser =  new SMICWipParser();
            //smicWipParser.GetSmicWipFiles();
            
            logger.info("Process End ...");
            long endTime = System.currentTimeMillis();
            logger.info("Total Time : " + (endTime - startTime)); 
            
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
