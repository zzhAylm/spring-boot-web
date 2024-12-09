package com.zzh.sleuth.disruptor;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/6 13:49
 */
@Data
public class Event<T> implements Serializable {

    private T data;

}
