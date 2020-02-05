package io.jopen.springboot.plugin.limit;

import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import io.jopen.springboot.plugin.common.SpringContainer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 流量控制 {@link BaseInterceptor}
 * <p>
 * SpringBoot Application 初始化完成 execute {@link CommandLineRunner#run(String...)}
 *
 * @author maxuefeng
 * @see Limiting
 */
@Component
public class FlowControl extends BaseInterceptor implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier(value = "limitScript")
    private DefaultRedisScript<Number> limitScript;

    /**
     * @see LimitKeyProducer  生产Key的Producer的实现类  开发者自定义
     */
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

    /**
     * 拉黑功能的控制器
     */
    private Keeper keeper;

    /**
     * 是否开启拉黑策略
     */
    private boolean enablePullBlack;

    /**
     * Keeper的Class类型对象
     */
    private Class<? extends Keeper> limitKeeperType;

    /**
     * 限流逻辑执行Function
     */
    private BiBiFunction<HttpServletRequest, HttpServletResponse, Object, Limiting, Boolean> runLimitFunction;

    public boolean getEnablePullBlack() {
        return this.enablePullBlack;
    }

    public void setEnablePullBlack(boolean enablePullBlack) {
        this.enablePullBlack = enablePullBlack;
    }

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

    public void setLimitKeeperType(@NonNull Class<? extends Keeper> limitKeeperType) {
        this.limitKeeperType = limitKeeperType;
    }

    public void setKeeper(@NonNull Keeper keeper) {
        this.keeper = keeper;
    }


    /**
     * {@link LimitKeyProducer#key(HttpServletRequest)} 此方法需要自定义  根据Request对象来生产key
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取限流注解
        Limiting limiting = super.getApiServiceAnnotation(Limiting.class, handler);
        if (limiting != null) {
            return this.runLimitFunction.apply(request, response, handler, limiting);
        }
        return true;
    }

    /**
     * 1 设定{@link Keeper}
     * 2 组装{@link FlowControl#runLimitFunction}
     *
     * @param args
     * @see SimpleKeeperImpl
     */
    @Override
    public void run(String... args) {
        this.keeper = SpringContainer.getBean(this.limitKeeperType);
        if (this.enablePullBlack) {
            this.runLimitFunction = (request, response, handler, limiting) -> {
                String limitKey = FlowControl.this.limitKeyProducer.key(request);
                // 黑名单操作
                Keeper.Info info = keeper.solicitingOpinions(limitKey);
                if (!info.isAllowAccess) {
                    throw new RuntimeException(info.errMsg);
                }
                // 拼接key
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                List<String> keys = Collections.singletonList(limitKey + "-" + handlerMethod.getMethod().getName() + "-" + limiting.key());

                //  统计访问次数
                Number r = redisTemplate.execute(limitScript, keys, limiting.count(), limiting.time());
                if (r != null && r.intValue() != 0 && r.intValue() <= limiting.count()) {
                    return true;
                } else {
                    // 记录违规操作
                    keeper.recordViolation(limitKey);
                    throw new RuntimeException("访问过于频繁，请稍后再试！");
                }
            };
        } else {
            this.runLimitFunction = (request, response, handler, limiting) -> {
                String limitKey = FlowControl.this.limitKeyProducer.key(request);
                // 拼接key
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                List<String> keys = Collections.singletonList(limitKey + "-" + handlerMethod.getMethod().getName() + "-" + limiting.key());

                //  统计访问次数
                Number r = redisTemplate.execute(limitScript, keys, limiting.count(), limiting.time());
                if (r != null && r.intValue() != 0 && r.intValue() <= limiting.count()) return true;
                else throw new RuntimeException("访问过于频繁，请稍后再试！");
            };
        }
    }
}
