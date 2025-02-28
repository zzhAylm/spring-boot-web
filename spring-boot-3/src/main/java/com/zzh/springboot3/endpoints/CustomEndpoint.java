package com.zzh.springboot3.endpoints;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/2/27 17:00
 */
@Slf4j
@Component
@WebEndpoint(id = "customEndpoint")
public class CustomEndpoint {

    @ReadOperation
    public String readOperation() {
        return "customReadOperation";
    }

    @WriteOperation
    public String writeOperation(@Selector String key) {
        log.info("write operation key is :{}", key);
        return "writeOperation";
    }

    @WriteOperation
    public String writeOperation_1(@RequestParam String request) {
        log.info("write operation request is :{}", request);
        return "writeOperation";
    }

}
