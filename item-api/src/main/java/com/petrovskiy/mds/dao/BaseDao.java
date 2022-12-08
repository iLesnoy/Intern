package com.petrovskiy.mds.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseDao<T,D> {

    Flux<T> findAll();

    Mono<T> create(T t);

    Mono<T> findById(D id);

    Mono<T> update(T t,D id);

    Mono<T> delete(D d);
}
