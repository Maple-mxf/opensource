package io.jopen.snack.embed.client;

import io.jopen.snack.common.Condition;
import io.jopen.snack.common.Id;
import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.annotation.PrimaryKey;
import io.jopen.snack.common.reflect.Field2ColumnHelper;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * converter java bean object to row {@link Row}
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
                String columnName = Field2ColumnHelper.columnName(field);

                if (pkAnno != null) {
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
        List<Condition> originConditions = originExpression.getConditions();

        expression.setTargetClass(Row.class);
        expression.setConditions(originConditions);


        return expression;
    }

    @NonNull
    final List<IntermediateExpression<Row>> convertBeansToExpressions(
            @NonNull List<T> beans) {

        List<IntermediateExpression<Row>> expressionList = new ArrayList<>();

        for (T bean : beans) {
            Field[] fields = bean.getClass().getDeclaredFields();

            IntermediateExpression<Row> expression = IntermediateExpression.buildFor(Row.class);

            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(bean);
                    if (value != null) {
                        String columnName = Field2ColumnHelper.columnName(field);
                        expression.eq(columnName, value);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            if (expression.getConditions().size() > 0) {
                expressionList.add(expression);
            }
        }
        return expressionList;
    }
}
