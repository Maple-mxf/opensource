package io.jopen.springboot.plugin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author maxuefeng
 * @since 2020/1/26
 */
@Configuration
@Component
public class AuthPluginConfiguration implements ImportAware, WebMvcConfigurer {

    private Authenticate authenticate;

    @Autowired
    public AuthPluginConfiguration(Authenticate authenticate) {
        this.authenticate = authenticate;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticate)
                .addPathPatterns(authenticate.getPathPatterns())
                .excludePathPatterns(authenticate.getExcludePathPatterns())
                .order(authenticate.getOrder());
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

        // 获取目标Class对象
        Class<? extends TokenProducer> tokenFunctionType = enableAuth.getClass("tokenFunctionType");
        TokenProducer tokenProducer;
        try {
            tokenProducer = tokenFunctionType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }

        //
        this.authenticate.setTokenProducer(tokenProducer);

        String[] pathPatterns = enableAuth.getStringArray("pathPatterns");
        String[] excludePathPatterns = enableAuth.getStringArray("excludePathPattern");
        int order = enableAuth.getNumber("order");

        Class<? extends AuthMetadata> authMetadataType = enableAuth.getClass("authMetadataType");
        AuthMetadata authMetadataInstance = null;
        try {
            authMetadataInstance = authMetadataType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


        Collection<AuthRule> authRules = authMetadataInstance.setupAuthRules();

        for (AuthRule authRule : authRules) {
            System.err.println(authRule.getPathPatterns());
            Function<HttpServletRequest, ? extends CredentialFunction> credentialFunction
                    = authRule.getCredentialFunction();

            credentialFunction.apply();
        }


        // 设置当前对象的拦截器的顺序
        this.authenticate.setPathPatterns(pathPatterns);
        this.authenticate.setExcludePathPatterns(excludePathPatterns);
        this.authenticate.setOrder(order);
    }
}
