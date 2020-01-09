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
@Import({InitApplication.class})
public @interface EnableJopenInit {
}
