package io.jopen.springboot.plugin.common;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启SpringContainer工具类
 *
 * @author maxuefeng
 * @since 2020/1/27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringContainer.class})
public @interface EnableJopenCommon {
}
