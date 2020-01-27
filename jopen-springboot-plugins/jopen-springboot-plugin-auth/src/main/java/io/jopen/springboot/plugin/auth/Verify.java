package io.jopen.springboot.plugin.auth;

import java.lang.annotation.*;

/**
 * 需要登陆验证
 *
 * @author maxuefeng
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Verify {

    /**
     * 默认任何角色都可以访问接口的角色
     *
     * @return 角色数组
     */
    String[] role() default {"*"};

    /**
     * @return 拦截之后的错误信息
     */
    String errMsg() default "您没有访问此接口的权限";
}
