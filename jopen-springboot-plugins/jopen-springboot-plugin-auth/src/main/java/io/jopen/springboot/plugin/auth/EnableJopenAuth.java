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
@Import({Authenticate.class, AuthConfiguration.class})
public @interface EnableJopenAuth {

    // public String
    TokenLocation location() default TokenLocation.HEADER;

    enum TokenLocation {
        HEADER,
        COOKIE
    }
}
