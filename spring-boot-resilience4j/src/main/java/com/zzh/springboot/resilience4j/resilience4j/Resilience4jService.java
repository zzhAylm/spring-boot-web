package com.zzh.springboot.resilience4j.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 17:50
 */
@Service
public class Resilience4jService {


    // 熔断器
    @CircuitBreaker(name = "")
    public void circuitBreaker(){

    }

}
