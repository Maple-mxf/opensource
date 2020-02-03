package io.jopen.springboot.plugin.quartz;

import org.quartz.*;
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
     * 是否开启分布式任务的状态检查
     */
    private boolean enableCheckDistributeTaskState = true;

    /**
     * job {@link org.quartz.JobDetail}   by jobKey {@link org.quartz.JobKey} get an job
     * all trigger, {@link Scheduler#getTriggerState(TriggerKey)}
     *
     * @see JobMonitors#addJob(String, String, String, String, String, boolean)
     */
    @Autowired
    private JobMonitors jobMonitors;

    public void setEnableCheckDistributeTaskState(boolean enableCheckDistributeTaskState) {
        this.enableCheckDistributeTaskState = enableCheckDistributeTaskState;
    }


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
     * @see java.util.Timer
     * @see JobTriggerStateDetector#run(ApplicationArguments) 此方法调动的元素应属于原子化的东西，所以作者抽象出来为公共的部分
     */
    @Override
    public void run(ApplicationArguments args) {

        // 1 先调用detection方法进行检测
        // detectionTriggerState();

        // 2 调用JVM级别定时器调动定时任务检测(无延迟执行  第一个值设定为now 第二个值设定为间隔时长    不需要设置delay的值)
        // 时间单位为time in milliseconds between successive task executions.毫秒级别
        detectionTriggerStateTimer.schedule(this.detectionTriggerStateTimeTask, new Date(), 1000 * 60 * 60);
    }

    private void detectionTriggerState() throws SchedulerException {

        LOGGER.info("start check distribute task trigger state");

        List<DistributeTaskInfo> distributeTaskInfoList = jobMonitors.distributeTaskList();
        distributeTaskInfoList.forEach(task -> {
            List<BaseTriggerInfo> triggerInfoList = task.getTriggerInfoList();

            long count = triggerInfoList.stream().filter(trigger -> {
                // 过滤状态正常的trigger
                TriggerKey triggerKey = trigger.getTriggerKey();
                try {
                    return !Trigger.TriggerState.COMPLETE.equals(scheduler.getTriggerState(triggerKey)) &&
                            !Trigger.TriggerState.NORMAL.equals(scheduler.getTriggerState(triggerKey));
                } catch (SchedulerException e) {
                    e.printStackTrace();
                    LOGGER.error("check distribute task occur an exception {} ", e.getMessage());
                    return true;
                }
            }).count();

            if (count > 0L) {
                try {
                    jobMonitors.restartJob(JobKey.jobKey(task.getName(), task.getGroup()));
                } catch (SchedulerException e) {
                    e.printStackTrace();
                    LOGGER.error("check distribute task occur an exception {} ", e.getMessage());
                }
            }
        });

        LOGGER.info("check distribute task trigger state completed");
    }
}
