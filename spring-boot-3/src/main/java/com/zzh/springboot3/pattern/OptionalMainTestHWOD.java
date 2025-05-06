package com.zzh.springboot3.pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/12 15:11
 */
@Slf4j
public class OptionalMainTestHWOD {
    public static void main(String[] args) {
        Optional<String> zzh = Optional.of("zzh");

        Optional<Integer> t = Optional.of(67);
    }

    @Test
    public void optionalTest() {
        Optional<Object> empty = Optional.empty();
        log.info("optional is :{}", empty);
        Class<? extends OptionalMainTestHWOD> optionalMainTestClass = OptionalMainTestHWOD.class;
        Class<?>[] classes = optionalMainTestClass.getClasses();
       for (Class<?> c:classes){
           log.info(c.getName().split("\\$")[1]);
       }
    }

    @Data
    public static class OptionalTest1{

    }

    @Data
    public static class OptionalTest2{

    }
}
