package com.jwt.secureme;

import com.jwt.secureme.dto.SystemRoles;
import com.jwt.secureme.service.RoleService;
import com.jwt.secureme.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SecureMeApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SecureMeApplication.class, args);
    }

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            if (!roleService.getRole(SystemRoles.USER.name()).isPresent()) {
                roleService.addRole(SystemRoles.USER.name());
                userService.addUserRole("mAli", SystemRoles.USER.name());
            }

        } catch (Exception e) {
            log.warn("Adding roles failed {}", e.getMessage());
        }

        try {
            if (!roleService.getRole(SystemRoles.ADMIN.name()).isPresent()) {
                roleService.addRole(SystemRoles.ADMIN.name());
                userService.addUserRole("msweelam", SystemRoles.ADMIN.name());
            }

        } catch (Exception e) {
            log.warn("Adding roles failed {}", e.getMessage());
        }
    }
}
