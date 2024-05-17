package com.zzh.springboot3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/17 14:15
 */
@Slf4j
@SpringBootTest
public class ApplicationTest {

    @Test
    public void applicationTest(){
        Assertions.assertEquals("1", "1");
        log.info("application test");
    }
}
