package io.jopen.springboot.plugin.encryption.annotation.decrypt;

import java.lang.annotation.*;

/**
 * @see DecryptBody
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DESDecryptBody {

    String otherKey() default "";

}
