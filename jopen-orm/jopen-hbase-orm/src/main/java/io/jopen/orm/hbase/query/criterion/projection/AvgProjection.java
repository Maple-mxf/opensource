package io.jopen.orm.hbase.query.criterion.projection;

import io.jopen.orm.hbase.query.criterion.Aggregate;

public class AvgProjection extends AggregateProjection{
    public AvgProjection(String propertyName) {
        super(Aggregate.AVG, propertyName);
    }
}
