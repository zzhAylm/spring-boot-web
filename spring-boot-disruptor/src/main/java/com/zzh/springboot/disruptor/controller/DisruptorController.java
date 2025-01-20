package com.zzh.springboot.disruptor.controller;

import com.zzh.springboot.disruptor.disruptor.StringDisruptorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/14 16:11
 */
@RestController
@RequestMapping("disruptor")
public class DisruptorController {

    @Resource
    private StringDisruptorService disruptorService;


    @GetMapping
    public void send() {
        disruptorService.publish(String.valueOf(System.currentTimeMillis()));
    }

}
