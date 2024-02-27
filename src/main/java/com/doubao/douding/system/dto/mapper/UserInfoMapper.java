package com.doubao.douding.system.dto.mapper;

import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.entity.UserInfo;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * @author Johnson
 * @date
 * @description: translate between entity and dto
 **/
@Mapper(builder = @org.mapstruct.Builder(disableBuilder = true), componentModel = "spring")
public interface UserInfoMapper {

    UserInfoDTO toDTO(UserInfo userInfo);

    UserInfo toEntity(UserInfoDTO userInfoDto);

    List<UserInfoDTO> toDTOList(Collection<UserInfo> userInfos);

    List<UserInfo> toEntityList(Collection<UserInfoDTO> userInfoDTOs);

}
