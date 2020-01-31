package io.jopen.springboot.plugin.quartz;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author maxuefeng
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
@Deprecated
// 过时的设计
// 因为trigger分为多类 此处仅仅设计了基于cron表达式的trigger信息包装

public class TriggerInfo {

    
    private String group;

    private String name;

    private String cron;

    private String state;

    private Date endTime;

    private Date previousFireTime;

    private Date nextFireTime;
}
