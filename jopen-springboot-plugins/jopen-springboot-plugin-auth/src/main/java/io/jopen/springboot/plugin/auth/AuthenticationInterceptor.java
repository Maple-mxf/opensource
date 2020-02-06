package io.jopen.springboot.plugin.auth;

import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 身份验证拦截器
 * {@link PathMatcher}
 * {@link AuthRegistration}
 * {@link CredentialFunction}
 *
 * @author maxuefeng
 */
@Component
public class AuthenticationInterceptor extends BaseInterceptor {

    /**
     * @see PathMatcher
     * {@link AntPathMatcher}
     */
    private PathMatcher pathMatcher = new AntPathMatcher("/");

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
     * 认证的规则
     *
     * @see AuthRegistration
     */
    private Collection<AuthRegistration> authRegistrations;

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

    public void setAuthRegistrations(Collection<AuthRegistration> authRegistrations) {
        this.authRegistrations = authRegistrations;
    }

    /**
     * @param request
     * @param response
     * @param handler  {@link org.springframework.web.method.HandlerMethod}
     * @return
     * @throws Exception
     * @see org.springframework.web.util.pattern.PathPattern
     * @see org.springframework.web.util.pattern.PathPattern
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Verify verify = super.getApiServiceAnnotation(Verify.class, handler);
        if (verify != null) {
            // 获取请求地址
            String requestURI = request.getRequestURI();
            // 按照开发者设定的规则进行检测身份Token信息
            boolean passAuthentication = this.authRegistrations.stream()
                    .filter(authRegistration -> {
                        for (String pathPattern : authRegistration.getPathPatterns()) {
                            if (this.matches(pathPattern, requestURI)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .anyMatch(authRegistration -> {
                        CredentialFunction credentialFunction = authRegistration.getCredentialFunction();
                        Credential credential = credentialFunction.apply(request);
                        if (credential.isEmpty()) return false;
                        if (!credential.getValid()) return false;

                        // 没有设定角色 || 或者设定了*号  任何角色都可以访问
                        String[] requireAllowRoles = verify.role();
                        if (requireAllowRoles.length == 0 || "*".equals(requireAllowRoles[0])) return true;

                        // 用户角色
                        String[] roles = credential.getRoles();
                        // 求两个数组的交际
                        List<String> requireAllowRoleList = Arrays.asList(requireAllowRoles);
                        return Arrays.stream(roles).anyMatch(requireAllowRoleList::contains);
                    });

            if (passAuthentication) return true;
            throw new RuntimeException(verify.errMsg());
        }
        return true;
    }


    /**
     * Determine a match for the given lookup path.
     *
     * @param pathPattern setup pathPattern
     * @param lookupPath  the current request path
     * @return {@code true} if the interceptor applies to the given request path
     */
    public boolean matches(String pathPattern, @NonNull String lookupPath) {
        return this.pathMatcher.match(pathPattern, lookupPath);
    }
}
