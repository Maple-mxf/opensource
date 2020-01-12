package io.jopen.orm.hbase.query.criterion.expression;


import io.jopen.orm.hbase.query.criterion.Operator;

/**
 * 一元表达式 (is null, not null, is empty, not empty)
 */
public class UnaryExpression extends Expression {

    public UnaryExpression(Operator operator, String propertyName) {
        super(operator, propertyName);
    }

    @Override
    public String toString() {
        return getPropertyName() + " " + getOperator();
    }

}
