package io.jopen.springboot.plugin.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * @author maxuefeng
 * @see com.google.common.eventbus.EventBus
 * @since 2020/1/30
 */
@Configuration
@Component
public class CommonPluginConfiguration implements ImportAware {

    // @Autowired
    // private Environment env;

    // private boolean printExceptionStack = false;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        /*AnnotationAttributes enableCommon = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableJopenCommon.class.getName(), false));

        if (enableCommon == null) {
            throw new IllegalArgumentException(
                    "@EnableCommon is not present on importing class " + importMetadata.getClassName());
        }

        // 配置是否打印全局异常
        String[] printExceptionStackEnvs = enableCommon.getStringArray("printExceptionStackInfoInEnv");
        List<String> printExceptionStackEnvList = Arrays.asList(printExceptionStackEnvs);
        String[] activeProfiles = env.getActiveProfiles();
        if (printExceptionStackEnvs.length > 0)
            if (Arrays.stream(activeProfiles).anyMatch(printExceptionStackEnvList::contains)) {
                this.printExceptionStack = true;
            }*/
    }

    // public boolean getPrintExceptionStack() {
    //    return this.printExceptionStack;
    // }
}
