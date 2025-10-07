package com.doubao.douding.system.dto.mapper;

import com.doubao.douding.system.dto.SysResourceDTO;
import com.doubao.douding.system.entity.SysResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(builder = @org.mapstruct.Builder(disableBuilder = true), componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysResourceMapper extends GenericMapper<SysResourceDTO, SysResource> {
}
