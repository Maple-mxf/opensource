package io.jopen.springboot.plugin.limit;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @since 2020/2/5
 */
public interface Keeper {

    /**
     * 记录违规操作
     *
     * @param limitKey 限流Key
     */
    void recordViolation(@NonNull String limitKey);

    /**
     * 获取信息是否允许此操作
     *
     * @param limitKey
     * @return
     */
    @NonNull
    Info solicitingOpinions(@NonNull String limitKey);

    /**
     * 超过多少次进行拉黑拉黑
     *
     * @return 违规操作次数
     */
    default int exceedViolation() {
        return 10;
    }

    /**
     * 冻结时长  单位为Ms
     *
     * @see java.util.concurrent.TimeUnit#MILLISECONDS
     */
    default long freezingTime() {
        // 返回的时间单位为毫秒 冻结默认时长为三天
        return 1000 * 60 * 60 * 24L;
    }


    @NonNull
    default String redisKey(@NonNull String limitKey) {
        return "limitKey-" + limitKey;
    }

    class Info {
        // 正常状态
        public static final Info NORMAL = new Info(true, null);
        // 不可访问状态
        public static final Info FORBIDDEN = new Info(true, "您访问过于频繁，现已经被冻结");

        // 是否允许访问
        boolean isAllowAccess;
        // 如果不允许访问 错误信息
        String errMsg;

        public Info(boolean isAllowAccess, String errMsg) {
            this.isAllowAccess = isAllowAccess;
            this.errMsg = errMsg;
        }
    }
}
