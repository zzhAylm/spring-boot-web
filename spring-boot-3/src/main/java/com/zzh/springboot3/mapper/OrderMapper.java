package com.zzh.springboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.springboot3.domain.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
