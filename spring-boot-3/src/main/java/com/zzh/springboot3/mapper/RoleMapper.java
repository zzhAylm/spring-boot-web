package com.zzh.springboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.springboot3.domain.Role;
import com.zzh.springboot3.domain.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}