import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import dev.morphia.*;
import dev.morphia.InsertOptions;
import dev.morphia.aggregation.AggregationPipeline;
import dev.morphia.mapping.Mapper;
import dev.morphia.query.*;

import java.util.List;
import java.util.Map;

/**
 * @author maxuefeng
 * @since 2019/10/17
 */
public class DataStoreTransactionImpl implements AdvancedDatastore {

    @Override
    public DBDecoderFactory getDecoderFact() {
        return null;
    }

    @Override
    public void setDecoderFact(DBDecoderFactory fact) {

    }

    @Override
    public AggregationPipeline createAggregation(String collection, Class<?> clazz) {
        return null;
    }

    @Override
    public <T> Query<T> createQuery(String collection, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> Query<T> createQuery(Class<T> clazz, DBObject q) {
        return null;
    }

    @Override
    public <T> Query<T> createQuery(String collection, Class<T> clazz, DBObject q) {
        return null;
    }

    @Override
    public <T, V> DBRef createRef(Class<T> clazz, V id) {
        return null;
    }

    @Override
    public <T> DBRef createRef(T entity) {
        return null;
    }

    @Override
    public <T> UpdateOperations<T> createUpdateOperations(Class<T> type, DBObject ops) {
        return null;
    }

    @Override
    public <T, V> WriteResult delete(String kind, Class<T> clazz, V id) {
        return null;
    }

    @Override
    public <T, V> WriteResult delete(String kind, Class<T> clazz, V id, DeleteOptions options) {
        return null;
    }

    @Override
    public <T, V> WriteResult delete(String kind, Class<T> clazz, V id, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> void ensureIndex(String collection, Class<T> clazz, String fields) {

    }

    @Override
    public <T> void ensureIndex(String collection, Class<T> clazz, String name, String fields, boolean unique, boolean dropDupsOnCreate) {

    }

    @Override
    public <T> void ensureIndexes(String collection, Class<T> clazz) {

    }

    @Override
    public <T> void ensureIndexes(String collection, Class<T> clazz, boolean background) {

    }

    @Override
    public Key<?> exists(Object keyOrEntity, ReadPreference readPreference) {
        return null;
    }

    @Override
    public <T> Query<T> find(String collection, Class<T> clazz) {
        return null;
    }

    @Override
    public <T, V> Query<T> find(String collection, Class<T> clazz, String property, V value, int offset, int size) {
        return null;
    }

    @Override
    public <T> T get(Class<T> clazz, DBRef ref) {
        return null;
    }

    @Override
    public <T, V> T get(String collection, Class<T> clazz, V id) {
        return null;
    }

    @Override
    public long getCount(String collection) {
        return 0;
    }

    @Override
    public <T> Key<T> insert(T entity) {
        return null;
    }

    @Override
    public <T> Key<T> insert(T entity, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> Key<T> insert(T entity, InsertOptions options) {
        return null;
    }

    @Override
    public <T> Key<T> insert(String collection, T entity) {
        return null;
    }

    @Override
    public <T> Key<T> insert(String collection, T entity, InsertOptions options) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> insert(T... entities) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> insert(Iterable<T> entities) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> insert(Iterable<T> entities, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> insert(Iterable<T> entities, InsertOptions options) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> insert(String collection, Iterable<T> entities) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> insert(String collection, Iterable<T> entities, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> insert(String collection, Iterable<T> entities, InsertOptions options) {
        return null;
    }

    @Override
    public <T> Query<T> queryByExample(String collection, T example) {
        return null;
    }

    @Override
    public <T> Key<T> save(String collection, T entity) {
        return null;
    }

    @Override
    public <T> Key<T> save(String collection, T entity, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> Key<T> save(String collection, T entity, InsertOptions options) {
        return null;
    }

    @Override
    public AggregationPipeline createAggregation(Class source) {
        return null;
    }

    @Override
    public <T> Query<T> createQuery(Class<T> collection) {
        return null;
    }

    @Override
    public <T> UpdateOperations<T> createUpdateOperations(Class<T> clazz) {
        return null;
    }

    @Override
    public <T, V> WriteResult delete(Class<T> clazz, V id) {
        return null;
    }

    @Override
    public <T, V> WriteResult delete(Class<T> clazz, V id, DeleteOptions options) {
        return null;
    }

    @Override
    public <T, V> WriteResult delete(Class<T> clazz, Iterable<V> ids) {
        return null;
    }

    @Override
    public <T, V> WriteResult delete(Class<T> clazz, Iterable<V> ids, DeleteOptions options) {
        return null;
    }

    @Override
    public <T> WriteResult delete(Query<T> query) {
        return null;
    }

    @Override
    public <T> WriteResult delete(Query<T> query, DeleteOptions options) {
        return null;
    }

    @Override
    public <T> WriteResult delete(Query<T> query, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> WriteResult delete(T entity) {
        return null;
    }

    @Override
    public <T> WriteResult delete(T entity, DeleteOptions options) {
        return null;
    }

    @Override
    public <T> WriteResult delete(T entity, WriteConcern wc) {
        return null;
    }

    @Override
    public void ensureCaps() {

    }

    @Override
    public void enableDocumentValidation() {

    }

    @Override
    public <T> void ensureIndex(Class<T> clazz, String fields) {

    }

    @Override
    public <T> void ensureIndex(Class<T> clazz, String name, String fields, boolean unique, boolean dropDupsOnCreate) {

    }

    @Override
    public void ensureIndexes() {

    }

    @Override
    public void ensureIndexes(boolean background) {

    }

    @Override
    public <T> void ensureIndexes(Class<T> clazz) {

    }

    @Override
    public <T> void ensureIndexes(Class<T> clazz, boolean background) {

    }

    @Override
    public Key<?> exists(Object keyOrEntity) {
        return null;
    }

    @Override
    public <T> Query<T> find(Class<T> clazz) {
        return null;
    }

    @Override
    public <T, V> Query<T> find(Class<T> clazz, String property, V value) {
        return null;
    }

    @Override
    public <T, V> Query<T> find(Class<T> clazz, String property, V value, int offset, int size) {
        return null;
    }

    @Override
    public <T> T findAndDelete(Query<T> query) {
        return null;
    }

    @Override
    public <T> T findAndDelete(Query<T> query, FindAndModifyOptions options) {
        return null;
    }

    @Override
    public <T> T findAndModify(Query<T> query, UpdateOperations<T> operations, FindAndModifyOptions options) {
        return null;
    }

    @Override
    public <T> T findAndModify(Query<T> query, UpdateOperations<T> operations) {
        return null;
    }

    @Override
    public <T> T findAndModify(Query<T> query, UpdateOperations<T> operations, boolean oldVersion) {
        return null;
    }

    @Override
    public <T> T findAndModify(Query<T> query, UpdateOperations<T> operations, boolean oldVersion, boolean createIfMissing) {
        return null;
    }

    @Override
    public <T, V> Query<T> get(Class<T> clazz, Iterable<V> ids) {
        return null;
    }

    @Override
    public <T, V> T get(Class<T> clazz, V id) {
        return null;
    }

    @Override
    public <T> T get(T entity) {
        return null;
    }

    @Override
    public <T> T getByKey(Class<T> clazz, Key<T> key) {
        return null;
    }

    @Override
    public <T> List<T> getByKeys(Class<T> clazz, Iterable<Key<T>> keys) {
        return null;
    }

    @Override
    public <T> List<T> getByKeys(Iterable<Key<T>> keys) {
        return null;
    }

    @Override
    public DBCollection getCollection(Class<?> clazz) {
        return null;
    }

    @Override
    public <T> long getCount(T entity) {
        return 0;
    }

    @Override
    public <T> long getCount(Class<T> clazz) {
        return 0;
    }

    @Override
    public <T> long getCount(Query<T> query) {
        return 0;
    }

    @Override
    public <T> long getCount(Query<T> query, CountOptions options) {
        return 0;
    }

    @Override
    public DB getDB() {
        return null;
    }

    @Override
    public MongoDatabase getDatabase() {
        return null;
    }

    @Override
    public WriteConcern getDefaultWriteConcern() {
        return null;
    }

    @Override
    public void setDefaultWriteConcern(WriteConcern wc) {

    }

    @Override
    public <T> Key<T> getKey(T entity) {
        return null;
    }

    @Override
    public MongoClient getMongo() {
        return null;
    }

    @Override
    public QueryFactory getQueryFactory() {
        return null;
    }

    @Override
    public void setQueryFactory(QueryFactory queryFactory) {

    }

    @Override
    public <T> MapreduceResults<T> mapReduce(MapReduceOptions<T> options) {
        return null;
    }

    @Override
    public <T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, String map, String reduce, String finalize, Map<String, Object> scopeFields, Class<T> outputType) {
        return null;
    }

    @Override
    public <T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, Class<T> outputType, MapReduceCommand baseCommand) {
        return null;
    }

    @Override
    public <T> Key<T> merge(T entity) {
        return null;
    }

    @Override
    public <T> Key<T> merge(T entity, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> Query<T> queryByExample(T example) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> save(Iterable<T> entities) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> save(Iterable<T> entities, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> save(Iterable<T> entities, InsertOptions options) {
        return null;
    }

    @Override
    public <T> Iterable<Key<T>> save(T... entities) {
        return null;
    }

    @Override
    public <T> Key<T> save(T entity) {
        return null;
    }

    @Override
    public <T> Key<T> save(T entity, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> Key<T> save(T entity, InsertOptions options) {
        return null;
    }

    @Override
    public <T> UpdateResults update(T entity, UpdateOperations<T> operations) {
        return null;
    }

    @Override
    public <T> UpdateResults update(Key<T> key, UpdateOperations<T> operations) {
        return null;
    }

    @Override
    public <T> UpdateResults update(Query<T> query, UpdateOperations<T> operations) {
        return null;
    }

    @Override
    public <T> UpdateResults update(Query<T> query, UpdateOperations<T> operations, UpdateOptions options) {
        return null;
    }

    @Override
    public <T> UpdateResults update(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing) {
        return null;
    }

    @Override
    public <T> UpdateResults update(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> UpdateResults updateFirst(Query<T> query, UpdateOperations<T> operations) {
        return null;
    }

    @Override
    public <T> UpdateResults updateFirst(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing) {
        return null;
    }

    @Override
    public <T> UpdateResults updateFirst(Query<T> query, UpdateOperations<T> operations, boolean createIfMissing, WriteConcern wc) {
        return null;
    }

    @Override
    public <T> UpdateResults updateFirst(Query<T> query, T entity, boolean createIfMissing) {
        return null;
    }

    @Override
    public Mapper getMapper() {
        return null;
    }
}
