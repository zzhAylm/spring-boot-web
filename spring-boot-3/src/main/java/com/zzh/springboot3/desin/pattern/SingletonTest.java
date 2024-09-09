package com.zzh.springboot3.desin.pattern;

import com.zzh.springboot3.desin.pattern.singleton.SingletonPattern;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description: 单例模式
 * @Author: zzh
 * @Crete 2024/9/9 16:58
 */
@Slf4j
public class SingletonTest {

    @Test
    public void getSingleton(){
        SingletonPattern instance = SingletonPattern.getInstance();
        log.info(instance.toString());
    }


}
