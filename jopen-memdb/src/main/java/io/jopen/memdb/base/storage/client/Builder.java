package io.jopen.memdb.base.storage.client;

import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
class Builder<T> {

    private IntermediateExpression<T> expression;

    private List<T> willSaveBody;

    private MemDBClientInstance clientInstance;

    public MemDBClientInstance getClientInstance() {
        return clientInstance;
    }

    public IntermediateExpression<T> getExpression() {
        return expression;
    }

    Builder(IntermediateExpression<T> expression, MemDBClientInstance clientInstance) {
        this.expression = expression;
    }

    Builder(List<T> willSaveBody, MemDBClientInstance clientInstance) {
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
        Builder<T> builder;

        Actuator actuator = new Actuator();

        Carrier(Builder<T> builder) {
            this.builder = builder;
        }

        public Builder<T> getBuilder() {
            return builder;
        }

        public void setBuilder(Builder<T> builder) {
            this.builder = builder;
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

        Update(Builder<T> builder) {
            super(builder);
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
        Select(Builder<T> builder) {
            super(builder);
        }
    }

    class Delete extends Carrier {
        Delete(Builder<T> builder) {
            super(builder);
        }
    }

    class Save extends Carrier {
        Save(Builder<T> builder) {
            super(builder);
        }
    }
}
