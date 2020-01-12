package io.jopen.orm.hbase.query.builder;

import io.jopen.orm.hbase.query.QueryOperationType;
import io.jopen.orm.hbase.query.QuerySelect;
import io.jopen.orm.hbase.query.QuerySelectImpl;
import io.jopen.orm.hbase.query.criterion.Criterion;
import io.jopen.orm.hbase.query.criterion.Ordering;
import io.jopen.orm.hbase.query.criterion.Orderings;
import io.jopen.orm.hbase.query.criterion.Restrictions;
import io.jopen.orm.hbase.query.criterion.projection.Projection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QueryBuilder<T, R> {

    private final Class<T> entityClass;
    private final Class<R> returnType;
    private List<Criterion> criteria = new ArrayList<>();
    private List<Criterion> groupCriteria = new ArrayList<>();
    private Orderings orderings = new Orderings();
    private List<Projection> projections = new ArrayList<>();
    private Integer maxResults;
    private List<String> returnFields = Collections.emptyList();
    private QueryOperationType queryOperationType;
    private String queryHint;

    public QueryBuilder(Class<T> entityClass, Class<R> returnType) {
        this.entityClass = entityClass;
        this.returnType = returnType;
    }

    public static <T, R> QueryBuilder<T, R> builderFor(Class<T> entityClass, Class<R> returnType,
                                                       String... returnFields) {
        return new QueryBuilder<T, R>(entityClass, returnType).setReturnFields(returnFields);
    }

    public static <T> QueryBuilder<T, T> builderFor(Class<T> entityClass) {
        return new QueryBuilder<T, T>(entityClass, entityClass);
    }

    public QueryBuilder<T, R> select() {
        queryOperationType = QueryOperationType.SELECT;
        return this;
    }

    public QueryBuilder<T, R> select(List<String> returnFields) {
        queryOperationType = QueryOperationType.SELECT;
        this.returnFields = returnFields;
        return this;
    }

    public QueryBuilder<T, R> update() {
        queryOperationType = QueryOperationType.UPDATE;
        return this;
    }

    public QueryBuilder<T, R> update(List<String> selectedFields) {
        queryOperationType = QueryOperationType.UPDATE;
        this.returnFields = selectedFields;
        return this;
    }

    public QueryBuilder<T, R> create() {
        queryOperationType = QueryOperationType.INSERT;
        return this;
    }

    public QueryBuilder<T, R> create(List<String> fields) {
        queryOperationType = QueryOperationType.INSERT;
        this.returnFields = fields;
        return this;
    }

    public QueryBuilder<T, R> delete() {
        queryOperationType = QueryOperationType.DELETE;
        return this;
    }

    public QueryBuilder<T, R> add(Criterion criterion) {
        criteria.add(criterion);
        return this;
    }

    public QueryBuilder<T, R> addOrder(Ordering... orders) {

        return addOrder(Arrays.asList(orders));

    }

    public QueryBuilder<T, R> addOrder(Iterable<Ordering> orders) {
        for (Ordering order : orders) {
            orderings.add(order);
        }
        return this;
    }

    public QueryBuilder<T, R> addProjection(Projection... projections) {
        this.projections.addAll(Arrays.asList(projections));
        return this;
    }

    public QueryBuilder<T, R> addGroupCriterion(Criterion groupCriterion) {
        groupCriteria.add(groupCriterion);
        return this;
    }

    public QueryBuilder<T, R> setReturnFields(String... returnFields) {
        this.returnFields = Arrays.asList(returnFields);
        return this;
    }

    public QueryBuilder<T, R> setMaxResults(int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public QueryBuilder<T, R> setQueryOperationType(QueryOperationType queryOperationType) {
        this.queryOperationType = queryOperationType;
        return this;
    }

    public QueryBuilder<T, R> setQueryHint(String queryHint) {
        this.queryHint = queryHint;
        return this;
    }

    public QuerySelect<T, R> build() {
        // if criteria.size == 0, rootCriterion = null
        Criterion rootCriterion = bindCriterion(this.criteria);
        Criterion groupCriterion = bindCriterion(this.groupCriteria);
        return new QuerySelectImpl<T, R>(entityClass, returnType, rootCriterion, groupCriterion, orderings, maxResults, returnFields,
                projections, queryOperationType, queryHint);
    }

    private Criterion bindCriterion(List<Criterion> criteria) {
        Criterion criterion = null;
        if (criteria.size() == 1) {
            criterion = criteria.get(0);
        } else if (criteria.size() > 1) {
            criterion = Restrictions.and(criteria.toArray(new Criterion[criteria.size()]));
        }
        return criterion;
    }

    @Override
    public String toString() {
        return "QueryBuilder [entityClass=" + entityClass + ", criteria=" + criteria + ", orderings=" + orderings
                + ", maxResults=" + maxResults + "]";
    }

}
