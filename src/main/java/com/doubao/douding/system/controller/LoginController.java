package com.doubao.douding.system.controller;

import com.doubao.douding.exception.ServiceException;
import com.doubao.douding.system.dto.LoginDTO;
import com.doubao.douding.system.dto.LoginResponseDTO;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.service.UserInfoService;
import com.doubao.douding.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Johnson
 * @date 2024-03-02
 * @description login controller
 */
@Tag(name = "user login")
@RestController
public class LoginController {

    public static final String JWT_PAYLOAD_USER_KEY = "user";

    @Resource
    JwtEncoder encoder;

    @Resource
    UserInfoService userInfoService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    JwtUtils jwtUtils;

    @Operation(summary = "login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        UserInfo userInfo = userInfoService.getUserInfo(loginDTO);
        if (null == userInfo) {
            throw new ServiceException("User does not exists!");
        }
        final boolean matches = passwordEncoder.matches(loginDTO.getPassword(), new String(userInfo.getPassword()));
        if (!matches) {
            throw new ServiceException("Oops, password not matched!");
        }
        return ResponseEntity.accepted().body(jwtUtils.buildLoginResponse(userInfo, null));
    }

    @Operation(summary = "refresh token")
    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.accepted().body(jwtUtils.refreshToken(refreshToken));
    }

}
