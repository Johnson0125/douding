package com.doubao.douding.system.controller;

import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.enums.UserEnum;
import com.doubao.douding.system.service.UserInfoService;
import com.doubao.douding.util.JsonUtils;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhangs890
 * @Description user manage controller
 */

@WebMvcTest(UserController.class)
@Slf4j
class UserControllerTest {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService userInfoService;

    private UserInfoDTO userInfoDTO;

    private List<UserInfoDTO> userInfoDTOS;

    @BeforeEach
    public void setup() {
        userInfoDTOS = Lists.newArrayList();
        userInfoDTO = UserInfoDTO.builder()
                                 .email("1@qq.com")
                                 .username("username")
                                 .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
                                 .gender(UserEnum.GenderEnum.FEMALE.getCode())
                                 .telephone("18547452131")
                                 .build();
        userInfoDTO.setId(1L);
        userInfoDTOS.add(userInfoDTO);

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                                             .email("2@qq.com")
                                             .username("anotherName")
                                             .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
                                             .gender(UserEnum.GenderEnum.MALE.getCode())
                                             .telephone("18547452131")
                                             .build();
        userInfoDTO.setId(2L);
        userInfoDTOS.add(userInfoDTO);

    }

    // add user
    @Test
    @SneakyThrows
    void givenUserInfo_whenAddUser_thenStatusOk() {

        UserInfoDTO resultUser = UserInfoDTO.builder()
                                            .email("1@qq.com")
                                            .username("username")
                                            .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
                                            .gender(UserEnum.GenderEnum.FEMALE.getCode())
                                            .telephone("18547452131")
                                            .build();
        resultUser.setId(1L);

        // remember here any instance of UserInfo, to make right
        // when we did not have hashcode and equals, we can save any(UserInfo.class), then
        // it works well
        given(userInfoService.save(userInfoDTO)).willReturn(resultUser);
        // when(userInfoService.save(userInfoDTO)).thenReturn(resultUser);

        mockMvc.perform(post("/userInfo/add").contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON)
                                             .content(JsonUtils.toJsonString(userInfoDTO)))
               .andExpect(status().isCreated())
               .andDo(print())
               .andExpect(jsonPath("$.email").value(resultUser.getEmail()))
               .andExpect(jsonPath("$.username").value(resultUser.getUsername()))
               .andExpect(jsonPath("$.telephone").value(resultUser.getTelephone()))
               .andExpect(jsonPath("$.id").value(resultUser.getId()))
               .andReturn()
               .getResponse()
               .getContentAsString(StandardCharsets.UTF_8);
    }

    // get user
    @Test
    @SneakyThrows
    void givenValidId_whenGetUserInfo_thenStatusOk() {
        when(userInfoService.findById(1L)).thenReturn(userInfoDTO);

        mockMvc.perform(get("/userInfo/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.email").value(userInfoDTO.getEmail()))
               .andExpect(jsonPath("$.username").value(userInfoDTO.getUsername()))
               .andExpect(jsonPath("$.telephone").value(userInfoDTO.getTelephone()))
               .andExpect(jsonPath("$.id").value(userInfoDTO.getId()))
               .andDo(print())
               .andReturn()
               .getResponse()
               .getContentAsString();
    }

    @Test
    @SneakyThrows
    void givenInvalidId_whenGetUserInfo_thenNull() {
        given(userInfoService.findById(999L)).willReturn(null);

        mockMvc.perform(get("/userInfo/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
        // .andExpect(status().isNotFound());
    }

    // generate user password

    // update user
    @Test
    @SneakyThrows
    void givenUserInfo_whenUpdate_thenStatusAccepted() {
        // given
        userInfoDTO.setId(1L);
        String mail = "update@email.com";
        userInfoDTO.setEmail(mail);
        when(userInfoService.update(userInfoDTO)).thenReturn(userInfoDTO);

        // when
        mockMvc.perform(put("/userInfo/update").contentType(MediaType.APPLICATION_JSON)
                                               .content(JsonUtils.toJsonString(userInfoDTO)))
               .andExpect(status().isAccepted())
               .andExpect(jsonPath("$.email").value(mail));

        // then
        verify(userInfoService, times(1)).update(userInfoDTO);
    }

    // delete user
    @Test
    @SneakyThrows
    void givenUser_whenDelete_thenOk() {
        mockMvc.perform(delete("/userInfo/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        willDoNothing().given(userInfoService).delete(1L);

        verify(userInfoService, times(1)).delete(1L);
    }

    // user list
    @Test
    @SneakyThrows
    void givenUserList_whenList_thenOk() {

        // given userInfoDTOS
        // when(userInfoService.list()).thenReturn(userInfoDTOS);
        // willReturn(userInfoDTOS).given(userInfoService).list();
        given(userInfoService.list()).willReturn(userInfoDTOS);

        // when get
        String response = mockMvc.perform(get("/userInfo/list").contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isOk())
                                 .andExpect(jsonPath("$").isArray())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString();

        // TypeReference<List<UserInfoDTO>> listTypeReference = new
        // TypeReference<List<UserInfoDTO>>(){};

        // then
        // List<UserInfoDTO> info = JsonUtils.toList(response, listTypeReference);
        List<UserInfoDTO> info = JsonUtils.toList(response, UserInfoDTO.class);
        assertThat(info.size()).isEqualTo(2);
    }

    // register user
    @Test
    void givenValidUser_whenRegister_thenOk() throws Exception {

        given(userInfoService.register(userInfoDTO)).willReturn(userInfoDTO);

        String response = mockMvc.perform(post("/userInfo/register").contentType(MediaType.APPLICATION_JSON)
                                                                    .content(JsonUtils.toJsonString(userInfoDTO)))
                                 .andExpect(status().isCreated())
                                 .andExpect(jsonPath("$.id").isNotEmpty())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString();

        // valid password
        // modify password with salt

        // userInfoDTO.setPassword(mypassword.toCharArray());

    }

    void givenUnvalidUser_whenRegister_thenNotOk() {

    }

    void givenUnValidUser_whenRegister_thenNotOk() {

    }
    // login user

    // change password

    // logout user

}
