package io.jopen.springboot.plugin.param.test;

import java.lang.annotation.*;

/**
 * @author maxuefeng
 * @see NotNull
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckParamNotNull {
}
