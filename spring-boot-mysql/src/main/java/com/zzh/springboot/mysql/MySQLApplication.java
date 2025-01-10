package com.zzh.springboot.mysql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @Author: zzh
 * @Crete ${DATE} ${TIME}
 */
@SpringBootApplication
@MapperScan("com.zzh.springboot.mysql.mapper")
public class MySQLApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySQLApplication.class, args);
    }
}
