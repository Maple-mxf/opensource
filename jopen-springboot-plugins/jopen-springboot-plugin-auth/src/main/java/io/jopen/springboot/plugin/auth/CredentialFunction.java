package io.jopen.springboot.plugin.auth;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxuefeng
 * @see java.util.function.Function
 * @see Credential
 * @since 2020/2/4
 */
@FunctionalInterface
public interface CredentialFunction {

    /**
     * @param request {@link HttpServletRequest}
     * @return {@link Credential#getValid()}
     */
    @NonNull
    Credential apply(@NonNull HttpServletRequest request);

    class EmptyCredentialFunction implements CredentialFunction {
        @Override
        public @NonNull Credential apply(@NonNull HttpServletRequest request) {
            return Credential.INVALID_CREDENTIAL;
        }
    }
}
