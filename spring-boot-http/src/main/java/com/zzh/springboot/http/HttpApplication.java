package com.zzh.springboot.http;

import com.zzh.springboot.http.service.HttpServiceTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: zzh
 * @Crete ${DATE} ${TIME}
 */
@SpringBootApplication
public class HttpApplication {
    public static void main(String[] args) {
        HttpServiceTest httpServiceTest = new HttpServiceTest();
        SpringApplication.run(HttpApplication.class, args);
    }
}
