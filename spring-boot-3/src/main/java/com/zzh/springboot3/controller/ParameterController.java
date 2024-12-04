package com.zzh.springboot3.controller;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.dto.FormDto;
import com.zzh.springboot3.common.dto.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/21 11:04
 */
@Slf4j
@RestController
@RequestMapping("parameter")
public class ParameterController {

    @RequestMapping("/path/{name}")
    public void pathParameter(@PathVariable String name) {
        log.info("path parameter,name is :{}", name);
    }

    @RequestMapping("/body")
    public void requestBodyParameter(@RequestBody RequestDto<String> requestDto) {
        log.info("request body parameter,request is :{}", JSONUtil.toJsonStr(requestDto));
    }

    @RequestMapping("form")
    public void formParameter(@ModelAttribute FormDto formDto) {
        log.info("form parameter is :{}", JSONUtil.toJsonStr(formDto));
    }

    @RequestMapping("query")
    public void queryParameter(@RequestParam("name") String name) {
        log.info("query parameter is :{}", name);
    }

}
