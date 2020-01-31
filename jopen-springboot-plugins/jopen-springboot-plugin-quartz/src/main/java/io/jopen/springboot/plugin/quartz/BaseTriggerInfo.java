package io.jopen.springboot.plugin.quartz;

import lombok.*;
import lombok.experimental.Accessors;
import org.quartz.Trigger;

import java.util.Date;

/**
 * 触发器的类别
 *
 * @author maxuefeng
 * <p>
 * 当前类定义了最基本的触发器的属性  其他触发器基于Base版本进行拓展
 * quartz触发器分为以下四个级别
 * @see org.quartz.SimpleTrigger 简单触发器
 * @see org.quartz.CronTrigger  基于cron表达式的触发器
 * @see org.quartz.DailyTimeIntervalTrigger  基于时间间隔的触发器
 * @see org.quartz.CalendarIntervalTrigger  基于日历的触发器
 * @since 2020/1/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
@Getter
@Setter
public class BaseTriggerInfo {


    /**
     * @see Trigger#getKey() triggerKey区别每一个trigger的区别
     * 触发器的名称trigger name
     */
    protected String triggerName;

    /**
     * @see Trigger#getKey()
     * <p>
     * 触发器的组 trigger group
     */
    protected String triggerGroup;

    /**
     * 触发器的state
     */
    protected String state;

    /**
     * 最后一次的执行时间
     */
    protected Date endTime;

    /**
     * 上一次执行时间
     */
    protected Date previousFireTime;

    /**
     * 下一次的执行时间
     */
    protected Date nextFireTime;
}
