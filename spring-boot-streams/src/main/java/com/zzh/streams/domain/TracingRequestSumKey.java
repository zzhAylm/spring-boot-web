package com.zzh.streams.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 17:19
 */
@Data
public class TracingRequestSumKey implements Serializable {

    private Integer partitionId;

    private String traceId;


}
