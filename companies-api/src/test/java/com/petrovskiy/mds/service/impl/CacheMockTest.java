package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.CompaniesApiApplication;
import com.petrovskiy.mds.dao.CompanyDao;
import com.petrovskiy.mds.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompaniesApiApplication.class)
public class CacheMockTest {


    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CompanyDao repository;
    private Company company;


    @BeforeEach
    void setUp() {
        company = Company.builder()
                .id(BigInteger.ONE)
                .created(LocalDateTime.now())
                .name("test")
                .email("test@gamil.com")
                .build();
        repository.save(company);
        repository.save(company);
    }

    private Optional<Company> getCachedUsers(String name) {
        return ofNullable(cacheManager.getCache("companies")).map(c -> c.get(name, Company.class));
    }

    @Test
    void givenCompaniesThatShouldBeCached_whenFindByEmail_thenResultShouldBePutInCache() {
        Optional<Company> user = repository.findById(BigInteger.ONE);
        assertEquals(user, getCachedUsers("test"));
    }

    @Test
    void givenCompaniesThatShouldNotBeCached_whenFindByID_thenResultShouldNotBePutInCache() {
        repository.findById(BigInteger.TWO);
        assertEquals(empty(), getCachedUsers("Masloria"));
    }


}
