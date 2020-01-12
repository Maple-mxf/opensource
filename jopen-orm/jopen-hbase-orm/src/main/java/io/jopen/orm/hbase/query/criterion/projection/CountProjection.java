package io.jopen.orm.hbase.query.criterion.projection;

import io.jopen.orm.hbase.query.criterion.Aggregate;

public class CountProjection extends AggregateProjection{
    public CountProjection(String propertyName) {
        super(Aggregate.COUNT, propertyName);
    }
}
