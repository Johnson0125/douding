package com.doubao.douding.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Johnson
 * @Date 2024/1/2-23:08
 * @Description: open api config
 **/

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEMA_NAME = "Authorization";
    private static final String SECURITY_SCHEMA_BEAR = "bearer";
    private static final String SECURITY_SCHEMA_JWT = "JWT";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("document of Douding")
                                            .description("document of Douding")
                                            .contact(new Contact().name("Johnson").email("johnsonzhang0311@126.com"))
                                            .version("V0.0.1")
                                            .license(new License().name("Apache 2.0")))
                            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEMA_NAME))
                            .components(new Components().addSecuritySchemes(
                                SECURITY_SCHEMA_NAME, new SecurityScheme().name(SECURITY_SCHEMA_NAME)
                                                                          .type(SecurityScheme.Type.HTTP)
                                                                          .scheme(SECURITY_SCHEMA_BEAR)
                                                                          .bearerFormat(SECURITY_SCHEMA_JWT)
                            ));
    }

}
