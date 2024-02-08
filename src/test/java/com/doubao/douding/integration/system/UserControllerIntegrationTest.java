package com.doubao.douding.integration.system;

import com.doubao.douding.dto.ResultBean;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.enums.UserEnum;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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

    @Resource
    private TestRestTemplate restTemplate;

    @Test
    @SneakyThrows
    void givenUserInfo_whenAddUser_thenStatusOk() {
        UserInfoDTO userInfoDTO = UserInfoDTO.builder().email("1@qq.com").username("username")
                                          .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
                                          .gender(UserEnum.GenderEnum.FEMALE.getCode())
                                          .telephone("18547452135").build();

        ResponseEntity<UserInfoDTO> response = restTemplate.exchange("/userInfo/add", HttpMethod.POST,
                                                                                 new HttpEntity<UserInfoDTO>(userInfoDTO),
                                                                                 UserInfoDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        UserInfoDTO data = response.getBody();
        assert data != null;
        assertThat(data).isNotNull();
        assertThat(data.getId()).isNotNull();
        assertThat(data.getEmail()).isEqualTo("1@qq.com");
        assertThat(data.getUsername()).isEqualTo("username");
        assertThat(data.getGender()).isEqualTo(UserEnum.GenderEnum.FEMALE.getCode());
    }

}
