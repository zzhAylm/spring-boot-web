package com.zzh.eureka.api;

import com.zzh.springboot3.common.dto.RequestDto;
import com.zzh.springboot3.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 20:55
 */
@RequestMapping("eureka")
public interface EurekaProviderApi {

    @PostMapping("")
    ResponseDto<String> provider(@RequestBody RequestDto<String> requestDto);
}
