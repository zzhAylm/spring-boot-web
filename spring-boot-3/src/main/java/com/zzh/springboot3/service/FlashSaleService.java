package com.zzh.springboot3.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: 秒杀系统设计
 * @Author: zzh
 * @Crete 2024/10/30 11:29
 */
@Slf4j
@Service
public class FlashSaleService {


    /**
     * 高性能：
     * 1.热点数据处理：使用缓存（JVM，redis）多级缓存
     * 2.静态资源处理：CDN，缓存静态资源，防止用户刷新静态资源请求访问到服务器
     *
     * 高可用：
     * 1.集群部署
     * 2.限流 ： 从用户范围内的压力角度考虑如何应用系统故障，接口的线程是为了对服务端的接口接口请求的频率进行限制，防护服务挂掉
     * 3.降级
     * 4.熔断
     * 5.流量削峰
     * **/



}
