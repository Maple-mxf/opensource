package io.jopen.springboot.plugin.quartz;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 分布式定时任务很容易出现任务状态处于NONE或者BLOCKED状态  因为处于分布式环境下，网络和数据库的不稳定会造成一定的
 * 分布式锁的错误  所以任务可能会出现以上两个状态，为了解决这个问题，
 * 1  我们需要在SpringBoot应用刚刚启动的时候的需要检查所有任务的状态，如果任务出现异常 必须restart或者resume
 * 2  我们需要在SpringBoot应用完全启动起来的时候，使用JVM级别的定时器检查quartz分布式定时器的状态和及时恢复，这在金融领域的
 * 互联网应用开发很有必要
 * <p>
 * check the {@link org.quartz.Trigger} state
 * <p>
 * <p>
 * 防止部分任务的{@link org.quartz.Trigger}的状态处于一个{@link Scheduler#getTriggerState(TriggerKey)}
 * <p>
 * {@link org.quartz.Trigger.TriggerState#PAUSED} TODO  暂停状态 需要restart
 *
 * @author maxuefeng
 * @see JobMonitors#restartJob(String, String)
 * <p>
 * {@link org.quartz.Trigger.TriggerState#BLOCKED} TODO  阻塞状态  需要resume 状态  恢复状态
 * @see Scheduler#resumeJob(JobKey)
 * <p>
 * {@link org.quartz.Trigger.TriggerState#COMPLETE} TODO  任务处于此种状态几率很小  因为SpringBoot应用刚刚启动起来
 * <p>
 * <p>
 * {@link org.quartz.Trigger.TriggerState#NONE} TODO  任务处于None状态 ，如果Job处于NONE状态  很大可能性DB的storage处于一个坏掉的状态
 * <p>
 * {@link org.quartz.Trigger.TriggerState#NORMAL}  TODO  任务处于NORMAL状态， 处于OK形态
 * @since 2020/1/31
 */
@Component
public class JobTriggerStateDetector implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobTriggerStateDetector.class);

    @Autowired
    private Scheduler scheduler;

    /**
     * job {@link org.quartz.JobDetail}   by jobKey {@link org.quartz.JobKey} get an job
     * all trigger, {@link Scheduler#getTriggerState(TriggerKey)}
     *
     * @see JobMonitors#addJob(String, String, String, String, String, boolean)
     */
    @Autowired
    private JobMonitors jobMonitors;


    /**
     * @see Timer#Timer(boolean)
     * @see Timer#Timer(String, boolean)
     */
    private final Timer detectionTriggerStateTimer = new Timer("detectionTriggerStateTimer", true);


    /**
     * @see TimerTask
     */
    private final TimerTask detectionTriggerStateTimeTask = new TimerTask() {
        @Override
        public void run() {
            try {
                JobTriggerStateDetector.this.detectionTriggerState();
            } catch (SchedulerException e) {
                e.printStackTrace();
                LOGGER.error("detection error {} ", e.getMessage());

            }
        }
    };

    /**
     * @param args
     * @throws Exception
     * @see java.util.Timer
     * @see JobTriggerStateDetector#run(ApplicationArguments) 此方法调动的元素应属于原子化的东西，所以作者抽象出来为公共的部分
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 1 先调用detection方法进行检测
        detectionTriggerState();

        // 2 调用JVM级别定时器调动定时任务检测(无延迟执行  第一个值设定为now 第二个值设定为间隔时长    不需要设置delay的值)
        // 时间单位为time in milliseconds between successive task executions.毫秒级别
        detectionTriggerStateTimer.schedule(this.detectionTriggerStateTimeTask, new Date(), 1000 * 60 * 5);
    }

    private void detectionTriggerState() throws SchedulerException {
        // get all jobs state
        List<String> jobGroupNames = scheduler.getJobGroupNames();
        for (String groupName : jobGroupNames) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                // 获取JOB的对象
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                // 查询所有的Trigger
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
                triggers.stream().filter(trigger -> {
                    try {
                        // 过滤状态正常的trigger
                        return !Trigger.TriggerState.COMPLETE.equals(scheduler.getTriggerState(trigger.getKey())) &&
                                !Trigger.TriggerState.NORMAL.equals(scheduler.getTriggerState(trigger.getKey()));
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                        return true;
                    }
                }).forEachOrdered(trigger -> {

                    // 1 如果状态为NONE  需要restart
                    // 2 如果状态为BLOCKED(注意此处很可能是运行环境除了问题 ，此处开发者可自定义报警组件的编写，作者会
                    // 留下对应的报警组件接入API)
                    try {

                        if (scheduler.getTriggerState(trigger.getKey()).equals(Trigger.TriggerState.NONE)) {
                            // 1 暂停任务
                            scheduler.pauseJob(jobKey);
                            // 2 启动任务
                            scheduler.scheduleJob(trigger);
                        }

                        if (scheduler.getTriggerState(trigger.getKey()).equals(Trigger.TriggerState.BLOCKED)) {
                            scheduler.resumeJob(jobKey);
                        }
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
