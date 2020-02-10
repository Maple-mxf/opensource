package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

    MongoEntityInformation<T, ID> getEntityInformation();

    List<Map> groupSum(String sumField, String... groupFields);

    List<Map> groupSumBy(Criteria criteria, String sumField, String... groupFields);

    MapReduceResults<T> mapReduce(String mapFunction, String reduceFunction);

    <S extends T> Optional<S> findOne(Query query);

    <S extends T> S getOne(Query query);

    <S extends T> List<S> list(Query query);

    <S extends T> List<S> listSort(Query query, Sort sort);

    <S extends T> Page<S> page(Query query, Pageable pageable);

    <S extends T> long count(Query query);

    <S extends T> boolean exists(Query query);

}
