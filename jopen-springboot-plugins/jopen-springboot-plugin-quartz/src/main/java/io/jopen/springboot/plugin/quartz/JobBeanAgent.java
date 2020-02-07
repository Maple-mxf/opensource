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

//    protected State state;
//
//    public enum State {
//        Started,
//        Stopped,
//        Abort
//    }
//
//    public final void setState(State state) {
//        this.state = state;
//    }
//
//    public final State getState() {
//        return this.state;
//    }

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

}
