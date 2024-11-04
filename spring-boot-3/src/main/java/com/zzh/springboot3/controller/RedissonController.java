package com.zzh.springboot3.controller;

import com.zzh.springboot3.dto.ResponseDto;
import com.zzh.springboot3.service.RedissonService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/20 16:30
 */
@RestController
@RequestMapping("/redis")
public class RedissonController {


    @Resource
    private RedissonService redissonService;

    @GetMapping
    public void get() {
        redissonService.getLock();
    }

    @GetMapping("/lock")
    public void lock() {
        redissonService.lock();
    }


    @PostMapping("/bitmap")
    public void bitmap(@RequestParam("num") Integer num) {
        redissonService.bitmap(num);
    }

    @GetMapping("/bitmap/{num}")
    public ResponseDto<Boolean> bitmapGet(@PathVariable Integer num) {
        return ResponseDto.success(redissonService.bitmapGet(num));
    }

    @PostMapping("/bloomFilter")
    public void bloomFilter(@RequestParam("num") Integer num) {
        redissonService.bloomFilter(num);
    }

    @GetMapping("/bloomFilter/{num}")
    public ResponseDto<Boolean> bloomFilterGet(@PathVariable Integer num) {
        return ResponseDto.success(redissonService.bloomFilterGet(num));
    }

}
