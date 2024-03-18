package com.doubao.douding.util;

import com.doubao.douding.exception.ServiceException;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.service.UserInfoService;
import jakarta.annotation.Resource;
import java.time.Instant;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
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
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    @Value("${expiry}")
    Long expiry;

    @Value("${refresh.expiry}")
    Long refreshExpiry;

    @Resource
    JwtEncoder encoder;

    @Resource
    UserInfoService userInfoService;

    @Resource
    PasswordEncoder passwordEncoder;

    /**
     * generate token
     * @param username the jwt subject
     * @return token
     */
    public String generateToken(String username) {
        Instant now = Instant.now();

        Instant expireAt = now.plusSeconds(expiry);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("self")
                                          .issuedAt(now)
                                          .expiresAt(expireAt)
                                          .subject(username)
                                          .claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toJsonString(userInfo))
                                          .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * according to token generate a refreshToken
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
}
