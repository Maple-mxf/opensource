package io.jopen.springboot.plugin.param.test;

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
@Import({ParameterTestAction.class})
public @interface EnableJopenParamTest {
}
