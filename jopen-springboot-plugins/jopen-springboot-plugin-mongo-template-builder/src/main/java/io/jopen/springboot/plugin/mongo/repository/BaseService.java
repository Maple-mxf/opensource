package io.jopen.springboot.plugin.mongo.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
@NoRepositoryBean
public interface BaseService<ID,T> {

    /**
     * Returns a single entity matching the given {@link Example} or {@literal null} if none was found.
     *
     * @param example must not be {@literal null}.
     * @return a single entity matching the given {@link Example} or {@link Optional#empty()} if none was found.
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if the Example yields more than one result.
     */
    <S extends T> Optional<S> findOne(Example<S> example);

    /**
     * Returns all entities matching the given {@link Example}. In case no match could be found an empty {@link Iterable}
     * is returned.
     *
     * @param example must not be {@literal null}.
     * @return all entities matching the given {@link Example}.
     */
    <S extends T> Iterable<S> findAll(Example<S> example);

    /**
     * Returns all entities matching the given {@link Example} applying the given {@link Sort}. In case no match could be
     * found an empty {@link Iterable} is returned.
     *
     * @param example must not be {@literal null}.
     * @param sort the {@link Sort} specification to sort the results by, must not be {@literal null}.
     * @return all entities matching the given {@link Example}.
     * @since 1.10
     */
    <S extends T> Iterable<S> findAll(Example<S> example, Sort sort);

    /**
     * Returns a {@link Page} of entities matching the given {@link Example}. In case no match could be found, an empty
     * {@link Page} is returned.
     *
     * @param example must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @return a {@link Page} of entities matching the given {@link Example}.
     */
    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    /**
     * Returns the number of instances matching the given {@link Example}.
     *
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @return the number of instances matching the {@link Example}.
     */
    <S extends T> long count(Example<S> example);

    /**
     * Checks whether the data store contains elements that match the given {@link Example}.
     *
     * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
     * @return {@literal true} if the data store contains elements that match the given {@link Example}.
     */
    <S extends T> boolean exists(Example<S> example);

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @return all entities sorted by the given options
     */
    Iterable<T> findAll(Sort sort);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    List<T> findAll();


    /**
     * Inserts the given entity. Assumes the instance to be new to be able to apply insertion optimizations. Use the
     * returned instance for further operations as the save operation might have changed the entity instance completely.
     * Prefer using {@link #save(Object)} instead to avoid the usage of store-specific API.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity
     * @since 1.7
     */
    <S extends T> S insert(S entity);

    /**
     * Inserts the given entities. Assumes the given entities to have not been persisted yet and thus will optimize the
     * insert over a call to {@link #save(Iterable)}. Prefer using {@link #save(Iterable)} to avoid the usage of store
     * specific API.
     *
     * @param entities must not be {@literal null}.
     * @return the saved entities
     * @since 1.7
     */
    <S extends T> List<S> insert(Iterable<S> entities);


    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    <S extends T> S save(S entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<T> findById(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    boolean existsById(ID id);

    /**
     * Returns all instances of the type {@code T} with the given IDs.
     * <p>
     * If some or all ids are not found, no entities are returned for these IDs.
     * <p>
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     *         {@literal ids}.
     * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
     */
    Iterable<T> findAllById(Iterable<ID> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    void deleteById(ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
     */
    void deleteAll(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();
}
