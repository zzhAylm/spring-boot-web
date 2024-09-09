package com.zzh.springboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.springboot3.domain.Dept;
import com.zzh.springboot3.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

}
