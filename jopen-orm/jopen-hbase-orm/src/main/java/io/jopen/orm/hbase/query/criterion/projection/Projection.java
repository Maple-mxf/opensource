package io.jopen.orm.hbase.query.criterion.projection;

import io.jopen.orm.hbase.query.criterion.Aggregate;
import io.jopen.orm.hbase.query.criterion.Criterion;
import io.jopen.orm.hbase.query.criterion.WithAggregateFunction;

import java.util.Arrays;
import java.util.List;

public class Projection implements Criterion, WithAggregateFunction {
    private final Aggregate function;
    private final List<String> propertyNames;

    protected Projection(Aggregate function, String... propertyNames) {
        this.function = function;
        this.propertyNames = Arrays.asList(propertyNames);
    }

    @Override
    public Aggregate getAggregate() {
        return this.function;
    }

    public List<String> getPropertyNames() {
        return this.propertyNames;
    }
}
