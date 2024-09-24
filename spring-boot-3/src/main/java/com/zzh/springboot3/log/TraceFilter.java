package com.zzh.springboot3.log;


import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/24 16:50
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@Component
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            TraceMdcUtil.beginTraceNum();
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            TraceMdcUtil.endTraceNum();
            throw new RuntimeException(e);
        }
    }
}
