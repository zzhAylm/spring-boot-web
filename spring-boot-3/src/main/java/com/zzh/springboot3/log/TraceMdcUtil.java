package com.zzh.springboot3.log;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/24 16:52
 */
@Slf4j
public class TraceMdcUtil {

    public static final String TRACE_ID = "traceId";

    private static final String TRACE_NUM = "traceNum";

    public static void beginTraceNum() {
        MDC.put(TRACE_NUM, geoTraceNum());
    }

    public static void endTraceNum() {
        MDC.remove(TRACE_NUM);
    }

    private static String geoTraceNum() {
        return IdUtil.fastSimpleUUID();
    }

    public static String traceId() {
        return IdUtil.fastSimpleUUID();
    }
}
