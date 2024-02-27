package com.doubao.douding.system.reposiroty;

import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.entity.query.QUserInfo;
import com.doubao.douding.system.enums.UserEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@Transactional
class UserInfoRepositoryTest {

    @Test
    void givenUserInfo_whenAdd_thenOk() {
        // given
        UserInfo userInfo = UserInfo.builder()
            .email("1@qq.com")
            .username("username")
            .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
            .gender(UserEnum.GenderEnum.FEMALE.getCode())
            .telephone("1854745213")
            .build();

        // when
        userInfo.save();

        // then
        UserInfo userInfoFromDb = new QUserInfo().telephone.equalTo("1854745213").findOne();

        assertThat(userInfoFromDb).isNotNull();
        assertThat(userInfoFromDb).isEqualTo(userInfo);
        assertEquals("username should be equal", "username", userInfo.getUsername());
        assertNotNull("user's id should not be null", userInfo.getId());
        assertEquals("user's gender should be Female", UserEnum.GenderEnum.FEMALE.getCode(),
                userInfoFromDb.getGender());

    }

}
