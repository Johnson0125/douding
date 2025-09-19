package com.doubao.douding.system.dto.mapper;

import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.entity.UserInfo;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author Johnson
 * @date
 * @description: translate between entity and dto
 **/
@Mapper(builder = @org.mapstruct.Builder(disableBuilder = true), componentModel = "spring")
public interface UserInfoMapper {

    @Mapping(source = "password", target = "password", qualifiedByName = "charArrayToString")
    UserInfoDTO toDTO(UserInfo userInfo);

    @Mapping(target = "recordModificationVersion", ignore = true)
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToCharArray")
    UserInfo toEntity(UserInfoDTO userInfoDto);

    List<UserInfoDTO> toDTOList(Collection<UserInfo> userInfos);

    List<UserInfo> toEntityList(Collection<UserInfoDTO> userInfoDTOs);

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
