package com.example.elastic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic.domain.User;
import com.example.elastic.service.UserService;

/**
 * @author xyw
 * @date 2020/12/02
 */
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public User findOne(){
        User user = userService.findOne();
        return user;
    }

}
