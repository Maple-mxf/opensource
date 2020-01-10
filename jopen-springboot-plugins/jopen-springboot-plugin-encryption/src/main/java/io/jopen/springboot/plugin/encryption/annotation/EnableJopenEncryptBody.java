package io.jopen.springboot.plugin.encryption.annotation;

import io.jopen.springboot.plugin.encryption.advice.DecryptRequestBodyAdvice;
import io.jopen.springboot.plugin.encryption.advice.EncryptResponseBodyAdvice;
import io.jopen.springboot.plugin.encryption.config.EncryptBodyConfig;
import io.jopen.springboot.plugin.encryption.config.HttpConverterConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>启动类</p>
 * <p>使用方法：在SpringBoot的Application启动类上添加此注解即可</p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EncryptBodyConfig.class,
        HttpConverterConfig.class,
        EncryptResponseBodyAdvice.class,
        DecryptRequestBodyAdvice.class})
public @interface EnableJopenEncryptBody {
}
