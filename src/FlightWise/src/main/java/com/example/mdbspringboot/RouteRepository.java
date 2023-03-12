package com.example.mdbspringboot;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class RouteRepository implements MongoRepository<Route, String> {

    @Override
    public <S extends Route> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Route> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Route> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Route> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Route> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Route> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Route> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Route> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Route, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Route> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Route> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Route> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Route> findAll() {
        return null;
    }

    @Override
    public List<Route> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Route entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Route> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Route> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Route> findAll(Pageable pageable) {
        return null;
    }
}
