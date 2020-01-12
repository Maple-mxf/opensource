package io.jopen.orm.hbase.query.criterion.projection;

import io.jopen.orm.hbase.query.criterion.Aggregate;

public class MaxProjection extends AggregateProjection {
    public MaxProjection(String propertyName) {
        super(Aggregate.MAX, propertyName);
    }
}
