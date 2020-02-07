package io.jopen.springboot.plugin.auth;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;

/**
 * 认证的元信息
 *
 * @author maxuefeng
 * @see AuthRegistration
 * @since 2020/2/4
 */
public interface AuthMetadata {

    /**
     * @return auth roles {@link AuthRegistration}
     */
    Collection<AuthRegistration> setupAuthRules();

    /**
     * 默认的空对象实现
     */
    class EmptyAuthMetadata implements AuthMetadata {
        @Override
        public Collection<AuthRegistration> setupAuthRules() {
            return ImmutableSet.of();
        }
    }
}
