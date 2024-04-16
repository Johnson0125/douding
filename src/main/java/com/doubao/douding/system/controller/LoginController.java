package com.doubao.douding.system.controller;

import com.doubao.douding.exception.ServiceException;
import com.doubao.douding.system.dto.LoginDTO;
import com.doubao.douding.system.dto.LoginResponseDTO;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.service.UserInfoService;
import com.doubao.douding.util.JsonUtils;
import jakarta.annotation.Resource;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Instant now = Instant.now();

        UserInfo userInfo = userInfoService.getUserInfo(loginDTO);
        final boolean matches = passwordEncoder.matches(loginDTO.getPassword(), new String(userInfo.getPassword()));
        if (!matches) {
            throw new ServiceException("Oops, password not matched!");
        }

        Instant expireAt = now.plusSeconds(expiry);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("douding")
                                          .issuedAt(now)
                                          .expiresAt(expireAt)
                                          .subject(userInfo.getUsername())
                                          .claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toJsonString(userInfo))
                                          .build();
        String token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        final Instant refreshExpiresAt = now.plusSeconds(refreshExpiry);
        claims = JwtClaimsSet.from(claims).subject(token).expiresAt(refreshExpiresAt).build();
        String refreshToken = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                                                            .token(token)
                                                            .expiry(expiry)
                                                            .expiredAt(expireAt)
                                                            .refreshToken(refreshToken)
                                                            .refreshTokenExpiry(refreshExpiry)
                                                            .refreshTokenExpiredAt(refreshExpiresAt)
                                                            .build();

        return ResponseEntity.accepted().body(loginResponseDTO);
    }
}
