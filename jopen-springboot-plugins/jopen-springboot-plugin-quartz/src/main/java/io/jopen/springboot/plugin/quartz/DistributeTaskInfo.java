package io.jopen.springboot.plugin.quartz;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

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

}
