package com.doubao.douding.system.service.impl;

import com.doubao.douding.common.dto.PageResultDTO;
import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.dto.mapper.SysRoleMapper;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.entity.query.QSysRole;
import com.doubao.douding.system.service.SysRoleService;
import io.ebean.PagedList;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Johnson
 * @Description TODO
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    /**
     * add
     *
     * @param sysRoleDTO dto
     * @return result
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRoleDTO add(final SysRoleDTO sysRoleDTO) {
        SysRole sysRole = sysRoleMapper.toEntity(sysRoleDTO);
        sysRole.save();
        return sysRoleMapper.toDTO(sysRole);
    }

    /**
     * find by id
     *
     * @param id id
     * @return result
     */
    @Override
    public SysRoleDTO findById(final long id) {
        return sysRoleMapper.toDTO(SysRole.FIND.byId(id));
    }

    /**
     * page request
     *
     * @param requestDTO request
     * @return result
     */
    @Override
    public PageResultDTO<SysRoleDTO> page(final SysRoleDTO requestDTO) {
        PagedList<SysRole> page = new QSysRole()
            .roleName.eqIfPresent(requestDTO.getRoleName())
            .roleStatus.eqIfPresent(requestDTO.getRoleStatus())
                       .setMaxRows(requestDTO.getPageSize())
                       .setFirstRow((requestDTO.getPageIndex() - 1) * requestDTO.getPageSize())
                       .findPagedList();

        return PageResultDTO.toPageDto(page, sysRoleMapper::toDTO);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleDTO update(final SysRoleDTO sysRoleDTO) {
        SysRole sysRole = sysRoleMapper.toEntity(sysRoleDTO);
        sysRole.update();
        return sysRoleMapper.toDTO(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        SysRole.FIND.deleteById(id);
    }
}
