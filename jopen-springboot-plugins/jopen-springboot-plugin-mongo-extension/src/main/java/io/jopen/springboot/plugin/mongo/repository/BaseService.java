package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @see com.mongodb.client.gridfs.model.GridFSFile
 * @since 2020/2/9
 */
@NoRepositoryBean
public interface BaseService<ID, T> {

    <S extends T> Optional<S> findOne(Example<S> example);

    /**
     * @param query query
     * @param <S>   entity type
     * @return {@link Optional}
     * @see io.jopen.springboot.plugin.mongo.template.builder.QueryBuilder
     */
    <S extends T> Optional<S> findOne(Query query);

    /**
     * @param example
     * @param <S>
     * @return
     * @see org.springframework.data.domain.ExampleMatcher
     */
    <S extends T> S getOne(Example<S> example);

    <S extends T> S getOne(Query query);

    <S extends T> List<S> list(Example<S> example);

    <S extends T> List<S> list(Query query);

    <S extends T> Stream<S> stream(Example<S> example);

    <S extends T> Stream<S> stream(Query query);

    <S extends T> List<S> listSort(Example<S> example, Sort sort);

    <S extends T> List<S> listSort(Query query, Sort sort);

    <S extends T> Page<S> page(Example<S> example, Pageable pageable);

    <S extends T> Page<S> page(Query query, Pageable pageable);

    <S extends T> long count(Example<S> example);

    <S extends T> long count(Query query);

    <S extends T> boolean exists(Example<S> example);

    <S extends T> boolean exists(Query query);

    <S extends T> List<S> listSort(Sort sort);

    <S extends T> Page<S> page(Pageable pageable);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    <S extends T> List<S> list();

    <S extends T> S insert(S entity);

    <S extends T> List<S> insert(Iterable<S> entities);

    <S extends T> S save(S entity);

    <S extends T> Optional<S> findById(ID id);

    boolean existsById(ID id);

    <S extends T> Iterable<S> findAllById(Iterable<ID> ids);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();

    List<Map> groupSum(String sumField, String... groupFields);

    List<Map> groupSumBy(Criteria criteria, String sumField, String... groupFields);

    MapReduceResults<T> mapReduce(String mapFunction, String reduceFunction);

    <F extends GridFsObject> F findFileBy(Query query);

    <F extends GridFsObject> F findFileById(ID id);

    /**
     * 索引管理
     *
     * @see org.springframework.data.mongodb.core.index.Index
     */
    String ensureIndex(Index index);

    List<IndexInfo> getIndexInfo();
}
