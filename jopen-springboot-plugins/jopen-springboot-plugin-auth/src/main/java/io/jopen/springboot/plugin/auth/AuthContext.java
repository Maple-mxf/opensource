package io.jopen.springboot.plugin.auth;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxuefeng
 * @see AuthenticationInterceptor#saveCredential(HttpServletRequest, Credential)
 * @since 2020/2/12
 */
@Component
public class AuthContext {

    public static final String CREDENTIAL_KEY = "jopen_auth_credential";

    public Credential getCredential(HttpServletRequest request) {
        return (Credential) request.getAttribute(CREDENTIAL_KEY);
    }

    public void setCredential(HttpServletRequest request, Credential credential) {
        request.setAttribute(CREDENTIAL_KEY, credential);
    }
}
