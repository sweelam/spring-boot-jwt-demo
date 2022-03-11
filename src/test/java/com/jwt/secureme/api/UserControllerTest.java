package com.jwt.secureme.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.secureme.ApplicationTestSupport;
import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.service.RoleService;
import com.jwt.secureme.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
                                objectMapper.writeValueAsString(UserRequest.builder()
                                        .name("Waheed Hamed")
                                        .password("123assaas11212")
                                        .username("wlaHamed")
                                        .build()
                                )
                        )
        ).andExpect(status().isCreated());
    }

    @Test
    void addUserShouldProvideAllRequiredFields() throws Exception {
        mockMvc.perform(
                post(RESOURCE_CONTEXT.concat("/"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                objectMapper.writeValueAsString(UserRequest.builder()
                                        .name("Waheed Hamed")
                                        .username("wlaHamed")
                                        .build()
                                )
                        )
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("password must not be null"));
    }

}