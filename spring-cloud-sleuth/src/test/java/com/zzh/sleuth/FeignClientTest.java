package com.zzh.sleuth;

import cn.hutool.json.JSONObject;
import com.zzh.sleuth.feign.NotifierClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 15:39
 */
@Slf4j
@SpringBootTest
public class FeignClientTest {

    @Resource
    private NotifierClient notifierClient;

    @Test
    public void redissonClientTest_2() {
        JSONObject jsonObject = new JSONObject();
        JSONObject value = new JSONObject();
        value.set("appName", "");
        jsonObject.set("data", value);
        notifierClient.send(jsonObject);

    }


}
