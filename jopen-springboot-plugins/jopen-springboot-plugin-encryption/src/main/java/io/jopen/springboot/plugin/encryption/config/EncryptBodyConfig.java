package io.jopen.springboot.plugin.encryption.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>加密数据配置读取类</p>
 * <p>在SpringBoot项目中的application.yml中添加配置信息即可</p>
 * <pre>
 *     encrypt:
 *      body:
 *       aes-key: 12345678 # AES加密秘钥
 *       des-key: 12345678 # DES加密秘钥
 * </pre>
 */
@ConfigurationProperties(prefix = "encrypt.body")
@Configuration
@Data
public class EncryptBodyConfig {

    private String aesKey;

    private String desKey;

    private String encoding = "UTF-8";



}
