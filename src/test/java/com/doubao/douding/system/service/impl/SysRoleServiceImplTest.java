package com.doubao.douding.system.service.impl;

import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.dto.mapper.SysRoleMapper;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class })
class SysRoleServiceImplTest {

    @InjectMocks
    private SysRoleServiceImpl sysRoleService;

    @Mock
    private SysRoleMapper sysRoleMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void add() {
        SysRoleDTO inputDTO = new SysRoleDTO();
        inputDTO.setRoleName("user");
        inputDTO.setRoleStatus(1);

        SysRole entity = mock(SysRole.class);
        entity.setRoleName("user");
        entity.setRoleStatus(1);

        when(sysRoleMapper.toDTO(any(SysRole.class))).thenReturn(inputDTO);
        when(sysRoleMapper.toEntity(any(SysRoleDTO.class))).thenReturn(entity);
        //doNothing().when(entity).save();
        willDoNothing().given(entity).save();

        sysRoleService.add(inputDTO);

        verify(entity, times(1)).save();
        verify(sysRoleMapper, times(1)).toEntity(inputDTO);
        verify(sysRoleMapper, times(1)).toDTO(entity);
    }


}