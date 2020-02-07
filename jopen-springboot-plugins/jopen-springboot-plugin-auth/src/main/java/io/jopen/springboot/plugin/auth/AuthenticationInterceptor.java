package io.jopen.springboot.plugin.auth;

import io.jopen.springboot.plugin.annotation.cache.BaseInterceptor;
import io.jopen.springboot.plugin.common.SpringContainer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.springframework.web.servlet.HandlerMapping.LOOKUP_PATH;

/**
 * 身份验证拦截器
 * {@link PathMatcher}
 * {@link AuthRegistration}
 * {@link CredentialFunction}
 *
 * @author maxuefeng
 */
@Component
public class AuthenticationInterceptor extends BaseInterceptor implements CommandLineRunner {

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
     * @see UrlPathHelper
     */
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

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

    /**
     *
     */
    private Class<? extends AuthMetadata> authMetadataType;

    public void setAuthMetadataType(@NonNull Class<? extends AuthMetadata> authMetadataType) {
        this.authMetadataType = authMetadataType;
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

    /**
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler  {@link org.springframework.web.method.HandlerMethod}
     * @return {@link Boolean} if true pass else throw a new RuntimeException
     * @see org.springframework.web.util.pattern.PathPattern
     * @see org.springframework.web.util.pattern.PathPattern
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Verify verify = super.getApiServiceAnnotation(Verify.class, handler);
        if (verify != null) {

            // 使用全局验证配置
            if (verify.usingGlobalConfig()) {
                // 获取请求地址
                String lookupPath = this.urlPathHelper.getLookupPathForRequest(request, LOOKUP_PATH);

                // 按照开发者设定的规则进行检测身份Token信息
                boolean passAuthentication = this.authRegistrations.stream()
                        .filter(authRegistration -> {
                            for (String pathPattern : authRegistration.getPathPatterns()) {
                                if (this.matches(pathPattern, lookupPath)) {
                                    return true;
                                }
                            }
                            throw new RuntimeException(verify.errMsg());
                        })
                        .anyMatch(authRegistration -> {
                            CredentialFunction credentialFunction = authRegistration.getCredentialFunction();
                            Credential credential = credentialFunction.apply(request);
                            checkupCredential(request, credential, verify);
                            return true;
                        });

                if (passAuthentication) {
                    return true;
                }
                throw new RuntimeException(verify.errMsg());
            }
            // 使用局部验证配置
            else {
                Class<? extends CredentialFunction> credentialFunctionType = verify.credentialFunctionType();
                // 如果无效  则需要抛出异常
                com.google.common.base.Verify.verify(!credentialFunctionType.equals(CredentialFunction.EmptyCredentialFunction.class),
                        "@Verify if not using global auth configuration;must be setup CredentialFunction implement Class");

                CredentialFunction credentialFunction;
                try {
                    credentialFunction = SpringContainer.getBean(credentialFunctionType);
                } catch (Exception ignored) {
                    throw new RuntimeException("@Verify if not using global auth configuration;must be inject CredentialFunction bean in Spring Container");
                }

                // 获取凭证对象
                Credential credential = credentialFunction.apply(request);
                checkupCredential(request, credential, verify);
                return true;
            }
        }
        return true;
    }

    private void checkupCredential(HttpServletRequest request, Credential credential, Verify verify) {
        if (!credential.getValid()) throw new RuntimeException("your account state is freeze");
        // 没有设定角色 || 或者设定了*号  任何角色都可以访问
        String[] requireAllowRoles = verify.role();
        if (requireAllowRoles.length == 0 || "*".equals(requireAllowRoles[0])) return;

        // 用户角色
        String[] roles = credential.getRoles();
        // 求两个数组的交集
        List<String> requireAllowRoleList = Arrays.asList(requireAllowRoles);
        if (Arrays.stream(roles).anyMatch(requireAllowRoleList::contains)) {
            request.setAttribute("credential", credential);
            return;
        }
        throw new RuntimeException(verify.errMsg());
    }


    /**
     * Determine a match for the given lookup path.
     *
     * @param pathPattern setup pathPattern
     * @param lookupPath  the current request path
     * @return {@code true} if the interceptor applies to the given request path
     */
    private boolean matches(String pathPattern, @NonNull String lookupPath) {
        return this.pathMatcher.match(pathPattern, lookupPath);
    }

    @Override
    public void run(String... args) {
        AuthMetadata authMetadataBean = SpringContainer.getBean(authMetadataType);
        this.authRegistrations = authMetadataBean.setupAuthRules();
    }
}
