package com.zzh.sleuth.advice;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/4 15:15
 */
@Slf4j
@RestControllerAdvice
public class SleuthRequestBodyAdvice extends RequestBodyAdviceAdapter {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        InputStream body = inputMessage.getBody();
        byte[] bytes = IoUtil.readBytes(body);
        HttpHeaders headers = inputMessage.getHeaders();
        String bodyStr = new String(bytes);
        log.info("before body read ,input message,body str is :{}", bodyStr);
        log.info("headers is :{}", headers);
        return new SleuthHttpInputMessage(headers, new ByteArrayInputStream(bodyStr.getBytes()));
//        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("after body read body is :{}", JSONUtil.toJsonStr(body));
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }

    public static class SleuthHttpInputMessage implements HttpInputMessage {
        private final HttpHeaders headers;

        private final InputStream body;

        public SleuthHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
