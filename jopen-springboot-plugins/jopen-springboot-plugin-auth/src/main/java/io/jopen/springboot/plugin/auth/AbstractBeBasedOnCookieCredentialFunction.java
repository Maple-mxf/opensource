package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @see CredentialFunction
 * @since 2020/2/6
 */
public abstract class AbstractBeBasedOnCookieCredentialFunction extends AbstractCredentialFunction {

    /**
     * {@link Cookie}
     */
    private String cookieName;

    public AbstractBeBasedOnCookieCredentialFunction(String cookieName) {
        com.google.common.base.Verify.verify(!Strings.isNullOrEmpty(cookieName), "cookieName must be set up  require non null");
        this.cookieName = cookieName;
    }

    @NonNull
    protected Credential verify(@NonNull HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return Credential.INVALID_CREDENTIAL;
        return Stream.of(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst()
                .map(this::mapCookieToCredential)
                .orElse(Credential.INVALID_CREDENTIAL);
    }

    /**
     * @param cookie base on {@link Cookie} map to credential object instance
     */
    @NonNull
    public abstract Credential mapCookieToCredential(@NonNull Cookie cookie);
}

