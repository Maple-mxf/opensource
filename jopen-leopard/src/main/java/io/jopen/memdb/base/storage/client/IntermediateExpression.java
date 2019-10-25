package io.jopen.memdb.base.storage.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import io.jopen.memdb.base.reflect.ReflectHelper;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

/**
 * 中间表达式  条件构造器  此处的中间表达式暂时定义为断言操作
 * <p>{@link com.google.common.base.Predicate}</p>
 * <p>{@link java.util.function.Predicate}</p>
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
public class IntermediateExpression<T> {

    private transient Class<T> targetClass;

    private IntermediateExpression(Class<T> targetClass) {
        Preconditions.checkNotNull(targetClass);
        this.targetClass = targetClass;
    }

    public static <T> IntermediateExpression<T> buildFor(Class<T> targetClass) {
        return new IntermediateExpression<>(targetClass);
    }

    public void setTargetClass(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public Class<T> getTargetClass() {
        return this.targetClass;
    }


    @FunctionalInterface
    public interface Condition {
        boolean test(Object row);
    }

    private List<Condition> conditions = new ArrayList<>();

    public List<Condition> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public IntermediateExpression<T> eq(String column, Object value) {

        Preconditions.checkNotNull(column);
        Preconditions.checkNotNull(value);

        conditions.add((row) -> {
            // 获取当前行的数据
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(row);
            // 获取当前行指定列的值
            Object val = filedNameValues.get(column);
            return val != null && val.equals(value);
        });
        return this;
    }

    /**
     * 小于等于
     *
     * @param column 列名称
     * @param value  条件对应的value
     * @return 返回当前对象继续构造
     * @see Comparable
     */
    public IntermediateExpression<T> le(@NonNull String column, Object value) {
        conditions.add((row) -> {

            // 获取当前行的数据
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(row);
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
            return false;
        });
        return this;
    }

    @NonNull
    public IntermediateExpression<T> lt(@NonNull String column, @NonNull Object value) {

        conditions.add((row) -> {

            // 获取当前行的数据
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(row);
            // 获取当前行指定列的值
            Object val = filedNameValues.get(column);

            if (val != null && val.equals(value)) {
                return true;
            }

            // 如果比较的数据实现了Comparable接口
            if (val != null && Arrays.asList(val.getClass().getInterfaces()).contains(Comparable.class)) {
                Comparable v1 = (Comparable) val;
                Comparable v2 = (Comparable) value;
                return Ordering.natural().compare(v2, v1) > 0;
            }
            return false;
        });
        return this;
    }

    /**
     * 小于等于
     *
     * @param column 列名称
     * @param value  条件对应的value
     * @return 返回当前对象继续构造
     * @see Comparable
     */
    public final IntermediateExpression<T> ge(@NonNull String column, Object value) {

        conditions.add((row) -> {

            // 获取当前行的数据
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(row);
            // 获取当前行指定列的值
            Object val = filedNameValues.get(column);

            if (val != null && val.equals(value)) {
                return true;
            }

            // 如果比较的数据实现了Comparable接口
            if (val != null && Arrays.asList(val.getClass().getInterfaces()).contains(Comparable.class)) {
                Comparable v1 = (Comparable) val;
                Comparable v2 = (Comparable) value;
                return Ordering.natural().compare(v2, v1) <= 0;
            }
            return false;
        });
        return this;
    }

    /**
     * 小于等于
     *
     * @param column 列名称
     * @param value  条件对应的value
     * @return 返回当前对象继续构造
     * @see Comparable
     */
    public final IntermediateExpression<T> gt(@NonNull String column, Object value) {

        conditions.add((row) -> {

            // 获取当前行的数据
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(row);
            // 获取当前行指定列的值
            Object val = filedNameValues.get(column);

            if (val != null && val.equals(value)) {
                return true;
            }

            // 如果比较的数据实现了Comparable接口
            if (val != null && Arrays.asList(val.getClass().getInterfaces()).contains(Comparable.class)) {
                Comparable v1 = (Comparable) val;
                Comparable v2 = (Comparable) value;
                return Ordering.natural().compare(v2, v1) < 0;
            }
            return false;
        });
        return this;
    }

    /**
     * @param column 列名称
     * @return 继续构造表达式
     * @see java.time.LocalDateTime
     * @see java.util.Date
     * @see java.sql.Date
     * @see Comparable
     * @see Ordering
     */
    @NonNull
    public final IntermediateExpression<T> between(
            @NonNull String column,
            @NonNull Comparable<? extends Comparable> start,
            @NonNull Comparable<? extends Comparable> end) {

        conditions.add((row) -> {

            // 获取当前行的数据
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(row);
            // 获取当前行指定列的值
            Object val = filedNameValues.get(column);
            Comparable middleVal = (Comparable) val;

            if (middleVal == null) {
                return false;
            }

            return middleVal.compareTo(start) >= 0 && middleVal.compareTo(end) <= 0;
        });
        return this;
    }

    @NonNull
    public final IntermediateExpression<T> in(@NonNull String column,
                                              @NonNull Collection<Object> objects) {
        if (objects.size() == 0) {
            return this;
        }
        conditions.add((row) -> {

            // 获取当前行的数据
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(row);
            // 获取当前行指定列的值
            Object val = filedNameValues.get(column);
            return objects.contains(val);
        });
        return this;
    }

    @NonNull
    public final IntermediateExpression<T> in(@NonNull String column, @NonNull Object... objects) {
        return this.in(column, Lists.newArrayList(objects));
    }
}
