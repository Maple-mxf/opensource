package io.jopen.springboot.plugin.quartz;

import com.google.common.collect.ImmutableMap;
import io.jopen.springboot.plugin.common.ReflectUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@Configuration
public class QuartzPluginConfiguration implements ImportAware {

    private final static Logger LOGGER = LoggerFactory.getLogger(QuartzPluginConfiguration.class);

    private Scheduler scheduler;

    private JobTriggerStateDetector jobTriggerStateDetector;

    private JobMonitors jobMonitors;

    @Autowired
    public QuartzPluginConfiguration(Scheduler scheduler,
                                     JobTriggerStateDetector jobTriggerStateDetector,
                                     JobMonitors jobMonitors) {
        this.scheduler = scheduler;
        this.jobTriggerStateDetector = jobTriggerStateDetector;
        this.jobMonitors = jobMonitors;
    }

    /**
     * 自动装配
     *
     * @param importMetadata springboot启动类导入的元信息
     */
    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes enableQuartz = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableJopenQuartz.class.getName(), false));

        if (enableQuartz == null) {
            throw new IllegalArgumentException(
                    "@EnableJopenQuartz is not present on importing class " + importMetadata.getClassName());
        }

        String[] packageArray = enableQuartz.getStringArray("jobBeanBasePackage");
        try {
            List<Class<?>> jobBeanClass = Arrays.stream(packageArray)
                    .flatMap(pa -> {
                        try {
                            return ReflectUtil.getClasses(pa).stream();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Stream.empty();
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(type -> type.getGenericSuperclass().equals(JobBeanAgent.class))
                    .collect(Collectors.toList());

            // 获取schedule存储的任务Job
            List<DistributeTaskInfo> taskList = jobMonitors.distributeTaskList(false);

            // 检测不存在的任务 （可能由于开发者删除或者其他原因）
            List<DistributeTaskInfo> notExistJobClassList = taskList.stream()
                    .filter(task -> !jobBeanClass.contains(task.getJobClass())).collect(Collectors.toList());
            // 删除不存在的任务
            for (DistributeTaskInfo distributeTaskInfo : notExistJobClassList) {
                scheduler.deleteJob(JobKey.jobKey(distributeTaskInfo.getName(), distributeTaskInfo.getGroup()));
            }

            for (Class<?> beanClass : jobBeanClass) {
                JobBeanAgent jobBeanAgent = (JobBeanAgent) beanClass.newInstance();

                // build job detail
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) beanClass)
                        .withIdentity(jobBeanAgent.setupJobKey())
                        .withDescription(jobBeanAgent.setupDescription())
                        .build();

                // build job trigger
                Set<? extends Trigger> triggers = jobBeanAgent.setupTriggers();

                // if exist old job info in db prepare delete job info data
                // after deleted , schedule the job by trigger
                if (scheduler.checkExists(jobDetail.getKey())) {
                    if (jobBeanAgent.setupReplace()) {
                        scheduler.scheduleJob(jobDetail, triggers, true);
                    }
                    // 否则则不重复调度任务
                } else {
                    scheduler.scheduleJobs(ImmutableMap.of(jobDetail, triggers), false);
                }
            }
            // start the scheduler
            securityStartScheduler();

            // setup enableCheckDistributeTaskState
            boolean enableCheckDistributeTaskState = enableQuartz.getBoolean("enableCheckDistributeTaskState");
            jobTriggerStateDetector.setEnableCheckDistributeTaskState(enableCheckDistributeTaskState);

            // 如果开启了报警策略 则设定具体的值  否则不设定
            if (enableCheckDistributeTaskState) {
                // setup callThePolicy
                Class<? extends CallThePolicy> policyStrategy = enableQuartz.getClass("checkDistributeTaskErrorCallThePolicyStrategy");
                this.jobTriggerStateDetector.setCallThePolicy(policyStrategy.newInstance());
            }

        } catch (InstantiationException | IllegalAccessException | SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * security start scheduler
     * if abort will be throw a {@link RuntimeException}
     */
    private void securityStartScheduler() {
        try {
            boolean started = this.scheduler.isStarted();
            if (!started) {
                this.scheduler.start();
            } else {
                LOGGER.warn("Jopen-quartz-plugin schedule status {} ", this.scheduler.isStarted());
                LOGGER.warn("Jopen-quartz-plugin start schedule start failure");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
