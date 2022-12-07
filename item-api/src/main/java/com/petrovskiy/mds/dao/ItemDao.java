package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ItemDao extends ReactiveMongoRepository<Item, String> {
    Flux<Item> findAll(Pageable page);

}
