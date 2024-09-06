package com.zzh.springboot3.controller;

import com.zzh.springboot3.domain.Order;
import com.zzh.springboot3.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/6 10:28
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;


    @PostMapping
    public void order(Order order){

    }

}
