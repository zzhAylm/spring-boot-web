package com.zzh.pattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 具体的被装饰者实现类
 * @Author: zzh
 * @Crete 2024/12/19 15:22
 */
@Slf4j
public class ComponentImpl implements Component {

    public void cost() {
        log.info("componentImpl is cost.");
    }
}
