package io.jopen.memdb.base.storage.server;

import io.jopen.memdb.base.annotation.PrimaryKey;
import io.jopen.memdb.base.annotation.Property;
import io.jopen.memdb.base.annotation.Util;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public final
class Mapper {

    @NotNull
    public RowStoreTable mapJavaBeanToTable(@NonNull Class clazz,
                                            @NonNull Database database) {
        Field[] fields = clazz.getDeclaredFields();

        if (fields.length == 0) {
            throw new RuntimeException(String.format("class %s has no property", clazz.getName()));
        }

        List<ColumnType> columnTypes = Stream.of(fields).map(field -> {
            PrimaryKey pkAnno = field.getDeclaredAnnotation(PrimaryKey.class);
            Property proAnno = field.getDeclaredAnnotation(Property.class);
            boolean pk = false;
            String columnName = field.getName();
            if (pkAnno != null && proAnno != null) {
                pk = true;
                columnName = pkAnno.value();
            }
            return new ColumnType(field.getType(), columnName, pk);
        }).collect(Collectors.toList());

        return new RowStoreTable(database, Util.entityVal(clazz), columnTypes);

    }

}
