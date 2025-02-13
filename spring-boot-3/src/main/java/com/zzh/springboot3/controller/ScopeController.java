package com.zzh.springboot3.controller;

import com.zzh.springboot3.service.ScopeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/2/12 18:55
 */
@Slf4j
@RestController
@RequestMapping("/scope")
public class ScopeController {


    //  prototype作用域，没次Provider get获取的实例都是不同的
    @Autowired
    private ObjectProvider<ScopeService> serviceObjectProvider;

    //prototype 作用域，每次Autowired都会创建新的实例,但是注入的实例是同一个
    @Autowired
    private ScopeService scopeServiceA;

    @GetMapping
    public void getScope(){
        ScopeService scopeService = serviceObjectProvider.getIfAvailable();
        assert scopeService != null;
        log.info("scope service is :{}", scopeService.toString());
        log.info("scope service is :{}", scopeServiceA.toString());
    }

}
