package com.petrovskiy.mds.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private UserDto userDto;
    private CompanyDto companyDto;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("todo_base_url", wireMockServer::baseUrl);
    }

    @AfterEach
    void resetAll() {
        wireMockServer.resetAll();
    }

    @Test
    void create() {
        wireMockServer.stubFor(post(urlEqualTo("/api/orders"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \" \""))
                .withRequestBody(containing("\"email\": \"gf@gmail.com\""))
                .withRequestBody(containing("\"name\": \"fdsgfd\""))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    @Test
    void createInvalidUrl() {
        wireMockServer.stubFor(post(urlEqualTo("/api/orders"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \"privet\""))
                .willReturn(aResponse()
                        .withStatus(404)));
    }


    @Test
    void findAll() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/api/orders"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("todo-api/response-200.json"))
        );

        this.webTestClient
                .get()
                .uri("/api/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.length()").isEqualTo(8);}

}