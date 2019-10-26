package io.jopen.leopard.base.storage.client;

import io.jopen.leopard.base.storage.serialize.Field2ColumnHelper;
import io.jopen.leopard.base.storage.server.Database;
import io.jopen.leopard.base.storage.server.Id;
import io.jopen.leopard.base.storage.server.Row;
import io.jopen.leopard.base.storage.server.RowStoreTable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 执行器  将中间构建的表达式和底层存储table进行对接
 * <p>{@link RowStoreTable}</p>
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
final
class Actuator<T> {

    // 转换器  避免类型擦除
    private transient final Converter<T> converter = new Converter<>();
    private transient final Mapper<T> mapper = new Mapper<>();

    final int update(QueryBuilder.Update update) {
        HashMap<String, Object> setKV = update.getBody();

        // 获取操作的数据库当前对象
        Database currentDatabase = update.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = update.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        IntermediateExpression expression = update.getQueryBuilder().getExpression();

        // 更新
        List<Id> updateRes = table.update(expression, setKV);

        return updateRes.size();
    }

    final int save(@NonNull QueryBuilder.Save save) {
        List<T> beans = save.getQueryBuilder().getBeans();
        if (beans.size() == 0) {
            return 0;
        }

        // 获取操作的数据库当前对象
        Database currentDatabase = save.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = beans.get(0).getClass();
        RowStoreTable table = securityCheckTable(currentDatabase, clazz);

        // 进行保存beans
        return table.saveBatch(converter.convertBean2Row(beans));
    }

    final int delete(@NonNull QueryBuilder.Delete delete) throws Throwable {
        // 获取操作的数据库当前对象
        Database currentDatabase = delete.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = null;


        // 开发者可能输入一些条件 也可能输入一些实体类
        IntermediateExpression<T> expression = delete.getQueryBuilder().getExpression();
        List<T> beans = delete.getQueryBuilder().getBeans();

        IntermediateExpression<Row> expressionRow;

        // beans不为空则把beans转换为IntermediateExpression<T> 条件体
        // 按照条件bean构造器
        if (expression == null && beans != null && beans.size() > 0) {
            clazz = beans.get(0).getClass();

            expression = IntermediateExpression.buildFor(clazz);

            T bean = beans.get(0);

            List<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields()).peek(field -> field.setAccessible(true))
                    .filter(field -> {
                        try {
                            return field.get(bean) != null;
                        } catch (IllegalAccessException ignored) {
                            return false;
                        }
                    }).collect(Collectors.toList());

            for (Field field : fields) {
                expression.eq(Field2ColumnHelper.columnName(field), field.get(bean));
            }

            expressionRow = converter.convertIntermediateExpressionType(expression);

        }
        //
        else if (expression != null && beans == null) {
            clazz = expression.getTargetClass();
            expressionRow = converter.convertIntermediateExpressionType(expression);
        }
        //
        else {
            throw new RuntimeException("illegal args");
        }

        RowStoreTable table = securityCheckTable(currentDatabase, clazz);
        return table.delete(expressionRow).size();
    }

    @NonNull
    final Collection<T> select(@NonNull QueryBuilder.Select select) throws Throwable {

        IntermediateExpression<T> expression = select.getQueryBuilder().getExpression();
        List<T> beans = select.getQueryBuilder().getBeans();

        // 获取类型
        Class clazz;

        IntermediateExpression<Row> rowExpression = null;

        if (expression != null && (beans == null || beans.size() == 0)) {
            clazz = expression.getTargetClass();
            expression.setTargetClass(clazz);
        } else if (expression == null && (beans != null && beans.size() > 0)) {
            clazz = beans.get(0).getClass();
            expression = IntermediateExpression.buildFor(clazz);

            // 将bean中不为空的字段全部查询出来
            for (T bean : beans) {
                // 进行过滤
                List<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields()).peek(field -> field.setAccessible(true))
                        .filter(field -> {
                            try {
                                return field.get(bean) != null;
                            } catch (IllegalAccessException ignored) {
                                return false;
                            }
                        }).collect(Collectors.toList());

                for (Field field : fields) {
                    expression.eq(Field2ColumnHelper.columnName(field), field.get(bean));
                }
            }

        } else {
            throw new RuntimeException("illegal args");
        }
        rowExpression = converter.convertIntermediateExpressionType(expression);

        // 获取操作的数据库当前对象
        Database currentDatabase = select.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取目标表格
        RowStoreTable table = securityCheckTable(currentDatabase, clazz);

        List<Row> selectResult = table.query(rowExpression);
        return mapper.mapRowsToBeans.apply(selectResult, clazz);
    }

    @NonNull
    private RowStoreTable securityCheckTable(
            @NonNull Database database, @NonNull Class clazz) {
        // 检测目标表格是否存在
        RowStoreTable table;
        try {
            table = database.getTable(clazz);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            table = database.createTable(clazz);
        }
        return table;
    }
}
