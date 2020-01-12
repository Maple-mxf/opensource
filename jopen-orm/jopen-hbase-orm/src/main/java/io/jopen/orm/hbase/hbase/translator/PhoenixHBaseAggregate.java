package io.jopen.orm.hbase.hbase.translator;

import io.jopen.orm.hbase.query.criterion.Symbolic;

public enum PhoenixHBaseAggregate implements Symbolic {

    GROUP_BY("GROUP BY"),
    COUNT("COUNT"),
    AVG("AVG"),
    SUM("SUM"),
    MAX("MAX"),
    MIN("MIN");

    private final String symbol;

    PhoenixHBaseAggregate(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String symbol() {
        return symbol;
    }


    @Override
    public String toString() {
        return symbol();
    }
}
