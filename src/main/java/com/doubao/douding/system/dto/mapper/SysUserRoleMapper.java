package com.doubao.douding.system.dto.mapper;

import com.doubao.douding.system.dto.SysUserRoleDTO;
import com.doubao.douding.system.entity.SysUserRole;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(builder = @org.mapstruct.Builder(disableBuilder = true), componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserRoleMapper extends GenericMapper<SysUserRoleDTO, SysUserRole> {
}
