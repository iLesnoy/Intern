package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.CategoryDao;
import com.petrovskiy.mds.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Category.class,id));
    }

    @Override
    public Category update(long id, Category category) {
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
}
