package com.zzh.eureka.controlller;

import com.zzh.eureka.api.EurekaProviderApi;
import com.zzh.springboot3.common.dto.RequestDto;
import com.zzh.springboot3.common.dto.ResponseDto;
import com.zzh.springboot3.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 21:04
 */
@Slf4j
@RestController
@RequestMapping("eureka")
public class EurekaProviderController implements EurekaProviderApi {
    @Override
    @PostMapping
    public ResponseDto<String> provider(RequestDto<String> requestDto) {
        log.info("eureka provider ,request is :{}", JsonUtil.toJson(requestDto));
        return ResponseDto.success("zzh");
    }
}
