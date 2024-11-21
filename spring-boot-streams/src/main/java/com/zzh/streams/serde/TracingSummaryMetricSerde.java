package com.zzh.streams.serde;

import lombok.Data;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/21 11:14
 */
@Data
public class TracingSummaryMetricSerde implements Serde<TracingSummaryMetric> {

    @Override
    public Serializer<TracingSummaryMetric> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<TracingSummaryMetric> deserializer() {
        return new JsonDeserializer<>();
    }
}
