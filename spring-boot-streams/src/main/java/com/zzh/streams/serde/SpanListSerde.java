package com.zzh.streams.serde;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class SpanListSerde<T> implements Serde<List<T>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private final Class<T> clazz;

    public SpanListSerde(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Serializer<List<T>> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Error serializing List", e);
            }
        };
    }

    @Override
    public Deserializer<List<T>> deserializer() {
        return (topic, data) -> {
            try {
                return objectMapper.readValue(data, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            } catch (Exception e) {
                throw new RuntimeException("Error deserializing List", e);
            }
        };
    }
}
