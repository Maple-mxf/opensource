package io.jopen.springboot.plugin.quartz;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.quartz.JobBuilder;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Set;

/**
 * @author maxuefeng
 * @see QuartzJobBean
 * @since 2020/1/31
 */
public abstract class JobBeanAgent extends QuartzJobBean {

    /**
     * @return jobKey  identity
     * @see JobBuilder#withIdentity(JobKey)
     */
    @NonNull
    public abstract JobKey setupJobKey();

    /**
     * @return job description
     * @see JobBuilder#withDescription(String)
     */
    @NonNull
    public abstract String setupDescription();

    /**
     * @return multi trigger
     * @see Trigger  顶级父类
     * @see org.quartz.CronTrigger    cron expression
     * @see org.quartz.SimpleTrigger  简单的trigger
     * @see org.quartz.DailyTimeIntervalTrigger
     * @see org.quartz.CalendarIntervalTrigger
     */
    @NonNull
    public abstract Set<? extends Trigger> setupTriggers();

    /**
     * 是否替换
     * 默认替换
     *
     * @return 添加新job是否替换
     */
    @NonNull
    public boolean setupReplace() {
        return false;
    }

    /**
     * @return true allow concurrent
     * @see org.quartz.DisallowConcurrentExecution
     */
    public boolean setupDisableConcurrentExecution() {
        return false;
    }
}
