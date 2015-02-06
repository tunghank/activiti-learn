package com.cista.job;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
 
import com.cista.job.ImportProcessorConfig;
/**
 * The  engine.
 * Core class for control engine start/end based on config.
 * @author Matrix
 *
 */
public class DataImportEngine {
    /** Logger. */
    protected final Log logger = LogFactory.getLog(getClass());

    private List<ImportProcessorConfig> processors;

    /**
     * status = 0, stop.
     * status = 1, running.
     */
    private int status;

    private Scheduler sched;

    /**
     * Default constructor.
     *
     */
    public DataImportEngine() {
        status = 0;
    }

    /**
     * Startup all the  processors defined.
     * @return 0 if failed, else return 1.
     */
    int startup() {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            sched = sf.getScheduler();

        } catch (SchedulerException e) {
            logger.error("Exception while init scheduler, startup fail.", e);
            status = 0;
            return status;
        }

        for (ImportProcessorConfig p : processors) {
            if (p.isEnable()) {
                logger.info("Startup  processor " + p.getName());
                try {
                    JobDetail job = new JobDetail(p.getName(), p.getName(), Class.forName(p.getProcessClass()));
                    CronTrigger trigger = new CronTrigger(p.getName(), p
                            .getName(), p.getName(), p.getName(), p
                            .getScheduleExpression());
                    sched.scheduleJob(job, trigger);
                } catch (ClassNotFoundException e) {
                    logger.error("Startup  processor " + p.getName()
                            + " fail, processor class " + p.getProcessClass()
                            + " not found.", e);
                } catch (ParseException e) {
                    logger.error("Startup  processor " + p.getName()
                            + " fail, cron expression " + p.getProcessClass()
                            + " incorrect.", e);
                } catch (SchedulerException e) {
                    logger.error("Startup  processor " + p.getName()
                            + " fail, schedule fail.", e);
                }
            } else {
                logger.info(" processor " + p.getName() + " is not enable, ignored.");
            }
            try {
                sched.start();
            } catch (SchedulerException e) {
                logger.error("Startup schedule fail", e);
            }
        }

        status = 1;

        return status;
    }

    /**
     * Shutdown all the  processors.
     * @return 1 if failed, else return 0.
     */
    int shutdown() {
        if (sched == null) {
            logger.error("Scheduler not exist, shutdown fail.");
            status = 0;
            return status;
        }

        for (ImportProcessorConfig p : processors) {
            if (p.isEnable()) {
                logger.info("Shutdown  processor " + p.getName());

                try {
                    sched.unscheduleJob(p.getName(), p.getName());
                } catch (SchedulerException e) {
                    logger.error("Shutdown  processor " + p.getName()
                            + " fail, unschedule fail.", e);
                }
            }
        }

        try {
            sched.shutdown();
        } catch (SchedulerException e) {
            logger.error("Startup schedule fail", e);
        }

        status = 0;

        return status;
    }

    /**
     * Return engine status.
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set processor config list.
     * @param _processors processor config list.
     */
    public void setProcessors(final List<ImportProcessorConfig> _processors) {
        this.processors = _processors;
    }
}
