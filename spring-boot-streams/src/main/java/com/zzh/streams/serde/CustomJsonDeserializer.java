package com.zzh.streams.serde;

import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomJsonDeserializer<T> extends JsonDeserializer<T> {
    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        if (headers == null || headers.toArray().length == 0) {
            // 处理头信息缺失的情况
            return super.deserialize(topic, data);
        }
        return super.deserialize(topic, headers, data);
    }
}

