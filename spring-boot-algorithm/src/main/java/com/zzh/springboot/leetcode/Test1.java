package com.zzh.springboot.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Create 2026/9/14 20:03
 */
public class Test1 {

    public static void main(String[] args) {

        dp(10, new int[]{1, 2, 5}, 3);
//        printA();
//        printNum(15);
//        printYXSJ(10);

//        intn(10);

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
        List<Integer> objects = new ArrayList<>();
        objects.add(0);
        objects.add(0);
        objects.add(0);
        print(objects, 10, new int[]{1, 2, 5}, 0);
    }


     static  List<List<Integer>> resList=new ArrayList<>();

    public static void print(List<Integer> res, int sum, int[] dp, int index) {
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

        for (int i = index; i < dp.length; i++) {
            res.set(index, res.get(index) + 1);
            print(res, sum - dp[i], dp, index );
            print(res, sum - dp[i], dp, (index + 1) % dp.length);
            res.set(index, res.get(index) - 1);
        }


    }

    public static void printA() {

        int n = 1;
        int max = 15;
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 15; j++) {
                int left = (max - n) / 2;
                int right = left + n;
                if (j >= left && j < right) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }

            }
            System.out.println();
            n += 2;
        }

    }


    public static List<List<Integer>> numsList = new ArrayList<>();

    public static void printNum(int n) {
        if (n <= 2) {
            return;
        }
        for (int i = 1; i < n; i++) {
            printNumDP(new ArrayList<>(), n, i);
        }
        if (numsList.size() == 0) {
            System.out.print("NONE");
        } else {
            for (int i = 0; i < numsList.size(); i++) {
                for (int j = 0; j < numsList.get(i).size(); j++) {
                    System.out.print(numsList.get(i).get(j) + " ");
                }
                System.out.println();
            }
        }
    }

    public static void printNumDP(List<Integer> res, int sum, int index) {
        if (sum == 0 && res.size() > 1) {
            numsList.add(res);
            return;
        }
        if (sum >= index) {
            res.add(index);
            printNumDP(res, sum - index, index + 1);
        }

    }

    public void dpYS(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 2;
        dp[3] = 3;
        dp[5] = 5;

    }

    public static void printYXSJ(int n) {

        int[][] dp = new int[n][n];

        for (int i = 0; i < dp.length; i++) {
            int left = 0;
            int right = left + i;

            int print = (n - i) / 2;
            for (int j = 0; j < print; j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < dp[i].length; j++) {
                if (j > right) {
                    System.out.print(" ");
                }
                if (j == left || j == right) {
                    dp[i][j] = 1;
                    System.out.print(dp[i][j] + " ");
                } else {
                    if (i >= 1 && j < right) {
                        dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                        System.out.print(dp[i][j] + " ");
                    }
                }

            }
            System.out.println();
        }
    }

    public static void intn(int n) {
        boolean[] booleans = new boolean[n + 1];
        countInt(1, booleans, 0, n);
    }

    public static void countInt(int index, boolean[] booleans, int count, int sum) {
        if (count == sum - 1) {
            for (int i = 1; i < booleans.length; i++) {
                if (!booleans[i]) {
                    System.out.println(i);
                }
            }
            return;
        }
        int num = 1;

        int start = index;
        while (num <= 3) {
            if (start > sum) {
                start = 1;
            }
            if (booleans[start]) {
                start++;
                continue;
            }
            if (num == 3) {
                booleans[start] = true;
                countInt(start + 1, booleans, count + 1, sum);
            }
            num++;
            start++;

        }


    }

}
