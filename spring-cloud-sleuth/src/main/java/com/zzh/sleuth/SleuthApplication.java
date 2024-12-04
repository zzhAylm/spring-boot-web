package com.zzh.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/22 14:45
 */
@Slf4j
@SpringBootApplication
public class SleuthApplication {
    public static void main(String[] args) {
        try {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(SleuthApplication.class);
            ApplicationContext context = builder.web(WebApplicationType.SERVLET).run(args);
            String[] activeProfiles = context.getEnvironment().getActiveProfiles();
            System.out.println("ActiveProfiles = " + String.join(",", activeProfiles));
        } catch (Exception e) {// 一定要加此try catch, 方便解决问题
            // 打印启动失败的错误信息
            e.printStackTrace();
        }
    }
}
