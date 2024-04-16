package com.doubao.douding.system.filter;

import com.doubao.douding.system.constant.SystemConstant;
import com.doubao.douding.system.security.DoudingUserDetails;
import com.doubao.douding.system.security.DoudingUserDetailsService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @description:
 * @author: Johnson
 * @create: 2024-04-05 16:13
 **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    JwtDecoder decoder;

    @Resource
    DoudingUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(SystemConstant.JWTHeader.TOKEN_HEADER);

        if (authHeader == null || !authHeader.startsWith(SystemConstant.JWTHeader.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        final Jwt jwt = decoder.decode(token);
        final String username = jwt.getSubject();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            final Instant expiresAt = jwt.getExpiresAt();
            final boolean before = jwt.getExpiresAt().isBefore(new Date().toInstant());

            if (before) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );

                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
