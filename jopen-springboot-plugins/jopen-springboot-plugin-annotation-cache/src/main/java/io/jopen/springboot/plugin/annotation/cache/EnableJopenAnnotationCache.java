package io.jopen.springboot.plugin.annotation.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author maxuefeng
 * @since 2020/1/11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({BaseInterceptor.class})
@Deprecated
public @interface EnableJopenAnnotationCache {
}
