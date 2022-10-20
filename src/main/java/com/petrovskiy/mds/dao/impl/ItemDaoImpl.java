package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.ItemDao;
import com.petrovskiy.mds.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ItemDaoImpl implements ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Item> findAll(Integer offset, Integer limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> itemRoot = query.from(Item.class);
        query.select(itemRoot);
        query.orderBy(cb.asc(itemRoot.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Item create(Item item) {
        entityManager.persist(item);
        return item;
    }

    @Override
    public Optional<Item> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Item.class,id));
    }

    @Override
    public Item update(UUID id, Item item) {
        return entityManager.merge(item);
    }

    @Override
    public void deleteById(Item item) {
        entityManager.remove(item);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Item> root = query.from(Item.class);
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }
}
