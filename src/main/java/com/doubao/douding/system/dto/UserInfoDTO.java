package com.doubao.douding.system.dto;

import com.doubao.douding.dto.BaseDTO;
import com.doubao.douding.dto.validate.Create;
import com.doubao.douding.dto.validate.Update;
import io.ebean.annotation.Length;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
    @Pattern(regexp = "^1+\\d{10}$", message = "telephone should be valid", groups = { Create.class, Update.class })
    private String telephone;

    @Range(min = 0, max = 1, message = "gender is not valid", groups = { Create.class, Update.class })
    private Integer gender;

    @Email(groups = { Create.class, Update.class })
    @NotBlank(message = "email could not be NULL", groups = { Create.class, Update.class })
    private String email;

    private Integer userStatus;

    private Integer role;

    private char[] password;

    @Length(12)
    private Long salt;

}
