package com.doubao.douding.system.service.impl;

import com.doubao.douding.common.dto.PageResultDTO;
import com.doubao.douding.system.dto.SysResourceDTO;
import com.doubao.douding.system.dto.mapper.SysResourceMapper;
import com.doubao.douding.system.entity.SysResource;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.entity.query.QSysResource;
import com.doubao.douding.system.entity.query.QSysRole;
import com.doubao.douding.system.service.SysResourceService;
import io.ebean.PagedList;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Resource
    private SysResourceMapper sysResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysResourceDTO save(final SysResourceDTO sysResourceDTO) {
        SysResource sysResource = sysResourceMapper.toEntity(sysResourceDTO);
        sysResource.save();
        return sysResourceMapper.toDTO(sysResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysResourceDTO update(final SysResourceDTO sysResourceDTO) {
        SysResource sysResource = sysResourceMapper.toEntity(sysResourceDTO);
        sysResource.update();
        return sysResourceMapper.toDTO(sysResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        SysResource.FIND.deleteById(id);
    }

    @Override
    public SysResourceDTO findById(final Long id) {
        return sysResourceMapper.toDTO(SysResource.FIND.byId(id));
    }

    @Override
    public PageResultDTO<SysResourceDTO> page(final SysResourceDTO requestDTO) {
        PagedList<SysResource> page = new QSysResource()
            .resourceName.eqIfPresent(requestDTO.getResourceName())
            .resourceType.eqIfPresent(requestDTO.getResourceType())
                       .setMaxRows(requestDTO.getPageSize())
                       .setFirstRow((requestDTO.getPageIndex() - 1) * requestDTO.getPageSize())
                       .findPagedList();

        return PageResultDTO.toPageDto(page, sysResourceMapper::toDTO);
    }
}
