package io.jopen.springboot.plugin.mongo.template.builder;

import org.apache.logging.log4j.util.Strings;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.beans.PropertyDescriptor;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author maxuefeng
 * @since 2019-11-13
 */
public class Builder<T> {

    Class<T> clazz;

    MongoTemplate mongoTemplate;

    /*缓存数据*/
    private static final ConcurrentHashMap<Class<?>, WeakReference<SerializedLambda>> SF_CACHE = new ConcurrentHashMap<>();

    Function<SFunction<T, ?>, String> produceValName = sFunction -> {
        WeakReference<SerializedLambda> weakReference = SF_CACHE.get(sFunction.getClass());
        SerializedLambda serializedLambda = Optional.ofNullable(weakReference)
                .map(Reference::get)
                .orElseGet(() -> {
                    SerializedLambda lambda = LambdaUtils.resolve(sFunction);
                    SF_CACHE.put(sFunction.getClass(), new WeakReference<>(lambda));
                    return lambda;
                });
        return this.resolve(serializedLambda);
    };

    private String resolve(SerializedLambda lambda) {

        String implMethodName = lambda.getImplMethodName();

        // 忽略大小写
        String valName;
        if (implMethodName.startsWith("get")) {
            valName = implMethodName.replaceFirst("get", "");
        } else if (implMethodName.startsWith("is")) {
            valName = implMethodName.replaceFirst("is", "");
        } else {
            System.err.println("字段错误");
            return null;
        }

        String eqName = "";
        PropertyDescriptor[] descriptors = ReflectUtils.getBeanProperties(clazz);

        for (PropertyDescriptor descriptor : descriptors) {
            if (valName.equalsIgnoreCase(descriptor.getName())) {
                String finalValName = valName.substring(0, 1).toLowerCase() + valName.substring(1);
                Field field;
                try {
                    field = clazz.getDeclaredField(finalValName);
                } catch (Exception ignored) {
                    return null;
                }
                field.setAccessible(true);
                org.springframework.data.mongodb.core.mapping.Field fanno =
                        field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);


                if (fanno != null && Strings.isNotEmpty(fanno.value())) {
                    eqName = fanno.value();
                    break;
                } else {
                    eqName = finalValName;
                    break;
                }
            }
        }
        return eqName;
    }

}
