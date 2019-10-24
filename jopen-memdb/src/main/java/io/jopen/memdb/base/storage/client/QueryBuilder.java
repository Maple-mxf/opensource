package io.jopen.memdb.base.storage.client;

import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
class QueryBuilder<T> {

    private IntermediateExpression<T> expression;

    private List<T> willSaveBody;

    private MemDBClientInstance clientInstance;

    public MemDBClientInstance getClientInstance() {
        return clientInstance;
    }

    public IntermediateExpression<T> getExpression() {
        return expression;
    }

    QueryBuilder(IntermediateExpression<T> expression, MemDBClientInstance clientInstance) {
        this.expression = expression;
    }

    QueryBuilder(List<T> willSaveBody, MemDBClientInstance clientInstance) {
        this.willSaveBody = willSaveBody;
    }

    public List<T> getWillSaveBody() {
        return willSaveBody;
    }

    public void select() {

    }

    public void delete() {
    }

    //

    public Update update() {
        return new Update(this);
    }

    abstract class Carrier {
        QueryBuilder<T> queryBuilder;

        Actuator actuator = new Actuator();

        Carrier(QueryBuilder<T> queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public QueryBuilder<T> getQueryBuilder() {
            return queryBuilder;
        }

        public void setQueryBuilder(QueryBuilder<T> queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public Actuator getActuator() {
            return actuator;
        }

        public void setActuator(Actuator actuator) {
            this.actuator = actuator;
        }

    }

    class Update extends Carrier {
        private HashMap<String, Object> body = Maps.newLinkedHashMap();

        Update(QueryBuilder<T> queryBuilder) {
            super(queryBuilder);
        }

        @NonNull
        Update set(@NonNull String column, @NonNull Object value) {
            body.put(column, value);
            return this;
        }

        HashMap<String, Object> getBody() {
            return body;
        }

        int execute() {
            return actuator.update(this);
        }
    }

    class Select extends Carrier {
        Select(QueryBuilder<T> queryBuilder) {
            super(queryBuilder);
        }
    }

    class Delete extends Carrier {
        Delete(QueryBuilder<T> queryBuilder) {
            super(queryBuilder);
        }
    }

    class Save extends Carrier {
        Save(QueryBuilder<T> queryBuilder) {
            super(queryBuilder);
        }
    }
}
