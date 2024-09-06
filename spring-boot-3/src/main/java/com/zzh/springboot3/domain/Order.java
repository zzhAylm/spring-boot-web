package com.zzh.springboot3.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/5 16:09
 */
@Data
@TableName("t_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id; // 主键 ID
    private Long userId;
    private String name;
    private Date createTime;
    private Date updateTime;
}
