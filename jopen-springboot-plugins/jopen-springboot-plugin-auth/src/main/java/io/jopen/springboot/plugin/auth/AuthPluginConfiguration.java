package io.jopen.springboot.plugin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author maxuefeng
 * @since 2020/1/26
 */
@Configuration
public class AuthPluginConfiguration implements ImportAware, WebMvcConfigurer {

    @Autowired
    private Authenticate authenticate;
    private InterceptorRegistration interceptorRegistration;


    public void addInterceptors(InterceptorRegistry registry) {
        interceptorRegistration = registry.addInterceptor(authenticate).addPathPatterns("/**");
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


        String[] pathPatterns = enableAuth.getStringArray("pathPatterns");
        String[] excludePathPatterns = enableAuth.getStringArray("excludePathPattern");
        int order = enableAuth.getNumber("order");

        // 设置当前对象的拦截器的顺序
        this.interceptorRegistration.addPathPatterns(Arrays.asList(pathPatterns));
        this.interceptorRegistration.excludePathPatterns(Arrays.asList(excludePathPatterns));
        this.interceptorRegistration.order(order);
    }
}
