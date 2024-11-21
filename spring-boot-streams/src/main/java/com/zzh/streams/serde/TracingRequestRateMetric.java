package com.zzh.streams.serde;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/21 17:48
 */
@Data
public class TracingRequestRateMetric implements Serializable {

    private String applicationName;
    private String instance;
    private String url;
    private String method;
}
