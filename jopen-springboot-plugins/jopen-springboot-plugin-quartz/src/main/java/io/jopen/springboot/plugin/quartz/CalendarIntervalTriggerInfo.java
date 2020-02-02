package io.jopen.springboot.plugin.quartz;

import lombok.*;
import lombok.experimental.Accessors;
import org.quartz.DateBuilder;

import java.util.TimeZone;

/**
 * @author maxuefeng
 * @since 2020/2/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
@Getter
@Setter
public class CalendarIntervalTriggerInfo extends BaseTriggerInfo {


    private int repeatInterval;
    private DateBuilder.IntervalUnit repeatIntervalUnit;
    private int timesTriggered;
    private int timesTriggered1;
    private TimeZone timeZone;
}
