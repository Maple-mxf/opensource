package io.jopen.springboot.plugin.auth;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @since 2020/2/7
 */
public class AuthException extends RuntimeException {

    public AuthException(@NonNull String errMsg) {
        super(errMsg);
    }
}
