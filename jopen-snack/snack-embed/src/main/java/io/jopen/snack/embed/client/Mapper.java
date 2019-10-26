package io.jopen.snack.embed.client;

import io.jopen.snack.common.Id;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.annotation.PrimaryKey;
import io.jopen.snack.common.annotation.Property;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * {@link Id}
 * @since 2019/10/24
 */
final
class Mapper<T> {

    /**
     * @param <T>
     * @param <U>
     * @param <R>
     * @see java.util.function.BiFunction not throw exception
     */
    public interface BiThrFunction<T, U, R> {
        @NonNull
        R apply(@NonNull T t, @NonNull U u) throws Throwable;
    }

    final BiThrFunction<Collection<Row>, Class<T>, Collection<T>> mapRowsToBeans =
            (rows, clazz) -> rows.parallelStream().map(row -> {
                // create object
                T instance;
                try {
                    instance = clazz.newInstance();
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable.getMessage());
                }

                Stream.of(instance.getClass().getDeclaredFields()).forEach(field -> {
                    // set field access
                    field.setAccessible(true);

                    // 获取当前field的value
                    PrimaryKey pkAnno = field.getDeclaredAnnotation(PrimaryKey.class);
                    Property proAnno = field.getDeclaredAnnotation(Property.class);

                    String columnName = field.getName();

                    if (pkAnno != null && proAnno == null && StringUtils.isNotBlank(pkAnno.value())) {
                        columnName = pkAnno.value();
                    } else if (pkAnno == null && proAnno != null && StringUtils.isNotBlank(proAnno.value())) {
                        columnName = proAnno.value();
                    }

                    try {
                        Object value = row.get(columnName);
                        field.set(instance, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage());
                    }
                });
                return instance;
            }).collect(Collectors.toList());
}
