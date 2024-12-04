package com.zzh.springboot3.controller;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.common.dto.RequestDto;
import com.zzh.springboot3.common.dto.ResponseDto;
import com.zzh.springboot3.dto.ValidDto;
import com.zzh.springboot3.dto.ValidNotNullDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDto<String> valid(@Validated @Valid @RequestBody ValidDto validDto) {
        log.info("request is :{}", JSONUtil.toJsonStr(validDto));
        return ResponseDto.success("zzh");
    }

    @PostMapping("/post")
    public ResponseDto<String> getValid(@Valid @RequestBody RequestDto<ValidNotNullDto> requestDto) {
        log.info("request is :{}", JSONUtil.toJsonStr(requestDto));
        return ResponseDto.success("zzh");
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @PostMapping("/exception")
    public ResponseDto<String> exception() {
        throw new RuntimeException("参数异常");
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> exceptionHandler(RuntimeException exception) {
        return ResponseEntity.ok().body(exception.getMessage());
    }
}
