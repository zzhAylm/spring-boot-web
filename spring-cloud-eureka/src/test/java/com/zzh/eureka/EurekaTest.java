package com.zzh.eureka;

import com.zzh.eureka.feign.EurekaProviderFeignClient;
import com.zzh.springboot3.common.dto.RequestDto;
import com.zzh.springboot3.common.dto.ResponseDto;
import com.zzh.springboot3.common.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 20:59
 */
@Slf4j
@SpringBootTest
public class EurekaTest {

    @Resource
    private EurekaProviderFeignClient eurekaClient;


    @Test
    public void eurekaTest()
    {
        ResponseDto<String> responseDto = eurekaClient.provider(RequestDto.<String>builder().build());
        log.info("eureka response is :{}", JsonUtil.toJson(responseDto));
    }
}
