package com.zzh.springboot3.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/9 12:10
 */
@Data
@TableName("t_dept")
public class Dept {
    private Long id;
    private String name;
    private Long parentId;
    private Long leaderId;
    private Date createTime;
    private Date updateTime;
}
