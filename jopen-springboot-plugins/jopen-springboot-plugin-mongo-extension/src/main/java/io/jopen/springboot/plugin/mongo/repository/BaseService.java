package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
@NoRepositoryBean
public interface BaseService<ID, T> {

    <S extends T> Optional<S> findOne(Example<S> example);

    <S extends T> S getOne(Example<S> example);

    <S extends T> Iterable<S> list(Example<S> example);

    <S extends T> Stream<S> stream(Example<S> example);

    <S extends T> Iterable<S> listSort(Example<S> example, Sort sort);

    <S extends T> Page<S> listPage(Example<S> example, Pageable pageable);

    <S extends T> Page<S> page(Example<S> example, Pageable pageable);

    <S extends T> long count(Example<S> example);

    <S extends T> boolean exists(Example<S> example);

    List<T> listSort(Sort sort);

    Page<T> listPage(Pageable pageable);

    Page<T> page(Pageable pageable);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    List<T> list();

    <S extends T> S insert(S entity);

    <S extends T> List<S> insert(Iterable<S> entities);

    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAllById(Iterable<ID> ids);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();

    List<Map> groupSum(String sumField, String... groupFields);

    List<Map> groupSumBy(Criteria criteria, String sumField, String... groupFields);

    MapReduceResults<T> mapReduce(String mapFunction, String reduceFunction);
}
