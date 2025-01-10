package com.zzh.springboot.mysql.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.springboot.mysql.domain.User;
import com.zzh.springboot.mysql.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/9 18:01
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user){
        userMapper.insert(user);
        throw new RuntimeException("事务回滚");
    }
}
