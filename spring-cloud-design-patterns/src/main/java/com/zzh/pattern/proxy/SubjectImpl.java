package com.zzh.pattern.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 15:50
 */
@Slf4j
public class SubjectImpl implements Subject {
    @Override
    public void proxy() {
        log.info("SubjectImpl proxy");
    }
}
