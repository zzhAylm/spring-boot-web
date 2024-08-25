package com.zzh.springboot3.desin.pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description: 适配器模式
 * @Author: zzh
 * @Crete 2024/8/6 19:30
 */
@Slf4j
public class AdapterTest {


    @Test
    public void test() {

        Adapter adapter = new Adapter(new AdaptedObj());
        adapter.adapterMethod();
    }


    /**
     * 优点：
     * 1、将目标类和适配者类解耦
     * 2、增加了类的透明性和复用性，将具体的实现封装在适配者类中，对于客户端类来说是透明的，而且提高了适配者的复用性
     * 3、灵活性和扩展性都非常好，符合开闭原则
     * <p>
     * 缺点：
     * 1、过多地使用适配器，会让系统非常零乱，不易整体进行把握。
     * 2、 由于 JAVA 至多继承一个类，所以至多只能适配一个适配者类，而且目标类必须是抽象类。
     **/

    // 要实现的方法，使用旧的类取适配次类的方法
    public interface TargetObj {
        void adapterMethod();
    }

    // 适配器中放入旧的类，方法出新的口，提供使用
    @Data
    public static class Adapter implements TargetObj {

        private AdaptedObj adaptedObj;

        public Adapter(AdaptedObj adaptedObj) {
            this.adaptedObj = adaptedObj;
        }

        public void adapterMethod() {
            log.info(adaptedObj.getClass().toString());
        }

    }

    public static class AdaptedObj {

    }

}
