package com.zzh.streams.constant;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/20 10:33
 */
public class TracingConstant {

    //topic
    public static final String TRACING_SPAN_GROUP_BY_TRACING_ID_TOPIC = "tracing-span-group-by-tracing-id-repartition";
    public static final String TRACING_SPAN_GROUP_BY_SUMMARY_TOPIC = "tracing-span-group-by-summary-repartition";
    public static final String TRACING_SPAN_REQUEST_TATE_TOPIC = "tracing-span-request-rate-repartition";


    // streams builder
    public static final String TRACING_STREAMS_BUILDER = "defaultKafkaStreamsBuilder";
    public static final String TRACING_REQUEST_SUMMARY_STREAMS_BUILDER = "tracingRequestSummaryStreamsBuilder";
    public static final String TRACING_REQUEST_SUM_STREAMS_BUILDER = "TraceRequestSumStreamsBuilder";
    public static final String TRACING_REQUEST_RATE_STREAMS_BUILDER = "TraceRequestRateStreamsBuilder";


    //windows store
    public static final String TRACING_REQUEST_SUM_STORE = "tracing_request_sum_store";
    public static final String TRACING_REQUEST_SUM_WINDOW_STORE = "tracing_request_sum_window_store";


    //windows size
    public static final long TRACING_REQUEST_RATE_WINDOW_SIZE = 60 * 1000;
    public static final long TRACING_REQUEST_SUMMARY_WINDOW_SIZE = 60 * 1000;

}
