package com.petrovskiy.mds.web.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = WireMockInitializer.class)
class UserControllerTest {

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
    }

    @Test
    void findById() {
        this.wireMockServer.stubFor(
                WireMock.get("/todos")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("[{\"userId\": 1,\"id\": 1,\"title\": \"Learn Spring Boot 3.0\", \"completed\": false}," +
                                        "{\"userId\": 1,\"id\": 2,\"title\": \"Learn WireMock\", \"completed\": true}]"))
        );
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
    }
}