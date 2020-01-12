package io.jopen.orm.hbase.query;

import io.jopen.orm.hbase.query.criterion.Criterion;
import io.jopen.orm.hbase.query.criterion.Orderings;
import io.jopen.orm.hbase.query.criterion.projection.Projection;

import java.util.List;


/**
 * A generic, object oriented representation of a query
 * 
 * @param <T>
 *            the entity type being queried
 * @param <R>
 *            the desired return type
 */
public interface QuerySelect<T, R> {

    /**
     * Get the queried entity type.
     * 
     * @return the entity class being queried
     */
    Class<T> getEntityClass();

    /**
     * Get the return entity type.
     * 
     * @return the class of the desired return type
     */
    Class<R> getReturnType();

    /**
     * The list of properties to return. An empty collection means all properties.
     * 
     * @return the fields to be returned from the query
     */
    List<String> getReturnFields();

    /**
     * Get the query criteria. The top level object can either be a single Criterion or a Junction of multiple nested
     * Criterion objects.
     * 
     * @return the root criterion node
     */
    Criterion getCriteria();

    Criterion getGroupCriteria();

    /**
     * Get the Order clauses.
     * 
     * @return the ordering clauses
     */
    Orderings getOrder();

    List<Projection> getProjection();

    /**
     * Get the max desired results. Null signifies no maximum.
     * 
     * @return the maximum number of results or null if no maximum
     * 
     */
    Integer getMaxResults();

    /**
     * Getter method for Query Operation type
     * 
     * @return <code>QueryOperationType</code> 
     */
    QueryOperationType getQueryOperationType();

    /**
     * Getter method for Query Hint
     *
     * @return <code>String</code>
     */
    String getQueryHint();

}
