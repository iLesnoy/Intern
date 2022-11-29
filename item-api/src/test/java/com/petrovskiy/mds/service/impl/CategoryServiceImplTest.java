package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.service.CategoryService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.exception.SystemException;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest extends AbstractCacheTest {

    private ItemDto item1;
    private ItemDto item2;

    private CategoryDto expected;
    private CategoryDto expected2;
    private Logger log = LoggerFactory.getLogger(ItemServiceImplTest.class);

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgreTestContainer.getInstance();

    @Autowired
    private CategoryServiceImpl service;

    @BeforeEach
    private void init(){
        expected = CategoryDto.builder()
                .id(BigInteger.ONE)
                .description("test")
                .name("test").parentCategory("test")
                .build();
        expected2 = CategoryDto.builder()
                .id(BigInteger.TWO)
                .description("test")
                .name("test2").parentCategory("test")
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

    @Order(1)
    @Test
    public void create() {
        createAndPrint(expected);
        createAndPrint(expected2);
        log.info("all entries are below:");
        service.findAll(Pageable.ofSize(2)).forEach(u -> log.info("{}", u.toString()));
    }

    @Order(2)
    @Test
    void findById() {
        CategoryDto categoryDto = service.create(this.expected);
        getAndPrint(categoryDto.getId());
        getAndPrint(categoryDto.getId());
        getAndPrint(categoryDto.getId());
        getAndPrint(categoryDto.getId());
        assertEquals(categoryDto.getName(), expected.getName());
    }

    @Test
    public void createAndRefresh() {
        service.createAndRefresh(this.expected);
        log.info("created item: {}", item1);

        service.createAndRefresh(this.expected2);
        log.info("created item2: {}", item2);

    }

    @Test
    public void delete() {
        CategoryDto categoryDto1 = service.create(expected);
        log.info("{}", service.findById(categoryDto1.getId()));

        CategoryDto categoryDto = service.create(expected2);
        log.info("{}", service.findById(categoryDto.getId()));

        service.delete(categoryDto.getId());
        service.delete(categoryDto1.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    log.info("{}", service.findById(categoryDto.getId()));
                    log.info("{}", service.findById(categoryDto1.getId()));
                }
        );
        assertNotNull(systemException);
    }

    private void createAndPrint(CategoryDto categoryDto) {
        log.info("created category: {}", service.create(categoryDto));
    }

    private void getAndPrint(BigInteger id) {
        log.info("find category: {}", service.findById(id));
    }
}