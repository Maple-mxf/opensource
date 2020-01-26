package io.jopen.springboot.plugin.auth;

import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [https://www.jianshu.com/p/e88d3f8151db]
 * 身份认证
 *
 * @author maxuefeng
 */
@Component
public class Authenticate extends BaseInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }
}
