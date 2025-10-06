package com.doubao.douding.system.controller;

import com.doubao.douding.util.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * @author Johnson
 * @Description base controller for test
 */
public abstract class BaseControllerTest {

    protected ResultActions getAction(MockMvc mockMvc, String url) throws Exception {
        return mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions postAction(MockMvc mockMvc, String url, Object content) throws Exception {
        return mockMvc.perform(
            post(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(
                JsonUtils.toJsonString(content)));
    }

    protected ResultActions putAction(MockMvc mockMvc, String url, Object content) throws Exception {
        return mockMvc.perform(
            put(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(
                JsonUtils.toJsonString(content)));
    }

    protected ResultActions deleteAction(MockMvc mockMvc, String url) throws Exception {
        return mockMvc.perform(
            delete(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    }

}
