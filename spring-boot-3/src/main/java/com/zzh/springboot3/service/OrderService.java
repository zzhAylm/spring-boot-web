package com.zzh.springboot3.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.springboot3.domain.Order;
import com.zzh.springboot3.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/5 19:50
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {


}
