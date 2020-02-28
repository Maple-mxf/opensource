package io.jopen.springboot.plugin.idempotency;

import com.google.common.base.Strings;
import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@Component
public class TokenIdempotentInterceptor extends BaseInterceptor {

    /**
     *
     */
    private int order;

    /**
     *
     */
    private String[] includePathPatterns;

    /**
     *
     */
    private String[] excludePathPatterns;

    /**
     * 默认的策略
     *
     * @see IdempotentTokenFunction
     */
    private DefaultIdempotentTokenFunctionImpl defaultIdempotentTokenFunctionImpl;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String[] getIncludePathPatterns() {
        return includePathPatterns;
    }

    public void setIncludePathPatterns(String[] includePathPatterns) {
        this.includePathPatterns = includePathPatterns;
    }

    public String[] getExcludePathPatterns() {
        return excludePathPatterns;
    }

    public void setExcludePathPatterns(String[] excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    private RedisTemplate<String, Object> redisTemplate;


    // Order
    // redis IO多路复用的意思只是acceptor是单线程的 而handler任然是多线程
    @Autowired
    public TokenIdempotentInterceptor(@NonNull RedisTemplate<String, Object> redisTemplate,
                                      @NonNull DefaultIdempotentTokenFunctionImpl defaultIdempotentTokenFunctionImpl
    ) {
        this.redisTemplate = redisTemplate;
        this.defaultIdempotentTokenFunctionImpl = defaultIdempotentTokenFunctionImpl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取注解
        ApiIdempotent apiIdempotent = super.getApiServiceAnnotation(ApiIdempotent.class, handler);
        if (apiIdempotent == null) return true;

        // 使用全局配置
        String idempotentTokenKey;
        TokenLocation tokenLocation;
        if (apiIdempotent.usingGlobalConfig()) {
            idempotentTokenKey = this.defaultIdempotentTokenFunctionImpl.setupTokenKey();
            tokenLocation = this.defaultIdempotentTokenFunctionImpl.setupTokenLocation();
        } else {
            idempotentTokenKey = apiIdempotent.idempotentTokenKey();
            tokenLocation = apiIdempotent.idempotentTokenLocation();
        }
        com.google.common.base.Verify.verify(!Strings.isNullOrEmpty(idempotentTokenKey), "idempotentTokenKey require non null");
        com.google.common.base.Verify.verify(tokenLocation != null, "tokenLocation require non null");

        //
        String tokenValue = null;

        if (TokenLocation.HEADER.equals(tokenLocation)) {
            tokenValue = request.getHeader(idempotentTokenKey);
        } else if (TokenLocation.COOKIE.equals(tokenLocation)) {
            tokenValue = Optional.ofNullable(request.getCookies())
                    .filter(cookies -> cookies.length > 0)
                    .map(cookies -> Stream.of(cookies).filter(cookie -> idempotentTokenKey.equals(cookie.getName())).findFirst().orElse(null))
                    .map(Cookie::getValue)
                    .orElse(null);
        } else if (TokenLocation.URL_PARAM.equals(tokenLocation)) {
            String[] paramValue = request.getParameterMap().get(idempotentTokenKey);
            if (paramValue.length > 0) tokenValue = paramValue[0];
        } else {
            throw new RuntimeException("TokenLocation value error");
        }
        com.google.common.base.Verify.verify(!Strings.isNullOrEmpty(tokenValue), "请求缺失幂等性参数");
        boolean hasKey = redisTemplate.hasKey(tokenValue);
        if (hasKey) {
            redisTemplate.delete(tokenValue);
            return true;
        }
        throw new RuntimeException("重复请求");
    }

    public static void main(String[] args) {
        String tokenValue = null;
        com.google.common.base.Verify.verify(!Strings.isNullOrEmpty(tokenValue), "请求缺失幂等性参数");
    }
}
