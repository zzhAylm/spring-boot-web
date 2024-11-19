package com.zzh.streams;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/14 19:41
 */
@Slf4j
@SpringBootApplication
public class KafkaStreamsApplication {

    public static void main(String[] args) {
        try {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(KafkaStreamsApplication.class);
            ApplicationContext context = builder.web(WebApplicationType.SERVLET).run(args);
            String[] activeProfiles = context.getEnvironment().getActiveProfiles();
            System.out.println("ActiveProfiles = " + String.join(",", activeProfiles));
        } catch (Exception e) {// 一定要加此try catch, 方便解决问题
            // 打印启动失败的错误信息
            e.printStackTrace();
        }
    }
}
