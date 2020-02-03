package io.jopen.springboot.plugin.quartz;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@link JobMonitors}
 * {@link QuartzPluginConfiguration}
 *
 * @author maxuefeng
 * @since 2020/1/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        JobMonitors.class,
        QuartzPluginConfiguration.class,
        JobTriggerStateDetector.class
})
public @interface EnableJopenQuartz {

    /**
     * job bean base package scanner all job bean
     *
     * @return devoloper define job task bean class package location
     */
    String[] jobBeanBasePackage() default {};

    /**
     * 是否开启分布式任务的状态检查
     *
     * @return true is check  false is not check distribute' task state
     */
    boolean enableCheckDistributeTaskState() default true;
}
