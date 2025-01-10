package com.zzh.springboot.mysql.domain;

import lombok.Data;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/9 17:24
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String address;
}
