package com.zzh.springboot3.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/2 13:39
 */
@Data
public class ValidNotNullDto implements Serializable {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "num不能为空")
    private Long num;
}
