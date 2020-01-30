package io.jopen.springboot.plugin.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author maxuefeng
 * @since 2020/1/29
 */
@Configuration
public class InitPluginConfiguration implements ImportAware {

    @Autowired
    private InitApplication initApplication;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes enableInit = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableJopenInit.class.getName(), false));

        if (enableInit == null) {
            throw new IllegalArgumentException(
                    "@EnableInit is not present on importing class " + importMetadata.getClassName());
        }

        Class<?> runSpringBootType = enableInit.getClass("runSpringBootType");
        this.initApplication.setBasePackage(runSpringBootType.getPackage().getName());
    }
}
