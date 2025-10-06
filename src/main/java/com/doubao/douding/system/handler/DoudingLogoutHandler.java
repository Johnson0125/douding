package com.doubao.douding.system.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static com.doubao.douding.system.constant.SystemConstant.JWTHeader.BEARER;
import static com.doubao.douding.system.constant.SystemConstant.JWTHeader.TOKEN_HEADER;

/**
 * @description: logout handler of douding
 * @author: Johnson
 * @create: 2024-04-05 15:52
 **/
@Service
public class DoudingLogoutHandler implements LogoutHandler {

    @Override
    public void logout(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final Authentication authentication) {
        String authHeader = request.getHeader(TOKEN_HEADER);
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(BEARER)) {
            return;
        }

        String token = authHeader.substring(7);
        // TODO set token expire in redis
    }
}
