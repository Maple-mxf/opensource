package io.jopen.springboot.plugin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author maxuefeng
 * @since 2020/1/26
 */
@Configuration
public class AuthConfiguration implements ImportAware, WebMvcConfigurer {

    @Autowired
    private Authenticate authenticate;

    @Nullable
    private AnnotationAttributes enableAuth;


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticate);
    }

    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableAuth = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableJopenAuth.class.getName(), false));

        if (this.enableAuth == null) {
            throw new IllegalArgumentException(
                    "@EnableAuth is not present on importing class " + importMetadata.getClassName());
        }
    }
}
