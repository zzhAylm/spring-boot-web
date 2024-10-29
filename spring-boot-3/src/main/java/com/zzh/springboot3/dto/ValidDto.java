package com.zzh.springboot3.dto;

import com.zzh.springboot3.annoncatin.ValidAnnotation;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/21 10:11
 */
@Data
@EqualsAndHashCode
public class ValidDto {

    @ValidAnnotation(value = {"0", "1"},message = "标志位只能位0或者1")
    private String flag;

    @NotEmpty
    private String name;

}
