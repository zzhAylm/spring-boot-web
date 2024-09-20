package com.zzh.springboot3.resilience4j;

import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 17:50
 */
@Service
public class Resilience4jService {


    // 熔断器
    @CircuitBreaker
    public void circuitBreaker(){

    }

}
