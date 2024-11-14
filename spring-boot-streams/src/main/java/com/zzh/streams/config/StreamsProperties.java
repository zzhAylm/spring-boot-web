package com.zzh.streams.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 19:58
 */
@Data
@ConfigurationProperties(prefix = StreamsProperties.PREFIX)
public class StreamsProperties {
    public static final String PREFIX = "zzh.streams";


    private String topic = "test";

    private Integer numThread = 5;

    private String bootstrapServers = "localhost:9092";


}
