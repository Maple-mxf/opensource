package io.jopen.orm.hbase.query.criterion.projection;

import io.jopen.orm.hbase.query.criterion.Aggregate;

public class GroupProjection extends Projection{

    public GroupProjection(String... propertyNames) {
        super(Aggregate.GROUP_BY, propertyNames);
    }

    @Override
    public String toString() {
        return "GroupProjection{" + getPropertyNames() + "}";
    }
}
