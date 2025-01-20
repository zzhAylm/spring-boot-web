package com.zzh.springboot.http.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/14 19:30
 */
@Slf4j
public class HttpServiceTest {


    @Test
    public void httpTest() {


    }


    @PostConstruct
    public void init(){
        log.info("httpServiceTest init");
    }

}
