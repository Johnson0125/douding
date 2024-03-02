package com.doubao.douding.system.controller;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Johnson
 * @date 2024-03-02
 * @description authenticate test
 */

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Resource
    MockMvc mockMvc;

    @Test
    void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/token")
                                                    .with(httpBasic("user", "password")))
                                       .andExpect(status().isOk())
                                       .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mockMvc.perform(get("/hello")
                                 .header("Authorization", "Bearer " + token))
                    .andExpect(content().string("hello, user~"));
    }

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        this.mockMvc.perform(get("/hello"))
                    .andExpect(status().isUnauthorized());
    }

    @Test
    void tokenWhenBadCredentialsThen401() throws Exception {
        this.mockMvc.perform(post("/token"))
                    .andExpect(status().isUnauthorized());
    }

}
