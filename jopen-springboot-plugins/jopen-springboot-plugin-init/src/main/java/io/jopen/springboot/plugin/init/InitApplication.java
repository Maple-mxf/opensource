package io.jopen.springboot.plugin.init;

import io.jopen.springboot.plugin.common.ReflectUtil;
import io.jopen.springboot.plugin.common.SpringContainer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * 当前类负责执行容器的初始化任务，负责扫描带有{@link Init}注解的类
 * 利用反|射进行加载初始化代码
 * <p></p>
 * 参数需要{@link ApplicationRunner#run(ApplicationArguments)}进行传递
 * @since 2020-01-08
 */
@Component
public class InitApplication implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitApplication.class);

    private String basePackage;

    public void setBasePackage(@NonNull String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("初始化业务容器");
        List<Class<?>> classList = ReflectUtil.getClasses(basePackage);
        Set<Class<?>> initTypeList = classList.parallelStream()
                .filter(type -> type.getDeclaredAnnotation(Init.class) != null)
                .collect(Collectors.toSet());

        for (Class<?> type : initTypeList) {
            Init init = type.getDeclaredAnnotation(Init.class);
            Init.InitMode mode = init.initialization();

            if (mode.equals(Init.InitMode.STATIC_CODE_BLOCK)) {
                Class.forName(type.getName());
            } else if (mode.equals(Init.InitMode.STATIC_METHOD)) {
                Method method = type.getMethod(init.value());
                // 无参数  并且 调用的是静态方法
                method.invoke(null);
            } else if (mode.equals(Init.InitMode.MEMBER_METHOD)) {
                Method method = type.getMethod(init.value());
                Object instance;
                try {
                    instance = SpringContainer.getBean(type);
                } catch (Exception ignored) {
                    instance = type.newInstance();
                }
                // 调用执行静态方法
                method.invoke(instance);
            }
        }

    }
}
