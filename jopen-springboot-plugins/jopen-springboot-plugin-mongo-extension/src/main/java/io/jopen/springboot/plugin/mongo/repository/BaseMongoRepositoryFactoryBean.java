package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.support.QuerydslMongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

import static org.springframework.data.querydsl.QuerydslUtils.QUERY_DSL_PRESENT;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
public class BaseMongoRepositoryFactoryBean<T extends MongoRepository<S, ID>, S, ID extends Serializable>
        extends MongoRepositoryFactoryBean<T, S, ID> {

    /**
     * Creates a new {@link MongoRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public BaseMongoRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
        return super.getFactoryInstance(operations);
    }

    private static class LCRRepositoryFactory<S, ID extends Serializable> extends MongoRepositoryFactory {

        private final MongoOperations mongoOperations;

        public LCRRepositoryFactory(MongoOperations mongoOperations) {
            super(mongoOperations);
            this.mongoOperations = mongoOperations;
        }

        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            Class<?> repositoryInterface = information.getRepositoryInterface();
            MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            /*if (isQueryDslRepository(repositoryInterface)) {
                return new QuerydslMongoRepository(entityInformation, mongoOperations);
            } else {
                return new BaseRepositoryImpl<S, ID>((MongoEntityInformation<S, ID>) entityInformation, this.mongoOperations);
            }*/
            return new BaseRepositoryImpl<S, ID>((MongoEntityInformation<S, ID>) entityInformation, this.mongoOperations);
        }


        /**
         * @see org.springframework.data.querydsl.QuerydslPredicateExecutor
         * @see org.springframework.data.mongodb.repository.support.QuerydslMongoRepository
         * @param repositoryInterface
         * @return
         */
        @Deprecated
        private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
            return QUERY_DSL_PRESENT && QuerydslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return isQueryDslRepository(metadata.getRepositoryInterface()) ? QuerydslMongoRepository.class
                    : BaseRepositoryImpl.class;
        }
    }
}
