package com.doubao.douding.system.controller;

import com.doubao.douding.DoudingApplication;
import com.doubao.douding.system.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {
    DoudingApplication.class,
    JwtUtils.class
})
@AutoConfigureMockMvc
@Slf4j
@WithMockUser(roles = {"ADMIN"})
public class SysResourceControllerTest extends BaseControllerTest {
}
