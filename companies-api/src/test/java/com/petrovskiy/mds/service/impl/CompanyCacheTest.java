package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.CompanyDao;
import com.petrovskiy.mds.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AopTestUtils;

import java.math.BigInteger;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class CompanyCacheTest {


    private CompanyDao mock;
    @Autowired
    private CompanyDao companyDao;

    Company COMPANY1 = Company.builder()
            .id(BigInteger.ONE)
                .name("test")
                .description("test")
                .email("email@gmail.com")
                .build();
    Company COMPANY2 = Company.builder()
            .id(BigInteger.TWO)
                .name("test2")
                .description("test")
                .email("emailo@gmail.com")
                .build();



    @EnableCaching
    @Configuration
    public static class CachingTestConfig {

        @Bean
        public CompanyDao companyRepositoryMockImplementation() {
            return Mockito.mock(CompanyDao.class);
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("companies");
        }
    }

    @BeforeEach
    void setUp() {
        mock = AopTestUtils.getTargetObject(companyDao);
        reset(mock);
        when(mock.findById(eq(BigInteger.ONE)))
                .thenReturn(of(COMPANY1));
        when(mock.findById(eq(BigInteger.TWO)))
                .thenReturn(of(COMPANY2))
                .thenThrow(new RuntimeException("Company should be cached!"));
        companyDao.save(COMPANY2);

    }

    @Test
    void givenCachedUser_whenFindByID_thenRepositoryShouldNotBeHit() {

        assertEquals(of(COMPANY2), companyDao.findById(BigInteger.TWO));
        verify(mock).findById(BigInteger.TWO);

        assertEquals(of(COMPANY2), companyDao.findById(BigInteger.TWO));
        assertEquals(of(COMPANY2), companyDao.findById(BigInteger.TWO));
        assertEquals(of(COMPANY2), companyDao.findById(BigInteger.TWO));

        verifyNoMoreInteractions(mock);
    }

    @Test
    void givenNotCachedUser_whenFindByID_thenRepositoryShouldBeHit() {
        assertEquals(of(COMPANY1), companyDao.findById(BigInteger.ONE));
        assertEquals(of(COMPANY1), companyDao.findById(BigInteger.ONE));
        assertEquals(of(COMPANY1), companyDao.findById(BigInteger.ONE));

        verify(mock, times(3)).findById(BigInteger.ONE);
    }


}
