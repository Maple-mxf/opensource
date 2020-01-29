package io.jopen.springboot.plugin.limit;

import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 流量控制 {@link BaseInterceptor}
 *
 * @author maxuefeng
 * @see Limiting
 */
@Component
public class FlowControl extends BaseInterceptor {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier(value = "limitScript")
    private DefaultRedisScript<Number> limitScript;

    //
    private LimitKeyProducer limitKeyProducer;

    /**
     * 当前拦截器的顺序
     */
    private int order;

    /**
     * 要拦截的路径
     */
    private String[] pathPatterns;

    /**
     * 要排除的路径
     */
    private String[] excludePathPatterns;


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String[] getPathPatterns() {
        return pathPatterns;
    }

    public void setPathPatterns(String[] pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public String[] getExcludePathPatterns() {
        return excludePathPatterns;
    }

    public void setExcludePathPatterns(String[] excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    public void setLimitKeyProducer(@NonNull LimitKeyProducer limitKeyProducer) {
        this.limitKeyProducer = limitKeyProducer;
    }

    public FlowControl() {
    }

    /**
     * {@link LimitKeyProducer#key(HttpServletRequest)} 此方法需要自定义  根据Request对象来生产key
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws IOException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取限流注解
        Limiting limiting = super.getApiServiceAnnotation(Limiting.class, handler);
        if (limiting != null) {
            // 获取客户端的限流Key
            String ip = limitKeyProducer.key(request);
            // 拼接key
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //
            List<String> keys = Collections.singletonList(ip + "-" + handlerMethod.getMethod().getName() + "-" + limiting.key());
            //  统计访问次数
            Number r = redisTemplate.execute(limitScript, keys, limiting.count(), limiting.time());
            //
            if (r != null && r.intValue() != 0 && r.intValue() <= limiting.count()) {
                return true;
            } else {
                // 返回错误消息
                throw new RuntimeException("访问过于频繁，请稍后再试！");
            }
        }
        return true;
    }
}
