package io.jopen.springboot.plugin.idempotency;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import io.jopen.springboot.plugin.common.IDUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author maxuefeng
 * @see ApiIdempotent
 * @since 2020/1/31
 */
@Configuration
@RestController
@RequestMapping(value = "/jopen-idempotency")
public class IdempotentPluginConfiguration implements ImportAware {

    private String tokenKey;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/getIdempotentToken", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ImmutableMap<String, Object> getIdempotentToken() {
        String idempotentToken = IDUtil.id();
        redisTemplate.opsForValue().set(idempotentToken, "1");
        redisTemplate.expire(idempotentToken, 5, TimeUnit.SECONDS);
        return ImmutableMap.of("idempotentToken", idempotentToken);
    }

    /**
     * 这个属于开发者自定义的导入的元信息
     * <p>
     * 使用实例：
     *
     * @param importMetadata
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
        Class<? extends IdempotentTokenProducer> type = enableIdempotent.getClass("idempotentTokenProducerType");
        try {
            IdempotentTokenProducer idempotentTokenProducer = type.newInstance();
            String idempotentToken = idempotentTokenProducer.applyIdempotentToken();

            // 检测空值
            if (Strings.isNullOrEmpty(idempotentToken)) {
                throw new RuntimeException("token key not set ");
            }

            // 设定tokenKey
            this.tokenKey = idempotentToken;


        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public String getTokenKey() {
        return this.tokenKey;
    }
}
