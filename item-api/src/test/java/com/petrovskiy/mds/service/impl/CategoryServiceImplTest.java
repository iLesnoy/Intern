/*
package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.exception.SystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryServiceImplTest extends AbstractCacheTest {

    private ItemDto item1;
    private ItemDto item2;

    private CategoryDto expected;
    private CategoryDto expected2;
    private Logger log = LoggerFactory.getLogger(ItemServiceImplTest.class);

    @Autowired
    private CategoryServiceImpl service;

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @BeforeEach
    private void init(){
        expected = CategoryDto.builder()
                .id(BigInteger.ONE)
                .description("test")
                .name("test").parentCategory("test")
                .build();
        expected2 = CategoryDto.builder()
                .id(BigInteger.TWO)
                .description("test2")
                .name("testTwo2").parentCategory("test")
                .build();
        item1 = ItemDto.builder()
                .id(UUID.randomUUID())
                .categoryDto(expected).created(LocalDateTime.now())
                .name("test")
                .build();
        item2 = ItemDto.builder()
                .id(UUID.randomUUID())
                .categoryDto(expected).created(LocalDateTime.now())
                .name("test2")
                .build();
    }

    @Test
    public void create() {
        CategoryDto categoryDto = service.create(expected);
        assertEquals(categoryDto.getName(),expected.getName());
    }

    @Test
    void findById() {
        CategoryDto categoryDto = service.create(expected);
        getAndPrint(categoryDto.getId());
        CategoryDto expected = service.findById(categoryDto.getId());
        assertEquals(categoryDto.getName(), expected.getName());
    }

    @Test
    void findByIdNonExist() {
        CategoryDto categoryDto = service.create(this.expected);
        getAndPrint(categoryDto.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    service.findById(BigInteger.valueOf(1000));
                }
        );
        assertNotNull(systemException);
    }

    @Test
    public void createAndRefresh() {
        CategoryDto refresh = service.createAndRefresh(this.expected);
        log.info("created category: {}", expected);
        assertEquals(refresh.getName(),expected.getName());
    }

    @Test
    public void delete() {
        CategoryDto categoryDto1 = service.create(expected);
        log.info("{}", service.findById(categoryDto1.getId()));

        CategoryDto categoryDto = service.create(expected2);
        log.info("{}", service.findById(categoryDto.getId()));

        service.delete(categoryDto1.getId());
        service.delete(categoryDto.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    log.info("{}", service.findById(categoryDto.getId()));
                    log.info("{}", service.findById(categoryDto1.getId()));
                }
        );
        assertNotNull(systemException);
    }

    private void getAndPrint(BigInteger id) {
        log.info("find category: {}", service.findById(id));
    }
}*/
