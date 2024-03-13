package com.doubao.douding.system.dto;

import com.doubao.douding.dto.BaseDTO;
import com.doubao.douding.dto.validate.Create;
import com.doubao.douding.dto.validate.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema
public class UserInfoDTO extends BaseDTO {

    @NotBlank(groups = { Create.class, Update.class })
    private String username;

    @NotNull(groups = { Create.class, Update.class })
    @Pattern(regexp = "^1+\\d{10}$", groups = { Create.class, Update.class }, message = "telephone should be valid")
    private String telephone;

    @Range(min = 0, max = 1, groups = { Create.class, Update.class }, message = "gender is not valid")
    private Integer gender;

    @Email(groups = { Create.class, Update.class }, message = "email address should be validate")
    @NotBlank(groups = { Create.class, Update.class }, message = "email could not be NULL")
    private String email;

    private Integer userStatus;

    private Integer role;

    @Size(groups = {Create.class, Update.class }, min = 8, max = 20, message = "password length should between 8 and 20")
    private char[] password;

}
