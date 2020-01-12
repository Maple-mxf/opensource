package io.jopen.orm.hbase.hbase.translator;

import io.jopen.orm.hbase.query.criterion.Symbolic;

public enum PhoenixHBaseClauses implements Symbolic {
    FROM("FROM"),
    WHERE("WHERE"),
    HAVING("HAVING"),
    ORDER_BY("ORDER BY"),
    LIMIT("LIMIT");

    private final String symbol;

    private PhoenixHBaseClauses(String symbol) {
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
