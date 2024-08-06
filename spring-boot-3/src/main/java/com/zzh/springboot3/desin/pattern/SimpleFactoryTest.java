package com.zzh.springboot3.desin.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * @Description: 简单工厂模式
 * @Author: zzh
 * @Crete 2024/8/6 13:40
 */
@Slf4j
public class SimpleFactoryTest {


    @Test
    public void test() {
        Product a = SimpleFactory.create("A");
        Product b = SimpleFactory.create("B");
        a.description();
        b.description();
    }


    /**
     * 优点：简单工厂可以使客户端免除直接创建对象的职责，能够根据需要创建出对应的产品。实现客户端和产品类代码分离。此外可以通过配置文件来实现不修改客户端代码的情况下添加新的具体产品类（改进）。
     * 缺点：违背开闭原则，如果需要新增其他产品类，就必须在工厂类中新增if-else逻辑判断（可以通过配置文件来改进）。但是整体来说，系统扩展还是相对其他工厂模式要困难。
     * 我们发现，简单工厂模式中的工厂类使用的是静态方法，那么为什么要这样做呢？可不可以使用非静态的方法呢？
     * 使用静态方法可以不需要使用new的方式创建对象，方便调用
     * 静态方法意味着可以直接获得实例对象，非静态方法只能通过构造方法（一般私有）调用，在工厂类以外不能被访问
     * 对于一些实例化和销毁对象比较敏感的场景，比如数据库连接池，实例化对象能够重复稳定的被使用
     * 综上来说，简单工厂模式适用于业务简单，产品固定不会经常改变工厂类的情况。
     ***/
    public static class SimpleFactory {
        public static Product create(String name) {
            if (Objects.equals(name, "A")) {
                return new ProductA();
            } else {
                return new ProductB();
            }
        }
    }

    private interface Product {
        void description();
    }

    private static class ProductA implements Product {
        @Override
        public void description() {
            log.info("ProductA");
        }
    }

    private static class ProductB implements Product {
        @Override
        public void description() {
            log.info("ProductB");
        }
    }


}
