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

    /**
     * @return the implement {@link TokenProducer} class path example:"io.jopen.springboot.plugin.auth.DefaultTokenProducer"
     * @see TokenProducer
     */
    String tokenProducerClassPath() default "";


}
