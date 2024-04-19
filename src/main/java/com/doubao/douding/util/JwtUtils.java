package com.doubao.douding.util;

import com.doubao.douding.system.dto.LoginResponseDTO;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.service.UserInfoService;
import jakarta.annotation.Resource;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import static com.doubao.douding.system.controller.LoginController.JWT_PAYLOAD_USER_KEY;

/**
 * @author Johnson
 * @date 2024-03-18
 * @description jwt utils for generate token
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    Long expiry;

    Long refreshExpiry;

    @Resource
    JwtEncoder encoder;

    @Resource
    UserInfoService userInfoService;

    @Resource
    PasswordEncoder passwordEncoder;

    /**
     * generate token
     *
     * @param userInfo the jwt subject
     * @return token
     */
    public String generateToken(UserInfo userInfo) {
        Instant now = Instant.now();

        Instant expireAt = now.plusSeconds(expiry);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("douding")
                                          .issuedAt(now)
                                          .expiresAt(expireAt)
                                          .subject(userInfo.getUsername())
                                          .claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toJsonString(userInfo))
                                          .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * according to token generate a refreshToken
     *
     * @param token token
     * @return refreshToken
     */
    public String generateRefreshToken(String token) {
        Instant now = Instant.now();
        final Instant refreshExpiresAt = now.plusSeconds(refreshExpiry);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .subject(token)
                                          .expiresAt(refreshExpiresAt)
                                          .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     *
     * @param userInfo userInfo
     * @return LoginResponseDTO
     */
    public LoginResponseDTO buildLoginResponse(final UserInfo userInfo) {
        Instant now = Instant.now();
        String token = generateToken(userInfo);
        String refreshToken = generateRefreshToken(token);

        final Instant expireAt = now.plusSeconds(expiry);
        final Instant refreshExpiresAt = now.plusSeconds(refreshExpiry);

        return LoginResponseDTO.builder()
                               .token(token)
                               .expiry(expiry)
                               .expiredAt(expireAt)
                               .refreshToken(refreshToken)
                               .refreshTokenExpiry(refreshExpiry)
                               .refreshTokenExpiredAt(refreshExpiresAt)
                               .build();
    }
}
