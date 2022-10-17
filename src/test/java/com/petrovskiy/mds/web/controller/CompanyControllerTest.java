package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.impl.CompanyServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyServiceImpl companyService;

    private CompanyDto companyDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        companyDto = new CompanyDto();
        companyDto.setEmail("comp@gmail.com");
        companyDto.setName("dsfgdf");
    }

    @Test
    void create() {
        RestAssuredMockMvc.given()
                .contentType("application/json").body(companyDto)
                .when().post("/api/companies")
                .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void update(){
        RestAssuredMockMvc.given()
                .contentType("application/json").body(companyDto)
                .when().patch("/api/companies/1")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findById() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(companyDto)
                .when().get("/api/companies/1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findAll() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(List.of(companyDto))
                .when().get("/api/companies?page=1&size=1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void delete() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .delete("/api/companies/1")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }
}