package com.zzh.springboot3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description:
 * @Author: zzh
 * @Crete ${DATE} ${TIME}
 */
@MapperScan("com.zzh.springboot3.mapper")
@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableTransactionManagement
public class SpringBoot3Application {
    private static final Logger log = LogManager.getLogger(SpringBoot3Application.class);

    public static void main(String[] args) {
        try {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBoot3Application.class);
            ApplicationContext context = builder.web(WebApplicationType.SERVLET).run(args);
            String[] activeProfiles = context.getEnvironment().getActiveProfiles();
            System.out.println("ActiveProfiles = " + String.join(",", activeProfiles));
        } catch (Exception e) {// 一定要加此try catch, 方便解决问题
            // 打印启动失败的错误信息
            e.printStackTrace();
        }
        print();

    }

    private static void print() {
        String sb = """

                                   _ooOoo_
                                  o8888888o
                                  88" . "88
                                  (| -_- |)
                                  O\\  =  /O
                               ____/`---'\\____
                             .'  \\\\|     |//  `.
                            /  \\\\|||  :  |||//  \\\s
                           /  _||||| -:- |||||-  \\\s
                           |   | \\\\\\  -  /// |   |
                           | \\_|  ''\\---/''  |   |
                           \\  .-\\__  `-`  ___/-. /
                         ___`. .'  /--.--\\  `. . __
                      ."" '<  `.___\\_<|>_/___.'  >'"".
                     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |
                     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /
                ======`-.____`-.___\\_____/___.-`____.-'======
                                   `=---='
                ...................................................
                """;
        log.info(sb);
    }

}
