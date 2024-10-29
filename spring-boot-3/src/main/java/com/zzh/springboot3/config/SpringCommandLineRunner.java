package com.zzh.springboot3.config;

import com.zzh.springboot3.service.TestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/16 18:24
 */
@Slf4j
@Component
public class SpringCommandLineRunner implements CommandLineRunner {

    @Resource
    private ObjectProvider<TestService> objectProvider;

    @Override
    public void run(String... args) throws Exception {
        log.info("spring web running,args is :{}", Arrays.toString(args));
        log.info("objectProvider is : {}",objectProvider.getIfAvailable());
    }
}
