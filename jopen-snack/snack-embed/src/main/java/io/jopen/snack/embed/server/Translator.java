package io.jopen.snack.embed.server;

import io.jopen.snack.common.ColumnInfo;
import io.jopen.snack.common.annotation.PrimaryKey;
import io.jopen.snack.common.annotation.Property;
import io.jopen.snack.common.annotation.Util;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public final
class Translator<T> {

    Translator() {
    }

    @NonNull
    public final RowStoreTable mapJavaBeanToTable(@NonNull Class clazz,
                                                  @NonNull Database database) {
        Field[] fields = clazz.getDeclaredFields();

        if (fields.length == 0) {
            throw new RuntimeException(String.format("class %s has no property", clazz.getName()));
        }

        List<ColumnInfo> columnInfos = Stream.of(fields).map(field -> {
            PrimaryKey pkAnno = field.getDeclaredAnnotation(PrimaryKey.class);
            Property proAnno = field.getDeclaredAnnotation(Property.class);
            boolean pk = false;
            String columnName = field.getName();
            if (pkAnno != null) {
                pk = true;
                columnName = pkAnno.value();
            } else if (proAnno != null) {
                columnName = proAnno.value();
            }
            return new ColumnInfo(field.getType(), columnName, pk);
        }).collect(Collectors.toList());
        return new RowStoreTable(database, Util.entityVal(clazz), columnInfos);
    }

}
