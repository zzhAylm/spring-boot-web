package com.zzh.springboot3.desin.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description: 抽象工厂
 * @Author: zzh
 * @Crete 2024/8/6 15:52
 */
@Slf4j
public class AbstractFactoryTest {

    @Test
    public void test() {
        AbstractFactory abstractFactory1=new Product1Factory();
        AbstractFactory abstractFactory2=new Product2Factory();
        abstractFactory1.createA().description();
        abstractFactory1.createB().description();
        abstractFactory2.createA().description();
        abstractFactory2.createB().description();

    }


    public interface AbstractFactory {
        //产品主机
        ProductA createA();
        //产品键盘
        ProductB createB();
    }

    // 戴尔品牌的产品
    public static class Product1Factory implements AbstractFactory {
        @Override
        public ProductA createA() {
            return new ProductA1();
        }

        @Override
        public ProductB createB() {
            return new ProductB1();
        }
    }

    // 惠普品牌的产品
    public static class Product2Factory implements AbstractFactory {

        @Override
        public ProductA createA() {
            return new ProductA2();
        }
        @Override
        public ProductB createB() {
            return new ProductB2();
        }
    }


    // 主机
    private interface ProductA {
        void description();
    }

    // 戴尔主机
    public static class ProductA1 implements ProductA {

        @Override
        public void description() {
            log.info("戴尔主机");
        }
    }

    //惠普主机
    public static class ProductA2 implements ProductA {

        @Override
        public void description() {
            log.info("惠普主机");
        }
    }


    // 键盘
    private interface ProductB {
        void description();
    }

    //戴尔键盘
    public static class ProductB1 implements ProductB {

        @Override
        public void description() {
            log.info("戴尔键盘");
        }
    }

    //惠普键盘
    public static class ProductB2 implements ProductB {

        @Override
        public void description() {
            log.info("惠普键盘");
        }
    }

}
