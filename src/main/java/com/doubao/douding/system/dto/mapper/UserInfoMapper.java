package com.doubao.douding.system.dto.mapper;

import com.doubao.douding.system.dto.SysUserRoleDTO;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.entity.SysUserRole;
import com.doubao.douding.system.entity.UserInfo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

/**
 * @author Johnson
 * @date
 * @description: translate between entity and dto
 **/
@Mapper(builder = @org.mapstruct.Builder(disableBuilder = true), componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserInfoMapper extends GenericMapper<UserInfoDTO, UserInfo> {

    @Mapping(source = "password", target = "password", qualifiedByName = "charArrayToString")
    UserInfoDTO toDTO(UserInfo userInfo);

    @Mapping(target = "recordModificationVersion", ignore = true)
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToCharArray")
    UserInfo toEntity(UserInfoDTO userInfoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "password", target = "password", qualifiedByName = "charArrayToString")
    void mergeEntityToDTO(UserInfo entity, @MappingTarget UserInfoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToCharArray")
    void mergeDTOToEntity(UserInfoDTO dto, @MappingTarget UserInfo entity);

    @Named("stringToCharArray")
    default char[] stringToCharArray(String str) {
        if (str == null) {
            return null;
        }
        return str.toCharArray();
    }

    // 2. 将 char[] 转换为 String
    @Named("charArrayToString")
    default String charArrayToString(char[] chars) {
        if (chars == null) {
            return null;
        }
        return new String(chars);
    }

}
