package com.jwt.secureme;


import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
public abstract class ApplicationJpaSupport {

    @Container
    protected static PostgreSQLContainer<?> postgresDB =
            new PostgreSQLContainer<>("postgres:13.2")
                    .withDatabaseName("jwt-db")
                    .withUsername("postgres")
                    .withPassword("postgres");
}
