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
        HashMap<String, Object> updateBody = update.getBody();

        // 获取操作的数据库当前对象
        Database currentDatabase = update.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = update.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        IntermediateExpression expression = update.getQueryBuilder().getExpression();

        // 更新
        List<Id> updateRes = table.update(expression, updateBody);

        return updateRes.size();
    }

    final int save(@NonNull QueryBuilder.Update update) {

        List<T> beans = update.getQueryBuilder().getBeans();

        // 获取操作的数据库当前对象
        Database currentDatabase = update.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = update.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        // 进行保存beans
        return table.saveBatch(converter.convertBean2Row(beans));
    }

    final int delete(@NonNull QueryBuilder.Delete delete) {
        // 获取操作的数据库当前对象
        Database currentDatabase = delete.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = delete.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        IntermediateExpression<T> expression = delete.getQueryBuilder().getExpression();

        // delete
        List<Id> deleteRes = table.delete(converter.convertIntermediateExpressionType(expression));

        return deleteRes.size();
    }

    @NonNull
    final List<T> select(@NonNull QueryBuilder.Select select) {

        IntermediateExpression<T> expression = select.getQueryBuilder().getExpression();
        // 获取操作的数据库当前对象
        Database currentDatabase = select.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = select.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        Mapper<T> mapper = new Mapper<>();

        List<Row> selectResult = table.query(converter.convertIntermediateExpressionType(expression));

        // select result -> Java Bean
        Collection<T> collection = mapper.mapRowsToBeans.apply(selectResult);

        return null;

    }
}
