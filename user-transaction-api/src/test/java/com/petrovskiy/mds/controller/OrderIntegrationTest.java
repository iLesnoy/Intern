package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.*;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.impl.OrderServiceImpl;
import com.petrovskiy.mds.service.impl.UserTransactionServiceImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgreTestContainer.getInstance();

    @Autowired
    private OrderServiceImpl orderService;
    private RequestOrderDto expected;
    @Autowired
    private UserTransactionServiceImpl userTransactionService;
    private UserTransaction userTransaction;

    @BeforeEach
    void setUp() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(new BigInteger(String.valueOf(6))).parentCategory("dsads").name("hello").description("test").build();
        userTransaction = UserTransaction.builder()
                .order(Order.builder()
                        .id(UUID.fromString("514fa2a6-7cfc-40e0-8ace-000578f0ab1d"))
                        .userId(UUID.fromString("3087eee4-7d02-4e7d-b90f-1d21ba44ba8e"))
                        .build())
                .id(UUID.randomUUID()).build();
        ItemDto itemDto = ItemDto.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .name("hello")
                .description("test")
                .categoryDto(categoryDto).build();
        CompanyDto companyDto = CompanyDto.builder()
                .id(new BigInteger(String.valueOf(5)))
                .description("test")
                .name("hello")
                .email("email@gmail.com")
                .created(LocalDateTime.now())
                .build();
        PositionDto positionDto = PositionDto.builder()
                .id(new BigInteger(String.valueOf(10)))
                .created(LocalDateTime.now())
                .amount(new BigDecimal(10))
                .companyDto(companyDto)
                .itemDto(itemDto)
                .build();
        expected = RequestOrderDto.builder().userId(UUID.fromString("3087eee4-7d02-4e7d-b90f-1d21ba44ba8e"))
                .amount(new BigDecimal("10.00"))
                .positionList(List.of(BigInteger.valueOf(10)))
                .build();

    }

    @Test
    void createWithInvalidData() {
        expected.setAmount(new BigDecimal(-10));
        TransactionSystemException systemException = assertThrows(
                TransactionSystemException.class,
                () -> {
                    orderService.create(expected);
                }
        );
        assertNotNull(systemException);

    }

    @Test
    void create() {
        Order actual = orderService.create(expected);
        assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test
    void createTransactions() {
        ResponseTransactionDto responseTransactionDto = userTransactionService.create(userTransaction);
        assertNotNull(responseTransactionDto.getTransactionDate());
    }


    @Test
    void findByNotExistId() {
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    orderService.findById(UUID.randomUUID());
                }
        );
        assertEquals(systemException.getErrorCode(), 40410);
    }

    @Test
    void findById() {
        Order actual = orderService.create(expected);
        Order expected = orderService.findById(actual.getId());
        assertEquals(actual.getAmount(), expected.getAmount());
    }

    @Test
    void updateNonExistId() {
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    orderService.update(UUID.randomUUID(), expected);
                }
        );
        assertEquals(systemException.getErrorCode(), 40410);
    }

    @Test
    void update() {
        Order actual = orderService.create(expected);
        expected.setAmount(BigDecimal.valueOf(200));
        Order update = orderService.update(actual.getId(), expected);
        assertNotEquals(actual.getAmount(),update.getAmount());
    }

    @Test
    void deleteByNotExistId() {
        SystemException systemException = assertThrows(
                SystemException.class,
                () -> {
                    orderService.delete(UUID.randomUUID());
                }
        );
        assertEquals(systemException.getErrorCode(), 40410);
    }


}
