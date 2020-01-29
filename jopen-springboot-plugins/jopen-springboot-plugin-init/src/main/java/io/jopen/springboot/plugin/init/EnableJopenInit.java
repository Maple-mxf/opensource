package io.jopen.springboot.plugin.init;

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
@Import({InitApplication.class, InitPluginConfiguration.class})
public @interface EnableJopenInit {

    /**
     * 当前SpringBoot运行的基包
     *
     * @return 基包的位置 比如 io.jopen.springboot.plugin.init
     */
    String basePackage();
}
