package io.jopen.springboot.plugin.quartz;

import java.lang.annotation.*;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobBean {
}
