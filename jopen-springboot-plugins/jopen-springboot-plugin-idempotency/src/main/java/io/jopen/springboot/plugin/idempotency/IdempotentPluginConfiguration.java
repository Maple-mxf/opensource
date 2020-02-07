package io.jopen.springboot.plugin.idempotency;

import com.google.common.base.Strings;
import com.google.common.base.Verify;
import com.google.common.collect.ImmutableMap;
import io.jopen.springboot.plugin.common.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @author maxuefeng
 * @see ApiIdempotent
 * @since 2020/1/31
 */
@Configuration
@RestController
@RequestMapping(value = "/jopen-idempotency")
public class IdempotentPluginConfiguration implements ImportAware, WebMvcConfigurer {

    private RedisTemplate<String, Object> redisTemplate;

    private TokenIdempotentInterceptor tokenIdempotentInterceptor;

    private DefaultIdempotentTokenFunctionImpl defaultIdempotentTokenFunctionImpl;

    @Autowired
    public IdempotentPluginConfiguration(RedisTemplate<String, Object> redisTemplate,
                                         TokenIdempotentInterceptor tokenIdempotentInterceptor,
                                         DefaultIdempotentTokenFunctionImpl defaultIdempotentTokenFunctionImpl
    ) {
        this.redisTemplate = redisTemplate;
        this.tokenIdempotentInterceptor = tokenIdempotentInterceptor;
        this.defaultIdempotentTokenFunctionImpl = defaultIdempotentTokenFunctionImpl;
    }

    @RequestMapping(value = "/getIdempotentToken", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ImmutableMap<String, Object> getIdempotentToken() {
        String idempotentToken = IDUtil.id();
        redisTemplate.opsForValue().set(idempotentToken, "1");
        redisTemplate.expire(idempotentToken, 5, TimeUnit.SECONDS);
        return ImmutableMap.of("idempotentToken", idempotentToken);
    }


    /**
     * @param registry
     * @see org.springframework.context.ConfigurableApplicationContext
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册
        registry.addInterceptor(tokenIdempotentInterceptor)
                .order(tokenIdempotentInterceptor.getOrder())
                .addPathPatterns(tokenIdempotentInterceptor.getIncludePathPatterns())
                .excludePathPatterns(tokenIdempotentInterceptor.getExcludePathPatterns());
    }

    /**
     * 这个属于开发者自定义的导入的元信息
     * <p>
     * 使用实例：
     *
     * @param importMetadata
     * @see com.fasterxml.jackson.annotation.JsonInclude
     */
    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {

        AnnotationAttributes enableIdempotent = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableJopenIdempotent.class.getName(), false));

        if (enableIdempotent == null) {
            throw new IllegalArgumentException(
                    "@EnableJopenIdempotent is not present on importing class " + importMetadata.getClassName());
        }

        // 获取注解的元素属性

        //
        String idempotentTokenKey = enableIdempotent.getString("idempotentTokenKey");
        Verify.verify(!Strings.isNullOrEmpty(idempotentTokenKey), "@EnableJopenIdempotent idempotentTokenKey require non null");

        // token Location
        Enum<TokenLocation> idempotentEnum = enableIdempotent.getEnum("idempotentTokenLocation");
        TokenLocation tokenLocation = (TokenLocation) idempotentEnum;

        // order
        int order = enableIdempotent.getNumber("order");
        // includePath
        String[] includePathPatterns = enableIdempotent.getStringArray("includePath");
        // excludePath
        String[] excludePathPatterns = enableIdempotent.getStringArray("excludePath");

        this.tokenIdempotentInterceptor.setOrder(order);
        this.tokenIdempotentInterceptor.setIncludePathPatterns(includePathPatterns);
        this.tokenIdempotentInterceptor.setExcludePathPatterns(excludePathPatterns);
        this.defaultIdempotentTokenFunctionImpl.setTokenKey(idempotentTokenKey);
        this.defaultIdempotentTokenFunctionImpl.setTokenLocation(tokenLocation);
    }

    public static void main(String[] args) {
    }

}
