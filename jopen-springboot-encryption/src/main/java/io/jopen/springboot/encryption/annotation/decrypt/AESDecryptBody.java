package io.jopen.springboot.encryption.annotation.decrypt;

import java.lang.annotation.*;

/**
 * @see DecryptBody
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AESDecryptBody {

    String otherKey() default "";

}
