package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
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
 * 实现mongodb的embedded文档的条件查询/分页查询/聚合查询  内嵌文档的添加/删除/修改
 *
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

    /**
     * 索引管理
     *
     * @see org.springframework.data.mongodb.core.index.Index
     */
    String ensureIndex(Index index);

    List<IndexInfo> getIndexInfo();

//    boolean update(Wrapper<T> updateWrapper);
//
//    boolean update(T entity, Wrapper<T> updateWrapper);
//
//    boolean updateById(T entity);
//
//    boolean updateBatchById(Collection<T> entityList);
//
//    boolean updateBatchById(Collection<T> entityList, int batchSize);
}
