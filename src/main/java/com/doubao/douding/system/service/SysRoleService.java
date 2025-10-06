package com.doubao.douding.system.service;

import com.doubao.douding.common.dto.PageResultDTO;
import com.doubao.douding.system.dto.SysRoleDTO;

/**
 * @author Johnson
 * @Description sysRoleService
 */
public interface SysRoleService {

    /**
     * add
     *
     * @param sysRoleDTO dto
     * @return result
     */
    SysRoleDTO add(SysRoleDTO sysRoleDTO);

    /**
     * find by id
     *
     * @param id id
     * @return result
     */
    SysRoleDTO findById(long id);

    /**
     * page request
     *
     * @param requestDTO request
     * @return result
     */
    PageResultDTO<SysRoleDTO> page(SysRoleDTO requestDTO);

    SysRoleDTO update(SysRoleDTO sysRoleDTO);

    void delete(Long id);
}
