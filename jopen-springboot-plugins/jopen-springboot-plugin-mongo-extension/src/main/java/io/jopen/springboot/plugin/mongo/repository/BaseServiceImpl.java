package io.jopen.springboot.plugin.mongo.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
@NoRepositoryBean
public class BaseServiceImpl<ID extends Serializable, T, R extends BaseRepository<T, ID>>
        implements BaseService<ID, T> {

    @Autowired
    private R repository;

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return this.repository.findOne(example);
    }

    @Override
    public <S extends T> Iterable<S> findAll(Example<S> example) {
        return this.repository.findAll(example);
    }

    @Override
    public <S extends T> Iterable<S> findAll(Example<S> example, Sort sort) {
        return this.repository.findAll(example, sort);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return this.repository.findAll(example, pageable);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return this.repository.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return this.repository.exists(example);
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return this.repository.saveAll(entities);
    }

    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    @Override
    public <S extends T> S insert(S entity) {
        return this.repository.insert(entity);
    }

    @Override
    public <S extends T> List<S> insert(Iterable<S> entities) {
        return this.repository.insert(entities);
    }

    @Override
    public <S extends T> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return this.repository.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return this.repository.existsById(id);
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return this.repository.findAllById(ids);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(ID id) {
        this.repository.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        this.repository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        this.repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }
}
