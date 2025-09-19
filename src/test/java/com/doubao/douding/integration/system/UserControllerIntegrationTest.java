package com.doubao.douding.integration.system;

import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.dto.mapper.UserInfoMapper;
import com.doubao.douding.system.enums.UserEnum;
import com.doubao.douding.system.security.JwtUtils;
import com.doubao.douding.util.JsonUtils;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Johnson
 * @Date 2023/12/25-20:01
 * @Description: the integration test of user
 **/
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void givenUserInfo_whenAddUser_thenStatusOk() {

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
            .email("1@qq.com")
            .username("username")
            .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
            .gender(UserEnum.GenderEnum.FEMALE.getCode())
            .telephone("18547452135")
            .build();

        mockMvc.perform(post("/userInfo/add").contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON)
                                             .content(JsonUtils.toJsonString(userInfoDTO)))
               .andExpect(status().isCreated())
               .andDo(print())
               .andExpect(jsonPath("$.email").value(userInfoDTO.getEmail()))
               .andExpect(jsonPath("$.username").value(userInfoDTO.getUsername()))
               .andExpect(jsonPath("$.telephone").value(userInfoDTO.getTelephone()))
               .andExpect(jsonPath("$.id").exists())
               .andReturn()
               .getResponse()
               .getContentAsString(StandardCharsets.UTF_8);


    }

}
