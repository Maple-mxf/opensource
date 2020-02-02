package io.jopen.springboot.plugin.quartz;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * implement a job {@link org.springframework.scheduling.quartz.QuartzJobBean}
 *
 * @author maxuefeng
 * @see org.quartz.SchedulerFactory
 * @see Scheduler
 * @since 2020/1/31
 */
@Component
public final class JobMonitors {

    private Scheduler scheduler;

    /**
     * @param scheduler 调度器 （non null）
     * @see JobDetail#isConcurrentExectionDisallowed() 控制是否并发执行
     */
    @Autowired
    public JobMonitors(@NonNull Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    public boolean deleteJob(String group, String name) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, group);
        boolean exists = scheduler.checkExists(jobKey);
        if (exists) {
            return scheduler.deleteJob(jobKey);
        }
        return false;
    }

    public boolean restartJob(String group, String name) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, group);
        boolean exists = scheduler.checkExists(jobKey);
        if (!exists) {
            return false;
        }
        // 暂停任务
        scheduler.pauseJob(jobKey);
        // 恢复任务
        scheduler.resumeJob(jobKey);
        return true;
    }

    public boolean pauseJob(String group, String name) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, group);
        boolean exists = scheduler.checkExists(jobKey);
        if (!exists) {
            return false;
        }
        // 暂停任务
        scheduler.pauseJob(jobKey);
        return true;
    }

    public void scheduleJob(String group, String name) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        scheduler.scheduleJob(jobDetail, triggers.get(0));
        for (Trigger trigger : triggers) {
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    public boolean addJob(String jobGroup, String jobName, String className, String desc, String cronExpression, boolean replace)
            throws SchedulerException {

        Class<? extends Job> cls;
        try {
            cls = (Class<? extends Job>) Class.forName(className);
        } catch (Exception ignored) {
            return false;
        }
        JobDetail jobDetail = JobBuilder.newJob(cls)
                .withIdentity(jobName, jobGroup)
                .withDescription(desc)
                .build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger" + jobName, jobGroup)
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        return true;
    }

    public List<DistributeTaskInfo> distributeTaskList() throws SchedulerException {
        List<DistributeTaskInfo> tasks = new ArrayList<>();
        List<String> jobGroupNames = scheduler.getJobGroupNames();
        for (String groupName : jobGroupNames) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                String className = jobDetail.getJobClass().getName();
                DistributeTaskInfo task = DistributeTaskInfo.builder()
                        .name(jobKey.getName())
                        .group(groupName)
                        .desc(jobDetail.getDescription())
                        .className(className)
                        .build();

                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                List<String> stateList = triggers.stream()
                        .map(trigger -> {
                            try {
                                return scheduler.getTriggerState(trigger.getKey()).toString();
                            } catch (SchedulerException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .filter(s -> !Strings.isNullOrEmpty(s))
                        .collect(Collectors.toList());

                String state = Joiner.on("-").join(stateList);
                // 设置任务状态
                task.setState(state);
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * @param group
     * @param name
     * @return
     * @throws SchedulerException
     * @see org.quartz.SimpleTrigger 简单触发器
     * @see org.quartz.CronTrigger  基于cron表达式的触发器
     * @see org.quartz.DailyTimeIntervalTrigger  基于时间间隔的触发器
     * @see org.quartz.CalendarIntervalTrigger  基于日历的触发器
     * @see BaseTriggerInfo  基于以上四种trigger的信息的包装
     */
    public List<BaseTriggerInfo> jobTriggerInfoList(String group, String name) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, group);
        List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

        return triggers.stream()
                .map(originTrigger -> {
                    try {
                        //
                        BaseTriggerInfo baseTriggerInfo;
                        if (originTrigger instanceof CronTrigger) {
                            // 强制转换
                            CronTrigger cronTrigger = (CronTrigger) originTrigger;
                            baseTriggerInfo = new CronTriggerInfo();

                            String cronExpression = cronTrigger.getCronExpression();
                            String expressionSummary = cronTrigger.getExpressionSummary();
                            TimeZone timeZone = cronTrigger.getTimeZone();


                            ((CronTriggerInfo) baseTriggerInfo).setCronExpression(cronExpression);
                            ((CronTriggerInfo) baseTriggerInfo).setExpressionSummary(expressionSummary);
                            ((CronTriggerInfo) baseTriggerInfo).setTimeZone(timeZone);


                        } else if (originTrigger instanceof SimpleTrigger) {

                            SimpleTrigger simpleTrigger = (SimpleTrigger) originTrigger;
                            int repeatCount = simpleTrigger.getRepeatCount();
                            long repeatInterval = simpleTrigger.getRepeatInterval();
                            int timesTriggered = simpleTrigger.getTimesTriggered();

                            baseTriggerInfo = new SimpleTriggerInfo();

                            ((SimpleTriggerInfo) baseTriggerInfo).setRepeatCount(repeatCount);
                            ((SimpleTriggerInfo) baseTriggerInfo).setRepeatInterval(repeatInterval);
                            ((SimpleTriggerInfo) baseTriggerInfo).setTimesTriggered(timesTriggered);

                        } else if (originTrigger instanceof DailyTimeIntervalTrigger) {

                            DailyTimeIntervalTrigger dailyTimeIntervalTrigger = (DailyTimeIntervalTrigger) originTrigger;
                            Set<Integer> daysOfWeek = dailyTimeIntervalTrigger.getDaysOfWeek();
                            TimeOfDay endTimeOfDay = dailyTimeIntervalTrigger.getEndTimeOfDay();
                            int repeatCount = dailyTimeIntervalTrigger.getRepeatCount();
                            int repeatInterval = dailyTimeIntervalTrigger.getRepeatInterval();
                            DateBuilder.IntervalUnit repeatIntervalUnit = dailyTimeIntervalTrigger.getRepeatIntervalUnit();
                            TimeOfDay startTimeOfDay = dailyTimeIntervalTrigger.getStartTimeOfDay();
                            int timesTriggered = dailyTimeIntervalTrigger.getTimesTriggered();


                            baseTriggerInfo = new DailyTimeIntervalTriggerInfo();

                            ((DailyTimeIntervalTriggerInfo) baseTriggerInfo).setDaysOfWeek(daysOfWeek);
                            ((DailyTimeIntervalTriggerInfo) baseTriggerInfo).setEndTimeOfDay(endTimeOfDay);
                            ((DailyTimeIntervalTriggerInfo) baseTriggerInfo).setRepeatCount(repeatCount);
                            ((DailyTimeIntervalTriggerInfo) baseTriggerInfo).setRepeatInterval(repeatInterval);
                            ((DailyTimeIntervalTriggerInfo) baseTriggerInfo).setRepeatIntervalUnit(repeatIntervalUnit);
                            ((DailyTimeIntervalTriggerInfo) baseTriggerInfo).setStartTimeOfDay(startTimeOfDay);
                            ((DailyTimeIntervalTriggerInfo) baseTriggerInfo).setTimesTriggered(timesTriggered);


                        } else if (originTrigger instanceof CalendarIntervalTrigger) {
                            CalendarIntervalTrigger calendarIntervalTrigger = (CalendarIntervalTrigger) originTrigger;

                            baseTriggerInfo = new CalendarIntervalTriggerInfo();

                            int repeatInterval = calendarIntervalTrigger.getRepeatInterval();
                            DateBuilder.IntervalUnit repeatIntervalUnit = calendarIntervalTrigger.getRepeatIntervalUnit();
                            int timesTriggered = calendarIntervalTrigger.getTimesTriggered();
                            TimeZone timeZone = calendarIntervalTrigger.getTimeZone();

                            ((CalendarIntervalTriggerInfo) baseTriggerInfo).setRepeatInterval(repeatInterval);
                            ((CalendarIntervalTriggerInfo) baseTriggerInfo).setRepeatIntervalUnit(repeatIntervalUnit);
                            ((CalendarIntervalTriggerInfo) baseTriggerInfo).setTimesTriggered(timesTriggered);
                            ((CalendarIntervalTriggerInfo) baseTriggerInfo).setTimeZone(timeZone);
                        } else {
                            throw new RuntimeException("trigger type not found");
                        }

                        // setup common property
                        // common info
                        String description = originTrigger.getDescription();
                        String calendarName = originTrigger.getCalendarName();
                        Date endTime = originTrigger.getEndTime();
                        Date finalFireTime = originTrigger.getFinalFireTime();
                        Date nextFireTime = originTrigger.getNextFireTime();
                        Date previousFireTime = originTrigger.getPreviousFireTime();
                        TriggerKey triggerKey = originTrigger.getKey();
                        Date startTime = originTrigger.getStartTime();
                        int misfireInstruction = originTrigger.getMisfireInstruction();
                        int priority = originTrigger.getPriority();
                        JobDataMap jobDataMap = originTrigger.getJobDataMap();


                        baseTriggerInfo.setDescription(description);
                        baseTriggerInfo.setCalendarName(calendarName);
                        baseTriggerInfo.setEndTime(endTime);
                        baseTriggerInfo.setFinalFireTime(finalFireTime);
                        baseTriggerInfo.setNextFireTime(nextFireTime);
                        baseTriggerInfo.setPreviousFireTime(previousFireTime);
                        baseTriggerInfo.setTriggerKey(triggerKey);
                        baseTriggerInfo.setStartTime(startTime);
                        baseTriggerInfo.setMisfireInstruction(misfireInstruction);
                        baseTriggerInfo.setPriority(priority);
                        baseTriggerInfo.setJobDataMap(jobDataMap);

                        return baseTriggerInfo;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
