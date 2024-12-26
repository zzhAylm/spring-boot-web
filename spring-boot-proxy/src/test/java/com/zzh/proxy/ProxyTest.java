package com.zzh.proxy;

import com.zzh.proxy.service.ProxyService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 17:23
 */
@SpringBootTest
public class ProxyTest {
    @Resource
    private ProxyService proxyService;


    @Test
    public void proxyTest()
    {
        proxyService.proxy();
    }
}
