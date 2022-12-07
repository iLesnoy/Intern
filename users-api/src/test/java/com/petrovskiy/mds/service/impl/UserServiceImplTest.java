package com.petrovskiy.mds;


import com.petrovskiy.mds.model.Role;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.impl.AbstractCacheTest;
import com.petrovskiy.mds.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;


import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest extends AbstractCacheTest {

    private UserDto expected;
    private UserDto expected2;
    private CompanyDto companyDto;
    private Logger log = LoggerFactory.getLogger(UserServiceImplTest.class);

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }


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

    @Test
    public void create() {
        UserDto userDto = service.create(expected);
        assertEquals(userDto.getName(), expected.getName());
    }

    @Test
    void findById() {
        UserDto userDto = service.create(expected);
        UserDto expected = service.findById(userDto.getId());
        assertEquals(userDto.getName(), expected.getName());
    }

    @Test
    void findByIdNonExist() {
        UserDto userDto = service.create(this.expected);
        getAndPrint(userDto.getId());
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    service.findById(UUID.randomUUID());
                }
        );
        assertNotNull(systemException);
    }


    @Test
    public void createAndRefresh() {
        UserDto refresh = service.createAndRefresh(this.expected);
        log.info("created user: {}", expected);
        assertEquals(refresh.getName(), expected.getName());
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

    private void getAndPrint(UUID id) {
        log.info("find user: {}", service.findById(id));
    }
}
