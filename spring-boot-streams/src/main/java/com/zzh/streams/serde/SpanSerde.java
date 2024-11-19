package com.zzh.streams.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 15:07
 */
public class SpanSerde implements Serde<Span> {
    @Override
    public Serializer<Span> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Span> deserializer() {
        return new JsonDeserializer<>();
    }
}
