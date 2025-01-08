package com.zzh.springboot.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: zzh
 * @Crete ${DATE} ${TIME}
 */
@SpringBootApplication
public class Resilience4jApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(Resilience4jApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

    }
}
