package io.jopen.memdb.base.annotation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author maxuefeng
 * @since 2019/9/24
 */
public class Util {

    public static String entityVal(Object object) {
        Entity entity = object.getClass().getAnnotation(Entity.class);
        return entity.value();
    }

    public static Object idVal(Object object) throws IllegalAccessException {

        Class clazz = object.getClass();
        Field[] fields = clazz.getFields();
        Optional<Field> optional = Arrays.stream(fields).filter(f -> f.getAnnotation(PrimaryKey.class) != null).findFirst();

        if (optional.isPresent()) {
            Field field = optional.get();
            field.setAccessible(true);
            return field.get(object);
        } else {
            throw new IllegalArgumentException("not idVal annotation,must have @PrimaryKey");
        }
    }
}
