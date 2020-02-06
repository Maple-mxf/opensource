package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxuefeng
 * @see CredentialFunction
 * @since 2020/2/6
 */
public abstract class AbstractBeBasedOnHeaderCredentialFunction extends AbstractCredentialFunction{

    /**
     * headerKey
     */
    private String headerKey;

    public AbstractBeBasedOnHeaderCredentialFunction(@NonNull String headerKey) {
        com.google.common.base.Verify.verify(!Strings.isNullOrEmpty(headerKey),
                "headerKey must be set up  require non null");
        this.headerKey = headerKey;
    }

    @NonNull
    protected Credential verify(@NonNull HttpServletRequest request) {
        String headerValue = request.getHeader(this.headerKey);
        if (Strings.isNullOrEmpty(headerValue)) return Credential.INVALID_CREDENTIAL;
        return mapHeaderValueToCredential(headerValue);
    }

    /**
     * @param headerValue base on {@link org.springframework.web.servlet.function.ServerRequest.Headers}
     *                    map to credential object instance
     */
    @NonNull
    public abstract Credential mapHeaderValueToCredential(@NonNull String headerValue);
}

