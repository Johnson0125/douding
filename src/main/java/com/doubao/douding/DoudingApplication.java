package com.doubao.douding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DoudingApplication {

    static void main(String[] args) {
        SpringApplication.run(DoudingApplication.class, args);
    }

}
