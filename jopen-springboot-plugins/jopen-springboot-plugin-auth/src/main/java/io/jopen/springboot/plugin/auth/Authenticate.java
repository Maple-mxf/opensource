package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 身份验证拦截器
 *
 * @author maxuefeng
 */
@Component
public class Authenticate extends BaseInterceptor {

    private TokenProducer tokenProducer;

    public void setTokenProducer(@NonNull TokenProducer tokenProducer) {
        this.tokenProducer = tokenProducer;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Verify verify = super.getApiServiceAnnotation(Verify.class, handler);
        return Optional.ofNullable(verify)
                .map(m -> {
                    String tokenKey;
                    Object tokenValue = null;
                    boolean result = (tokenProducer == null
                            || Strings.isNullOrEmpty((tokenKey = this.tokenProducer.parseRequestTokenKey(request)))
                            || (tokenValue = this.tokenProducer.getVerifyToken(tokenKey)) == null);

                    // 没有授权信息
                    if (result) throw new RuntimeException(m.errMsg());

                    // 检测角色
                    String[] permissionRoles = m.role();
                    if (permissionRoles.length == 0) return false;
                    if ("*".equals(permissionRoles[0])) return true;
                    //
                    List<String> roles = this.tokenProducer.getRoles(tokenValue);
                    boolean hasAllowRole = Arrays.stream(permissionRoles) .anyMatch(roles::contains);

                    if (hasAllowRole) return true;
                    else throw new RuntimeException(m.errMsg());
                })
                .orElse(true);
    }
}
