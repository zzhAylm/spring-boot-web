package com.zzh.redisson;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.zzh.redisson.service.RedissonService;
import groovy.lang.GroovyShell;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonReactive;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/16 20:56
 */
@Slf4j
public class StringTest {


    @Test
    public void stringTest() {
        System.out.println(String.class.getName());
        System.out.println(Integer.class.getName());
        System.out.println(Long.class.getName());
        System.out.println(int.class.getName());
        System.out.println(long.class.getName());
        System.out.println(double.class.getName());
        System.out.println(boolean.class.getName());
    }


    @Test
    public void expressionTest() {

        String str = "100 % 10 >=1";

        System.out.println(evaluateExpression(str));
    }

    public static boolean evaluateExpression(String expression) {
        // 创建脚本引擎管理器
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        try {
            Object result = engine.eval(expression);  // 执行 JavaScript 脚本
            System.out.println("Result: " + result);  // 输出结果
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Test
    public void groovy() {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.setVariable("a", 10);
        groovyShell.setVariable("b", 10);
        Object evaluate = groovyShell.evaluate("return a + b");
        log.info("evaluate is :{}", evaluate);
    }

    @Test
    public void groovyTest() {
        GroovyShell groovyShell = new GroovyShell();
        Object evaluate = groovyShell.evaluate("class Person {\n" +
                "    String name\n" +
                "    int age\n" +
                "}\n" +
                "\n" +
                "def person = new Person(name: \"John\", age: 30)\n" +
                "return person");
        log.info("evaluate is :{}", JSONUtil.toJsonStr(evaluate));
    }

    @Test
    public void aviatorTest() {
        Object result = AviatorEvaluator.execute("2 + 3 * 4");
        System.out.println(result);  // 输出 14

        Map<String, Object> env = new HashMap<>();
        env.put("x", 5);
        env.put("y", 10);

        // 解析表达式，并传入上下文变量
        Object result1 = AviatorEvaluator.execute("x + y * 2", env);
        System.out.println(result1);  // 输出 25 (5 + 10 * 2)
    }

    @Test
    public void aviatorTest2() {

        List<Long> a = new ArrayList<>();
        a.add(12L);
        a.add(20L);
        Map<String, Object> env = new HashMap<>();
        env.put("a", a);
        Boolean r = (Boolean) AviatorEvaluator.execute("a[0] > 10", env);

        log.info("boolean is :{}", r);
    }

    @Test
    public void aviatorTest3() throws IOException {
        //1.
        String exp = "a+b+c";
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 1);
        map.put("c", 1);
        Object res = AviatorEvaluator.getInstance().execute(exp, map);
        // Object res = AviatorEvaluator.getInstance();
        //2.
        Expression expression = AviatorEvaluator.getInstance().compileScript("av/hello.av");
        Object res2 = expression.execute(map);
        Expression expression1 = AviatorEvaluator.compile(exp);
        Object res3 = expression1.execute(map);
        System.out.println(res);
        System.out.println(res2);
        System.out.println(res3);
    }

    @Test
    public void aviatorTest4() throws IOException {
        Expression expression = AviatorEvaluator.getInstance().compileScript(ResourceUtil.getResource("new.av").getPath());
        expression.execute();
    }

    @Test
    public void aviatorTest5() {
        String exp = """
                let a = 1;
                let b = 2;
                c = a + b;
                return c;
                """;
        Object execute = AviatorEvaluator.execute(exp);
        log.info("execute result is :{}", execute);
    }

    @Test
    public void aviatorTest6() {
        String exp = """
                let a = 1;
                let b = 2;
                c = a + b;
                return c;
                """;
        Object execute = AviatorEvaluator.execute(exp);
        log.info("execute result is :{}", execute);
    }


    @Test
    public void aviatorTest7() {
        String expression = """
                a = 1;
                println(type(a));
                                
                s = "Hello AviatorScript";
                println(type(s));
                                
                let p = /(?i)hello\\s+(.*)/;
                println(type(p));
                                
                if s =~ p {
                  println($1);
                }
                """;
        AviatorEvaluator.execute(expression);
    }

    @Test
    public void aviatorTest8() {
        String expression = """
                use java.util.*;
                                
                let list = new ArrayList(10);
                                
                seq.add(list, 1);
                seq.add(list, 2);
                                
                p("list[0]=#{list[0]}");
                p("list[1]=#{list[1]}");
                                
                let set = new HashSet();
                seq.add(set, "a");
                seq.add(set, "a");
                                
                p("set type is: " + type(set));
                p("set is: #{set}");
                """;
        AviatorEvaluator.execute(expression);
    }

    @Test
    public void aviatorTest9() {
        String expression = """
                for i in range(0,10){
                     println(i);
                }
                """;
        AviatorEvaluator.execute(expression);
    }
}


