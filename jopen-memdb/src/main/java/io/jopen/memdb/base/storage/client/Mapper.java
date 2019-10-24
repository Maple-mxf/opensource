package io.jopen.memdb.base.storage.client;

import com.google.common.collect.Maps;
import io.jopen.core.common.reflect.ReflectHelper;
import io.jopen.memdb.base.annotation.Util;
import io.jopen.memdb.base.storage.server.Id;
import io.jopen.memdb.base.storage.server.Row;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author maxuefeng
 * {@link Id}
 * @since 2019/10/24
 */
final
class Mapper<T> {

    final Function<List<Object>, Collection<Row<String, Object>>> mapBeanToMap = objects -> {

        objects.parallelStream().map(obj -> {
            // 转换为Map
            Map<String, Object> columnValues = ReflectHelper.getObjFiledValues(obj);
            // 获取主键
            List<Field> idFields = Util.idFields(obj.getClass());

            Map<String, Object> pk = Maps.newHashMap();
            idFields.forEach(pkF -> {
                pkF.get()
            });
        })

        return null;
    };

    final Function<IntermediateExpression<T>, IntermediateExpression<Row<String, Object>>> mapExpressionToExpressionRow = objectIntermediateExpression -> {
        System.err.println();
        return null;
    };

    final Function<Collection<Row<String, Object>>, Collection<T>> mapRowsToBeans = new Function<Collection<Row<String, Object>>, Collection<T>>() {
        @Override
        public Collection<T> apply(Collection<Row<String, Object>> rows) {
            return null;
        }
    };
}
