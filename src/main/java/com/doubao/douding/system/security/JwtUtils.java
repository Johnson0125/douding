package com.doubao.douding.system.security;

import com.doubao.douding.system.dto.LoginResponseDTO;
import com.doubao.douding.system.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

    private Long expiry;

    private Long refreshExpiry;

    private String key;

    private String createToken(Map<String, Object> claims, String userName, Date expire) {
        Date now = new Date();
        if (null == expire) {
            expire = new Date(System.currentTimeMillis() + 1000 * expiry);
        }

        return Jwts.builder()
                   .claims()
                   .issuer("douding")
                   .subject(userName)
                   .issuedAt(now)
                   .expiration(expire)
                   .add(claims)
                   .and()
                   //                   .signWith(getSignKey())
                   //                   .signWith(key)
                   .signWith(getSignKey(), Jwts.SIG.HS256)
                   .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * generate token
     *
     * @param userName the userName
     * @return token
     */
    public String generateToken(String userName) {
        return createToken(null, userName, null);
    }

    /**
     * according to username generate a refreshToken
     *
     * @param userInfo userInfo
     * @return refreshToken
     */
    public String generateRefreshToken(UserInfo userInfo) {
        Date expire = new Date(System.currentTimeMillis() + 1000 * expiry);
        return createToken(null, userInfo.getUsername(), expire);
    }

    /**
     * @param userInfo     userInfo
     * @return LoginResponseDTO
     */
    public LoginResponseDTO buildLoginResponse(final UserInfo userInfo) {
        Instant now = Instant.now();
        String token = generateToken(userInfo.getUsername());
        String refreshToken = generateRefreshToken(userInfo);

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
    public LoginResponseDTO refreshAccessToken(final String refreshToken) {

        final Claims claim = getClaims(refreshToken);

        String userName = claim.getSubject();
        final Date expiration = claim.getExpiration();

        String token = generateToken(userName);

        Instant now = Instant.now();
        final Instant expireAt = now.plusSeconds(expiry);

        return LoginResponseDTO.builder()
                               .token(token)
                               .expiry(expiry)
                               .expiredAt(expireAt)
                               .refreshToken(refreshToken)
                               .refreshTokenExpiry(refreshExpiry)
                               .refreshTokenExpiredAt(expiration.toInstant())
                               .build();
    }

    public Claims getClaims(final String refreshToken) {
        return Jwts.parser()
                   .verifyWith(getSignKey())
                   .build()
                   .parseSignedClaims(refreshToken)
                   .getPayload();
    }
}
