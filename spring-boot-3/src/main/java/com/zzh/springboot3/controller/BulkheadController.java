package com.zzh.springboot3.controller;

import com.zzh.springboot3.service.BulkHeadService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 14:25
 */
@Slf4j
@RestController
@RequestMapping("/bulkhead")
public class BulkheadController {

    @Resource
    private BulkHeadService bulkHeadService;


    @RequestMapping("/a")
    public void bulkheadA() {
        bulkHeadService.bulkheadA();
    }

    @RequestMapping("/b")
    public void bulkheadB() {
        bulkHeadService.bulkheadB();
    }


    @RequestMapping("/threadPool/a")
    public void threadPoolBulkheadA() {
        bulkHeadService.threadPoolBulkheadA();
    }

    @RequestMapping("/threadPool/b")
    public void threadPoolBulkheadB() {
        bulkHeadService.threadPoolBulkheadB();
    }
}
