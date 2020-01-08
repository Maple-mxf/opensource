package io.jopen.springboot.plugin.encryption.enums;

import lombok.AllArgsConstructor;

/**
 * <p>SHA加密类型</p>
 */
@AllArgsConstructor
public enum  SHAEncryptType {

    SHA224("sha-224"),
    SHA256("sha-256"),
    SHA384("sha-384"),
    SHA512("sha-512"),
    ;

    public String value;


}
