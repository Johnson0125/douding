package com.doubao.douding.system.service.impl;

import com.doubao.douding.system.dto.LoginDTO;
import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.dto.SysUserRoleDTO;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.dto.mapper.UserInfoMapper;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.entity.SysUserRole;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.entity.query.QUserInfo;
import com.doubao.douding.system.repository.UserInfoRepository;
import com.doubao.douding.system.service.SysUserRoleService;
import com.doubao.douding.system.service.UserInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Johnson
 * @create 2023-12-01
 * @Description implementation of userInfo Service
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    private final PasswordEncoder passwordEncoder;

    private final SysUserRoleService sysUserRoleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDto) {
        UserInfo entity = convertToEntity(userInfoDto);
        if (StringUtils.isNotBlank(userInfoDto.getPassword())) {
            entity.setPassword(passwordEncoder.encode(userInfoDto.getPassword()).toCharArray());
        }
        entity.save();

        final List<SysRoleDTO> roles = userInfoDto.getRoles();
        for (SysRoleDTO roleDTO : roles) {
            SysRole role = new SysRole();
            role.setId(roleDTO.getId());

            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUser(entity);
            sysUserRole.setRole(role);
            sysUserRoleService.save(sysUserRole);
        }
        return convertToDTO(entity);
    }

    @Override
    public UserInfoDTO findById(Long userId) {
        return convertToDTO(UserInfo.FIND.byId(userId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfoDTO update(UserInfoDTO userInfoDto) {
        UserInfo userInfo = convertToEntity(userInfoDto);
        userInfo.update();
        return convertToDTO(userInfo);
    }

    public UserInfoDTO convertToDTO(UserInfo userInfo) {
        return userInfoMapper.toDTO(userInfo);
    }

    public UserInfo convertToEntity(UserInfoDTO userInfoDTO) {
        return userInfoMapper.toEntity(userInfoDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long userId) {
        UserInfo.FIND.deleteById(userId);
    }

    @Override
    public List<UserInfoDTO> list() {
        List<UserInfo> all = UserInfo.FIND.all();
        return userInfoMapper.toDTOList(all);
    }

    @Override
    public UserInfo getUserInfo(final LoginDTO loginDTO) {
        return new QUserInfo().username.equalTo(loginDTO.getUsername()).findOne();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfoDTO register(final UserInfoDTO userInfoDto) {
        UserInfo entity = convertToEntity(userInfoDto);
        entity.setPassword(passwordEncoder.encode(userInfoDto.getPassword()).toCharArray());
        entity.save();
        return convertToDTO(entity);
    }

}
