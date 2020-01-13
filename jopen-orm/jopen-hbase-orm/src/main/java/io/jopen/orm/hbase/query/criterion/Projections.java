package io.jopen.orm.hbase.query.criterion;

import io.jopen.orm.hbase.query.criterion.projection.*;

public class Projections {

    private Projections() {
    }

    public static GroupProjection groupBy(String... propertyNames ) {
        return new GroupProjection( propertyNames );
    }

    public static CountProjection count(String propertyName ) {
        return new CountProjection( propertyName );
    }

    public static MaxProjection max(String propertyName ) {
        return new MaxProjection( propertyName );
    }

    public static MinProjection min(String propertyName ) {
        return new MinProjection( propertyName );
    }

    public static AvgProjection avg(String propertyName ) {
        return new AvgProjection( propertyName );
    }
}
