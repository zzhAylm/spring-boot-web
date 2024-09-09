package com.zzh.springboot3;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzh.springboot3.domain.Dept;
import com.zzh.springboot3.domain.Order;
import com.zzh.springboot3.domain.Role;
import com.zzh.springboot3.domain.User;
import com.zzh.springboot3.service.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/17 14:15
 */
@Slf4j
@SpringBootTest
public class ApplicationMapperTest {

    @Test
    public void applicationTest() {
        Assertions.assertEquals("1", "1");
        log.info("application test");
    }

    @Resource
    private OrderService orderService;

    @Test
    public void setShardingSphereDataSourceTest() {
        Order order = new Order();
        order.setUserId(IdUtil.getSnowflakeNextId());
        order.setName("zzh");
        orderService.save(order);
        log.info("order is : {}", JSONUtil.toJsonStr(order));
    }

    @Test
    public void orderServiceQuery() {
        Order order = orderService.getById(1038846286184316928L);
        log.info("query order is : {}", JSONUtil.toJsonStr(order));
    }

    @Resource
    private UserService userService;

    @Test
    public void userServiceTest() {
        User user = new User();
        user.setRoleId(IdUtil.getSnowflakeNextId());
        user.setName("zzh");
        userService.save(user);
        log.info("user is : {}", JSONUtil.toJsonStr(user));
    }


    @Resource
    private RoleService roleService;

    @Test
    public void roleServiceTest() {
        Role role = new Role();
        role.setName("管理员");
        roleService.save(role);
        log.info("role is : {}", JSONUtil.toJsonStr(role));
        log.info("start query-------------------------");
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(role);
        Role one = roleService.getOne(queryWrapper);
        log.info("query role is : {}", JSONUtil.toJsonStr(one));
    }

    @Test
    public void roleServiceQueryTest() {
        Role role = roleService.getById(1831952957720940545L);
        log.info("query role is :{}", JSONUtil.toJsonStr(role));
    }

    @Resource
    private DeptService deptService;

    @Test
    public void deptTest() {
        Dept dept = new Dept();
        dept.setLeaderId(1L);
        dept.setName("架构部");
        dept.setParentId(0L);
        deptService.deptService(dept);
        log.info("query dept is :{}", JSONUtil.toJsonStr(dept));
    }


    @Resource
    private TransactionService transactionService;


    @Test
    public void transactionService() {
        transactionService.transaction();
    }

}
