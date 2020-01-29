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

    /**
     * 客户端自定义获取token操作
     *
     * @see TokenProducer
     */
    private TokenProducer tokenProducer;

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
                    boolean hasAllowRole = Arrays.stream(permissionRoles).anyMatch(roles::contains);

                    if (hasAllowRole) return true;
                    else throw new RuntimeException(m.errMsg());
                })
                .orElse(true);
    }
}
