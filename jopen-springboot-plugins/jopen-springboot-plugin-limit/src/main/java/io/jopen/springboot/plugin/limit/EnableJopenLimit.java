package io.jopen.springboot.plugin.limit;

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
@Import({FlowControl.class, LuaConfiguration.class})
public @interface EnableJopenLimit {
}
