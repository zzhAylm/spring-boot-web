package com.zzh.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/5/29 14:26
 */
@Slf4j
@RestController
@RequestMapping("")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        // 1. 验证用户名/密码是否正确（查数据库）
        // 2. 构建 userDetails 信息
        // 3. 生成 token 并保存到 Redis
        // 4. 返回 token

        return ResponseEntity.ok(Collections.singletonMap("token", "token"));
    }
}
