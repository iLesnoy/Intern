package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.service.ItemService;
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


class ItemServiceImplTest extends AbstractCacheTest{

    private ItemDto expected;
    private ItemDto expected2;

    private CategoryDto categoryDto;
    private Logger log = LoggerFactory.getLogger(ItemServiceImplTest.class);

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgreTestContainer.getInstance();

    @Autowired
    private ItemServiceImpl itemService;

    @BeforeEach
    private void init(){
        categoryDto = CategoryDto.builder()
                .id(BigInteger.ONE)
                .description("test")
                .name("test").parentCategory("test")
                .build();
        expected = ItemDto.builder()
                .id(UUID.randomUUID())
                .categoryDto(categoryDto).created(LocalDateTime.now())
                .name("test")
                .build();
        expected2 = ItemDto.builder()
                .id(UUID.randomUUID())
                .categoryDto(categoryDto).created(LocalDateTime.now())
                .name("test2")
                .build();
    }

    @Order(1)
    @Test
    public void create() {
        createAndPrint(expected);
        createAndPrint(expected2);
        log.info("all entries are below:");
        itemService.findAll(Pageable.ofSize(2)).forEach(u -> log.info("{}", u.toString()));
    }

    @Order(2)
    @Test
    void findById() {
        ItemDto itemDto = itemService.create(this.expected);
        getAndPrint(itemDto.getId());
        getAndPrint(itemDto.getId());
        getAndPrint(itemDto.getId());
        getAndPrint(itemDto.getId());
        assertEquals(itemDto.getName(),expected.getName());
    }

    @Test
    public void createAndRefresh() {
        itemService.createAndRefresh(this.expected);
        log.info("created item: {}", expected);

        itemService.createAndRefresh(this.expected2);
        log.info("created item2: {}", expected2);

    }

    @Test
    public void delete() {
        ItemDto itemDto = itemService.create(expected);
        log.info("{}", itemService.findById(itemDto.getId()));

        ItemDto itemDto2 = itemService.create(expected2);
        log.info("{}", itemService.findById(itemDto2.getId()));

        itemService.delete(itemDto.getId());
        itemService.delete(itemDto2.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    log.info("{}", itemService.findById(itemDto.getId()));
                    log.info("{}", itemService.findById(itemDto2.getId()));
                }
        );
        assertNotNull(systemException);
    }

    private void createAndPrint(ItemDto itemDto) {
        log.info("created user: {}", itemService.create(itemDto));
    }

    private void getAndPrint(UUID id) {
        log.info("find user: {}",itemService.findById(id));
    }
}