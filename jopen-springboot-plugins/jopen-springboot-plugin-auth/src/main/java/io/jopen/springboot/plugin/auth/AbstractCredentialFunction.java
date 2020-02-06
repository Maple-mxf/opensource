package io.jopen.springboot.plugin.auth;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxuefeng
 * @since 2020/2/6
 */
public abstract class AbstractCredentialFunction implements CredentialFunction {

    @Override
    public @NonNull Credential apply(@NonNull HttpServletRequest request) {
        return verify(request);
    }

    @NonNull
    protected abstract Credential verify(@NonNull HttpServletRequest request);
}
