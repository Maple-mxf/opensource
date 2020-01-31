package io.jopen.springboot.plugin.idempotency;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
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

    private IdempotentPluginConfiguration idempotentPluginConfiguration;

    private int order;
    private String[] includePath;
    private String[] excludePath;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String[] getIncludePath() {
        return includePath;
    }

    public void setIncludePath(String[] includePath) {
        this.includePath = includePath;
    }

    public String[] getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String[] excludePath) {
        this.excludePath = excludePath;
    }

    // Order
    // redis IO多路复用的意思只是acceptor是单线程的 而handler任然是多线程
    public TokenIdempotent(@NonNull RedisTemplate<String, Object> redisTemplate,
                           @NonNull IdempotentPluginConfiguration idempotentPluginConfiguration) {
        this.idempotentPluginConfiguration = idempotentPluginConfiguration;
        this.redisTemplate = redisTemplate;
        this.tokenKey = idempotentPluginConfiguration.getTokenKey();
        Preconditions.checkArgument(Strings.isNullOrEmpty(tokenKey), "token Key参数不可为空");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
