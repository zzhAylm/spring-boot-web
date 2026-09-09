package com.zzh.springboot.leetcode;

import java.util.Scanner;

/**
 * @Description:
 * @Author: zzh
 * @Create 2026/9/8 13:35
 */
public class NK1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            System.out.println(dp(a));



        }
    }

    public static int dp(int n){
        if (n<=1){
            return 0;
        }
        int[] dp=new int[n+1];
        dp[2]=1;
        dp[3]=1;
        for (int i = 5; i <dp.length ; i++) {
            if (i%2==0){
                dp[i]=i/2;
            }else {
                dp[i]=dp[i-1];
            }

        }

        return dp[n];
        // n=2  1
        // n=3  1
        // n=4  2
        // 5    2
        // 6    3
        // 7    3
        // 8    4
        // 9    4

        // dp[i]=  n/3 + (n%3+n/3)/3
    }
}
