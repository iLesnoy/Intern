package com.petrovskiy.mds.dao.impl;

import com.petrovskiy.mds.dao.CompanyDao;
import com.petrovskiy.mds.model.Company;
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
public class CompanyDaoImpl implements CompanyDao {

    private EntityManager entityManager;
    private CriteriaBuilder cb;

    @Autowired
    public CompanyDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Company> findAll(Integer offset, Integer limit) {
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> companyRoot = query.from(Company.class);
        query.select(companyRoot);
        query.orderBy(cb.asc(companyRoot.get("id")));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Company create(Company company) {
        entityManager.persist(company);
        return company;
    }

    @Override
    public Optional<Company> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Company.class,id));
    }

    @Override
    public Company update(long id, Company company) {
        return entityManager.merge(company);
    }

    @Override
    public void deleteById(Company company) {
        entityManager.remove(company);
    }

    @Override
    public Long findEntityNumber() {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Company> root = query.from(Company.class);
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }
}
