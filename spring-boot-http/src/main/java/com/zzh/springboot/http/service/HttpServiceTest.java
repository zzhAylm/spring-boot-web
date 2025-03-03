package com.zzh.springboot.http.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/14 19:30
 */
@Slf4j
public class HttpServiceTest {


    @Test
    public void httpTest() {


    }


    @PostConstruct
    public void init(){
        log.info("httpServiceTest init");
    }


    @Test
    public void webClientTest(){

//        RestClient client = RestClient.builder()
//                .baseUrl("https://api.example.com")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//        WebClient webClient = WebClient.create();

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.example.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        webClient.get()
                .uri(URI.create("/api/data"))
                .retrieve()
                .bodyToMono(String.class)
                .block();  // `block()` 会阻塞等待结果
    }

}
