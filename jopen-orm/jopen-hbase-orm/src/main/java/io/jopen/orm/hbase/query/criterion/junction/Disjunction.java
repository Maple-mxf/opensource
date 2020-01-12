package io.jopen.orm.hbase.query.criterion.junction;


import io.jopen.orm.hbase.query.criterion.Criterion;
import io.jopen.orm.hbase.query.criterion.Operator;

/**
 * Or “或者”
 */
public class Disjunction extends Junction {

    public Disjunction() {
        super(Operator.OR);
    }

    public Disjunction(Criterion... criteria) {
        this();
        addAll(criteria);
    }

}