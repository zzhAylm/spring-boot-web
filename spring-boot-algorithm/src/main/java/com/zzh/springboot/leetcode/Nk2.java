package com.zzh.springboot.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @Description:
 * @Author: zzh
 * @Create 2026/9/8 13:51
 */
public class Nk2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int size = in.nextInt();
        Set<Integer> set=new HashSet<>();
        for (int i = 0; i < size; i++) {
            int b = in.nextInt();
            set.add(b);
        }
        int[] arr=new int[set.size()];
        int index=0;
        for (int num:set){
            arr[index++]=num;
        }
        Arrays.sort(arr);
        for (int num:arr){
            System.out.println(num);
        }




    }
}
