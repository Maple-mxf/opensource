package io.jopen.springboot.plugin.quartz;

import lombok.*;
import lombok.experimental.Accessors;
import org.quartz.JobDataMap;
import org.quartz.TriggerKey;

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
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseTriggerInfo {

    protected String description;
    protected String calendarName;
    protected Date endTime;
    protected Date finalFireTime;
    protected Date nextFireTime;
    protected Date previousFireTime;
    private boolean mayFireAgain;
    protected TriggerKey triggerKey;
    protected Date startTime;
    protected int misfireInstruction;
    protected int priority;
    protected JobDataMap jobDataMap;
    protected String triggerType;

    // 设定Trigger的类型
    BaseTriggerInfo() {
        setupTriggerType();
    }

    public abstract void setupTriggerType();
}


