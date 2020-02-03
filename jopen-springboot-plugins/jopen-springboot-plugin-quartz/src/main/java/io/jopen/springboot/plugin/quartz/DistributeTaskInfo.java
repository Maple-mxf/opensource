package io.jopen.springboot.plugin.quartz;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.quartz.JobDataMap;

import java.util.List;

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

    private Class jobClass;

    private boolean durable;
    private JobDataMap jobDataMap;
    private boolean concurrentExecutionDisallowed;
    private boolean persistJobDataAfterExecution;
    private boolean requestsRecovery;
    private List<BaseTriggerInfo> triggerInfoList;
}
