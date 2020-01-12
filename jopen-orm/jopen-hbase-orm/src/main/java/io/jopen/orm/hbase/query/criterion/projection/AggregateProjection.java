package io.jopen.orm.hbase.query.criterion.projection;

import io.jopen.orm.hbase.query.criterion.Aggregate;

public class AggregateProjection extends Projection {
    private String propertyName;
    private Aggregate function;

    protected AggregateProjection(Aggregate function, String propertyName) {
        super(function, propertyName);
        this.propertyName = propertyName;
        this.function = function;
    }

    public String getPropertyName(){
        return propertyName;
    }

    public AggregateProjection setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public String getName() {
        return function.symbol() + "(" + propertyName + ")";
    }
}
