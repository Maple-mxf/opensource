package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 身份验证拦截器
 *
 * @author maxuefeng
 */
@Component
public class Authenticate extends BaseInterceptor {

    private TokenProducer tokenProducer;

    public void setTokenProducer(@NonNull TokenProducer tokenProducer) {
        this.tokenProducer = tokenProducer;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Verify verify = super.getMark(Verify.class, handler);
        return Optional.ofNullable(verify)
                .map(m -> {
                    String tokenKey;
                    return tokenProducer != null
                            && !Strings.isNullOrEmpty((tokenKey = this.tokenProducer.parseRequestTokenKey(request)))
                            && this.tokenProducer.getVerifyToken(tokenKey) != null;
                })
                .orElse(true);
    }
}
