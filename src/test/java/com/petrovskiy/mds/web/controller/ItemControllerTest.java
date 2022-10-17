package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.impl.ItemServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemServiceImpl itemService;

    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        itemDto = new ItemDto();
        itemDto.setName("test");
        itemDto.setDescription("sdasd@text.com");


    }

    @Test
    void createInvalidBody() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(Object.class)
                .when()
                .post("/api/items")
                .then()
                .log()
                .body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void create() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(itemDto)
                .when().post("/api/items")
                .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void findByIdIncorrectUrl() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(itemDto)
                .when().get("/system/api/utems/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void findById() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(itemDto)
                .when().get("/api/items/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void update() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(itemDto)
                .when().patch("/api/items/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void delete() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .delete("/api/items/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findAll() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(List.of(itemDto))
                .when().get("/api/items?page=1&size=1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }
}