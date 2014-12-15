package com.cista.job;

/**
 * Data import processor config per import processor.
 * @author Matrix
 *
 */
public class ImportProcessorConfig {
    /**
     * The import processor name.
     */
    private String name;

    /**
     * The import processor is enabled or disabled.
     */
    private boolean enable = true;

    /**
     * The Quartz CronTrigger expression for processor schedule.
     */
    private String scheduleExpression;

    /**
     * The process implement class.
     */
    private String processClass;

    /**
     * Check whether the processor is enabled.
     * @return true or false.
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Enable or disable the processor.
     * @param _enable enable or disable
     */
    public void setEnable(final boolean _enable) {
        this.enable = _enable;
    }

    /**
     * Return the Quartz CronTrigger expression for processor schedule.
     * @return Quartz CronTrigger expression
     */
    public String getScheduleExpression() {
        return scheduleExpression;
    }

    /**
     * Set the Quartz CronTrigger expression for processor schedule.
     * @param _scheduleExpression CronTrigger expression
     */
    public void setScheduleExpression(final String _scheduleExpression) {
        this.scheduleExpression = _scheduleExpression;
    }

    /**
     * Return processor name.
     * @return processor name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set processor name.
     * @param _name processor name.
     */
    public void setName(final String _name) {
        this.name = _name;
    }

    /**
     * Get process implement class name.
     * @return process implement class name.
     */
    public String getProcessClass() {
        return processClass;
    }

    /**
     * Set process implement class name.
     * @param _processClass process implement class name.
     */
    public void setProcessClass(final String _processClass) {
        this.processClass = _processClass;
    }
}
