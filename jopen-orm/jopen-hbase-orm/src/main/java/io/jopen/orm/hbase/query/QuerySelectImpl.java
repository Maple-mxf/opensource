package io.jopen.orm.hbase.query;

import io.jopen.orm.hbase.query.criterion.Criterion;
import io.jopen.orm.hbase.query.criterion.Orderings;
import io.jopen.orm.hbase.query.criterion.projection.Projection;

import java.util.List;

/**
 * 默认实现查询类
 *
 * @param <T> the entity type being queried
 * @param <R> the desired return type
 */
public class QuerySelectImpl<T, R> implements QuerySelect<T, R> {

    private final Class<T> entityClass;
    private final Class<R> returnType;
    private final Criterion criteria;
    private final Criterion groupCriterion;
    private final Orderings orderings;
    private final Integer maxResults;
    private final List<String> returnFields;
    private final List<Projection> projections;
    private final QueryOperationType queryOperationType;
    private final String queryHint;

    public QuerySelectImpl(Class<T> entityClass, Class<R> returnType, Criterion criteria, Criterion groupCriterion, Orderings orderings,
                           Integer maxResults, List<String> returnFields, List<Projection> projections, QueryOperationType queryOperationType, String queryHint) {
        this.entityClass = entityClass;
        this.returnType = returnType;
        this.criteria = criteria;
        this.groupCriterion = groupCriterion;
        this.returnFields = returnFields;
        this.orderings = orderings;
        this.maxResults = maxResults;
        this.projections = projections;
        this.queryOperationType = queryOperationType;
        this.queryHint = queryHint;
    }

    @Override
    public QueryOperationType getQueryOperationType() {
        return queryOperationType;
    }

    @Override
    public String getQueryHint() {
        return queryHint;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public Class<R> getReturnType() {
        return returnType;
    }

    @Override
    public List<String> getReturnFields() {
        return returnFields;
    }

    @Override
    public Criterion getCriteria() {
        return criteria;
    }

    @Override
    public Criterion getGroupCriteria() {
        return groupCriterion;
    }

    @Override
    public Orderings getOrder() {
        return orderings;
    }

    @Override
    public List<Projection> getProjection() {
        return this.projections;
    }


    @Override
    public Integer getMaxResults() {
        return maxResults;
    }

    @Override
    public String toString() {
        return "QueryImpl [entityClass=" + entityClass + ", criteria=" + criteria + ", orderings=" + orderings
                + ", maxResults=" + maxResults + "]";
    }
}
