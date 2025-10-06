package com.doubao.douding.system.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Johnson
 * @date 2024-03-02
 * @description the response of login
 */
@Getter
@Setter
@Builder
public class LoginResponseDTO {
    private String token;
    private Long expiry;
    private Instant expiredAt;

    private String refreshToken;
    private Long refreshTokenExpiry;
    private Instant refreshTokenExpiredAt;

}
