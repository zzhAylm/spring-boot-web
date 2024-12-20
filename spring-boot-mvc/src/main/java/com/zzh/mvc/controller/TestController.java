package com.zzh.mvc.controller;

import cn.hutool.json.JSONUtil;
import com.zzh.mvc.dto.Dept;
import com.zzh.mvc.dto.LoginUser;
import com.zzh.mvc.dto.UserDto;
import com.zzh.mvc.valid.AddGroup;
import com.zzh.mvc.valid.DefaultGroup;
import com.zzh.mvc.valid.UpdateGroup;
import com.zzh.springboot3.common.utils.JsonUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/20 14:11
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping()
    public void test(Dept dept, LoginUser loginUser) {
        log.info("dept is :{}", JSONUtil.toJsonStr(dept));
        log.info("user is :{}", JSONUtil.toJsonStr(loginUser));
    }


    @PostMapping("/valid")
    public void valid(@Validated(value = AddGroup.class) @RequestBody UserDto userDto) {
        log.info("valid user is :{}", JsonUtil.toJson(userDto));
    }

    @PostMapping("/valid/add")
    public void defaultValid(@Validated(value = DefaultGroup.class) @RequestBody UserDto userDto) {
        log.info("valid user is :{}", JsonUtil.toJson(userDto));
    }

    @PostMapping("/valid/update")
    public void updateValid(@Validated(value = UpdateGroup.class) @RequestBody UserDto userDto) {
        log.info("valid user is :{}", JsonUtil.toJson(userDto));
    }
}
