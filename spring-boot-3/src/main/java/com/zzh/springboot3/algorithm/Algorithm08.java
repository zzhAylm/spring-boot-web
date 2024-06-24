package com.zzh.springboot3.algorithm;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/21 17:06
 */
public class Algorithm08 {

    // utf-8 编码的方式一个 汉字 3 个字节

    public static void main(String[] args) {
        String str = "中文汉字";
        System.out.println(str.getBytes().length);

    }
}
