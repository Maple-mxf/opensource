package io.jopen.snack.server.tcp;

import com.google.common.collect.Lists;
import com.google.protobuf.Any;
import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.serialize.KryoHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * {@link io.jopen.snack.server.storage.RowStoreTable}
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
public interface Executor {

    /**
     * 转换数据
     *
     * @param any
     * @return
     */
    default IntermediateExpression<Row> convertByteArray2Expression(Any any) {

        if (any == null) {
            return IntermediateExpression.buildFor(Row.class);
        }

        byte[] bytes = any.getValue().toByteArray();
        try {
            return KryoHelper.deserialization(bytes, IntermediateExpression.class);
        } catch (Exception ignored) {
            return IntermediateExpression.buildFor(Row.class);
        }
    }

    default List<IntermediateExpression<Row>> convertByteArray2Expressions(List<Any> anyList) {

        if (anyList == null || anyList.size() == 0) {
            return Lists.newArrayList(IntermediateExpression.buildFor(Row.class));
        }

        List<IntermediateExpression<Row>> expressions = new ArrayList<>();

        for (Any any : anyList) {
            byte[] bytes = any.getValue().toByteArray();
            try {
                IntermediateExpression<Row> expression = KryoHelper.deserialization(bytes, IntermediateExpression.class);
                expressions.add(expression);
            } catch (Exception ignored) {
            }
        }
        return expressions;
    }

    default Collection<Map<String, Object>> query(IntermediateExpression<Row> expression) {
        return null;
    }

    default Collection<Map<String, Object>> query(List<IntermediateExpression<Row>> expressions) {
        return null;
    }
}
