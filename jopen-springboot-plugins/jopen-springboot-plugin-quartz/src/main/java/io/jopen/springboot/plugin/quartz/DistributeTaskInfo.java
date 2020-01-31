package io.jopen.springboot.plugin.quartz;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author maxuefeng
 * @see org.quartz.Trigger
 * @since 2019/11/1
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistributeTaskInfo {

    private String name;

    private String group;

    private String desc;

    private String className;

    // 状态
    private String state;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @lombok.Builder(builderClassName = "Builder", toBuilder = true)
    @Getter
    @Setter
    public class TriggerInfo {
        private String group;

        private String name;

        private String cron;

        private String state;

        private Date endTime;

        private Date previousFireTime;

        private Date nextFireTime;
    }
}
