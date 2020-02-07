package io.jopen.springboot.plugin.idempotency;

import java.lang.annotation.*;

/**
 * 基于redis的幂等性接口的插件保证
 * <p>
 * TODO  注意  此注解仅限于method级别使用
 * 为了保证更原子化的接口安全实现
 * <p>
 * 针对于金融领域的接口 意指多个点击操作都不会造成多个结果的产生  只允许产生一个结果
 * <p>
 * 比如A下单买东西，多次点击下单，可能由于网络的故障或者网络延迟（或者tcp网络丢包）
 * 都不能造成一次下单  多次付款的场景  TODO  用户会哭的
 *
 * @author maxuefeng
 * @since 2020/1/31
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiIdempotent {

    /**
     * @return true or false
     * @see EnableJopenIdempotent#idempotentTokenKey()
     * @see EnableJopenIdempotent#idempotentTokenLocation()
     */
    boolean usingGlobalConfig() default true;

    /**
     * 拦截器获取幂等性token的Key
     *
     * @return {@link javax.servlet.http.HttpServletRequest}
     */
    String idempotentTokenKey() default "";

    /**
     * 从什么位置获取幂等性token
     *
     * @return
     */
    TokenLocation idempotentTokenLocation() default TokenLocation.HEADER;
}
