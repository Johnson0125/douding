package com.doubao.douding.system.service;

import com.doubao.douding.system.dto.SysUserRoleDTO;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.entity.SysUserRole;
import java.util.List;

public interface SysUserRoleService {

    List<SysUserRoleDTO> getRoleForUser(UserInfoDTO userInfoDTO);

    void save(SysUserRole sysUserRole);
}
