package com.petrovskiy.mds.controller;

import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles("dev")
public class PostgreTestContainer extends PostgreSQLContainer<PostgreTestContainer> {

    private static final String IMAGE_VERSION = "postgres:11.1";
    private static PostgreTestContainer container;

    private PostgreTestContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgreTestContainer getInstance() {
        if (container == null) {
            container = new PostgreTestContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}