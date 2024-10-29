package com.zzh.springboot3.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/12 10:00
 */
@Slf4j
public class PatternMainTest {

    @Test
    public void pattern() {
        String str = "zzh-ylm-zzh";
        Pattern pattern = Pattern.compile("(\\w)(\\w+)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            log.info("matcher count is :{}", matcher.groupCount());
            log.info("{}", matcher.group());
            log.info(matcher.group(1));
            log.info(matcher.group(2));
        }
    }

    public static void main(String[] args) {


        String str1 = "18348950824";
        Pattern pattern1 = Pattern.compile("[^a-z]+");
        Matcher matcher1 = pattern1.matcher(str1);
        while (matcher1.find()) {
            System.out.println(matcher1.group());
        }

        Pattern pattern2 = Pattern.compile("abc[^0-9]");
        String str2 = "abcd";
        String str3 = "abc1";
        String str4 = "abc2";
        Matcher matcher2 = pattern2.matcher(str2);
        while (matcher2.find()) {
            System.out.println(matcher2.group());
        }

        Matcher matcher3 = pattern2.matcher(str3);
        while (matcher3.find()) {
            System.out.println(matcher3.group());
        }

        Matcher matcher4 = pattern2.matcher(str4);
        while (matcher4.find()) {
            System.out.println(matcher4.group());
        }


        Pattern pattern3 = Pattern.compile("a(.)+c");
        String str5 = "abcabcabcabcabc";

        Matcher matcher5 = pattern3.matcher(str5);
        while (matcher5.find()) {
            System.out.println(matcher5.group());
            System.out.println(matcher5.group(1));
        }

        // \\1 替代前面匹配过程的，用于对称标签匹配
        Pattern compile = Pattern.compile("<(h[1-6])>\\w+?</\\1>");
        Matcher matcher6 = compile.matcher("<h3>x</h3>");

        while (matcher6.find()) {
            System.out.println(matcher6.group());
        }


        // 正则替换
        Pattern compile1 = Pattern.compile("(\\d{3})(-)(\\d{3})(-)(\\d{4})");

        Matcher matcher7 = compile1.matcher("313-555-1234");
        System.out.println(matcher7.replaceAll("($1) $3-$5"));
//        String str6="313-555-1234";
//        str6.replaceAll("(\\d{3})(-)(\\d{3})(-)(\\d{4})", "");

//         大小写转换：
        Pattern compile2 = Pattern.compile("(\\w)(\\w{2})(\\w)");
        Matcher matcher8 = compile2.matcher("abcd");
        System.out.println(matcher8.replaceAll("$1$2$3"));
    }


    @Test
    public void testPattern() {


        String str2 = "zzhylmzzh";
        Pattern pattern2 = Pattern.compile("(\\w)?(\\w)+?");
        Matcher matcher1 = pattern2.matcher(str2);
        while (matcher1.find()) {
            System.out.println(matcher1.group(0));
            System.out.println(matcher1.group(1));
            System.out.println(matcher1.group(2));
        }

        Pattern pattern3 = Pattern.compile("a(.+)c");
        String str5 = "abcabcabcabcabc";

        Matcher matcher5 = pattern3.matcher(str5);
        while (matcher5.find()) {
            System.out.println(matcher5.group());
            System.out.println(matcher5.group(1));
        }

    }

    @Test
    public void testW() {
        Pattern compile2 = Pattern.compile("(\\w)(\\w{2})(\\w)");
        Matcher matcher8 = compile2.matcher("abcd");
        System.out.println(matcher8.replaceAll("$1$2$3"));
    }


    // 分组匹配 分割
    @Test
    public void groupPatternTest() {
        System.out.println(Arrays.toString("a b c".split("\\s"))); // { "a", "b", "c" }
        System.out.println(Arrays.toString("a b  c".split("\\s+"))); // { "a", "b", "c" }
        System.out.println(Arrays.toString("a, b ;; ; c".split("[,;\\s]+"))); // { "a", "b", "c" }

        String str = "zzh-ylm-zzh";
        Pattern pattern = Pattern.compile("(\\w)(\\w)+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }


    // 搜索查抄匹配
    @Test
    public void searchPatternTest() {
        Pattern p = Pattern.compile("\\wo\\w");
        Matcher matcher = p.matcher("i dog fox wo od hhh opp ppo and");
        while (matcher.find()) {
            System.out.println(matcher.group()); // dog fox
        }
    }


    // 字符串替换
    @Test
    public void replacePatternTest() {
        String s = "the quick brown fox jumps over the lazy dog.";
        String r = s.replaceAll("\\s([a-z]{4})\\s", " <b>$1</b> ");
        System.out.println(r); // the quick brown fox jumps <b>over</b> the <b>lazy</b> dog.
        Pattern pattern = Pattern.compile("\\s([a-z]{4})\\s");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            System.out.println(matcher.replaceAll(" <b>$1</b> "));
        }

        // 自定义命名：捕获
        System.out.println(s.replaceAll("\\s(?<four>[a-z]{4})\\s", " <b>${four}</b> "));
    }

    @Test
    public void replacePatternTest2() {
        String input = "Hello, world! How are you?";
        Pattern pattern = Pattern.compile("(\\w+),\\s+(\\w+)!");
        Matcher matcher = pattern.matcher(input);
        String output = matcher.replaceAll("$2, $1!");
        System.out.println(output); // "world, Hello! How are you?"
    }

    @Test
    public void patternTest() {
        


    }


}
