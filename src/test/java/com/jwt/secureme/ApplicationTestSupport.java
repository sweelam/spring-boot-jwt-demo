package com.jwt.secureme;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "spring", password = "secret")
@Testcontainers
@ActiveProfiles("test")
public abstract class ApplicationTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Container
    protected static PostgreSQLContainer<?> postgresDB =
            new PostgreSQLContainer<>("postgres:13.2")
                    .withDatabaseName("jwt-db")
                    .withUsername("postgres")
                    .withPassword("postgres");

}
