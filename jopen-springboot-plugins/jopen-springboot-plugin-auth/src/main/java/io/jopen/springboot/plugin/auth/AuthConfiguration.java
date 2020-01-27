package io.jopen.springboot.plugin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
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


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticate).addPathPatterns("/**");
    }

    /**
     * @param importMetadata 导入的元数据信息
     */
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes enableAuth = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableJopenAuth.class.getName(), false));

        if (enableAuth == null) {
            throw new IllegalArgumentException(
                    "@EnableAuth is not present on importing class " + importMetadata.getClassName());
        }

        String tokenProducerClassPath = enableAuth.getString("tokenProducerClassPath");
        Class type;
        try {
            type = Class.forName(tokenProducerClassPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("class not found %s", tokenProducerClassPath));
        }
        TokenProducer tokenProducer;
        try {
            tokenProducer = (TokenProducer) type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getCause());
        }
        this.authenticate.setTokenProducer(tokenProducer);
    }
}
