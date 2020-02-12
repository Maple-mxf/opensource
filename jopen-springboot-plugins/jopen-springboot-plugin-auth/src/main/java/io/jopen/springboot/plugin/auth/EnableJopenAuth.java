package io.jopen.springboot.plugin.auth;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author maxuefeng
 * @since 2020/1/26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({AuthenticationInterceptor.class,
        AuthPluginConfiguration.class,
        AuthContext.class
})
public @interface EnableJopenAuth {

    /**
     * 当前拦截器执行的顺序
     *
     * @return 顺序order {@link org.springframework.web.servlet.config.annotation.InterceptorRegistration#order(int)}
     */
    int order() default 0;

    /**
     * 所要拦截的路径
     *
     * @see org.springframework.web.servlet.config.annotation.InterceptorRegistration#addPathPatterns(String...)
     */
    String[] pathPatterns() default {"/**"};

    /**
     * 所有排除的路径
     *
     * @see org.springframework.web.servlet.config.annotation.InterceptorRegistration#excludePathPatterns(String...)
     */
    String[] excludePathPattern() default {};

    /**
     * @return {@link AuthMetadata} 认证的元数据信息
     */
    Class<? extends AuthMetadata> authMetadataType();
}
