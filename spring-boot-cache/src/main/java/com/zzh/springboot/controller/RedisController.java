package com.zzh.springboot.controller;

import com.zzh.springboot.service.RedisService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/2/27 13:48
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    @GetMapping
    public void publishMsg(String msg){
        redisService.publish(msg);
    }

    @GetMapping("queue")
    public void sendMsg(String msg){
        redisService.sendQueue(msg);
    }

    @GetMapping("blockQueue")
    public void sendMsgBlockQueue(){
        redisService.blockQueueSendMsg("blockQueue");
    }

    @GetMapping("delayQueue")
    public void sendMsgDelayQueue(){
        redisService.delayQueue();
    }


}
