package io.jopen.springboot.plugin.common;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启工具类
 *
 * @author maxuefeng
 * @since 2020/1/27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        // Spring容器对象获取工具
        SpringContainer.class,
        // 全局异常捕获器
        GlobalThrowableCaptors.class})
public @interface EnableJopenCommon {

    /**
     * 哪种环境下开启打印异常
     *
     * @return
     */
    String printExceptionStackInfoInEnv();

}
