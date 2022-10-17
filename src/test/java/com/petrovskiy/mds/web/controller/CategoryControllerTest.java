package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.impl.CategoryServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl categoryService;

    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        categoryDto = new CategoryDto();
        categoryDto.setName("category");
        categoryDto.setDescription("descr");
    }

    @Test
    void createInvalidBody() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(Object.class)
                .when().post("/api/categories")
                .then()
                .log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void create() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(categoryDto)
                .when().post("/api/categories")
                .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void updateNonExistId(){
        RestAssuredMockMvc.given()
                .contentType("application/json").body(categoryDto)
                .when().patch("/api/categories/gfd")
                .then()
                .log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void update(){
        RestAssuredMockMvc.given()
                .contentType("application/json").body(categoryDto)
                .when().patch("/api/categories/1")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findById() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(categoryDto)
                .when().get("/api/categories/1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findAll() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(List.of(categoryDto))
                .when().get("/api/categories?page=1&size=1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deleteCompany() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .delete("/api/categories/1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }
}