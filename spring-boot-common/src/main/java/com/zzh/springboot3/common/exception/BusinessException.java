package com.zzh.springboot3.common.exception;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/9/10 11:18
 */
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String code, String message) {
        super("code:" + code + ",message:" + message);
    }

    public BusinessException(String code,String message, Throwable cause) {
        super("code:" + code + ",message:" + message, cause);
    }

}
