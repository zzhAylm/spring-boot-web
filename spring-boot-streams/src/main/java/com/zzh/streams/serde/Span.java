package com.zzh.streams.serde;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 20:04
 */
@Data
public class Span implements Serializable {

    private String traceId;
    private String id;
    private String kind;
    private String name;
    private String parentId;
    private long timestamp;
    private long duration;
    private Endpoint localEndpoint;
    private Endpoint remoteEndpoint;
    private List<Annotation> annotations;
    private Map<String, String> tags;

    @Data
    public static class Endpoint implements Serializable {
        private String serviceName;
        private String ipv4;
        private int port;
    }

    @Data
    public static class Annotation implements Serializable {
        private String timestamp;
        private String value;
    }
}
