package com.zzh.springboot3.service;

import com.zzh.springboot3.advice.CustomAnnotation;
import com.zzh.springboot3.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/25 14:38
 */
@Slf4j
@Service
public class TestService {


    public void get()  {
        log.info("test service test method start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("test service test method end");
    }

    @CustomAnnotation
    public ResponseDto<String> customAnnotation(){
        log.info("custom annotation is running");
        return ResponseDto.success("test");
    }

}
