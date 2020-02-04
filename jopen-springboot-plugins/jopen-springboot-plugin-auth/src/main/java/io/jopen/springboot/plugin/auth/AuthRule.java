package io.jopen.springboot.plugin.auth;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
     */
    private Set<String> rulePath = new HashSet<>();

    /**
     * 认证身份凭证生产者
     */
    private Function<HttpServletRequest, ? extends CredentialFunction> credentialFunction;

    private AuthRule() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AuthRule authRule;

        Builder() {
            authRule = new AuthRule();
        }

        public Builder setupAuthPath(@NonNull String... authPaths) {
            this.authRule.rulePath.addAll(Arrays.asList(authPaths));
            return this;
        }

        public Builder setupCredentialFunction(@NonNull Function<HttpServletRequest, ? extends CredentialFunction> credentialFunction) {
            this.authRule.credentialFunction = credentialFunction;
            return this;
        }

        public AuthRule build() {
            // 检测Path
            if (authRule.rulePath.size() == 0) {
                throw new RuntimeException("AuthRule auth path must be setup");
            }
            // 检测path规则
            for (String path : authRule.rulePath) {
                if (Strings.isNullOrEmpty(path) || !path.startsWith("/")) {
                    throw new RuntimeException(String.format("Path %s must be not null and must be start with '/' ", path));
                }
            }
            return this.authRule;
        }
    }
}
