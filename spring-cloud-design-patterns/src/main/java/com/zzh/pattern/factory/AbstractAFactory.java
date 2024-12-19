package com.zzh.pattern.factory;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 12:02
 */
//抽象工厂，AbstractFactoryBean
public class AbstractAFactory implements AbstractFactory {

    @Override
    public Product createProduct() {
        return new ProductA();
    }

    @Override
    public ProductUp createProductUp() {
        return new ProductAUp();
    }
}
