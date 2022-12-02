package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.exception.SystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceImplTest extends AbstractCacheTest{


    private CompanyDto expected;
    private CompanyDto expected2;
    private Logger log = LoggerFactory.getLogger(CompanyServiceImplTest.class);

    @Autowired
    private CompanyServiceImpl service;

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @BeforeEach
    private void init(){
        expected = CompanyDto.builder()
                .id(BigInteger.ONE)
                .name("test")
                .description("test")
                .email("email@gmail.com")
                .build();
        expected2 = CompanyDto.builder()
                .id(BigInteger.TWO)
                .name("test2")
                .description("test")
                .email("emailo@gmail.com")
                .build();

    }

    @Test
    public void create() {
        CompanyDto companyDto = service.create(expected);
        assertEquals(companyDto.getName(),expected.getName());
    }

    @Test
    void findByIdNonExist() {
        CompanyDto companyDto = service.create(this.expected);
        getAndPrint(companyDto.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    service.findById(BigInteger.ZERO);
                }
        );
        assertNotNull(systemException);
    }

    @Test
    public void createAndRefresh() {
        CompanyDto refresh = service.createAndRefresh(this.expected);
        log.info("created company: {}", expected);
        assertEquals(refresh.getName(),expected.getName());
    }

    @Test
    public void delete() {
        CompanyDto companyDto = service.create(expected);
        log.info("{}", service.findById(companyDto.getId()));
        CompanyDto companyDto1 = service.create(expected2);
        log.info("{}", service.findById(companyDto1.getId()));

        service.delete(companyDto.getId());
        service.delete(companyDto1.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    log.info("{}", service.findById(companyDto.getId()));
                    log.info("{}", service.findById(companyDto1.getId()));
                }
        );
        assertNotNull(systemException);
    }

    private void createAndPrint(CompanyDto companyDto) {
        log.info("created company: {}", service.create(companyDto));
    }

    private void getAndPrint(BigInteger id) {
        log.info("find company: {}", service.findById(id));
    }
}