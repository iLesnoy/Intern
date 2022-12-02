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

import static org.junit.jupiter.api.Assertions.*;


class ItemServiceImplTest extends AbstractCacheTest{

    private ItemDto expected;
    private ItemDto expected2;

    private CategoryDto categoryDto;
    private Logger log = LoggerFactory.getLogger(ItemServiceImplTest.class);

    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private CategoryServiceImpl categoryService;

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @BeforeEach
    private void init(){
        categoryDto = CategoryDto.builder()
                .id(BigInteger.ONE)
                .description("test")
                .name("test").parentCategory("test")
                .build();
        categoryService.create(categoryDto);
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

    @Test
    public void create() {
        ItemDto itemDto = itemService.create(expected);
        assertEquals(itemDto.getName(),expected.getName());
    }

    @Test
    void findByIdNonExist() {
        ItemDto itemDto = itemService.create(this.expected);
        getAndPrint(itemDto.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    ItemDto actual = itemService.findById(UUID.randomUUID());
                }
        );
        assertNotNull(systemException);
    }

    @Test
    public void createAndRefresh() {
        ItemDto refresh = itemService.createAndRefresh(this.expected);
        log.info("created item: {}", expected);
        assertEquals(refresh.getName(),expected.getName());
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