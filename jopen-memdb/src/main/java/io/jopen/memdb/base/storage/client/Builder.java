package io.jopen.memdb.base.storage.client;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
class Builder<T> {

    private IntermediateExpression<T> expression;

    private List<T> willSaveBody;

    public IntermediateExpression<T> getExpression() {
        return expression;
    }

    Builder(IntermediateExpression<T> expression) {
        this.expression = expression;
    }

    Builder(List<T> willSaveBody) {
        this.willSaveBody = willSaveBody;
    }

    public void select() {

    }

    public void delete() {
    }

    //

    public Update update() {
        return new Update<>(this);
    }

    abstract class Carrier<O> {
        Builder<O> builder;

        Actuator actuator = new Actuator();

        Carrier(Builder<O> builder) {
            this.builder = builder;
        }

        public Builder<O> getBuilder() {
            return builder;
        }

        public void setBuilder(Builder<O> builder) {
            this.builder = builder;
        }

        public Actuator getActuator() {
            return actuator;
        }

        public void setActuator(Actuator actuator) {
            this.actuator = actuator;
        }

    }

    class Update<O> extends Carrier<O> {
        private HashMap<String, Object> body = Maps.newLinkedHashMap();

        Update(Builder<O> builder) {
            super(builder);
        }

        Update set(String column, Object value) {
            body.put(column, value);
            return this;
        }

        HashMap<String, Object> getBody() {
            return body;
        }

        <ID> List<ID> execute() {
            actuator.update(this);
            return null;
        }
    }

    class Select<O> extends Carrier<O> {
        Select(Builder<O> builder) {
            super(builder);
        }
    }

    class Delete<O> extends Carrier<O> {
        Delete(Builder<O> builder) {
            super(builder);
        }
    }

    class Save<O> extends Carrier<O> {
        Save(Builder<O> builder) {
            super(builder);
        }
    }
}
