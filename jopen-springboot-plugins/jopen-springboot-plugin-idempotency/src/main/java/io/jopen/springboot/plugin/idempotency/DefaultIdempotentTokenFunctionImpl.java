package io.jopen.springboot.plugin.idempotency;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author maxuefeng
 * @see IdempotentTokenFunction
 * @since 2020/2/7
 */
@Component
public class DefaultIdempotentTokenFunctionImpl implements IdempotentTokenFunction {

    private String tokenKey;
    private TokenLocation tokenLocation;

    public void setTokenKey(@NonNull String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public void setTokenLocation(TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
    }

    @Override
    public String setupTokenKey() {
        return this.tokenKey;
    }

    @Override
    public TokenLocation setupTokenLocation() {
        return this.tokenLocation;
    }
}
