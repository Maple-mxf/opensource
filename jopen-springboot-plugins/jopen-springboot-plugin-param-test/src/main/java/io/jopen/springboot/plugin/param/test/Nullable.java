package io.jopen.springboot.plugin.param.test;

import java.lang.annotation.*;

/**
 * @author maxuefeng
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nullable {
}
