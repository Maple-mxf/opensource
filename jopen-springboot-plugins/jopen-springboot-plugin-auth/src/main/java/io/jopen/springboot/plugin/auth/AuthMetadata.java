package io.jopen.springboot.plugin.auth;

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
}
