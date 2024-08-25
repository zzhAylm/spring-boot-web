package com.zzh.springboot3.desin.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description: 装饰者模式
 * @Author: zzh
 * @Crete 2024/8/6 20:29
 */
@Slf4j
public class DecoratorTest {

    @Test
    public void decoratorTest() {


    }


    // 装饰器
    // BufferedInputStream 装饰者
    // InputStream  被装饰者
    // ByteInputStream , FileInputStream : 被装饰者具体实现类
    public static abstract class Decorator implements Text {

        protected final Text text;

        public Decorator(Text text) {
            this.text = text;
        }

        @Override
        public void operator() {
            text.operator();
        }

    }

    // 具体装饰器
    public static class  AddDecorator extends Decorator {


        public AddDecorator(Text text) {
            super(text);
        }

        @Override
        public void operator() {
            log.info("add");
            text.operator();
        }
    }
    // 具体装饰器
    public static class  ReduceDesDecorator extends Decorator {

        public ReduceDesDecorator(Text text) {
            super(text);
        }

        @Override
        public void operator() {
            log.info("reduce");
            text.operator();
        }
    }

    public interface Text {
        void operator();
    }


    // 被装饰者
    public static class TextObj implements Text {

        @Override
        public void operator() {
            log.info("targetObj");
        }
    }


}

