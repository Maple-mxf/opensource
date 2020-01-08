package io.jopen.springboot.plugin.encryption.annotation.encrypt;

import java.lang.annotation.*;

/**
 * @see EncryptBody
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RSAEncryptBody {
}
