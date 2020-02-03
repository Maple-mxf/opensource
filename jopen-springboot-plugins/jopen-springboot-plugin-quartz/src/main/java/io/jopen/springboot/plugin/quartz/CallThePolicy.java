package io.jopen.springboot.plugin.quartz;

import com.alibaba.fastjson.JSON;
import io.jopen.springboot.plugin.common.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 报警插件
 *
 * @author maxuefeng
 * @since 2020/2/3
 */
@FunctionalInterface
public interface CallThePolicy extends java.io.Serializable {

    /**
     * @param distributeTaskInfo 分布式任务
     * @param errMsg             错误信息
     * @param occurTimeMs        报警时间
     *                           报警内容
     */
    void call(DistributeTaskInfo distributeTaskInfo, String errMsg, long occurTimeMs);


    /**
     * 默认的报警策略
     *
     * @see EnableJopenQuartz
     */
    final class DefaultCallPolicyStrategy implements CallThePolicy {

        private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCallPolicyStrategy.class);

        @Override
        public void call(DistributeTaskInfo distributeTaskInfo, String errMsg, long occurTimeMs) {
            LOGGER.error("distribute task occur an Exception");
            LOGGER.error("--------------------------------------------------------------------------------------------");
            LOGGER.error("distributeTaskInfo  {} ", JSON.toJSONString(distributeTaskInfo));
            LOGGER.error("error message {} ", errMsg);
            LOGGER.error("occur date time {} ", Formatter.format(occurTimeMs, Formatter.P.P4));
            LOGGER.error("--------------------------------------------------------------------------------------------");
        }
    }
}
