package com.doubao.douding.system.controller;

import com.doubao.douding.DoudingApplication;
import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.dto.SysUserRoleDTO;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.dto.mapper.UserInfoMapper;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.entity.SysUserRole;
import com.doubao.douding.system.enums.UserEnum;
import com.doubao.douding.system.security.JwtUtils;
import com.doubao.douding.system.service.SysRoleService;
import com.doubao.douding.system.service.SysUserRoleService;
import com.doubao.douding.system.service.UserInfoService;
import com.doubao.douding.util.JsonUtils;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Johnson
 * @Date 2023/12/25-20:01
 * @Description: the integration test of user
 **/
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {
    DoudingApplication.class,
    JwtUtils.class
})
@AutoConfigureMockMvc
@Slf4j
@Transactional
@WithMockUser(roles = {"ADMIN"})
@Sql(
    statements = "INSERT INTO `user_info` (`id`, `username`, `telephone`, `gender`, `email`, `user_status`, `password`, `when_created`, `when_modified`, `who_created`, `who_modified`, `record_modification_version`, `deleted`) VALUES (1484950337669074944, 'username', '18547462136', 1, '1@qq.com', 1, NULL, '2025-09-17 11:02:29.520000', '2025-09-17 11:02:29.520000', 'test', 'test', 1, 0);\n",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    private UserInfoMapper userInfoMapper;

    private UserInfoDTO userInfoDTO;

    private List<UserInfoDTO> userInfoDTOS;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @BeforeEach
    public void setup() {

        userInfoDTOS = Lists.newArrayList();
        userInfoDTO = UserInfoDTO.builder()
                                 .email("1@qq.com")
                                 .username("username")
                                 .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
                                 .gender(UserEnum.GenderEnum.FEMALE.getCode())
                                 .telephone("18547462136")
                                 .whenCreated(Instant.now())
                                 .whenModified(Instant.now())
                                 .build();
        userInfoDTO.setId(1484950337669074944L);
        userInfoDTOS.add(userInfoDTO);

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                                             .email("2@qq.com")
                                             .username("anotherName")
                                             .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
                                             .gender(UserEnum.GenderEnum.MALE.getCode())
                                             .telephone("18547462145")
                                             .build();
        userInfoDTO.setId(2L);
        userInfoDTOS.add(userInfoDTO);
    }

    @Nested
    public class AddTest {
        @Test
        @SneakyThrows
        void givenUserInfo_whenAddUser_thenStatusOk() {
            userInfoDTO.setId(null);
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

        @Test
        @SneakyThrows
        void addUser_withRole_thenStatusOk() {
            List<SysRoleDTO> sysRoleDTOS = Lists.newArrayList();

            SysRoleDTO sysRoleDTO = SysRoleDTO.builder().roleStatus(1).roleName("user management").build();
            sysRoleDTO = sysRoleService.add(sysRoleDTO);
            sysRoleDTOS.add(sysRoleDTO);

            sysRoleDTO = SysRoleDTO.builder().roleStatus(1).roleName("admin management").build();
            sysRoleDTO = sysRoleService.add(sysRoleDTO);
            sysRoleDTOS.add(sysRoleDTO);

            userInfoDTO.setRoles(sysRoleDTOS);
            userInfoDTO.setId(null);

            final String contentAsString = mockMvc.perform(post("/userInfo/add").contentType(MediaType.APPLICATION_JSON)
                                                                                .accept(MediaType.APPLICATION_JSON)
                                                                                .content(JsonUtils.toJsonString(
                                                                                    userInfoDTO)))
                                                  .andExpect(status().isCreated())
                                                  .andDo(print())
                                                  .andExpect(jsonPath("$.email").value(userInfoDTO.getEmail()))
                                                  .andExpect(jsonPath("$.username").value(userInfoDTO.getUsername()))
                                                  .andExpect(jsonPath("$.telephone").value(userInfoDTO.getTelephone()))
                                                  .andExpect(jsonPath("$.id").exists())
                                                  .andReturn()
                                                  .getResponse()
                                                  .getContentAsString(StandardCharsets.UTF_8);
            final UserInfoDTO result = JsonUtils.toObject(contentAsString, UserInfoDTO.class);
            userInfoDTO.setId(result.getId());

            List<SysUserRoleDTO> sysUserRoleDTOS = sysUserRoleService.getRoleForUser(userInfoDTO);
            assertThat(sysUserRoleDTOS.size()).isEqualTo(2);
        }
    }

    @Nested
    public class GetTest {
        // get user
        @Test
        @Sql(
            statements = "INSERT INTO `user_info` (`id`, `username`, `telephone`, `gender`, `email`, `user_status`, `password`, `when_created`, `when_modified`, `who_created`, `who_modified`, `record_modification_version`, `deleted`)" +
                " VALUES (1484950337669074945, 'username', '18547462137', 1, '1@qq.com', 1, NULL, '2025-09-17 11:02:29.520000', '2025-09-17 11:02:29.520000', 'test', 'test', 1, 0);\n",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @SneakyThrows
        void givenValidId_whenGetUserInfo_thenStatusOk() {
            //        when(userInfoService.findById(1L)).thenReturn(userInfoDTO);
            UserInfoDTO result = UserInfoDTO.builder()
                                            .username("username")
                                            .email("1@qq.com")
                                            .telephone("18547462137")
                                            .id(1484950337669074945L)
                                            .build();

            mockMvc.perform(get("/userInfo/1484950337669074945").contentType(MediaType.APPLICATION_JSON)
                                                                .accept(MediaType.APPLICATION_JSON))
                   .andExpect(status().isOk())
                   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                   .andExpect(jsonPath("$.email").value(result.getEmail()))
                   .andExpect(jsonPath("$.username").value(result.getUsername()))
                   .andExpect(jsonPath("$.telephone").value(result.getTelephone()))
                   .andExpect(jsonPath("$.id").value(result.getId()))
                   .andDo(print())
                   .andReturn()
                   .getResponse()
                   .getContentAsString();
        }

        @Test
        @SneakyThrows
        void givenInvalidId_whenGetUserInfo_thenNull() {
            mockMvc.perform(get("/userInfo/999").contentType(MediaType.APPLICATION_JSON))
                   .andExpect(status().isNotFound());
        }

    }

    // generate user password

    // update user
    @Test
    @SneakyThrows
    void givenUserInfo_whenUpdate_thenStatusAccepted() {
        // given
        String mail = "update@email.com";
        userInfoDTO.setEmail(mail);
        userInfoDTO.setWhoCreated("test");
        //        userInfoDTO.setWhenCreated(Instant.now());
        //        userInfoDTO.setWhenModified(Instant.now());

        mockMvc.perform(put("/userInfo/update").contentType(MediaType.APPLICATION_JSON)
                                               .content(JsonUtils.toJsonString(userInfoDTO)))
               .andExpect(status().isAccepted())
               .andExpect(jsonPath("$.email").value(mail));
    }

    // delete user
    @Test
    @SneakyThrows
    void givenUser_whenDelete_thenOk() {
        mockMvc.perform(delete("/userInfo/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    // user list
    @Test
    @SneakyThrows
    void givenUserList_whenList_thenOk() {

        // when get
        String response = mockMvc.perform(get("/userInfo/list").contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isOk())
                                 .andExpect(jsonPath("$").isArray())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString();

        List<UserInfoDTO> info = JsonUtils.toList(response, UserInfoDTO.class);
        assertThat(info.size()).isEqualTo(1);
    }

    // register user
    @Test
    void givenValidUser_whenRegister_thenOk() throws Exception {

        userInfoDTO.setPassword("123456785");
        userInfoDTO.setId(null);
        //        given(userInfoService.register(userInfoDTO)).willReturn(userInfoDTO);

        String response = mockMvc.perform(post("/userInfo/register").contentType(MediaType.APPLICATION_JSON)
                                                                    .content(JsonUtils.toJsonString(userInfoDTO)))
                                 .andExpect(status().isCreated())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString();

    }

}
