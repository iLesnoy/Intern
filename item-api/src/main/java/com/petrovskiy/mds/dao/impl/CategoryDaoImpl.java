package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.CategoryDao;
import com.petrovskiy.mds.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryDaoImpl implements CategoryDao {

    private final ReactiveMongoTemplate template;

    @Override
    public Flux<Category> findAll() {
        return template.findAll(Category.class);
    }

    @Override
    public Mono<Category> create(Category category) {
        return template.save(category);
    }

    @Override
    public Mono<Category> findById(String id) {
        return template.findById(id, Category.class);
    }

    @Override
    public Mono<Category> update(Category category, String id) {
        Query query = new Query()
                .addCriteria(Criteria.where("id")
                        .is(id));
        Update update = new Update()
                .set("name",category.getName());
        update.set("description",category.getDescription());
        update.set("parent_category",category.getParent_category());
        return template.findAndModify(query, update, Category.class);
    }

    @Override
    public Mono<Category> delete(String id) {
        return template.findAndRemove(query(where("id").is(id)), Category.class);

    }

    @Override
    public Mono<Category> findByName(String name) {
        return template.findOne(query(where("name").is(name)), Category.class);
    }
}
