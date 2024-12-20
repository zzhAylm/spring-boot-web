/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.zzh.mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zzh.mvc.valid.AddGroup;
import com.zzh.mvc.valid.DefaultGroup;
import com.zzh.mvc.valid.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;


@Data
public class UserDto implements Serializable {

    @Schema(title = "id")
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;


    @Schema(title = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{valid.password.require}", groups = AddGroup.class)
    private String password;


    @Schema(title = "性别   0：男   1：女    2：保密", required = true)
    @Max(value = 2, message = "{valid.gender.max}", groups = DefaultGroup.class)
    @Min(value = 0, message = "{valid.gender.min}", groups = DefaultGroup.class)
    private Integer gender;

    @Schema(title = "邮箱")
    @Email(message = "{valid.email.error}", groups = DefaultGroup.class)
    private String email;


    @Schema(title = "状态  0：停用    1：正常", required = true)
    @Max(value = 1, message = "{valid.status.max}", groups = DefaultGroup.class)
    @Min(value = 0, message = "{valid.status.min}", groups = DefaultGroup.class)
    private Integer status;


}
