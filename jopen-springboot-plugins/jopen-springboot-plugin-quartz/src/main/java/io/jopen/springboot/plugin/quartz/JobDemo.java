package io.jopen.springboot.plugin.quartz;

import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.TimeZone;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
public class JobDemo extends QuartzJobBean {

    /**
     * @param context
     * @throws JobExecutionException
     * @see JobDetail
     * @see org.springframework.scheduling.support.CronTrigger#CronTrigger(String, TimeZone)
     * @see CronTrigger
     * @see org.quartz.impl.triggers.CronTriggerImpl
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // do some things

        // build job demo
        JobDetail jobDetail = JobBuilder.newJob().withIdentity(JobKey.jobKey("", ""))
                .storeDurably().withDescription("")
                .build();

        // build trigger

        // 1 cron trigger
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("myCronJobTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();

        // 2 simple trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(15).repeatForever();
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("myJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();


    }
}
