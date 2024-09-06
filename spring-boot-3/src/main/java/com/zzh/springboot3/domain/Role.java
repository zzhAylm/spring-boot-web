package com.zzh.springboot3.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/6 12:06
 */
@Data
@TableName("t_role")
public class Role {
    private Long id;
    private String name;
    private Date createTime;
    private Date updateTime;
}
