package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CategoryDao extends ReactiveMongoRepository<Category, String> {
    Mono<Optional<Category>> findByName(String name);
    Flux<Category> findAll(Pageable page);
}
