package io.jopen.springboot.plugin.limit;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author maxuefeng
 * @since 2020/1/9
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({FlowControl.class, LimitPluginConfiguration.class})
public @interface EnableJopenLimit {

    /**
     * 限流Key定义  {@link }
     *
     * @return
     */
    // LimitType keyType() default LimitType.IP;


    /**
     * 限流方式
     */
    @Deprecated
    enum LimitType {
        IP, // 基于IP限流
        TOKEN // 基于token限流
    }

    /**
     * 限流Key的实现类
     *
     * @return limit key
     */
    String limitKeyProducerClassPath();
}
