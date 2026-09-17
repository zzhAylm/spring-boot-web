package com.zzh.springboot3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Create 2026/9/14 20:03
 */
public class Test {

    public static void main(String[] args) {

        dp(10, new int[]{1, 2, 5}, 3);
    }

    public int zhFlag(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n % 2 == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        for (int i = 2; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i * j > n) {
                    continue;
                }
                if (i * j == n) {
                    return 0;
                }
            }
        }
        return 1;
    }


    public void mn(int m, int n) {

        int max = 1;
        int min = m * n;
        for (int i = 2; i <= m * n; i++) {
            if (m % i == 0 && n % i == 0) {
                max = Math.max(max, i);
            }
            if (i % m == 0 && i % n == 0) {
                min = Math.min(min, i);
            }
        }
        System.out.println("最大公约数：" + max);
        System.out.println("最小公倍数：" + min);

    }

    public static void dp(int sum, int[] dp, int n) {
        print(new int[3], 10, new int[]{1, 2, 5}, 0);
    }

    public static void print(int[] res, int sum, int[] dp, int index) {
        if (sum < 0) {
            return;
        }

        if (sum == 0) {
            for (int re : res) {
                if (re == 0) {
                    return;
                }
            }
            System.out.println(res);
            return;
        }

        for (int i = 0; i < dp.length; i++) {
            res[index]++;
            print(res, sum - dp[i], dp, index);

            print(res, sum - dp[i], dp, index + 1);
            res[index]--;
        }


    }


}
