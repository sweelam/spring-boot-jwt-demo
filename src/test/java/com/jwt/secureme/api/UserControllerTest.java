package com.jwt.secureme.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.secureme.ApplicationTestSupport;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.service.RoleService;
import com.jwt.secureme.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends ApplicationTestSupport {
    private final String RESOURCE_CONTEXT = "/api/user";

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    RoleService roleService;

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(
                get(RESOURCE_CONTEXT.concat("/")).accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());
    }


    @Test
    void addUser() throws Exception {
        mockMvc.perform(
                post(RESOURCE_CONTEXT.concat("/"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                objectMapper.writeValueAsString(AppUser.builder()
                                        .name("Waheed Hamed")
                                        .password("123")
                                        .username("wlaHamed")
                                        .build()
                                )
                        )
        ).andExpect(status().isCreated());
    }

}