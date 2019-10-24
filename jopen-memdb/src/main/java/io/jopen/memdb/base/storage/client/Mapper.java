package io.jopen.memdb.base.storage.client;

import io.jopen.memdb.base.storage.server.Id;
import io.jopen.memdb.base.storage.server.Row;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author maxuefeng
 * {@link Id}
 * @since 2019/10/24
 */
final
class Mapper<T> {

/*    final Function<List<Object>, Collection<Row>> mapBeanToMap = objects -> {

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
    };*/


    final Function<Collection<Row>, Collection<T>> mapRowsToBeans = rows -> {
        System.err.println();

        for (Row row : rows) {

        }

        return null;

    };
}
