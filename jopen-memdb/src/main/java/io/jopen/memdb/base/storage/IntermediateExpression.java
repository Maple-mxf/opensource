package io.jopen.memdb.base.storage;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import io.jopen.core.common.reflect.ReflectHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 中间表达式  条件构造器  此处的中间表达式暂时定义为断言操作
 * <p>{@link com.google.common.base.Predicate}</p>
 * <p>{@link java.util.function.Predicate}</p>
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
public class IntermediateExpression<T> {

    @FunctionalInterface
    interface Condition<T> {
        boolean test(JavaModelTable<T> table, Object value);
    }

    @FunctionalInterface
    @Deprecated
    interface Match {
        boolean test(Object v1, Object v2);
    }

    private List<Condition<T>> conditions = new ArrayList<>();

    public List<Condition<T>> getConditions() {
        return this.conditions;
    }

/*    private boolean common(String column, Object value, Match match) {

        Preconditions.checkNotNull(column);
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(match);

        conditions.add((table, value1) -> {

            List<T> cells = table.queryAll();

            for (T object : cells) {
                // 获取当前行的数据
                Map<String, Object> filedNameValues = ReflectHelper.getObjFiledValues(object);
                // 获取当前行指定列的值
                Object val = filedNameValues.get(column);
                return match.test(val, value1);
            }

            return false;
        });
    }*/

    public IntermediateExpression<T> eq(String column, Object value) {

        Preconditions.checkNotNull(column);
        Preconditions.checkNotNull(value);

        conditions.add((table, receiveValue) -> {
            List<T> cells = table.queryAll();
            for (T object : cells) {
                // 获取当前行的数据
                Map<String, Object> filedNameValues = ReflectHelper.getObjFiledValues(object);
                // 获取当前行指定列的值
                Object val = filedNameValues.get(column);
                if (val != null && val.equals(value)) {
                    return true;
                }
            }
            return false;
        });
        return this;
    }

    /**
     * @param column 列名称
     * @param value  条件对应的value
     * @return 返回当前对象继续构造
     * @see Comparable
     */
    public IntermediateExpression<T> le(String column, Object value) {

        Preconditions.checkNotNull(column);

        conditions.add((table, receiveValue) -> {
            List<T> cells = table.queryAll();
            for (T object : cells) {
                // 获取当前行的数据
                Map<String, Object> filedNameValues = ReflectHelper.getObjFiledValues(object);
                // 获取当前行指定列的值
                Object val = filedNameValues.get(column);

                if (val != null && val.equals(value)) {
                    return true;
                }

                // 如果比较的数据实现了Comparable接口
                if (val != null && Arrays.asList(val.getClass().getInterfaces()).contains(Comparable.class)) {
                    Comparable v1 = (Comparable) val;
                    Comparable v2 = (Comparable) value;
                    return Ordering.natural().compare(v2, v1) >= 1;
                }
            }
            return false;
        });
        return this;
    }
}
