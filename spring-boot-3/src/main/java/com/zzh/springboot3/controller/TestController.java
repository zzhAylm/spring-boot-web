package com.zzh.springboot3.controller;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.common.dto.ResponseDto;
import com.zzh.springboot3.service.TestService;
import com.zzh.springboot3.utils.IPUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServletServerHttpRequest;
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

    @Resource
    private TestService testService;

    @GetMapping
    public void get() {
        log.info("get start");
        testService.get();
        log.info("get stop");
        log.info("get stop");
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

    @GetMapping("/ip")
    public ResponseDto<String> ipAddress(HttpServletRequest httpServletRequest) {
        String ipAddress = IPUtil.getIpAddress(new ServletServerHttpRequest(httpServletRequest));
        return ResponseDto.success(ipAddress);
    }

    @GetMapping("/customAnnotation")
    public ResponseDto<String> customAnnotation() {
        return testService.customAnnotation();
    }

}
