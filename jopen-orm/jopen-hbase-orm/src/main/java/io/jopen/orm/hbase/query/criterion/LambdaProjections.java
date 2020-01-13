package io.jopen.orm.hbase.query.criterion;

import io.jopen.orm.hbase.query.criterion.projection.*;

/**
 * @author maxuefeng
 * @see Projections
 * @since 2020-01-13
 */
public final
class LambdaProjections extends LambdaBuilder {
    private LambdaProjections() {
    }

    public static GroupProjection groupBy(SFunction<?, ?>... sFunctions) {
        String[] propertyNames = new String[sFunctions.length];
        for (int i = 0; i < sFunctions.length; i++)
            propertyNames[i] = produceValName.apply(sFunctions[i]);

        return new GroupProjection(propertyNames);
    }

    public static CountProjection count(SFunction<?, ?> sFunction) {
        String propertyName = produceValName.apply(sFunction);
        return new CountProjection(propertyName);
    }

    public static MaxProjection max(SFunction<?, ?> sFunction) {
        String propertyName = produceValName.apply(sFunction);
        return new MaxProjection(propertyName);
    }

    public static MinProjection min(SFunction<?, ?> sFunction) {
        String propertyName = produceValName.apply(sFunction);
        return new MinProjection(propertyName);
    }

    public static AvgProjection avg(SFunction<?, ?> sFunction) {
        String propertyName = produceValName.apply(sFunction);
        return new AvgProjection(propertyName);
    }

}
