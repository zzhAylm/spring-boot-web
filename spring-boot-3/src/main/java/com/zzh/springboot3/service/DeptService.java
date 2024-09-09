package com.zzh.springboot3.service;

import com.zzh.springboot3.domain.Dept;
import com.zzh.springboot3.mapper.DeptMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 10:07
 */
@Service
public class DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void deptService(Dept dept) {
        deptMapper.insert(dept);
        if (dept.getId() % 2 == 0) {
            throw new RuntimeException("事务回滚");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void deptServiceUpdate(Dept dept) {



    }

}
