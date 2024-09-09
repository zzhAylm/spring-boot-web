package com.zzh.springboot3.service;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.domain.Dept;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 15:50
 */
@Slf4j
@Service
public class TransactionService {

    @Resource
    private DeptService deptService;


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void transaction() {
        Dept dept = new Dept();
        dept.setLeaderId(1L);
        dept.setName("架构部");
        dept.setParentId(0L);
        deptService.deptService(dept);
        log.info("insert into dept {}", JSONUtil.toJsonStr(dept));
    }

}
