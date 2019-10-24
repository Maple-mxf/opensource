package io.jopen.memdb.base.storage.client;

import io.jopen.memdb.base.storage.server.Database;
import io.jopen.memdb.base.storage.server.Id;
import io.jopen.memdb.base.storage.server.Row;
import io.jopen.memdb.base.storage.server.RowStoreTable;

import java.util.Collection;
import java.util.Collections;
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

    int update(QueryBuilder.Update update) {
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

    int save(QueryBuilder.Update update) {

        List<T> saveBody = update.getQueryBuilder().getWillSaveBody();

        // 获取操作的数据库当前对象
        Database currentDatabase = update.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = update.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        // 进行保存  saveBody
        return table.saveBatch();
    }

    int delete(QueryBuilder.Delete delete) {


        // 获取操作的数据库当前对象
        Database currentDatabase = delete.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = delete.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        IntermediateExpression<T> expression = delete.getQueryBuilder().getExpression();

        Mapper<T> mapper = new Mapper<>();
        // 进行保存
        List<Id> deleteRes = table.delete(mapper.mapExpressionToExpressionRow.apply(expression));
        return deleteRes.size()
    }

    List<T> select(QueryBuilder.Select select) {

        IntermediateExpression<T> expression = select.getQueryBuilder().getExpression();
        // 获取操作的数据库当前对象
        Database currentDatabase = select.getQueryBuilder().getClientInstance().getCurrentDatabase();

        // 获取对应table对象
        Class clazz = select.getQueryBuilder().getExpression().getTargetClass();
        RowStoreTable table = currentDatabase.getTable(clazz);

        Mapper<T> mapper = new Mapper<>();

        // table.query(expression);
        List<Row<String, Object>> selectResult = table.query(mapper.mapExpressionToExpressionRow.apply(expression));

        // select result -> Java Bean
        Collection<T> collection = mapper.mapRowsToBeans.apply(selectResult);

        return Collections.list(collection);

    }
}
