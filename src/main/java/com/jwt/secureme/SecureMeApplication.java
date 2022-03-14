package com.jwt.secureme;

import com.jwt.secureme.dto.AdminDetails;
import com.jwt.secureme.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(AdminDetails.class)
@Slf4j
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
