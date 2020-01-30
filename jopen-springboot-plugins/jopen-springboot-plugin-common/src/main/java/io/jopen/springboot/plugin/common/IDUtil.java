package io.jopen.springboot.plugin.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

/**
 * 全局对象
 *
 * @author maxuefeng
 * @since 2019-04-26
 */
public class IDUtil {

    /**
     * 通用缓存
     */
    public static Cache<String, Object> commonCache = CacheBuilder.newBuilder().build();

    /*orderId*/
    private static SequenceGenerator sequenceGenerator4 = new SequenceGenerator(RandomUtils.nextInt(7, 8));

    /*流水号*/
    private static SequenceGenerator sequenceGenerator1 = new SequenceGenerator(RandomUtils.nextInt(5, 6));

    /*用户ID*/
    private static SequenceGenerator sequenceGenerator2 = new SequenceGenerator(RandomUtils.nextInt(3, 4));

    /*1-2*/
    private static SequenceGenerator sequenceGenerator3 = new SequenceGenerator(RandomUtils.nextInt(1, 2));

    /*票据id*/
    private static SequenceGenerator sequenceGenerator5 = new SequenceGenerator(RandomUtils.nextInt(9, 10));


    public static String id() {
        return sequenceGenerator3.nextId() + "";
    }

    public static String id(String start) {
        return start + id();
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

    public static String orderId() {
        return sequenceGenerator4.nextId() + "";
    }

    public static String credentialId() {
        return sequenceGenerator5.nextId() + "";
    }

    public static Long now() {
        return new Date().getTime();
    }
}
