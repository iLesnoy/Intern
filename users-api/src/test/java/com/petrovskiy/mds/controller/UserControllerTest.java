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

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

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
        wireMockServer.stubFor(post(urlEqualTo("/api/users"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \"bca0d2f3-57f8-449f-84b7-1dc50e3ee19d\""))
                .withRequestBody(containing("\"email\": \"gf@gmail.com\""))
                .withRequestBody(containing("\"name\": \"fdsgfd\""))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    @Test
    void createInvalidUrl() {
        wireMockServer.stubFor(post(urlEqualTo("/api/us"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \"privet\""))
                .willReturn(aResponse()
                        .withStatus(404)));
    }

    @Test
    void findByIdHystrixCompany() {
        companyDto.setName("comp");
        companyDto.setId(2L);
        companyDto.setEmail("rew@fds.com");
        userDto = UserDto.builder().id(UUID.fromString("bca0d2f3-57f8-449f-84b7-1dc50e3ee19d")).email("gf@gmail.com").name("fdsgfd")
                .username("we1").updated(LocalDateTime.parse("2022-10-16 18:41:22.495227")).companyDto(companyDto).build();
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/api/users/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(String.valueOf(userDto))
                                .withBodyFile("todo-api/response-200.json"))
        );

        this.webTestClient
                .patch()
                .uri("/api/users/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d\"")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.length()").isEqualTo(1);
    }


    @Test
    void update() {
        wireMockServer.stubFor(post(urlEqualTo("/api/users/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \"bca0d2f3-57f8-449f-84b7-1dc50e3ee19d\""))
                .withRequestBody(containing("\"email\": \"gf@gmail.com\""))
                .withRequestBody(containing("\"name\": \"fdsgfd\""))
                .withRequestBody(containing("\"userName\": \"test\""))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    @Test
    void updateInvalidUrl() {
        wireMockServer.stubFor(post(urlEqualTo("/api/fdsfsf/bca0d2f3-57f8-449f-84b7-1dc50e3ee19d"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"id\": \"bca0d2f3-57f8-449f-84b7-1dc50e3ee19d\""))
                .withRequestBody(containing("\"email\": \"gf@gmail.com\""))
                .withRequestBody(containing("\"name\": \"fdsgfd\""))
                .willReturn(aResponse()
                        .withStatus(404)));
    }

    @Test
    void findAll() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/api/users"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("todo-api/response-200.json"))
        );

        this.webTestClient
                .get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.length()").isEqualTo(8);}

}