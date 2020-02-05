package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * @author maxuefeng
 * @since 2020/2/4
 */
public final class AuthRule {

    /**
     * 规则路径
     *
     * @see org.springframework.util.PathMatcher
     * @see AntPathMatcher
     */
    private Set<String> pathPatterns = new HashSet<>();

    /**
     * 认证身份凭证生产者
     *
     * @see Function
     */
    private Function<HttpServletRequest, ? extends CredentialFunction> credentialFunction;

    private AuthRule() {
    }

    public Set<String> getPathPatterns() {
        return this.pathPatterns;
    }

    public Function<HttpServletRequest, ? extends CredentialFunction> getCredentialFunction() {
        return this.credentialFunction;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AuthRule authRule;

        Builder() {
            authRule = new AuthRule();
        }

        /**
         * 添加需要认证路径
         *
         * @param authPath
         * @return
         */
        public Builder addAuthPath(@NonNull String authPath) {
            this.authRule.pathPatterns.add(authPath);
            return this;
        }

        /**
         * 设定检测规则
         *
         * @param credentialFunction
         * @return
         */
        public Builder setupCredentialFunction(@NonNull Function<HttpServletRequest, ? extends CredentialFunction> credentialFunction) {
            this.authRule.credentialFunction = credentialFunction;
            return this;
        }

        public AuthRule build() {
            // 检测Path
            if (authRule.pathPatterns.size() == 0) {
                throw new RuntimeException("AuthRule auth path must be setup");
            }
            // 检测path规则
            for (String path : authRule.pathPatterns) {
                if (Strings.isNullOrEmpty(path) || !path.startsWith("/")) {
                    throw new RuntimeException(String.format("Path %s must be not null and must be start with '/' ", path));
                }
            }
            return this.authRule;
        }
    }
}
