package com.doubao.douding.system.controller;

import com.doubao.douding.exception.ServiceException;
import com.doubao.douding.system.dto.LoginDTO;
import com.doubao.douding.system.dto.LoginResponseDTO;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.service.UserInfoService;
import com.doubao.douding.util.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Johnson
 * @date 2024-03-02
 * @description login controller
 */
@RestController
public class LoginController {

    public static final String JWT_PAYLOAD_USER_KEY = "user";

    @Value("${jwt.expiry}")
    Long expiry;

    @Value("${jwt.refresh.expiry}")
    Long refreshExpiry;

    @Resource
    JwtEncoder encoder;

    @Resource
    UserInfoService userInfoService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        UserInfo userInfo = userInfoService.getUserInfo(loginDTO);
        final boolean matches = passwordEncoder.matches(loginDTO.getPassword(), new String(userInfo.getPassword()));
        if (!matches) {
            throw new ServiceException("Oops, password not matched!");
        }
        return ResponseEntity.accepted().body(jwtUtils.buildLoginResponse(userInfo));
    }

}
