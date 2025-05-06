//package com.zzh.springboot3.annoncatin;
//
//import java.util.Arrays;
//import java.util.Scanner;
//
//// 注意类名必须为 Main, 不要有任何 package xxx 信息
//public class Main {
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//            int a = in.nextInt();
//           int[] arr=new int[a];
//
//           for(int i=0;i<a;i++){
//            arr[i]=in.nextInt();
//           }
//
//             int[] leftDp = new int[arr.length];
//        Arrays.fill(leftDp, 1);
//        for (int i = 1; i < arr.length; i++) {
//            for (int j = 0; j < i; j++) {
//                if (arr[i] > arr[j]) {
//                    leftDp[i] = Math.max(leftDp[i], leftDp[j] + 1);
//                }
//            }
//        }
//        int[] rightDp = new int[arr.length];
//        Arrays.fill(rightDp, 1);
//        rightDp[rightDp.length - 1] = 1;
//        for (int i = rightDp.length - 2; i >= 0; i--) {
//            for (int j = rightDp.length - 1; j > i; j--) {
//                if (arr[i] > arr[j]) {
//                    rightDp[i] = Math.max(rightDp[i], rightDp[j] + 1);
//                }
//            }
//        }
//
//        int max = 0;
//        for (int i = 0; i < arr.length; i++) {
//            if (leftDp[i] + rightDp[i] - 1 > max) {
//                max = leftDp[i] + rightDp[i] - 1;
//            }
//        }
//       System.out.println(a-max);
//
//
//    }
//}
