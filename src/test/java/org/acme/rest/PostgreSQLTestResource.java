package org.acme.rest;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Collections;
import java.util.Map;

public class PostgreSQLTestResource implements QuarkusTestResourceLifecycleManager {

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:13")
            .withUsername("postgres")
            .withPassword("postgres");

    @Override
    public Map<String, String> start() {
        container.start();
        return Collections.singletonMap("quarkus.datasource.jdbc.url", container.getJdbcUrl());
    }

    @Override
    public void stop() {
        container.stop();
    }

}
