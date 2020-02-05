package io.jopen.springboot.plugin.idempotency;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({TokenIdempotentInterceptor.class, IdempotentPluginConfiguration.class})  // 导入外部的对象注入
public @interface EnableJopenIdempotent {

    /**
     * @return class object  {@link Class#newInstance()}
     * @see IdempotentTokenProducer
     */
    Class<? extends IdempotentTokenProducer> idempotentTokenProducerType();

    /**
     * default 0
     *
     * @return
     */
    int order() default 0;

    /**
     * default /**
     *
     * @return
     */
    String[] includePath() default {"/**"};

    /**
     * @return
     */
    String[] excludePath() default {};
}
