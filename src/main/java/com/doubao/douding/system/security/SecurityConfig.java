package com.doubao.douding.system.security;

import com.doubao.douding.system.filter.JwtAuthenticationFilter;
import com.doubao.douding.system.handler.DoudingLogoutHandler;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Johnson
 * @date 2024-03-02
 * @description Security config
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Resource
    private DoudingLogoutHandler logoutHandler;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private DoudingAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private DoudingAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        authorize -> authorize.requestMatchers(
                                                  "/login", "/other", "/userInfo/register", "/generateReportTask",
                                                  "/v3/api-docs", "/v3/api-docs/**",
                                                  "/swagger-ui.html", "/swagger-ui/**",
                                                  "/rapidoc.html"
                                              )
                                              .permitAll()
                                              .requestMatchers("/**").hasRole("ADMIN")
                                              .anyRequest()
                                              .authenticated())
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    // allow iframe access
                    .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling(
                        exceptions -> exceptions.authenticationEntryPoint(authenticationEntryPoint)
                                                .accessDeniedHandler(accessDeniedHandler))
                    .logout(l -> l.logoutUrl("/logout")
                                  .addLogoutHandler(logoutHandler)
                                  .logoutSuccessHandler(
                                      (request, response, authentication) -> SecurityContextHolder.clearContext()));

        return httpSecurity.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * password encoder
     *
     * @return password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
