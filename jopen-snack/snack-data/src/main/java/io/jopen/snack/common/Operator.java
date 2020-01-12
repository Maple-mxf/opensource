package io.jopen.snack.common;

/**
 * Query operator type enumeration
 */
public enum Operator {

    EQUAL("="),

    NOT_EQUAL("!="),

    GREATER_THAN(">"),

    GREATER_THAN_OR_EQUAL(">="),

    LESS_THAN("<"),

    LESS_THAN_OR_EQUAL("<="),

    BETWEEN("between"),

    LIKE("like"), ILIKE("ilike"),

    NULL("null"), NOT_NULL("not null"), EMPTY("empty"), NOT_EMPTY("not empty"),

    IN("in"), NOT_IN("not in"), CONTAINS("contains"),

    WITHIN("within"),

    AND("and"), OR("or");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }
}
