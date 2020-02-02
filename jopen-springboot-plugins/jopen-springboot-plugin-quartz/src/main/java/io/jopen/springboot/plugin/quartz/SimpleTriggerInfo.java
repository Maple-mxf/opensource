package io.jopen.springboot.plugin.quartz;

import lombok.*;
import lombok.experimental.Accessors;

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
public class SimpleTriggerInfo extends BaseTriggerInfo {

    private int repeatCount;
    private long repeatInterval;
    private int timesTriggered;
}
