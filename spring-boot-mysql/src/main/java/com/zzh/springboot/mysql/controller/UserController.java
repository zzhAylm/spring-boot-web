package com.zzh.springboot.mysql.controller;

import com.zzh.springboot.mysql.domain.User;
import com.zzh.springboot.mysql.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/10 10:06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping
    public void addUser() {
        User user = new User();
        user.setAge(18);
        user.setName("zzh");
        user.setAddress("北京");
        userService.addUser(user);
    }
}
