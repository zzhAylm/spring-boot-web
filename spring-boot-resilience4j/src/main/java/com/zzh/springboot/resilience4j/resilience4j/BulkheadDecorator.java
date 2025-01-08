package com.zzh.springboot.resilience4j.resilience4j;

import com.zzh.springboot.resilience4j.config.Resilience4jProperties;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/11 11:55
 */
@Slf4j
@Component
public class BulkheadDecorator extends AbstractBulkheadDecorator {

    private final Bulkhead bulkhead;
    private final ThreadPoolBulkhead threadPoolBulkhead;
    private final static String BULKHEAD_NAME = "bulkheadA";
    private final static String THREAD_POOL_BULKHEAD_NAME = "threadPoolBulkheadA";

    public BulkheadDecorator(Resilience4jProperties resilience4jProperties) {
        this.bulkhead = Resilience4j.getInstance().getBulkheadRegisterInstance().bulkhead(BULKHEAD_NAME, bulkheadConfig(resilience4jProperties));
        this.threadPoolBulkhead = Resilience4j.getInstance().getThreadPoolBulkheadRegisterInstance().bulkhead(THREAD_POOL_BULKHEAD_NAME, threadPoolBulkheadConfig(resilience4jProperties));
    }

    @Override
    Bulkhead getBulkhead() {
        return this.bulkhead;
    }

    @Override
    ThreadPoolBulkhead getThreadPoolBulkhead() {
        return this.threadPoolBulkhead;
    }

    @Override
    void doBulkheadMethod() {
        log.info("do method bulkhead start.....");
        try {
            Thread.sleep(1000);
            log.info("run bulkhead");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("do method bulkhead end.....");
    }


    @Override
    String doThreadPoolBulkheadMethod() {
        log.info("do method threadPool bulkhead start.....");
        try {
            Thread.sleep(1000);
            log.info("run threadPoolBulkhead");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("do method  threadPool bulkhead end.....");
        return "zzh";
    }
}
