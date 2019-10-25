package io.jopen.leopard.base.storage.deprecated;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import io.jopen.leopard.base.annotation.Entity;
import io.jopen.leopard.base.reflect.ReflectHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 这种存储方式序列化存在很多问题
 * 丢弃当前的存储策略
 *
 * @author maxuefeng
 * @since 2019/10/22
 * <p>{@link com.google.common.collect.Table}</p>
 */
@Deprecated
public final
class JavaModelTable<T> implements Serializable {

    // 数据存储
    private CopyOnWriteArrayList<T> cells = new CopyOnWriteArrayList<>();

    // private Database currentDatabase

    // primary key
    private Set<Object> ids = new ConcurrentSkipListSet<>();

    public List<T> queryAll() {
        return this.cells;
    }

    // 修改表格之前的回调函数
    static List<PreModifyTableCallback> preModifyTableCallbacks = new ArrayList<>();

    // 修改表格之后的回调函数
    static List<AfterModifyTableCallback> afterModifyTableCallbacks = new ArrayList<>();

    static {
        // 修改表格之前预操作
        preModifyTableCallbacks.add((database, object) -> {
            JavaModelTable javaModelTable = null;
            // JavaModelTable javaModelTable = database.getTable(parseEntity(object.getClass()));

            if (javaModelTable == null) {
                javaModelTable = new JavaModelTable(object.getClass());
                // database.tables.put(javaModelTable.getTableName(), javaModelTable);
            }
            return ReturnValue.of(object.getClass().getName(), javaModelTable);
        });


        // 修改表格之后的回调函数
        afterModifyTableCallbacks.add((database, table) -> {

            // 数据库名称
            String dbName = database.getDbName();

            // 持久化数据
            String path = "./memdb/" + dbName;
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                boolean mkdirSuccess = dirFile.mkdirs();
                if (!mkdirSuccess) {
                    throw new RuntimeException("create dir fail");
                }

                // TODO  暂时直接覆盖原有的数据文件
                // 将java对象转换为List<Map<String,Object>>类型进行序列化
                // ByteStreams.
                // File dataFile = new File(path + "/" + Objects.hashCode(table.getTableName()));
                // com.google.common.io.Files.asCharSink(dataFile, StandardCharsets.UTF_8, FileWriteMode.APPEND).openStream().write();
                // Closeables.c
                // 将数据进行加密
                // MoreFiles.asCharSink().openStream().write();
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path + "/" + Objects.hashCode(table.getTableName())));
                out.writeObject(table);
                out.close();
            }
        });
    }

    // 存储的目标类型
    private Class<T> target;

    private String tableName;

    JavaModelTable(Class<T> target) {
        Preconditions.checkNotNull(target);
        this.target = target;
        this.tableName = parseEntity(target);
    }

    public String getTableName() {
        return this.tableName;
    }

    T queryOne(T t) {
        Optional<T> optional = cells.parallelStream().filter(cell -> {
            // 获取指定属性匹配值
            // key fieldName value fieldValue
            Map<String, Object> filedNameValues = ReflectHelper.getBeanFieldValueMap(t);

            Set<Field> fieldSet = Arrays.stream(cell.getClass().getFields())
                    .filter(field -> filedNameValues.containsKey(field.getName()))
                    .collect(Collectors.toSet());

            // 进行断言
            List<Predicate<T>> predicateList = new ArrayList<>();

            // 循环添加不为空字段必须相同
            fieldSet.forEach(field -> predicateList.add(input -> {
                try {
                    // 当前cell的值要匹配才可以
                    Object objFiledValue = ReflectHelper.getObjFiledValue(cell, field.getName());
                    assert objFiledValue != null;
                    return objFiledValue.equals(filedNameValues.get(field.getName()));
                } catch (NoSuchFieldException ignored) {
                    return false;
                }
            }));

            // 断言结果
            Predicate<T> predicate = Predicates.and(predicateList);

            // 进行过滤
            return predicate.apply(cell);
        }).findFirst();

        return optional.orElse(null);
    }

    List<T> queryList() {
        return cells.parallelStream().filter(t -> false).collect(Collectors.toList());
    }

    Boolean add(T t) {
        return cells.addIfAbsent(t);
    }

    public void delete(T t) throws Throwable {
        Optional<T> optional = cells.parallelStream().filter(cell -> cell.equals(t)).findFirst();
        T target = optional.orElseThrow((Supplier<Throwable>) RuntimeException::new);
        // this.cells.removeIf()
        this.cells.remove(target);


    }

    @Override
    public String toString() {

        StringBuilder column = new StringBuilder();
        List<String> fieldNames = Arrays.stream(this.target.getFields()).map(Field::getName).collect(Collectors.toList());
        fieldNames.forEach(fieldName -> column.append(" ").append(fieldName).append("                   "));

        // 拼接换行
        column.append("\n");
        column.append("\n");

        StringBuilder value = new StringBuilder();
        for (T cell : this.cells) {
            fieldNames.forEach(fieldName -> {
                try {
                    Field field = cell.getClass().getField(fieldName);
                    field.setAccessible(true);
                    Object obj = field.get(cell);
                    if (obj == null) {
                        value.append(" ");
                    } else {
                        value.append(" ").append(obj.toString());
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            value.append("\n");
            value.append("\n");
        }
        return column.append(value).toString();
    }


    public static <T> String parseEntity(Class<T> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);
        if (annotation == null || Strings.isNullOrEmpty(annotation.value())) {
            return clazz.getSimpleName();
        }
        return annotation.value();
    }
}
