//package com.zzh.springboot3.blockqueue;
//
//import java.util.Scanner;
//
//import java.util.*;
//
//// 注意类名必须为 Main, 不要有任何 package xxx 信息
//public class Main {
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        int n = in.nextInt();
//        List<String> strings = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            strings.add(in.next());
//        }
//        String str = in.next();
//        int index = in.nextInt();
//
//        if (strings == null || strings.size() == 0) {
//            System.out.println(0);
//            return;
//        }
//        List<String> brothers = new ArrayList<>();
//
//        for (String s : strings) {
//            if (str.length() != s.length() || str.equals(s)) {
//                continue;
//            }
//            int sum = 0;
//            for (int i = 0; i < str.length(); i++) {
//                if (str.charAt(i) != s.charAt(i)) {
//                    sum++;
//                }
//            }
//            if (sum != 2) {
//                continue;
//            }
//            brothers.add(s);
//        }
//        brothers.sort(String::compareTo);
//        System.out.println(brothers.size());
//        if (index <= brothers.size()) {
//            System.out.println(brothers.get(index));
//        }
//
//    }
//
//
//}
