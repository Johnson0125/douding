package com.doubao.douding.system.service.impl;

import com.doubao.douding.system.dto.SysUserRoleDTO;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.dto.mapper.SysUserRoleMapper;
import com.doubao.douding.system.entity.SysUserRole;
import com.doubao.douding.system.entity.query.QSysUserRole;
import com.doubao.douding.system.service.SysUserRoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRoleDTO> getRoleForUser(final UserInfoDTO userInfoDTO) {
        List<SysUserRole> sysRoles = new QSysUserRole().user.id.eq(userInfoDTO.getId()).findList();
        return sysUserRoleMapper.toDTOList(sysRoles);
    }

    @Override
    public void save(final SysUserRole sysUserRole) {
        sysUserRole.save();
    }
}
