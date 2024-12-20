package com.zzh.mvc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/20 14:12
 */
@Data
public class Dept implements Serializable {

    private String deptId;

    private String name;

}
