package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.impl.UserServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        userDto = new UserDto();
        userDto.setEmail("sdasd@text.com");
        userDto.setName("test");
        userDto.setUsername("test");

    }

    @Test
    void createInvalidBody() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(Object.class)
                .when()
                .post("/api/users")
                .then()
                .log()
                .body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void create() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(userDto)
                .when().post("/api/users")
                .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void findByIdIncorrectUrl() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(userDto)
                .when().get("/system/api/users/1")
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void findById() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(userDto)
                .when().get("/api/users/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void update() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(userDto)
                .when().patch("/api/users/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findAll() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(List.of(userDto))
                .when().get("/api/users?page=1&size=1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }
}