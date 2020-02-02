package io.jopen.springboot.plugin.idempotency;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@Component
public class TokenIdempotent extends BaseInterceptor {

    private String tokenKey;

    private RedisTemplate<String, Object> redisTemplate;

    // Order
    // redis IO多路复用的意思只是acceptor是单线程的 而handler任然是多线程
    @Autowired
    public TokenIdempotent(@NonNull RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setTokenKey(String tokenKey){
        this.tokenKey = tokenKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取注解
        ApiIdempotent apiIdempotent = super.getApiServiceAnnotation(ApiIdempotent.class, handler);
        if (apiIdempotent == null) {
            return true;
        }

        // 否则此接口属于幂等性要求的接口
        // 获取Header中的redis中Key
        String tokenValue = request.getHeader(tokenKey);
        Preconditions.checkArgument(Strings.isNullOrEmpty(tokenValue), "幂等性的key值不可为空");
        Boolean exist = redisTemplate.hasKey(tokenKey);

        if (exist != null && exist) {
            return true;
        }
        throw new RuntimeException("重复请求");
    }
}
