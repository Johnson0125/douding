package com.doubao.douding.system.dto;

import com.doubao.douding.common.dto.BaseDTO;
import com.doubao.douding.common.dto.validate.Create;
import com.doubao.douding.common.dto.validate.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Johnson
 * @Description SysRoleDTO
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema
public class SysRoleDTO extends BaseDTO {

    @NotBlank(groups = {
        Create.class,
        Update.class
    })
    @Schema(title = "role name")
    private String roleName;

    @NotNull(groups = {
        Create.class,
        Update.class
    })
    @Schema(title = "role status")
    private Integer roleStatus;

    @Schema(title = "remark")
    @Size(groups = {
        Create.class,
        Update.class
    }, max = 100, message = "remark length should less than 100")
    private String remark;
}
