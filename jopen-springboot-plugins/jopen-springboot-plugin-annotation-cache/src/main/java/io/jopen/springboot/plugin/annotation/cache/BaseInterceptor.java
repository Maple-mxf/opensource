package io.jopen.springboot.plugin.annotation.cache;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MutableClassToInstanceMap;
import io.jopen.springboot.plugin.common.ReflectUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 通用方法抽象
 * <p>
 * {@link HandlerInterceptor}
 * {@link Method}
 *
 * @author maxuefeng
 * {@link Annotation}
 * 注解实例调用<code>getClass()<code/>方法的结果是一个Proxy对象 具体打印结果是com.sun.proxy.$Proxy72
 * 注解实例调用<code>annotationType()<code/>方法的结果是一个正确的Class对象  而非一个Proxy对象
 * @see Annotation#annotationType()
 */
@Component
public class BaseInterceptor implements HandlerInterceptor, CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);

    /**
     * 缓存接口的注解，此处对数据有强一致性的要求，读远大于写
     * read >> write
     *
     * @see ClassToInstanceMap
     * @see java.util.concurrent.ConcurrentHashMap
     */
    private final static Map<Integer, ClassToInstanceMap<Annotation>> ANNOTATION_CACHE = new HashMap<>(300);


    /**
     * 获取指定标记
     *
     * @param type    注解类型
     * @param handler 目标接口方法
     * @return 返回指定的注解实例
     */
    @Nullable
    public <TYPE extends Annotation> TYPE getMark(@NonNull Class<TYPE> type,
                                                  @NonNull Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        int key = handlerMethod.getMethod().toGenericString().hashCode();
        ClassToInstanceMap<Annotation> classToInstanceMap = ANNOTATION_CACHE.get(key);
        return classToInstanceMap == null ? null : classToInstanceMap.getInstance(type);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("load api interface annotation");

        // TODO  需要设置controller包  API访问策略
        List<Class<?>> classList = ReflectUtil.getClasses("com.planet.biz.modules.controller");

        // 需要过滤的注解
        final Set<Class<?>> filterTypes = ImmutableSet.of(
                RestController.class,
                Controller.class,
                ResponseBody.class,
                Component.class,
                RequestMapping.class,
                PostMapping.class,
                GetMapping.class,
                PostConstruct.class);

        classList.parallelStream()
                // 进行过滤
                .filter(controllerType -> controllerType.getDeclaredAnnotation(Controller.class) != null || controllerType.getDeclaredAnnotation(RestController.class) != null)
                // 进行消费
                .forEach(controllerType -> {
                    // 类级别的注解
                    Annotation[] typeAnnotations = controllerType.getDeclaredAnnotations();

                    Method[] methods = controllerType.getDeclaredMethods();
                    for (Method method : methods) {
                        Annotation[] methodAnnotations = method.getDeclaredAnnotations();
                        Set<Annotation> mergeSet = new HashSet<>();
                        int key = method.toGenericString().hashCode();

                        // 添加类级别的注解
                        if (typeAnnotations != null) {
                            Collections.addAll(mergeSet, typeAnnotations);
                        }

                        // 添加方法级别的注解
                        if (methodAnnotations != null) {
                            Collections.addAll(mergeSet, methodAnnotations);
                        }

                        MutableClassToInstanceMap<Annotation> classToInstanceMap = MutableClassToInstanceMap.create();
                        for (Annotation annotation : mergeSet) {
                            if (filterTypes.contains(annotation.annotationType())) {
                                continue;
                            }
                            classToInstanceMap.put(annotation.annotationType(), annotation);
                        }
                        ANNOTATION_CACHE.put(key, classToInstanceMap);
                    }
                });

        LOGGER.info("cache api interface annotation complete");
    }

}
