package com.zzh.springboot3.controller;

import cn.hutool.json.JSONObject;
import com.zzh.springboot3.schedule.RedissonDelayQueueService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/31 15:42
 */
@Slf4j
@RestController
@RequestMapping("/delay")
public class DelayController {

    @Resource
    private RedissonDelayQueueService delayQueueService;


    @PostMapping
    public void addTask(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.set("name", "zzh");
        jsonObject.set("age", 18);
        delayQueueService.addTask(jsonObject,10L);
    }


}
