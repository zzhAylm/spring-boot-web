package com.zzh.pattern.factory;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 12:02
 */
//简单工厂
public class SimpleFactory {

    public Product createProduct(String type) {
        if ("A".equals(type)) {
            return new ProductA();
        } else if ("B".equals(type)) {
            return new ProductB();
        }
        return null;
    }
}
