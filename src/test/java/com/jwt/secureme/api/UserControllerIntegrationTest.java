package com.jwt.secureme.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.secureme.ApplicationTestSupport;
import com.jwt.secureme.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerIntegrationTest extends ApplicationTestSupport {
    private final String RESOURCE_CONTEXT = "/user";

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addDuplicateUsersShouldThrowException() throws Exception {
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
        ).andDo(print()).andExpect(status().isBadRequest());
    }

}
