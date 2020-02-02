package io.jopen.springboot.plugin.quartz;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.TimeZone;

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
public class CronTriggerInfo extends BaseTriggerInfo {

    private String cronExpression;
    private String expressionSummary;
    private TimeZone timeZone;
}
