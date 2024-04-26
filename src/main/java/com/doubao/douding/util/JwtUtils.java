package com.doubao.douding.util;

import com.doubao.douding.exception.ServiceException;
import com.doubao.douding.system.dto.LoginDTO;
import com.doubao.douding.system.dto.LoginResponseDTO;
import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.service.UserInfoService;
import jakarta.annotation.Resource;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
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
    JwtDecoder decoder;

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
     * @param userInfo userInfo
     * @return LoginResponseDTO
     */
    public LoginResponseDTO buildLoginResponse(final UserInfo userInfo, String refreshToken) {
        Instant now = Instant.now();
        String token = generateToken(userInfo);
        if (StringUtils.isBlank(refreshToken)) {
            refreshToken = generateRefreshToken(token);
        }

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

    /**
     * using the refreshToken to generate a new token
     *
     * @param refreshToken refreshToken
     * @return all the token info
     */
    public LoginResponseDTO refreshToken(final String refreshToken) {
        Jwt jwt = this.decoder.decode(refreshToken);
        Instant expireAt = jwt.getExpiresAt();
        if (expireAt == null || expireAt.isBefore(Instant.now())) {
            throw new ServiceException("Oops refresh token expired!");
        }

        final String token = jwt.getSubject();
        Jwt tokenJwt = this.decoder.decode(token);
        String userName = tokenJwt.getSubject();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(userName);
        UserInfo userInfo = userInfoService.getUserInfo(loginDTO);
        return buildLoginResponse(userInfo, refreshToken);
    }
}
