package com.zzh.springboot3.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/6 11:53
 */
@Data
@TableName("t_user")
public class User {
    private Long id;
    private Long roleId;
    private String name;
    private Date createTime;
    private Date updateTime;

}
