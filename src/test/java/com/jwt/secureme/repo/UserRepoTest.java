package com.jwt.secureme.repo;

import com.jwt.secureme.ApplicationJpaSupport;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.service.RoleService;
import com.jwt.secureme.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest extends ApplicationJpaSupport {
    @Autowired
    private UserRepo userRepo;

    @MockBean
    UserService userService;
    @MockBean
    RoleService roleService;

    @Test
    void findByUsernameShouldReturn() {
        userRepo.save(AppUser.builder()
                .name("Waleed Ali")
                .username("WAli")
                .password("waleed123")
                .build());

        var user =
                userRepo.findAppUserByUsername("WAli").orElse(null);

        assertNotNull(user);
        assertEquals("Waleed Ali", user.getName());
    }
}