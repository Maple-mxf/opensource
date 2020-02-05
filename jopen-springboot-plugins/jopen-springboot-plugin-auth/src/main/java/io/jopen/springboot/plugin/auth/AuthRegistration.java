package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.util.AntPathMatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * @author maxuefeng
 * @since 2020/2/4
 */
public final class AuthRegistration {

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
    private CredentialFunction credentialFunction;

    private AuthRegistration() {
    }

    public Set<String> getPathPatterns() {
        return this.pathPatterns;
    }

    public CredentialFunction getCredentialFunction() {
        return this.credentialFunction;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AuthRegistration authRegistration;

        Builder() {
            authRegistration = new AuthRegistration();
        }

        /**
         * 添加需要认证路径
         *
         * @param authPath
         * @return
         */
        public Builder addAuthPath(@NonNull String authPath) {
            this.authRegistration.pathPatterns.add(authPath);
            return this;
        }

        /**
         * 设定检测规则
         *
         * @param credentialFunction {@link CredentialFunction}
         * @return {@link Builder}
         */
        public Builder setupCredentialFunction(@NonNull CredentialFunction credentialFunction) {
            this.authRegistration.credentialFunction = credentialFunction;
            return this;
        }

        /**
         * @return {@link AuthRegistration}
         * @see org.springframework.web.util.pattern.PathPattern
         */
        public AuthRegistration build() {
            // 检测Path
            if (authRegistration.pathPatterns.size() == 0) {
                throw new RuntimeException("AuthRegistration auth path must be setup");
            }
            // 检测path规则
            for (String path : authRegistration.pathPatterns) {
                if (Strings.isNullOrEmpty(path) || !path.startsWith("/")) {
                    throw new RuntimeException(String.format("Path %s must be not null and must be start with '/' ", path));
                }
            }
            return this.authRegistration;
        }
    }


}

