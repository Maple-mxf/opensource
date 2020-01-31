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
     * @return
     */
    String[] jobBeanBasePackage() default {};
}
