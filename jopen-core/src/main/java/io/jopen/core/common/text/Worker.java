package io.jopen.core.common.text;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.RandomUtils;

/**
 * 全局对象
 *
 * @author maxuefeng
 * @since 2019-04-26
 */
public class Worker {

    /**
     * 通用缓存
     */
    public static Cache<String, Object> commonCache = CacheBuilder.newBuilder().build();

    /**
     * 流水号
     */
    private static final SequenceGeneratorID sequenceGenerator1 = new SequenceGeneratorID(RandomUtils.nextInt(601, 900));

    private static final SequenceGeneratorID sequenceGenerator2 = new SequenceGeneratorID(RandomUtils.nextInt(301, 600));

    private static final SequenceGeneratorID sequenceGenerator3 = new SequenceGeneratorID(RandomUtils.nextInt(1, 300));

    public static String id() {
        return sequenceGenerator3.nextId() + "";
    }

    /**
     * 生成流水號
     *
     * @return
     */
    public static String sn() {
        return sequenceGenerator1.nextId() + "";
    }

    /**
     * 生成用戶ID
     *
     * @return
     */
    public static String uid() {
        return sequenceGenerator2.nextId() + "";
    }
}
