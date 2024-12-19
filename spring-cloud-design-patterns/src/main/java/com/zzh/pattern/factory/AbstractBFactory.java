package com.zzh.pattern.factory;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 12:02
 */
//抽象工厂,spring 使用实例：AbstractFactoryBean
public class AbstractBFactory implements AbstractFactory {
    @Override
    public Product createProduct() {
        return new ProductB();
    }

    @Override
    public ProductUp createProductUp() {
        return new ProductBUp();
    }
}
