package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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


}
