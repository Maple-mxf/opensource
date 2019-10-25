package io.jopen.leopard.base.annotation;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * @since 2019/9/24
 */
public class Util {

    public static String entityVal(Class<?> clazz) {
        Preconditions.checkNotNull(clazz);
        Entity annotation = clazz.getAnnotation(Entity.class);
        if (annotation == null || Strings.isNullOrEmpty(annotation.value())) {
            return clazz.getSimpleName();
        }
        return annotation.value();
    }

    public static List<Field> idFields(Class clazz) {

        Field[] fields = clazz.getFields();
        List<Field> primaryKeyFields = Arrays.stream(fields).filter(f -> f.getAnnotation(PrimaryKey.class) != null).collect(Collectors.toList());


        if (primaryKeyFields.size() == 0) {
            throw new IllegalArgumentException("not idVal annotation,must have @PrimaryKey");
        }

        return primaryKeyFields;

    }

}
