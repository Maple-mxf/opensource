package io.jopen.orm.hbase.translator;

import io.jopen.orm.hbase.query.QuerySelect;
import io.jopen.orm.hbase.query.criterion.Aggregate;
import io.jopen.orm.hbase.query.criterion.Criterion;
import io.jopen.orm.hbase.query.criterion.Operator;
import io.jopen.orm.hbase.query.criterion.Ordering;
import io.jopen.orm.hbase.query.criterion.expression.*;
import io.jopen.orm.hbase.query.criterion.junction.Conjunction;
import io.jopen.orm.hbase.query.criterion.junction.Disjunction;
import io.jopen.orm.hbase.query.criterion.junction.Junction;
import io.jopen.orm.hbase.query.criterion.projection.AggregateProjection;
import io.jopen.orm.hbase.query.criterion.projection.GroupProjection;
import io.jopen.orm.hbase.query.criterion.projection.Projection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractQueryTranslator<Q, O, P> implements QueryTranslator<Q, O, P> {

    private final Class<Q> queryClass;
    private final Class<O> orderClass;
    private final PropertyResolver propertyResolver;

    public AbstractQueryTranslator(Class<Q> queryClass, Class<O> orderClass, PropertyResolver propertyResolver) {
        this.queryClass = queryClass;
        this.orderClass = orderClass;
        this.propertyResolver = propertyResolver;
    }

    protected Class<Q> getQueryClass() {
        return queryClass;
    }

    protected Class<O> getOrderClass() {
        return orderClass;
    }

    protected PropertyResolver getPropertyResolver() {
        return propertyResolver;
    }

    @Override
    public <T, R> Q translate(QuerySelect<T, R> query) {
        Criterion rootCriterion = query.getCriteria();
        Class<T> entityClass = query.getEntityClass();
        return translate(rootCriterion, entityClass);
    }

    protected <T> Q translate(Criterion c, Class<T> entityClass, AggregateProjection projection) {
        // a Criterion can be an Expression or a Junction
        if (c instanceof Expression) {
            return translate((Expression) c, entityClass, projection);
        } else if (c instanceof Junction) {
            return translate((Junction) c, entityClass);
        } else if (c instanceof GroupProjection) {
            return translate((GroupProjection) c, entityClass);
        } else if (c instanceof NativeExpression) {
            return translate((NativeExpression) c, entityClass);
        } else {
            throw unsupported(c.getClass());
        }
    }

    protected <T> Q translate(Criterion c, Class<T> entityClass) {
        return translate(c, entityClass, null);
    }

    protected abstract <T> Q translate(NativeExpression e, Class<T> entityClass);

    protected <T> Q translate(Expression e, Class<T> entityClass, AggregateProjection projection) {
        String fieldName = propertyResolver.resolve(e.getPropertyName(), entityClass);
        if (e instanceof EqualityExpression) {
            return translate((EqualityExpression) e, fieldName, projection);
        } else if (e instanceof RangeExpression) {
            return translate((RangeExpression) e, fieldName, projection);
        } else if (e instanceof SetExpression) {
            return translate((SetExpression) e, fieldName, projection);
        } else if (e instanceof UnaryExpression) {
            return translate((UnaryExpression) e, fieldName, projection);
        } else {
            throw unsupported(e.getClass());
        }
    }

    protected <T> Q translate(Projection projection, Class<T> entityClass) {
        if (projection instanceof GroupProjection) {
            List<String> propertyNames = projection.getPropertyNames();
            return translate((GroupProjection) projection,
                    propertyNames.stream()
                            .map(n -> propertyResolver.resolve(n, entityClass))
                            .toArray(String[]::new));
        } else {
            throw unsupported(projection.getClass());
        }
    }

    protected <T> Q translate(Junction j, Class<T> entityClass) {
        if (j instanceof Conjunction) {
            return translate((Conjunction) j, entityClass);
        } else if (j instanceof Disjunction) {
            return translate((Disjunction) j, entityClass);
        } else {
            throw unsupported(j.getClass());
        }
    }

    protected <T> Q translate(Conjunction j, Class<T> entityClass) {
        return and(subqueries(j, entityClass));
    }

    protected <T> Q translate(Disjunction j, Class<T> entityClass) {
        return or(subqueries(j, entityClass));
    }

    @SuppressWarnings("unchecked")
    protected <T> Q[] subqueries(Junction j, Class<T> entityClass) {
        List<Criterion> criteria = j.getCriteria();
        List<Q> translated = new ArrayList<Q>(criteria.size());
        for (Criterion c : criteria) {
            Q q = translate(c, entityClass, ((Expression) c).getAggregateProjection());
            if (q != null) {
                translated.add(q);
            }
        }
        return translated.toArray((Q[]) Array.newInstance(queryClass, translated.size()));
    }

    protected Q translate(EqualityExpression e, String fieldName, AggregateProjection projection) {
        Operator operator = e.getOperator();
        Object value = e.getValue();
        if (projection != null) {
            fieldName = (String) translate(projection, fieldName);
        }
        switch (operator) {
            case EQUAL:
                return eq(fieldName, value);
            case NOT_EQUAL:
                return ne(fieldName, value);
            case GREATER_THAN:
                return gt(fieldName, value);
            case GREATER_THAN_OR_EQUAL:
                return gte(fieldName, value);
            case LESS_THAN:
                return lt(fieldName, value);
            case LESS_THAN_OR_EQUAL:
                return lte(fieldName, value);
            case LIKE:
                return like(fieldName, value);
            case ILIKE:
                return insensitiveLike(fieldName, value);
            default:
                throw unsupported(operator, EqualityExpression.class);
        }
    }

    protected Q translate(RangeExpression e, String fieldName, AggregateProjection projection) {
        Operator operator = e.getOperator();
        Object from = e.getFrom();
        Object to = e.getTo();
        if (projection != null) {
            fieldName = (String) translate(projection, fieldName);
        }
        switch (operator) {
            case BETWEEN:
                return between(fieldName, from, to);
            default:
                throw unsupported(operator, RangeExpression.class);
        }
    }

    protected Q translate(SetExpression e, String fieldName, AggregateProjection projection) {
        Operator operator = e.getOperator();
        Object[] values = e.getValues();
        if (projection != null) {
            fieldName = (String) translate(projection, fieldName);
        }
        switch (operator) {
            case IN:
                return in(fieldName, values);
            case NOT_IN:
                return notIn(fieldName, values);
            case CONTAINS:
                return contains(fieldName, values);
            default:
                throw unsupported(operator, SetExpression.class);
        }
    }

    protected Q translate(UnaryExpression e, String fieldName, AggregateProjection projection) {
        Operator operator = e.getOperator();
        if (projection != null) {
            fieldName = (String) translate(projection, fieldName);
        }
        switch (operator) {
            case NULL:
                return isNull(fieldName);
            case NOT_NULL:
                return notNull(fieldName);
            case EMPTY:
                return isEmpty(fieldName);
            case NOT_EMPTY:
                return notEmpty(fieldName);
            default:
                throw unsupported(operator, UnaryExpression.class);
        }
    }

    protected P translate(AggregateProjection a, String fieldName) {
        Aggregate aggregate = a.getAggregate();
        switch (aggregate) {
            case AVG:
                return avg(fieldName);
            case MAX:
                return max(fieldName);
            case MIN:
                return min(fieldName);
            case COUNT:
                return count(fieldName);
            default:
                throw unsupported(aggregate, AggregateProjection.class);
        }
    }

    protected Q translate(GroupProjection g, String... fieldNames) {
        return groupBy(fieldNames);
    }


    @Override
    public <T, R> O translateOrder(QuerySelect<T, R> query) {
        List<Ordering> orderingList = query.getOrder().get();
        @SuppressWarnings("unchecked")
        O[] orders = (O[]) Array.newInstance(orderClass, orderingList.size());
        for (int i = 0; i < orders.length; i++) {
            Ordering ordering = orderingList.get(i);
            orders[i] = order(propertyResolver.resolve(ordering.getPropertyName(), query.getEntityClass()),
                    ordering);
        }
        return order(orders);
    }

    protected UnsupportedOperationException unsupported(Class<? extends Criterion> type) {
        throw new UnsupportedOperationException(type.getSimpleName() + " type not supported.");
    }

    protected UnsupportedOperationException unsupported(Operator operator, Class<? extends Expression> expressionType) {
        throw new UnsupportedOperationException(operator + " not supported for " + expressionType.getSimpleName());
    }

    protected UnsupportedOperationException unsupported(Aggregate aggregate, Class<? extends Projection> projectionType) {
        throw new UnsupportedOperationException(aggregate + " not supported for " + projectionType.getSimpleName());
    }

    protected UnsupportedOperationException unsupported(NativeExpression e) {
        throw new UnsupportedOperationException("Native Expression (" + e.getExpression() + ") of type "
                + e.getExpressionClass() + " not supported.");
    }

    public abstract Q eq(String fieldName, Object value);

    public abstract Q ne(String fieldName, Object value);

    public abstract Q lt(String fieldName, Object value);

    public abstract Q lte(String fieldName, Object value);

    public abstract Q gt(String fieldName, Object value);

    public abstract Q gte(String fieldName, Object value);

    public abstract Q between(String fieldName, Object from, Object to);

    public abstract Q in(String fieldName, Object[] values);

    public abstract Q notIn(String fieldName, Object[] values);

    public abstract Q contains(String fieldName, Object[] values);

    public abstract Q isNull(String fieldName);

    public abstract Q notNull(String fieldName);

    public abstract Q like(String fieldName, Object value);

    public abstract Q insensitiveLike(String fieldName, Object value);

    public abstract Q isEmpty(String fieldName);

    public abstract Q notEmpty(String fieldName);

    public abstract O order(String fieldName, Ordering ordering);

    public abstract O order(@SuppressWarnings("unchecked") O... orders);

    public abstract Q and(@SuppressWarnings("unchecked") Q... subqueries);

    public abstract Q or(@SuppressWarnings("unchecked") Q... subqueries);

    public abstract Q limit(Integer value);

    public abstract Q groupBy(String... fieldNames);

    public abstract P count(String fieldName);

    public abstract P avg(String fieldName);

    public abstract P countAll();

    public abstract P sum(String fieldName);

    public abstract P max(String fieldName);

    public abstract P min(String fieldName);

}
