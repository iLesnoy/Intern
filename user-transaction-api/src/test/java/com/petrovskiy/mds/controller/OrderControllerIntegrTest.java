package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.dto.*;
import com.petrovskiy.mds.service.impl.OrderServiceImpl;
import com.petrovskiy.mds.service.impl.UserTransactionServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@WebMvcTest(OrderController.class)

public class OrderControllerIntegrTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;
    private RequestOrderDto transactionDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        CategoryDto categoryDto = CategoryDto.builder()
                .id(new BigInteger(String.valueOf(6))).parentCategory("dsads").name("hello").description("test").build();
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
                .id(new BigInteger(String.valueOf(3)))
                .created(LocalDateTime.now())
                .amount(new BigDecimal(200))
                .companyDto(companyDto)
                .itemDto(itemDto)
                .build();
        transactionDto = RequestOrderDto.builder().userId(UUID.randomUUID())
                .amount(new BigDecimal(200))
                .userId(UUID.randomUUID())
                .build();

    }

    @Test
    void createInvalidBody() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(Object.class)
                .when()
                .post("/api/orders")
                .then()
                .log()
                .body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void create() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(transactionDto)
                .when().post("/api/orders")
                .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void findByIdIncorrectUrl() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(transactionDto)
                .when().get("/api/orders/1kgj")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void findById() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(transactionDto)
                .when().get("/api/orders/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void update() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(transactionDto)
                .when().patch("/api/orders/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findAll() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(List.of(transactionDto))
                .when().get("/api/orders?page=1&size=1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void delete() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(List.of(transactionDto))
                .when().delete("/api/orders/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }
}