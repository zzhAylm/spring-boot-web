package com.zzh.pattern.chain;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/23 20:40
 */
public interface AbstractHandler<T> {

    void handler(T t);

}
