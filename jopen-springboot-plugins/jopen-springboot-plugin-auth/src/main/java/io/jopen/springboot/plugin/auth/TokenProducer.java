package io.jopen.springboot.plugin.auth;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxuefeng
 * @since 2020/1/27
 */
public abstract class TokenProducer {

    /*自定义无参数构造函数*/
    protected TokenProducer() {
    }

    /**
     * 获取token 开发者自定义操作
     *
     * @return tokenKey
     */
    @Nullable
    public abstract String parseRequestTokenKey(HttpServletRequest request);

    /**
     * @param tokenKey {@link TokenProducer#parseRequestTokenKey(HttpServletRequest)}
     * @param <U>      verify value
     * @return storage cache token verify info
     */
    @Nullable
    public abstract <U> U getVerifyToken(@NonNull String tokenKey);
}
