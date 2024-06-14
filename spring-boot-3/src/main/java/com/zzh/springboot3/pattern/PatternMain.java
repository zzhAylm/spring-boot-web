package com.zzh.springboot3.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/12 10:00
 */
public class PatternMain {

    public static void main(String[] args) {
        String str="zzh-ylm-zzh";
        Pattern pattern=Pattern.compile("(\\w)(\\w)+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }


        String str1="18348950824";
        Pattern pattern1=Pattern.compile("[^a-z]+");
        Matcher matcher1 = pattern1.matcher(str1);
        while (matcher1.find()){
            System.out.println(matcher1.group());
        }

        Pattern pattern2=Pattern.compile("abc[^0-9]");
        String str2="abcd";
        String str3="abc1";
        String str4="abc2";
        Matcher matcher2 = pattern2.matcher(str2);
        while (matcher2.find()){
            System.out.println(matcher2.group());
        }

        Matcher matcher3 = pattern2.matcher(str3);
        while (matcher3.find()){
            System.out.println(matcher3.group());
        }

        Matcher matcher4 = pattern2.matcher(str4);
        while (matcher4.find()){
            System.out.println(matcher4.group());
        }


        Pattern pattern3=Pattern.compile("a(.)+c");
        String str5="abcabcabcabcabc";

        Matcher matcher5 = pattern3.matcher(str5);
        while (matcher5.find()){
            System.out.println(matcher5.group());
            System.out.println(matcher5.group(1));
        }

        Pattern compile = Pattern.compile("<(h[1-6])>\\w+?</\\1>");
        Matcher matcher6 = compile.matcher("<h3>x</h3>");

        while (matcher6.find()){
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
}
