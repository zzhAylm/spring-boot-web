package com.zzh.springboot3.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.springboot3.domain.Order;
import com.zzh.springboot3.domain.User;
import com.zzh.springboot3.mapper.OrderMapper;
import com.zzh.springboot3.mapper.UserMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/5 19:50
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {


    @Transactional
    public void userAdd1() {
        // 防止事务失效
        ((UserService) AopContext.currentProxy()).userAdd2();
    }

    @Transactional
    public void userAdd2() {

    }

}
