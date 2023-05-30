package com.douding.douding.controller;

import com.douding.douding.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController{

    @GetMapping("/user")
    public User get(){
        User user = User.find.byId(1L);
        return user;
    }
}
