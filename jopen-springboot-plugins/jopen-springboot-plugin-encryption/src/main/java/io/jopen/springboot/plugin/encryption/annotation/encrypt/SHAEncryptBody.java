package io.jopen.springboot.plugin.encryption.annotation.encrypt;


import io.jopen.springboot.plugin.encryption.enums.SHAEncryptType;

import java.lang.annotation.*;

/**
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SHAEncryptBody {

    SHAEncryptType value() default SHAEncryptType.SHA256;

}
