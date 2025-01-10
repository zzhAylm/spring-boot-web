package com.zzh.springboot.mysql;

import com.zzh.springboot.mysql.domain.User;
import com.zzh.springboot.mysql.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/9 17:47
 */
@SpringBootTest
public class DBTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void insertTest() {
        User user = new User();
        user.setName("zzh");
        user.setAge(18);
        user.setAddress("北京");
        userMapper.insert(user);
    }

}
