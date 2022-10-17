package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.impl.PositionServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(PositionController.class)
class PositionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionServiceImpl positionService;

    private PositionDto positionDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        positionDto = new PositionDto();
        positionDto.setCreated_by("Fds");
        positionDto.setCreated(LocalDateTime.now());
    }

    @Test
    void create() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(positionDto)
                .when().post("/api/positions")
                .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void createInvalidData() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(Object.class)
                .when().post("/api/positions")
                .then()
                .log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void update(){
        RestAssuredMockMvc.given()
                .contentType("application/json").body(positionDto)
                .when().patch("/api/positions/1")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findById() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(positionDto)
                .when().get("/api/positions/1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findAll() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(List.of(positionDto))
                .when().get("/api/positions?page=1&size=1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deleteCompany() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .delete("/api/positions/1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }
}