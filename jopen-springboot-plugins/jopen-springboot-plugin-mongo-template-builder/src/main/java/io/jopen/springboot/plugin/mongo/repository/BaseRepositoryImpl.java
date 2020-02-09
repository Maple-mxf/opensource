package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

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

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
        this.entityInformation = metadata;
    }

    protected Class<T> getEntityClass() {
        return entityInformation.getJavaType();
    }

    @Override
    public T getTestObject(ID id) {

        return null;
    }
}
