package io.jopen.memdb.base.storage.client;

import io.jopen.memdb.base.storage.server.Database;
import io.jopen.memdb.base.storage.server.Id;
import io.jopen.memdb.base.storage.server.Row;
import io.jopen.memdb.base.storage.server.RowStoreTable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 执行器  将中间构建的表达式和底层存储table进行对接
 * <p>{@link io.jopen.memdb.base.storage.server.RowStoreTable}</p>
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

    final int delete(@NonNull QueryBuilder.Delete delete) {
        // 获取操作的数据库当前对象
        Database currentDatabase = delete.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = delete.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = securityCheckTable(currentDatabase, clazz);

        // 开发者可能输入一些条件 也可能输入一些实体类
        IntermediateExpression<T> expression = delete.getQueryBuilder().getExpression();
        List<T> beans = delete.getQueryBuilder().getBeans();

        // beans不为空则把beans转换为IntermediateExpression<T> 条件体
        if (expression == null && beans != null) {
            List<IntermediateExpression<Row>> expressionList = converter.convertBeansToExpressions(beans);
            return table.delete(expressionList).size();
        } else if (expression != null && beans == null) {
            return table.delete(converter.convertIntermediateExpressionType(expression)).size();
        } else if (expression != null) {
            List<IntermediateExpression<Row>> expressionList = converter.convertBeansToExpressions(beans);
            expressionList.add(converter.convertIntermediateExpressionType(expression));
            // delete
            return table.delete(expressionList).size();
        }
        return table.delete(IntermediateExpression.buildFor(Row.class)).size();
    }

    @NonNull
    final Collection<T> select(@NonNull QueryBuilder.Select select) throws Throwable {

        IntermediateExpression<T> expression = select.getQueryBuilder().getExpression();
        List<T> beans = select.getQueryBuilder().getBeans();

        // 获取操作的数据库当前对象
        Database currentDatabase = select.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = null;


        RowStoreTable table = securityCheckTable(currentDatabase, clazz);

        List<Row> selectResult = table.query(converter.convertIntermediateExpressionType(expression));
        return mapper.mapRowsToBeans.apply(selectResult, clazz);
    }

    @NonNull
    final RowStoreTable securityCheckTable(
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
