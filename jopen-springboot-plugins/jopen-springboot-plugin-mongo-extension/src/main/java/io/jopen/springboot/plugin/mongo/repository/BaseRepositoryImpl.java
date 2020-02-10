package io.jopen.springboot.plugin.mongo.repository;

import io.jopen.springboot.plugin.mongo.template.builder.AggregationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable>
        extends SimpleMongoRepository<T, ID>
        implements BaseRepository<T, ID> {


    private final MongoOperations mongoOperations;

    private final MongoEntityInformation<T, ID> entityInformation;

    public MongoEntityInformation<T, ID> getEntityInformation() {
        return this.entityInformation;
    }

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata,
                              MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
        this.entityInformation = metadata;
    }

    @Override
    public <S extends T> Optional<S> findOne(Query query) {
        return (Optional<S>) Optional.ofNullable(mongoOperations.findOne(query, entityInformation.getJavaType()));
    }

    @Override
    public <S extends T> S getOne(Query query) {
        return (S) this.findOne(query).orElse(null);
    }

    @Override
    public <S extends T> List<S> list(Query query) {
        List<T> ts = mongoOperations.find(query, entityInformation.getJavaType());
        return (List<S>) ts;
    }

    @Override
    public <S extends T> List<S> listSort(Query query, Sort sort) {
        query.with(sort);
        return this.list(query);
    }

    @Override
    public <S extends T> boolean exists(Query query) {
        return this.mongoOperations.exists(query,entityInformation.getJavaType());
    }

    @Override
    public <S extends T> long count(Query query) {
        return this.mongoOperations.count(query, entityInformation.getJavaType());
    }

    @Override
    public <S extends T> Page<S> page(Query query, Pageable pageable) {
        query.with(pageable);
        List<T> result = mongoOperations.find(query, entityInformation.getJavaType());
        Page<T> page = PageableExecutionUtils.getPage(result, pageable,
                () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1),
                        entityInformation.getCollectionName()));

        return (Page<S>) page;
    }

    /**
     * @see AggregationBuilder
     */
    public List<Map> groupSum(String sumField, String... groupFields) {
        GroupOperation groupOperation = Aggregation.group(groupFields).sum(sumField).as(sumField);
        Aggregation aggregation = Aggregation.newAggregation(groupOperation);
        return mongoOperations.aggregate(aggregation, this.entityInformation.getJavaType(), Map.class)
                .getMappedResults();
    }

    /**
     * @param sumField
     * @param groupFields
     * @return
     * @see org.springframework.data.mongodb.core.query.Query
     * @see io.jopen.springboot.plugin.mongo.template.builder.QueryBuilder
     */
    @Override
    public List<Map> groupSumBy(Criteria criteria, String sumField, String... groupFields) {

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = Aggregation.group(groupFields).sum(sumField).as(sumField);
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation);
        return mongoOperations.aggregate(aggregation, this.entityInformation.getJavaType(), Map.class)
                .getMappedResults();
    }

    @Override
    public MapReduceResults<T> mapReduce(String mapFunction, String reduceFunction) {
        return this.mongoOperations.mapReduce(this.entityInformation.getCollectionName(),
                mapFunction,
                reduceFunction,
                this.entityInformation.getJavaType());
    }


}
