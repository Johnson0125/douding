package com.doubao.douding.system.service;

import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.dto.mapper.UserInfoMapper;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.entity.finder.UserInfoFinder;
import com.doubao.douding.system.enums.UserEnum;
import com.doubao.douding.system.service.impl.UserInfoServiceImpl;
import io.ebean.mocker.WithStaticFinder;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;

/**
 * @author Johnson
 * @create 2023-12-13
 * @Description unit test for userInfoService
 */
@ExtendWith({MockitoExtension.class})
class UserInfoServiceTest {
    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @Mock
    private UserInfoMapper userInfoMapper;

    private UserInfo userInfo;

    private WithStaticFinder<UserInfo> staticFinder;

    @Mock
    private UserInfoFinder finder;

    @BeforeEach
    void setUp() {
        userInfo = UserInfo.builder().email("1@qq.com").username("username")
                           .userStatus(UserEnum.UserStatusEnum.NORMAL.getCode())
                           .gender(UserEnum.GenderEnum.FEMALE.getCode())
                           .telephone("1854745213").build();
        userInfo.setId(1L);

        staticFinder = WithStaticFinder.use(UserInfo.class, finder);
    }

    @AfterEach
    void tearDown() {
        // release the static final
        staticFinder.restoreOriginal();
    }

    @Test
    void givenUserInfo_whenAdd_thenReturnWithId() {
        // given
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        UserInfo userInfo = mock(UserInfo.class);

        when(userInfoMapper.toEntity(userInfoDTO)).thenReturn(userInfo);
        when(userInfoMapper.toDTO(userInfo)).thenReturn(userInfoDTO);
        willDoNothing().given(userInfo).save();

        // when
        UserInfoDTO savedUserDto = userInfoService.save(userInfoDTO);

        // then
        verify(userInfo, times(1)).save();
        verify(userInfoMapper, times(1)).toEntity(userInfoDTO);
        verify(userInfoMapper, times(1)).toDTO(userInfo);
    }

    @Test
    @SneakyThrows
    void givenUserInfo_whenGetUserInfo_thenStatusOk() {
        // given
        when(finder.byId(anyLong())).thenReturn(userInfo);

        // when
        userInfoService.findById(1L);

        // then
        verify(finder, times(1)).byId(1L);
    }

    @Test
    void givenUserInfo_whenUpdate_thenStatusAccepted() {
        // given
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        UserInfo userInfo = mock(UserInfo.class);

        when(userInfoMapper.toEntity(userInfoDTO)).thenReturn(userInfo);
        when(userInfoMapper.toDTO(userInfo)).thenReturn(userInfoDTO);

        // when
        userInfoService.update(userInfoDTO);

        // then
        verify(userInfo, times(1)).update();
    }

    @Test
    @SneakyThrows
    void whenDelete_thenStatusOK() {

        // given
        willDoNothing().given(finder).deleteById(1L);

        //when
        userInfoService.delete(1L);

        // then
        verify(finder, times(1)).deleteById(1L);
    }

    @Test
    @SneakyThrows
    void whenList_thenStatusOK() {
        // given
        List<UserInfo> lists = List.of(userInfo);
        willReturn(lists).given(finder).all();

        // when
        userInfoService.list();

        // then
        verify(finder, times(1)).all();
        verify(userInfoMapper, times(1)).toDTOList(any());
    }

}
