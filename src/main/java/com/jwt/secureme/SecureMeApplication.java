package com.jwt.secureme;

import com.jwt.secureme.dto.AdminDetails;
import com.jwt.secureme.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static com.jwt.secureme.config.SecurityConfig.APPLICATION_NAME;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(AdminDetails.class)
@OpenAPIDefinition(
        info = @Info(
        description = "jwt-auth demo using springboot",
        version = "1.0",
        title = "secure-me")
)
@SecurityScheme(
        name = APPLICATION_NAME,
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class SecureMeApplication implements ApplicationRunner {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SecureMeApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // Admin setup
        userService.setupAdmin();
    }
}
