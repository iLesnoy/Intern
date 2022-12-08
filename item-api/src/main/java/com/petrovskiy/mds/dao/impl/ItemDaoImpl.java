package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.ItemDao;
import com.petrovskiy.mds.model.Item;
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
public class ItemDaoImpl implements ItemDao {

    private final ReactiveMongoTemplate template;

    @Override
    public Flux<Item> findAll() {
        return template.findAll(Item.class);
        /*Pageable pageable = PageRequest.of(0, 10);
        Query patientsDynamicQuery = new Query().with(pageable);
        Flux<Item> filteredPatients =
                template.find(patientsDynamicQuery, Item.class, "items");
        return PageableExecutionUtils.getPage(
                filteredPatients,
                pageable,
                () -> template.count(patientsDynamicQuery, Item.class));*/
    }

    @Override
    public Mono<Item> create(Item item) {
        return template.save(item);
    }

    @Override
    public Mono<Item> findById(String id) {
        return template.findById(id, Item.class);
    }

    @Override
    public Mono<Item> update(Item item, String id) {
        Query query = new Query().
                addCriteria(Criteria.where("id")
                        .is(id));
        Update update = new Update()
                .set("name", "James");
        update.set("created",item.getCreated());
        update.set("description",item.getDescription());
        update.set("category_id",item.getCategoryId());
        return template.findAndModify(query, update, Item.class);
    }

    @Override
    public Mono<Item> delete(String id) {
        return template.findAndRemove(query(where("id").is(id)), Item.class);

    }

}
