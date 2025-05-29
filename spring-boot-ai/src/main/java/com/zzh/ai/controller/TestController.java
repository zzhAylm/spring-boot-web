package com.zzh.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/5/29 13:57
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("")
    public String get() {
        return "GET";
    }
}
