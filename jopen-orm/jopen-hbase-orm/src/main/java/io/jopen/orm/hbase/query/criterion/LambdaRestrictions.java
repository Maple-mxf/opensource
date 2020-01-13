package io.jopen.orm.hbase.query.criterion;

import io.jopen.orm.hbase.query.criterion.expression.*;
import io.jopen.orm.hbase.query.criterion.junction.Conjunction;
import io.jopen.orm.hbase.query.criterion.junction.Disjunction;

import java.util.Collection;

/**
 * An base lambda restrictions {@link Restrictions}
 * must base on {@link LambdaBuilder#SF_CACHE} local cache
 *
 * @author maxuefeng
 * @since 2020-01-13
 */
public final class LambdaRestrictions implements LambdaBuilder {
    private LambdaRestrictions() {
    }

    /**
     * Apply an "equal" constraint to the named property
     *
     * @param methodRef property name ref
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression eq(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.EQUAL, propertyName, value);
    }

    /**
     * Apply a "not equal" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression ne(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.NOT_EQUAL, propertyName, value);
    }

    /**
     * Apply a "less than" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression lt(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.LESS_THAN, propertyName, value);
    }

    /**
     * Apply a "like" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression like(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.LIKE, propertyName, value);
    }

    /**
     * Apply a "ilike" (case insensitive like) constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression ilike(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.ILIKE, propertyName, value);
    }

    /**
     * Apply a "less than or equal" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression lte(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.LESS_THAN_OR_EQUAL, propertyName, value);
    }

    /**
     * Apply a "greater than" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression gt(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.GREATER_THAN, propertyName, value);
    }

    /**
     * Apply a "greater than or equal" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return Criterion
     */
    public static EqualityExpression gte(SFunction<?, String> methodRef, Object value) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new EqualityExpression(Operator.GREATER_THAN_OR_EQUAL, propertyName, value);
    }

    /**
     * Apply a "between" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param from      Object
     * @param to        Object
     * @return Criterion
     */
    public static RangeExpression between(SFunction<?, String> methodRef, Object from, Object to) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new RangeExpression(Operator.BETWEEN, propertyName, from, to);
    }

    /**
     * Apply a "between" constraint to the named integer property with a finite, discrete number of values. This is
     * translated into an inclusive "in" expression.
     *
     * @param methodRef Lambda ref {@code ::}
     * @param from      Object
     * @param to        Object
     * @return Criterion
     */
    public static SetExpression discreteRange(SFunction<?, String> methodRef, int from, int to) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new SetExpression(Operator.IN, propertyName, from <= to ? range(from, to) : range(to, from));
    }

    /*
     * NOTE: this yields an Integer[], not an int[] to conform with the Object[] signature of the Expressions. Give
     * that, nulls are still unacceptable.
     */
    protected static Integer[] range(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from must be <= to (" + from + "," + to + ")");
        }
        int n = to - from + 1;
        Integer[] range = new Integer[n];
        for (int i = 0; i < n; i++) {
            range[i] = from + i;
        }
        return range;
    }

    /**
     * Apply an "in" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param values    Object[]
     * @return Criterion
     */
    public static SetExpression in(SFunction<?, String> methodRef, Object[] values) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new SetExpression(Operator.IN, propertyName, values);
    }

    /**
     * Apply an "in" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param values    Collection
     * @return Criterion
     */
    public static SetExpression in(SFunction<?, String> methodRef, Collection<? extends Object> values) {
        return in(methodRef, values.toArray());
    }

    /**
     * Apply a "not in" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param values    Object[]
     * @return Criterion
     */
    public static SetExpression notIn(SFunction<?, String> methodRef, Object[] values) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new SetExpression(Operator.NOT_IN, propertyName, values);
    }

    /**
     * Apply a "not in" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param values    Collection
     * @return Criterion
     */
    public static SetExpression notIn(SFunction<?, String> methodRef, Collection<? extends Object> values) {
        return notIn(methodRef, values.toArray());
    }

    /**
     * Apply a "contains" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param value     Object
     * @return SetExpression
     */
    public static SetExpression contains(SFunction<?, String> methodRef, Object value) {
        return contains(methodRef, new Object[]{value});
    }

    /**
     * Apply a "contains" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @param values    Object[]
     * @return Criterion
     */
    public static SetExpression contains(SFunction<?, String> methodRef, Object[] values) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new SetExpression(Operator.CONTAINS, propertyName, values);
    }

    /**
     * Apply an "is null" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @return Criterion
     */
    public static UnaryExpression isNull(SFunction<?, String> methodRef) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new UnaryExpression(Operator.NULL, propertyName);
    }

    /**
     * Apply an "is not null" constraint to the named property
     *
     * @param methodRef Lambda ref {@code ::}
     * @return Criterion
     */
    public static UnaryExpression isNotNull(SFunction<?, String> methodRef) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new UnaryExpression(Operator.NOT_NULL, propertyName);
    }

    /**
     * Constrain a collection valued property to be empty
     *
     * @param methodRef Lambda ref {@code ::}
     * @return UnaryExpression
     */
    public static UnaryExpression isEmpty(SFunction<?, String> methodRef) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new UnaryExpression(Operator.EMPTY, propertyName);
    }

    public static UnaryExpression isNotEmpty(SFunction<?, String> methodRef) {
        String propertyName = LambdaBuilder.produceValName.apply(methodRef);
        return new UnaryExpression(Operator.NOT_EMPTY, propertyName);
    }

    public static <T> NativeExpression nativeQuery(Class<T> type, T expression) {
        return new NativeExpression(type, expression);
    }

    /**
     * Return the conjunction of two expressions
     *
     * @param criteria Criterion
     * @return Conjunction
     */
    public static Conjunction and(Criterion... criteria) {
        return new Conjunction(criteria);
    }

    /**
     * Return the disjunction of two expressions
     *
     * @param criteria Criterion
     * @return Disjunction
     */
    public static Disjunction or(Criterion... criteria) {
        return new Disjunction(criteria);
    }
}
