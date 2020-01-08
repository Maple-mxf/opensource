package io.jopen.springboot.plugin.limit;

import java.lang.annotation.*;

/**
 * 流量削峰
 * 接口限流注解
 *
 * @author maxuefeng
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiting {

    /**
     * 限流唯一标示  在redis中表示K
     *
     * @return
     */
    String key() default "";

    /**
     * 限流时间
     *
     * @return
     */
    int time() default 10;

    /**
     * 限流次数
     * 10(time)可以访问多少次
     *
     * @return
     */
    int count() default 50;

}
