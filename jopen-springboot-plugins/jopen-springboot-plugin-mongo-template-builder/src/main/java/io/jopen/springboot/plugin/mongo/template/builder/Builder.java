package io.jopen.springboot.plugin.mongo.template.builder;

import com.google.common.base.Strings;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.beans.PropertyDescriptor;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * @author maxuefeng
 * @since 2019-11-13
 */
public class Builder<T> {

    Class<T> clazz;

    MongoTemplate mongoTemplate;

    Function<SFunction<T, ?>, String> produceValName = sFunction -> {

        SerializedLambda lambda = LambdaUtils.resolve(sFunction);
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


                if (fanno != null && Strings.isNullOrEmpty(fanno.value())) {
                    eqName = fanno.value();
                    break;
                } else {
                    eqName = finalValName;
                    break;
                }
            }
        }
        return eqName;
    };

}
