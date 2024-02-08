package com.doubao.douding.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Johnson
 * @Date 2024/1/2-23:08
 * @Description: open api config
 **/

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                                      .title("document of Douding")
                                      .description("document of Douding")
                                      .contact(new Contact().name("Johnson").email("superzsen@126.com"))
                                      .version("0.0.1"));
    }
}
