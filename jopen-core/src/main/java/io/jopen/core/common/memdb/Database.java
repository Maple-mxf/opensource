package io.jopen.core.common.memdb;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.jopen.core.common.memdb.annotation.Entity;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
@Deprecated
final
class Database implements Serializable {

    private String dbName;

    private ConcurrentHashMap<String, JavaModelTabale> tables = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, JavaModelTabale> showTables() {
        return this.tables;
    }

    public String getDbName() {
        return dbName;
    }

    Database(String dbName) {
        this.dbName = dbName;
    }

    private static <T> String parseEntity(Class<T> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);
        if (annotation == null || Strings.isNullOrEmpty(annotation.value())) {
            return clazz.getSimpleName();
        }
        return annotation.value();
    }

    public <T> void createTable(Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        String tableName = parseEntity(clazz);

        // 重复检测
        if (tables.containsKey(tableName)) {
            throw new IllegalArgumentException("table existed");
        }

        // 创建表格
        this.tables.put(tableName, new JavaModelTabale<T>(clazz));
    }

    public <T> JavaModelTabale<T> getTable(Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        String tableName = parseEntity(clazz);
        if (!tables.containsKey(tableName)) {
            // 创建表格
            createTable(clazz);
        }
        return this.tables.get(tableName);

    }
}
