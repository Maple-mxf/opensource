package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxuefeng
 * @see CredentialFunction
 * @since 2020/2/6
 */
public abstract class AbstractBeBasedOnURLCredentialFunction extends AbstractCredentialFunction {

    private String urlParamName;

    public AbstractBeBasedOnURLCredentialFunction(String urlParamName) {
        com.google.common.base.Verify.verify(!Strings.isNullOrEmpty(urlParamName),
                "headerKey must be set up  require non null");
        this.urlParamName = urlParamName;

    }

    @NonNull
    protected Credential verify(@NonNull HttpServletRequest request) {
        String queryString = request.getQueryString();
        return mapCookieToCredential(queryString);
    }

    /**
     * @param urlParamValue base on url param {@link HttpServletRequest#getQueryString()}
     */
    @NonNull
    abstract Credential mapCookieToCredential(@NonNull String urlParamValue);
}

