package com.doubao.douding.system.dto.mapper;

import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.entity.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Johnson
 * @Description sys role mapper
 */
@Mapper(builder = @org.mapstruct.Builder(disableBuilder = true), componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleMapper extends GenericMapper<SysRoleDTO, SysRole> {
}
