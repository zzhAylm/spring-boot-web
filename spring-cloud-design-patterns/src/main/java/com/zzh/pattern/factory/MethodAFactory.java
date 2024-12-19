package com.zzh.pattern.factory;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 12:02
 */
//工厂方法
public class MethodAFactory implements MethodFactory {
    public Product createProduct() {
        return new ProductA();
    }
}
