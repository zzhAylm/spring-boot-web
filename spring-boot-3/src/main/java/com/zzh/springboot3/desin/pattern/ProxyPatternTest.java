package com.zzh.springboot3.desin.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description: 代理模式
 * @Author: zzh
 * @Crete 2024/9/23 10:44
 */
@Slf4j
public class ProxyPatternTest {


    public static interface Target {
        void display();
    }

    public static class TargetImpl implements Target {
        @Override
        public void display() {
            log.info("do display......");
        }
    }

    public static class ProxyTarget implements Target {

        private final Target target;

        public ProxyTarget(Target target) {
            this.target = target;
        }

        public void display() {
            log.info("proxy before......");
            target.display();
            log.info("proxy after......");
        }
    }


    @Test
    public void ProxyPatternMethodTest() {
        Target target = new TargetImpl();
        ProxyTarget proxyTarget = new ProxyTarget(target);
        proxyTarget.display();
    }

}
