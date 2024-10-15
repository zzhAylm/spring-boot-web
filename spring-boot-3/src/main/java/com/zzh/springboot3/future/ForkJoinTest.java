package com.zzh.springboot3.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/14 20:24
 */
@Slf4j
public class ForkJoinTest {


    @Test
    public void forkJoinTest() {
        int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();
        log.info("commonPoolParallelism is :{}", commonPoolParallelism);
        ForkJoinPool ForkJoinPool = new ForkJoinPool();

    }


}
