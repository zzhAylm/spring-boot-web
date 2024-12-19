package com.zzh.pattern;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 17:27
 */
@Slf4j
@SpringBootTest
public class DesignPatternTest {

    @Resource
    private List<String> designPatterBeans;

    @Test
    public void  testBeanRegister(){
        log.info("list bean is : {}", JSONUtil.toJsonStr(designPatterBeans));
    }

}
