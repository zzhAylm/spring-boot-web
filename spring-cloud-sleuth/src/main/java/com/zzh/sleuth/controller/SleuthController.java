package com.zzh.sleuth.controller;

import cn.hutool.json.JSONUtil;
import com.zzh.springboot3.common.dto.RequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/25 10:11
 */
@Slf4j
@RestController
@RequestMapping("/sleuth")
public class SleuthController {
//
//    @Resource
//    private Tracer tracer;

    @RequestMapping
    public void sleuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        log.info("start sleuth is");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        log.info("request URI is :{}",requestURI);
        log.info("end sleuth is ");
//    // Start a span. If there was a span present in this thread it will become
//    // the `newSpan`'s parent.
//        Span newSpan = this.tracer.nextSpan().name("calculateTax");
//        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
//            // ...
//            // You can tag a span
//            newSpan.tag("taxValue", "zzh");
//            // ...
//            // You can log an event on a span
//            newSpan.event("taxCalculated");
//        }
//        finally {v
//            // Once done remember to end the span. This will allow collecting
//            // the span to send it to a distributed tracing system e.g. Zipkin
//            newSpan.end();
//        }



    }



    @PostMapping
    public ResponseEntity<String> sleuthPost(HttpServletRequest httpServletRequest, @RequestBody RequestDto<String> requestDto) {
       log.info("request body is:{}", JSONUtil.toJsonStr(requestDto));
        return ResponseEntity.ok().body(httpServletRequest.getRequestURI());
    }




}
