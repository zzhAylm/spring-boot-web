package com.zzh.springboot3.controller;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/20 14:18
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public void get() {
        log.info("get method");
    }

    @PostMapping
    public void post() {
        log.info("post method");
    }

    @DeleteMapping
    public void delete() {
        log.info("delete method");
    }

    @PutMapping
    public void put() {
        log.info("put method");
    }

    @PostMapping("/post")
    public ResponseDto<String> test(@RequestBody Object obj) {
        log.info("request is {}", JSONUtil.toJsonStr(obj));
        return ResponseDto.success("请求数据成功");
    }


}
