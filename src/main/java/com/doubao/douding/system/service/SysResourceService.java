package com.doubao.douding.system.service;

import com.doubao.douding.common.dto.PageResultDTO;
import com.doubao.douding.system.dto.SysResourceDTO;

public interface SysResourceService {

    SysResourceDTO save(SysResourceDTO sysResourceDTO);

    SysResourceDTO update(SysResourceDTO sysResourceDTO);

    void delete(Long id);

    SysResourceDTO findById(Long id);

    PageResultDTO<SysResourceDTO> page(SysResourceDTO sysResourceDTO);
}
