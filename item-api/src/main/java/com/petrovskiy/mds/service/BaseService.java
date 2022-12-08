package com.petrovskiy.mds.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T,B> {

    Mono<T> create(T t);

    Mono<T> update(B id, T t);

    Mono<T> findById(B id);

    Flux<T> findAll();

    void delete(B id);
}
