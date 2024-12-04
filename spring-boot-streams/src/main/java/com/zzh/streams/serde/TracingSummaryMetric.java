package com.zzh.streams.serde;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/19 14:32
 */
@Data
public class TracingSummaryMetric implements Serializable {

    @Schema(name = "调用链名称")
    private String tracingName;
    @Schema(name ="调用方ServiceName")
    private String serviceName;
    @Schema(name ="调用方port")
    private Integer port;
    @Schema(name ="接口地址")
    private String url;
    @Schema(name ="请求方法类型：POST,GET")
    private String method;
    @Schema(name ="请求类型：CLIENT,SERVICE")
    private String kind;
    @Schema(name ="远程调用方的ServiceName（客户端或者服务端）")
    private String remoteEndpointServiceName;
    @Schema(name ="远程调用方的IP")
    private String remoteEndpointInstance;
    @Schema(name ="远程调用方的端口")
    private Integer remoteEndpointPort;


}
