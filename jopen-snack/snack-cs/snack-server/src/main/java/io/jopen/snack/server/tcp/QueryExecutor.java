package io.jopen.snack.server.tcp;

import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;

import java.util.Collection;
import java.util.Map;

/**
 * @author maxuefeng
 * @since 2019/10/26
 */
public class QueryExecutor implements Executor {

    void execute() {
    }

    @Override
    public Collection<Map<String, Object>> query(IntermediateExpression<Row> expression) {
        return null;
    }
}
