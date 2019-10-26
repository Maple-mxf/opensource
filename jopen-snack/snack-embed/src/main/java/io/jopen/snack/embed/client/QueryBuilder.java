package io.jopen.snack.embed.client;

import com.google.common.collect.Maps;
import io.jopen.snack.common.IntermediateExpression;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public class QueryBuilder<T> {

    private IntermediateExpression<T> expression;

    private List<T> beans;

    private LeopardClient clientInstance;

    public LeopardClient getClientInstance() {
        return clientInstance;
    }

    public IntermediateExpression<T> getExpression() {
        return expression;
    }

    QueryBuilder(IntermediateExpression<T> expression, LeopardClient clientInstance) {
        this.expression = expression;
        this.clientInstance = clientInstance;
    }

    QueryBuilder(List<T> beans, LeopardClient clientInstance) {
        this.clientInstance = clientInstance;
        this.beans = beans;
    }

    public List<T> getBeans() {
        return beans;
    }

    public Select select() {
        return new Select(this);
    }

    public Delete delete() {
        return new Delete(this);
    }

    public Save save() {
        return new Save(this);
    }

    //

    public Update update() {
        return new Update(this);
    }

    public abstract class Carrier {
        QueryBuilder<T> queryBuilder;

        Actuator actuator = new Actuator();

        Carrier(QueryBuilder<T> queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public QueryBuilder<T> getQueryBuilder() {
            return queryBuilder;
        }

        public void setQueryBuilder(@NonNull QueryBuilder<T> queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public Actuator getActuator() {
            return actuator;
        }

        public void setActuator(Actuator actuator) {
            this.actuator = actuator;
        }

    }

    public class Update extends Carrier {
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

        public int execute() {
            return actuator.update(this);
        }
    }

    public class Select extends Carrier {
        Select(QueryBuilder<T> queryBuilder) {
            super(queryBuilder);
        }

        public Collection<T> execute() throws Throwable {
            return actuator.select(this);
        }
    }

    public class Delete extends Carrier {
        Delete(@Nullable QueryBuilder<T> queryBuilder) {
            super(queryBuilder);
        }

        // 执行删除操作
        public int execute() {
            return actuator.delete(this);
        }

    }

    public class Save extends Carrier {
        public Save(QueryBuilder<T> queryBuilder) {
            super(queryBuilder);
        }

        public int execute() {
            return actuator.save(this);
        }
    }
}
