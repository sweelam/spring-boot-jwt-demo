package com.jwt.secureme;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;

@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "spring", password = "secret")
public abstract class ApplicationTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Container
    protected static PostgreSQLContainer<?> postgresDB =
            new PostgreSQLContainer<>("postgres:13.2")
                    .withDatabaseName("jwt-db")
                    .withUsername("postgres")
                    .withPassword("postgres");


    protected static final MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);
}

