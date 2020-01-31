package io.jopen.springboot.plugin.quartz;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<DistributeTaskInfo.TriggerInfo> jobTriggerInfoList(String group, String name) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, group);
        List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

        return triggers.stream()
                .map(originTrigger -> {
                    try {
                        CronTrigger cronTrigger = (CronTrigger) originTrigger;
                        return DistributeTaskInfo.TriggerInfo.builder()
                                .cron(((CronTrigger) originTrigger).getCronExpression())
                                .name(cronTrigger.getKey().getName())
                                .group(cronTrigger.getKey().getGroup())
                                .state(scheduler.getTriggerState(cronTrigger.getKey()).name())
                                .nextFireTime(cronTrigger.getNextFireTime())
                                .previousFireTime(cronTrigger.getPreviousFireTime())
                                .endTime(cronTrigger.getEndTime())
                                .build();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
