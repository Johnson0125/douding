package com.doubao.douding.system.controller;

import com.doubao.douding.dto.validate.Create;
import com.doubao.douding.dto.validate.Update;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.enums.UserEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoRequestTests {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateFieldsForCreate() {
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
            .email("1@qq.com")
            .username("username")
            .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
            .gender(UserEnum.GenderEnum.FEMALE.getCode())
            .telephone("18547452131")
            .build();

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO, Create.class);

        assertThat(violations).isEmpty();
    }

    @Test
    void validateFieldsForUpdate() {
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
            .email("1@qq.com")
            .username("username")
            .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
            .gender(UserEnum.GenderEnum.FEMALE.getCode())
            .telephone("18547452131")
            .build();
        userInfoDTO.setId(1L);

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO, Update.class);

        assertThat(violations).isEmpty();
    }

}
