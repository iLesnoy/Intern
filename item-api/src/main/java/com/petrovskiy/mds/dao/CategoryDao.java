package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Category;
import reactor.core.publisher.Mono;

public interface CategoryDao extends BaseDao<Category, String> {
    Mono<Category> findByName(String name);
}
