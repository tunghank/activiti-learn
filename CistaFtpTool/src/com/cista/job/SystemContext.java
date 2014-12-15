package com.cista.job;

import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;
 

/**
 * 
 * @author Matrix
 *
 */
public final class SystemContext {
    /** Logger. */
    private static final Log LOGGER = LogFactory.getLog(SystemContext.class);

    /** Context configuration file path and name. */
    private static final String[] CONTEXT_CONFIGURATION = new String[] {
            "SystemContext.xml", "JobContextImport.xml" };

    /** The Core ApplicationContext of springframework, instanced by initialize() once only. **/
    private static ApplicationContext context;

    /** Default locale for i18n message fetch. */
    private static final Locale DEFAULT_LOCALE = Locale.TAIWAN;

    private static Properties ftpConfig;

    /** The key for UserTo in HttpSession. */
    private static final String LOGIN_USER = "LOGIN_USER";

    /** The key for authentication list in HttpSession. */
    private static final String USER_AUTHENTICATION = "USER_AUTHENTICATION";

    /**
     * Default constructor, it will not instanced directly.
     *
     */
    private SystemContext() {

    }

    /**
     * BeanFactory initialize internal only.
     *
     */
    private static synchronized void initialize() {
        try {
            if (context == null) {
                //init application context.
                context = new ClassPathXmlApplicationContext(CONTEXT_CONFIGURATION);
                LOGGER.debug("FTP ApplicationContext "
                        + CONTEXT_CONFIGURATION[0] + ", "
                        + CONTEXT_CONFIGURATION[1] + " loaded success.");

                ftpConfig = (Properties) context.getBean("ftpConfig");
            }
        } catch (Exception e) {
            LOGGER.error("FTP ApplicationContext "
                    + CONTEXT_CONFIGURATION[0] + ", "
                    + CONTEXT_CONFIGURATION[1] + " load failure.", e);
        }
    }


    /**
     * Return a DataImportEngine.
     * @return DataImportEngine.
     */
    public static DataImportEngine getDataImportEngine() {
        initialize();
        return (DataImportEngine) context.getBean("dataImportEngine");
    }

    /**
     * Get a message from resource.
     * @param key resource key.
     * @return message.
     */
    public static String getMessage(final String key) {
        initialize();
        return context.getMessage(key, null, key, DEFAULT_LOCALE);
    }

    public static String getConfig(final String key) {
        initialize();
        return ftpConfig.getProperty(key, null);
    }
    /**
     * Return defined bean.
     * @return bean.
     */
    public static Object getBean(final String beanName) {
        initialize();
        return context.getBean(beanName);
    }
}
