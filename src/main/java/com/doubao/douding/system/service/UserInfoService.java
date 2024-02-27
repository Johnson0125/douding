package com.doubao.douding.system.service;

import com.doubao.douding.system.dto.UserInfoDTO;

import java.util.List;

/**
 * @author Johnson
 * @create 2023-12-01
 * @Description userinfo service
 */
public interface UserInfoService {

    /**
     * add user
     * @param userInfoDto userInfoDto
     * @return user's id
     */
    UserInfoDTO save(UserInfoDTO userInfoDto);

    /**
     * get By id
     * @param userId user’s id
     * @return userinfo
     */
    UserInfoDTO findById(Long userId);

    /**
     * update user
     * @param userInfoDto userInfoDto
     * @return userInfo
     */
    UserInfoDTO update(UserInfoDTO userInfoDto);

    void delete(Long userId);

    /**
     * @return list user
     */
    List<UserInfoDTO> list();

}
