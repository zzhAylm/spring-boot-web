package com.zzh.streams.serde;

import com.zzh.streams.domain.TracingRequestSumKey;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/15 17:28
 */
public class TracingRequstSumKeySerde implements Serde<TracingRequestSumKey> {


    @Override
    public Serializer<TracingRequestSumKey> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<TracingRequestSumKey> deserializer() {
        return new JsonDeserializer<>();
    }
}
