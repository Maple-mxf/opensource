package io.jopen.memdb.base.storage.serialize;

import io.jopen.memdb.base.annotation.PrimaryKey;
import io.jopen.memdb.base.annotation.Property;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;

/**
 * <p>{@link io.jopen.memdb.base.annotation.Property}</p>
 *
 *
 * <p>{@link io.jopen.memdb.base.annotation.PrimaryKey}</p>
 *
 * @author maxuefeng
 * @since 2019/10/25
 */
public class Field2ColumnHelper {

    @NonNull
    public
    static String columnName(@NonNull Field field) {
        PrimaryKey pkAnno = field.getDeclaredAnnotation(PrimaryKey.class);
        Property proAnno = field.getDeclaredAnnotation(Property.class);

        String columnName = field.getName();

        if (pkAnno != null && proAnno == null && StringUtils.isNotBlank(pkAnno.value())) {
            columnName = pkAnno.value();
        } else if (pkAnno == null && proAnno != null && StringUtils.isNotBlank(proAnno.value())) {
            columnName = proAnno.value();
        }
        return columnName;
    }
}
