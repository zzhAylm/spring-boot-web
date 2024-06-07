package com.zzh.springboot3.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2023/9/27 14:51
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto<T> implements Serializable {

    @Builder.Default
    @ApiModelProperty("版本号 默认1.0")
    private String version = "1.0";

    @ApiModelProperty("签名")
    private String sign;

    @Builder.Default
    @ApiModelProperty("签名类型 默认RSA")
    private String signType = "RSA";

    @Builder.Default
    @ApiModelProperty("格式化编码 默认UTF-8")
    private String format = "UTF-8";

    @Builder.Default
    @ApiModelProperty("系统标识")
    @NotNull(message = "系统标识(sysId)不能为空")
    private String sysId = "zzh";

    @Builder.Default
    @ApiModelProperty("请求时间戳, 格式：yyyy-MM-dd HH:mm:ss.SSS")
    private String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());

    @Valid
    @NotNull(message = "业务请求数据(data)不能为空")
    @ApiModelProperty("业务请求数据")
    private T data;
}
