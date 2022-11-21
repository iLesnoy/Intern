package com.petrovskiy.mds.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
                .withRequestBody(containing("\"id\": \"514fa2a6-7cfc-40e0-8ace-000578f0ab1d \""))
                .withRequestBody(containing("\"amount\": \"200\""))
                .withRequestBody(containing("\"user_id\": \"3087eee4-7d02-4e7d-b90f-1d21ba44ba8e\""))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    @Test
    void createTransaction() {
        wireMockServer.stubFor(post(urlEqualTo("/api/createTransaction"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"order\": \"{\"id\":\"4e029bdb-fbb3-4345-baa4-fe7541f5989b\"} \""))

                .willReturn(aResponse()
                        .withStatus(200)));
    }

    @Test
    void createTransactionInvalidBody() {
        wireMockServer.stubFor(post(urlEqualTo("/api/createTransaction"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"order\": \"{\"id\":\"\"} \""))
                .willReturn(aResponse()
                        .withStatus(500)));
    }

    @Test
    void createWithInvalidParam() {
        wireMockServer.stubFor(post(urlEqualTo("/api/orders"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \"5fds \""))
                .withRequestBody(containing("\"amount\": \"\""))
                .withRequestBody(containing("\"user_id\": \"3087eee4-7d02-4e7d-b90f-1d21ba44ba8e\""))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    @Test
    void createInvalidUrl() {
        wireMockServer.stubFor(post(urlEqualTo("/api/orders"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \"privet\""))
                .willReturn(aResponse()
                        .withStatus(500)));
    }



}