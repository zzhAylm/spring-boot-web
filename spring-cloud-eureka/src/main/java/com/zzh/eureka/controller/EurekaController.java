package com.zzh.eureka.controller;

import com.zzh.eureka.feign.EurekaProviderFeignClient;
import com.zzh.springboot3.common.dto.RequestDto;
import com.zzh.springboot3.common.dto.ResponseDto;
import com.zzh.springboot3.common.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 20:57
 */
@Slf4j
@RestController
@RequestMapping("/eureka")
public class EurekaController {

    @Resource
    private EurekaProviderFeignClient eurekaProviderFeignClient;


    @GetMapping
    public void eureka()
    {
        RequestDto<String> requestDto = RequestDto.<String>builder().data("ylm").build();
        ResponseDto<String> responseDto = eurekaProviderFeignClient.provider(requestDto);
        log.info("response is :{}", JsonUtil.toJson(responseDto));
    }




}
