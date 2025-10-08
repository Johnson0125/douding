package com.doubao.douding.system.dto;

import com.doubao.douding.common.dto.BaseDTO;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.entity.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema
public class SysUserRoleDTO extends BaseDTO {

    private UserInfo user;

    private SysRole role;
}
