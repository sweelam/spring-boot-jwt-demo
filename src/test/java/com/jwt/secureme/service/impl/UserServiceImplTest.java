package com.jwt.secureme.service.impl;

import com.jwt.secureme.ApplicationTestSupport;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.repo.UserRepo;
import com.jwt.secureme.service.RoleService;
import com.jwt.secureme.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserServiceImplTest extends ApplicationTestSupport {
    @Autowired
    UserService userService;

    @MockBean
    UserRepo userRepo;

    @MockBean
    RoleService roleService;

    private static AppUser user;

    @BeforeAll
    static void init() {
        user = AppUser.builder()
                .name("Emad Hamd")
                .username("emoHamed")
                .password("emoHamd123")
                .build();
    }

    @Test
    void getUser() {
        when(userRepo.findAppUserByUsername("emoHamed"))
                .thenReturn(Optional.of(user));

        var result = userService.getUser("emoHamed");

        assertNotNull(result);
        verify(userRepo, times(1))
                .findAppUserByUsername("emoHamed");
    }

    @Test
    void getUsers() {
        var users = List.of(
                AppUser.builder()
                        .name("Emad Hamd")
                        .username("emoHamed")
                        .password("emoHamd123")
                        .build(),
                AppUser.builder()
                        .name("Sayed Baba")
                        .username("saiod")
                        .password("sayed123")
                        .build(),
                AppUser.builder()
                        .name("Bahaa Alii")
                        .username("bAli")
                        .password("bahaa123")
                        .build()
        );

        userRepo.saveAll(users);

        doReturn(users).when(userRepo).findAll();

        var result = userService.getUsers();

        assertNotNull(result);
        assertEquals(3, result.size());

        verify(userRepo, times(1)).findAll();
    }

}