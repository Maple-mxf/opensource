package io.jopen.orm.hbase.api;


import io.jopen.orm.hbase.query.QuerySelect;

import java.util.List;
import java.util.Set;

public interface DataStoreApi {

    <T> T save(T entity);

    <T> Iterable<T> save(Iterable<T> entities);

    <T> int[] saveBatch(Iterable<T> entities);

    <T, R> Iterable<R> findAll(QuerySelect<T, R> query);

    <T, R> List<R> findList(QuerySelect<T, R> query);

    <T, R> Set<R> findSet(QuerySelect<T, R> query);

    <T, R> R findOne(QuerySelect<T, R> query);

    <T> T save(T entity, List<String> selectedFields);
}
