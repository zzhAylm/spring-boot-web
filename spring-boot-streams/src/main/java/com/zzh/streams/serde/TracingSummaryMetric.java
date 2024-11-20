package com.zzh.streams.serde;

import cn.hutool.core.util.ReflectUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/19 14:32
 */
@Slf4j
@Data
public class TracingSummaryMetric implements Serializable {

    @ApiModelProperty("调用链名称")
    private String tracingName;
    @ApiModelProperty("调用方ServiceName")
    private String serviceName;
    @ApiModelProperty("调用方port")
    private Integer port;
    @ApiModelProperty("接口地址")
    private String url;
    @ApiModelProperty("请求方法类型：POST,GET")
    private String method;
    @ApiModelProperty("请求类型：CLIENT,SERVICE")
    private String kind;
    @ApiModelProperty("远程调用方的ServiceName（客户端或者服务端）")
    private String remoteEndpointServiceName;
    @ApiModelProperty("远程调用方的IP")
    private String remoteEndpointInstance;
    @ApiModelProperty("远程调用方的端口")
    private Integer remoteEndpointPort;




}