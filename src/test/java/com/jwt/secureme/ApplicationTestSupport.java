package com.jwt.secureme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WithMockUser(username = "spring", password = "secret")
public abstract class ApplicationTestSupport extends ApplicationJpaSupport{

    @Autowired
    protected MockMvc mockMvc;
}

