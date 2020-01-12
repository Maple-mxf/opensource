package io.jopen.orm.hbase.query.criterion.junction;

import io.jopen.orm.hbase.query.criterion.Criterion;
import io.jopen.orm.hbase.query.criterion.Operator;

/**
 * And “和”操作
 */
public class Conjunction extends Junction {

    public Conjunction() {
        super(Operator.AND);
    }

    public Conjunction(Criterion... criteria) {
        this();
        addAll(criteria);
    }
}
