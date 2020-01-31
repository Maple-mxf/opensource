package io.jopen.springboot.plugin.quartz;

import io.jopen.springboot.plugin.common.ReflectUtil;
import org.quartz.*;
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

    @Autowired
    private Scheduler scheduler;

    /**
     * 自动装配
     *
     * @param importMetadata
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
                    boolean deleteSuccess = scheduler.deleteJob(jobDetail.getKey());
                    if (!deleteSuccess)
                        throw new RuntimeException(String.format("Job delete fail job Key %s", jobDetail.getKey()));
                }
                // schedule the job
                scheduler.scheduleJob(jobDetail, triggers, jobBeanAgent.setupReplace());
            }

            // start the scheduler
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (InstantiationException | IllegalAccessException | SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
