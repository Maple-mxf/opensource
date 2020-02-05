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
     * 返回一个空的Empty的对象
     */
    Credential EMPTY_CREDENTIAL = new Credential(true);

    /**
     * @param request {@link HttpServletRequest}
     * @return {@link Credential#empty}
     */
    @NonNull
    Credential apply(@NonNull HttpServletRequest request);
}
