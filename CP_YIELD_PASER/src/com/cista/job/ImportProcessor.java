package com.cista.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * The base import processor.
 * @author Matrix
 *
 */
public abstract class ImportProcessor implements Job {
    /** Logger. */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * The Quarz job execute stub.
     * @param arg0 JobExecutionContext
     * @throws JobExecutionException JobExecutionException
     */
    public void execute(final JobExecutionContext arg0) throws JobExecutionException {
        run();
    }

    /**
     * The processor run method will implement by sub-classes.
     *
     */
    public abstract void run();

}
