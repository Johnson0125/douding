package com.doubao.douding.config;

import io.ebean.Database;
import io.ebean.config.CurrentUserProvider;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Johnson
 * @Description Test Ebean Config
 */
@Configuration
@TestConfiguration
public class EbeanTestConfig {

    @Resource
    private Database database;

    @Bean
    public CurrentUserProvider currentUserProvider() {
        return () -> "test-user"; // 测试环境默认用户
    }
}
