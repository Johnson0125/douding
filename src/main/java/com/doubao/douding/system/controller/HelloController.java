package com.doubao.douding.system.controller;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Johnson
 * @date 2024-03-02
 * @description hello controller for security test
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "hello" + authentication.getName() + "~";
    }

    @PostMapping("/generateReportTask")
    public void generateReportTask(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startTime, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {

    }
}
