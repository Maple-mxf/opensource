package io.jopen.memdb.base.storage.client;

import io.jopen.memdb.base.annotation.PrimaryKey;
import io.jopen.memdb.base.annotation.Property;
import io.jopen.memdb.base.storage.server.Id;
import io.jopen.memdb.base.storage.server.Row;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * converter java bean object to row {@link io.jopen.memdb.base.storage.server.Row}
 *
 * @author maxuefeng
 * @since 2019/10/24
 * <p>{@link Mapper}</p>
 */
final
class Converter<T> {

    Converter() {
    }

    /**
     * @param beans java bean object
     * @return rows
     * <p>{@link RuntimeException} may be throw some exception </p>
     * @see Row
     */
    @NonNull
    final Collection<Row> convertBean2Row(@NonNull Collection<T> beans) {

        Collection<Row> rows = new ArrayList<>();

        for (T bean : beans) {

            Class<?> clazz = bean.getClass();
            Field[] fields = clazz.getDeclaredFields();

            HashMap<String, Object> columnValues = new HashMap<>();
            HashMap<String, Object> ids = new HashMap<>();

            for (Field field : fields) {

                field.setAccessible(true);
                Object fieldValue;

                try {
                    fieldValue = field.get(bean);
                } catch (Throwable e) {
                    throw new RuntimeException(e.getMessage());
                }

                PrimaryKey pkAnno = field.getDeclaredAnnotation(PrimaryKey.class);
                Property proAnno = field.getDeclaredAnnotation(Property.class);

                boolean pk = false;

                String columnName = field.getName();

                if (pkAnno != null && proAnno == null && StringUtils.isNotBlank(pkAnno.value())) {
                    columnName = pkAnno.value();
                    pk = true;
                } else if (pkAnno == null && proAnno != null && StringUtils.isNotBlank(proAnno.value())) {
                    columnName = proAnno.value();
                }

                if (pk) {
                    ids.put(columnName, fieldValue);
                }
                columnValues.put(columnName, fieldValue);
            }
            Row row = new Row(Id.of(ids));
            row.putAll(columnValues);
            rows.add(row);
        }
        return rows;
    }

    /**
     * 转换Express
     *
     * @see IntermediateExpression#getConditions()
     */
    @NonNull
    final IntermediateExpression<Row> convertIntermediateExpressionType(
            @NonNull IntermediateExpression<T> originExpression) {

        IntermediateExpression<Row> expression = IntermediateExpression.buildFor(Row.class);

        // 获取条件
        List<IntermediateExpression.Condition> originConditions = originExpression.getConditions();

        expression.setTargetClass(Row.class);
        expression.setConditions(originConditions);

        return expression;
    }
}
