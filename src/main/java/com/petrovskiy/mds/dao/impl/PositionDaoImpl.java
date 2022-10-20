package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.PositionDao;
import com.petrovskiy.mds.model.Position;
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
public class PositionDaoImpl implements PositionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Position> findAll(Integer offset, Integer limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Position> query = cb.createQuery(Position.class);
        Root<Position> userRoot = query.from(Position.class);
        query.select(userRoot);
        query.orderBy(cb.asc(userRoot.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Position create(Position position) {
        entityManager.persist(position);
        return position;
    }

    @Override
    public Optional<Position> findById(BigInteger id) {
        return Optional.ofNullable(entityManager.find(Position.class,id));
    }

    @Override
    public Position update(BigInteger id, Position position) {
        return entityManager.merge(position);
    }

    @Override
    public void deleteById(Position position) {
        entityManager.remove(position);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Position> root = query.from(Position.class);
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }
}
