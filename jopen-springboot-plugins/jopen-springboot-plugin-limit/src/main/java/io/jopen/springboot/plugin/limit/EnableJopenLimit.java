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
@Import({
        FlowControlInterceptor.class,
        LimitPluginConfiguration.class,
        ScriptConfiguration.class,
        // 默认拉黑的策略实现
        SimpleKeeperImpl.class})
public @interface EnableJopenLimit {

    /**
     * 限流Key的实现类
     *
     * @return limit key
     * @see LimitKeyProducer
     */
    Class<? extends LimitKeyProducer> limitKeyFunctionType();

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
     * 是否使用IP拉黑功能
     *
     * @return
     */
    boolean enablePullBlack() default true;

    /**
     * 默认Keeper实现策略 (实现基于IP/Token拉黑)
     *
     * @return {@link Keeper}
     */
    Class<? extends Keeper> limitKeeperType() default SimpleKeeperImpl.class;
}
