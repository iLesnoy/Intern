package com.petrovskiy.mds;

import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.impl.UserServiceImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgreTestContainer.getInstance();

    @Autowired
    private UserServiceImpl userService;

    private CompanyDto companyDto;
    private UserDto expected;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("comp");
        companyDto.setDescription("comp");
        companyDto.setCreated(LocalDateTime.parse("2022-10-30T18:54:24.642255"));
        companyDto.setEmail("compa@gmail.com");

        expected = UserDto.builder().
        username("IVAN")
                .name("ivak")
        .email("user@mail.ru")
        .companyDto(companyDto).build();


    }


    @Test
    void findAll() {
        Page<UserDto> page = userService.findAll(pageable);
        assertTrue(page.getSize()>=2);
    }

    @Test
    void createWithInvalidData() {
        expected.setEmail("invalid");
        TransactionSystemException systemException = assertThrows(
                TransactionSystemException.class,
                ()->{userService.create(expected);}
        );
        assertNotNull(systemException);

    }

    @Test
    void create() {
        expected.setCompanyDto(companyDto);
        UserDto actual = userService.create(expected);
        assertEquals(expected.getCompanyDto(),actual.getCompanyDto());
    }


    @Test
    void findByNotExistId() {
        SystemException systemException = assertThrows(
                SystemException.class,
                ()->{userService.findById(UUID.randomUUID());}
        );
        assertEquals(systemException.getErrorCode(),40410);
    }

    @Test
    void findById() {
        UserDto actual = userService.findById(UUID.fromString("51dfcd39-6046-413d-aa88-6c9530375247"));
        assertEquals(actual.getName(),"ivak");
    }

    @Test
    void updateNonExistId() {
        SystemException systemException = assertThrows(
                SystemException.class,
                ()->{userService.update(UUID.randomUUID(),expected);}
        );
        assertEquals(systemException.getErrorCode(),40410);
    }

    @Test
    void update() {
        expected.setEmail("updated@gmail.com");
        UserDto userDto = userService.update(UUID.fromString("51dfcd39-6046-413d-aa88-6c9530375247"),expected);
        assertEquals("updated@gmail.com",userDto.getName());
    }

    @Test
    void deleteByNotExistId() {
        SystemException systemException = assertThrows(
                SystemException.class,
                ()->{userService.delete(UUID.randomUUID());}
        );
        assertEquals(systemException.getErrorCode(),40410);
    }

    @Test
    void deleteById() {
        userService.delete(UUID.fromString("51dfcd39-6046-413d-aa88-6c9530375247"));
    }

}