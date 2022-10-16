package com.petrovskiy.mds.dao.impl;

import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class AbstractContainerBaseTest {

    private static final PostgreSQLContainer postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer("postgres:latest");
        postgreSQLContainer.withPassword("12345");
        postgreSQLContainer.withUsername("root");
        postgreSQLContainer.withDatabaseName("agreg");
        /*postgreSQLContainer.withInitScript("init.sql");*/
    }

    @PostConstruct
    public void start() {
        postgreSQLContainer.start();
    }

    @PreDestroy
    public void stop() {
        postgreSQLContainer.stop();
    }
}
