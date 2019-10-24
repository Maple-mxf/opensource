package io.jopen.memdb.base.storage.client;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
class Builder<T> {

    private IntermediateExpression<T> expression;

    public IntermediateExpression<T> getExpression() {
        return expression;
    }

    public Builder(IntermediateExpression<T> expression) {
        this.expression = expression;
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

        Object execute(){
            actuator.equals();
        };
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

        @Override
        Object execute() {
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
}
