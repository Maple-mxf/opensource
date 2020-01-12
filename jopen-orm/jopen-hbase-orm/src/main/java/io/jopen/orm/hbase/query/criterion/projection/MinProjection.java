package io.jopen.orm.hbase.query.criterion.projection;

import io.jopen.orm.hbase.query.criterion.Aggregate;

public class MinProjection extends AggregateProjection {
    public MinProjection(String propertyName) {
        super(Aggregate.MIN,  propertyName);
    }
}
