package com.zzh.springboot.aviator.service;

import cn.hutool.extra.expression.engine.aviator.AviatorEngine;
import com.googlecode.aviator.AviatorEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2025/1/14 14:38
 */
@Slf4j
public class AviatorServiceTest {

    AviatorEngine aviatorEngine = new AviatorEngine();

    @Test
    public void aviatorTest() {
        String expression = "a + b";
        Map<String, Object> context = new HashMap<>();
        context.put("a", 1);
        context.put("b", 2);
        Object eval = aviatorEngine.eval(expression, context, null);
        log.info("eval is :{}", eval);
    }

    @Test
    public void aviatorTest2() {
        String expression = "a + b > 0? 1 : 0";
        Map<String, Object> context = new HashMap<>();
        context.put("a", 1);
        context.put("b", -2);
        Object eval = aviatorEngine.eval(expression, context, null);
        log.info("eval is :{}", eval);
    }


    @Test
    public void aviatorTest3() {
        String expression = "a!='1'";
        Map<String, Object> context = new HashMap<>();
        context.put("a", 1);
        Object eval = aviatorEngine.eval(expression, context, null);
        log.info("eval is :{}", eval);
    }

    @Test
    public void aviatorTest4() {
        String expression = "sysdate()";
        Map<String, Object> context = new HashMap<>();
        Object eval = aviatorEngine.eval(expression, context, null);
        log.info("eval is :{}", eval);
    }

    @Test
    public void aviatorTest5() {
        String expression = "rand()";
        Map<String, Object> context = new HashMap<>();
        Object eval = aviatorEngine.eval(expression, context, null);
        log.info("eval is :{}", eval);
    }


    @Test
    public void aviatorTest6() {
        String expression = "now()";
        Map<String, Object> context = new HashMap<>();
        Object eval = aviatorEngine.eval(expression, context, null);
        log.info("eval is :{}", eval);
    }


    @Test
    public void aviatorTest7() {
        String expression = "println(i);";
        Map<String, Object> context = new HashMap<>();
        context.put("i", 10);
        Object eval = aviatorEngine.eval(expression, context, null);
        log.info("eval is :{}", eval);
    }

    @Test
    public void aviatorTest8(){
        // 创建上下文
        Map<String, Object> env = new HashMap<>();

        // 使用 abs 函数
        String expression = "math.abs(-10)";  // 结果: 10
        Object result = AviatorEvaluator.execute(expression, env);
        System.out.println("math.abs(-10) = " + result);

        // 使用 sqrt 函数
        expression = "math.sqrt(16)";  // 结果: 4.0
        result = AviatorEvaluator.execute(expression, env);
        System.out.println("sqrt(16) = " + result);

        // 使用 max 函数
        expression = "max(5, 10)";  // 结果: 10
        result = AviatorEvaluator.execute(expression, env);
        System.out.println("max(5, 10) = " + result);

        expression="string.substring('adfasdf',1,2)";
        result = AviatorEvaluator.execute(expression, env);
        env.put("str","abcdefg");
        env.put("begin",0);
        env.put("end",1);
        log.info("string.substring is :{}", result);
    }


}
