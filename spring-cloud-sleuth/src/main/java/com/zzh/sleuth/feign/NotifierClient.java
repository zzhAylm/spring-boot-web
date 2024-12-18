package com.zzh.sleuth.feign;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/9 20:21
 */
@Component
@FeignClient(name = "NOTIFIER-WEB")
public interface NotifierClient {

    @PostMapping("/notifier/send")
    void send(@RequestBody JSONObject jsonObject);

}
