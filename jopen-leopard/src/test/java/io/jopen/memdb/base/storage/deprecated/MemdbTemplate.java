package io.jopen.memdb.base.storage.deprecated;

import com.google.common.base.Preconditions;
import io.jopen.core.common.memdb.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
@Deprecated
interface MemdbTemplate {

    <T> Boolean save(T t) throws Throwable;

    <T> void delete(T t) throws Throwable;

    <T> void update(T t);

    <T> void select(T t);

    default <T> boolean checkNoNullProperty(T t) {
        Preconditions.checkNotNull(t);

        // ID不可为空
        // PrimaryKey primaryKey = t.getClass().getAnnotation(PrimaryKey.class);

        Set<Field> primaryFields = Arrays.stream(t.getClass().getDeclaredFields())
                .filter(field -> {
                    field.setAccessible(true);
                    PrimaryKey primaryKey = field.getDeclaredAnnotation(PrimaryKey.class);
                    return primaryKey != null;
                }).collect(Collectors.toSet());

        // 如果一个主键都没有
        if (primaryFields.size() == 0) {
            throw new IllegalArgumentException(String.format("target class  %s not primary field", t.getClass().getName()));
        }

        // 第一种情况 当前实体类只有一个主键
        if (primaryFields.size() == 1) {
            return true;
        }

        // 第二种情况 当前实体类多个属性决定主键
        // TODO  待完善  目前仅支持一个主键
        return false;
    }

/*    static <T> String parseEntity(Class<T> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);
        if (annotation == null || Strings.isNullOrEmpty(annotation.value())) {
            return clazz.getSimpleName();
        }
        return annotation.value();
    }*/

  /*  default <T> void createTable(Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        String tableName = MemdbTemplate.parseEntity(clazz);

        // 重复检测
        if (this..containsKey(tableName)) {
            throw new IllegalArgumentException("table existed");
        }

        // 检测

        // 创建表格
        DBManagement.DBA.databases.put(tableName, new JavaModelTable<T>(clazz));
    }*/

/*    default <T> JavaModelTable<T> getRowsData(Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        String tableName = MemdbTemplate.parseEntity(clazz);
        if (!tables.containsKey(tableName)) {
            // 创建表格
            createTable(clazz);
        }
        return this.tables.get(tableName);
    }*/

}
