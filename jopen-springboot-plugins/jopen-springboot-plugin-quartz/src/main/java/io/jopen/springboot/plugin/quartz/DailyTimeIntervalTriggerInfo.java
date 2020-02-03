package io.jopen.springboot.plugin.quartz;

import lombok.*;
import lombok.experimental.Accessors;
import org.quartz.DateBuilder;
import org.quartz.TimeOfDay;

import java.util.Set;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyTimeIntervalTriggerInfo extends BaseTriggerInfo {

    private Set<Integer> daysOfWeek;
    private TimeOfDay endTimeOfDay;
    private int repeatCount;
    private int repeatInterval;
    private DateBuilder.IntervalUnit repeatIntervalUnit;
    private TimeOfDay startTimeOfDay;
    private int timesTriggered;
}
