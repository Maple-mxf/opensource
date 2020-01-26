package io.jopen.springboot.plugin.auth;

import java.lang.annotation.*;

/**
 * 需要登陆验证
 *
 * @author maxuefeng
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Verify {
}
