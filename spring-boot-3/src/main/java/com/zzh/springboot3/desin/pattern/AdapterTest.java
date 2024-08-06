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

        Adapter adapter=new Adapter(new AdaptedObj());
        adapter.adapterMethod();
    }


    public  interface TargetObj{
        void adapterMethod();
    }

    @Data
    public static class Adapter implements TargetObj{

        private AdaptedObj adaptedObj;

        public Adapter(AdaptedObj adaptedObj) {
            this.adaptedObj = adaptedObj;
        }

        public void adapterMethod(){
            log.info(adaptedObj.getClass().toString());
        }

    }

    public static class AdaptedObj {

    }

}
