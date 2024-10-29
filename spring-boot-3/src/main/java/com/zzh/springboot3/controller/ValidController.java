package com.zzh.springboot3.controller;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.dto.RequestDto;
import com.zzh.springboot3.dto.ResponseDto;
import com.zzh.springboot3.dto.ValidDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/21 10:06
 */
@Slf4j
@RestController
@RequestMapping("/valid")
public class ValidController {


    @PostMapping("")
    public ResponseDto<String> valid(@Validated @RequestBody RequestDto<String> requestDto) {
        log.info("request is :{}", JSONUtil.toJsonStr(requestDto));
        return ResponseDto.success("zzh");
    }
    @RequestMapping("/custom")
    public ResponseDto<String> valid(@Validated @RequestBody ValidDto validDto) {
        log.info("request is :{}", JSONUtil.toJsonStr(validDto));
        return ResponseDto.success("zzh");
    }
}
