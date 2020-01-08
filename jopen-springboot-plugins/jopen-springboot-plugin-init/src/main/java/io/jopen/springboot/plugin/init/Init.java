package io.jopen.springboot.plugin.init;

import java.lang.annotation.*;

/**
 * 由{@link InitApplication}负责加载带有{@link Init}注解的类进行初始化
 * Spring容器启动需要初始化的类
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Init {

    /**
     * 初始化形式
     *
     * @return
     */
    InitMode initialization() default InitMode.STATIC_CODE_BLOCK;

    /**
     * 静态代码块是空格
     *
     * @return
     */
    String value() default "";

    /**
     * 初始化的方式（是执行静态代码块 或者是执行静态方法，或者是调用某个方法）
     */
    enum InitMode {

        // 静态代码块
        STATIC_CODE_BLOCK,

        // 静态方法
        STATIC_METHOD,

        // 成员方法
        MEMBER_METHOD;
    }
}
