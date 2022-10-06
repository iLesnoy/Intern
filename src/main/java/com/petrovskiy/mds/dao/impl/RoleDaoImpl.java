package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.RoleDao;
import com.petrovskiy.mds.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;
    private CriteriaBuilder cb;

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Role> findAll(Integer offset, Integer limit) {
        CriteriaQuery<Role> query = cb.createQuery(Role.class);
        Root<Role> userRoot = query.from(Role.class);
        query.select(userRoot);
        query.orderBy(cb.asc(userRoot.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Role create(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Role.class,id));
    }

    @Override
    public Role update(long id, Role role) {
        return entityManager.merge(role);
    }

    @Override
    public void deleteById(Role role) {
        entityManager.remove(role);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Role> root = query.from(Role.class);
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Optional<Role> findByName(String name) {
        CriteriaQuery<Role> query = cb.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);
        query.select(root);
        query.where(cb.like(root.get("name"),name));
        return entityManager.createQuery(query).getResultStream().findAny();
    }

}
