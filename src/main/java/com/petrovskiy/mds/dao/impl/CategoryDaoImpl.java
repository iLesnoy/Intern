package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.CategoryDao;
import com.petrovskiy.mds.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Category> findAll(Integer offset, Integer limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> categoryRoot = query.from(Category.class);
        query.select(categoryRoot);
        query.orderBy(cb.asc(categoryRoot.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Category create(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Override
    public Optional<Category> findById(BigInteger id) {
        return Optional.ofNullable(entityManager.find(Category.class, id));
    }

    @Override
    public Category update(BigInteger id, Category category) {
        return entityManager.merge(category);
    }

    @Override
    public void deleteById(Category category) {
        entityManager.remove(category);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Category> root = query.from(Category.class);
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Optional<Category> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root);
        query.where(cb.like(root.get("name"), name));
        return entityManager.createQuery(query).getResultStream().findFirst();
    }
}
