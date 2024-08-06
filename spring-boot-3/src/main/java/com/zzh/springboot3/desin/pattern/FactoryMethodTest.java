package com.zzh.springboot3.desin.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 工厂方法模式
 * @Author: zzh
 * @Crete 2024/8/6 14:14
 */
@Slf4j
public class FactoryMethodTest {

    // 工厂方法示例
    private static Logger logger = LoggerFactory.getLogger(FactoryMethodTest.class);

    /**
     * 工厂方法模式
     **/
    @Test
    public void test() {
        Product productA = new ProductAFactory().create();
        Product productB = new ProductBFactory().create();
        productA.description();
        productB.description();
    }


    /**
     * 其中最主要的是 AbstractFactory类中的createProduct方法，通过这个方法来生成具体产品，这也是为什么叫工厂方法的原因。
     * 和简单工厂的静态方法不同，这里是使用的非静态调用方式。而且可以发现，没有了简单工厂中的 if-else逻辑判断，相对而言扩展性也要强的多。
     * 优点：完全实现开闭原则，实现了可扩展和更复杂的层次结构。明确了职责，具有多态性，适用于任何实体类。
     * 缺点：如果业务增加，会使得系统中类的个数成倍增加，提高了代码的复杂度
     **/
    public interface ProductFactory {
        Product create();
    }

    public static class ProductAFactory implements ProductFactory {
        public Product create() {
            return new ProductA();
        }
    }

    public static class ProductBFactory implements ProductFactory {
        public Product create() {
            return new ProductB();
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
