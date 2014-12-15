package com.cista.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 

/**
 * FTP bootstrape.
 * @author Matrix
 *
 */
public final class DataImportEngineBootstrape {
    /** Logger. */
    private static final Log logger = LogFactory.getLog(DataImportEngineBootstrape.class);

    private static DataImportEngine instance;

    /**
     * Default contructor, will not initialized.
     *
     */
    private DataImportEngineBootstrape() {

    }

    /**
     * FTP bootstrape.
     * @param args arguments
     */
    public static void main(final String[] args) {
        long startTime = System.currentTimeMillis();
        startup();
        printLogo(System.currentTimeMillis() - startTime);
    }

    /**
     * The logo printer.
     * @param timeCast how many times spend on start up.
     */
    private static void printLogo(final long timeCast) {
        int logoWidth = 78;
        String appName = "Himax FTP JOBs";
        int spacesBeforeAppName = (logoWidth - 2 - appName.length()) / 2;
        String timeStr = "Startup success in " + timeCast + "ms.";
        int spacesBeforeTimeStr = (logoWidth - 2 - timeStr.length()) / 2;

        System.out.println();
        for (int i = 0; i < logoWidth; i++) {
            System.out.print("*");
        }
        System.out.println("");
        System.out.print("*");
        for (int i = 0; i < spacesBeforeAppName; i++) {
            System.out.print(" ");
        }
        System.out.print(appName);
        for (int i = 0; i < logoWidth - 2 - spacesBeforeAppName - appName.length(); i++) {
            System.out.print(" ");
        }
        System.out.print("*");
        System.out.println("");
        System.out.print("*");
        for (int i = 0; i < spacesBeforeTimeStr; i++) {
            System.out.print(" ");
        }
        System.out.print(timeStr);
        for (int i = 0; i < logoWidth - 2 - spacesBeforeTimeStr - timeStr.length(); i++) {
            System.out.print(" ");
        }
        System.out.print("*");
        System.out.println("");
        for (int i = 0; i < logoWidth; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    /**
     * Startup FTP.
     *
     */
    public static synchronized void startup() {
        if (instance == null) {
            instance = SystemContext.getDataImportEngine();
        }

        if (instance.getStatus() != 0) {
            logger.debug("FTP instance is currently running, "
                    + "can not startup again, please try restart instead.");
        } else {
            logger.info("FTP is begining to startup ...");
            instance.startup();
            logger.info("FTP finished startup.");
        }
    }

    /**
     * Shutdown FTP.
     *
     */
    public static synchronized void shutdown() {
        if (instance == null) {
            logger.debug("FTP instance is not exist, shutdown operation is ignored.");
        } else if (instance.getStatus() != 1) {
            logger.debug("FTP instance is not startup, shutdown operation is ignored.");
        } else {
            logger.info("FTP is begining to shutdown ...");
            instance.shutdown();
            //Destroy current engine instance.
            instance = null;
            logger.info("FTP finished shutdown.");
        }
    }

    /**
     * Restart FTP.
     *
     */
    public static synchronized void restart() {
        shutdown();
        startup();
    }

}
