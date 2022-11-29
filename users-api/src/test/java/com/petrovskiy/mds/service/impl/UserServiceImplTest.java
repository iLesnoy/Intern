package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.PostgreTestContainer;
import com.petrovskiy.mds.model.Role;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest extends AbstractCacheTest{

    private UserDto expected;
    private UserDto expected2;
    private CompanyDto companyDto;
    private Logger log = LoggerFactory.getLogger(UserServiceImplTest.class);

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgreTestContainer.getInstance();

    @Autowired
    private UserServiceImpl service;


    @BeforeEach
    void setUp() {
        companyDto = CompanyDto.builder()
                .created(LocalDateTime.now())
                .description("test")
                .name("test")
                .id(1L)
                .build();
        expected = UserDto.builder()
                .id(UUID.randomUUID())
                .updated(LocalDateTime.now())
                .name("test")
                .companyDto(companyDto)
                .role(Role.MANAGER).email("hello@gmai.com")
                .build();
        expected2 = UserDto.builder()
                .id(UUID.randomUUID())
                .updated(LocalDateTime.now())
                .name("test2").companyDto(companyDto)
                .role(Role.STORAGE_MANAGER).email("arnold@gmai.com")
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
        UserDto userDto = service.create(this.expected);
        getAndPrint(userDto.getId());
        getAndPrint(userDto.getId());
        getAndPrint(userDto.getId());
        assertEquals(userDto.getName(), expected.getName());
    }

    @Test
    public void createAndRefresh() {
        UserDto userDto1 = service.createAndRefresh(this.expected);
        log.info("created user: {}", userDto1);

        UserDto userDto = service.createAndRefresh(this.expected2);
        log.info("created user2: {}", userDto);

    }

    @Test
    public void delete() {
        UserDto userDto = service.create(expected);
        log.info("{}", service.findById(userDto.getId()));

        UserDto userDto1 = service.create(expected2);
        log.info("{}", service.findById(userDto1.getId()));

        service.delete(userDto.getId());
        service.delete(userDto1.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    log.info("{}", service.findById(userDto.getId()));
                    log.info("{}", service.findById(userDto1.getId()));
                }
        );
        assertNotNull(systemException);
    }

    private void createAndPrint(UserDto userDto) {
        log.info("created user: {}", service.create(userDto));
    }

    private void getAndPrint(UUID id) {
        log.info("find user: {}", service.findById(id));
    }
}