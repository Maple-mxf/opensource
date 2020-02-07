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
// 导入外部的对象注入
@Import({TokenIdempotentInterceptor.class,
        IdempotentPluginConfiguration.class,
        DefaultIdempotentTokenFunctionImpl.class
})
public @interface EnableJopenIdempotent {

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

    /**
     * 拦截器获取幂等性token的Key
     *
     * @return {@link javax.servlet.http.HttpServletRequest}
     */
    String idempotentTokenKey();

    /**
     * 从什么位置获取幂等性token
     *
     * @return
     */
    TokenLocation idempotentTokenLocation() default TokenLocation.HEADER;
}
