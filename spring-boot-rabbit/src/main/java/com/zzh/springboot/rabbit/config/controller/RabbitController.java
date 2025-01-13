package com.zzh.springboot.rabbit.config.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/13 15:38
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @GetMapping
    public void getMessage(HttpServletRequest httpServletRequest){

    }
}
