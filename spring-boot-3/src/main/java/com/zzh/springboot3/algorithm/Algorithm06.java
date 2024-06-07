package com.zzh.springboot3.algorithm;

import java.util.Arrays;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/7 11:05
 */
public class Algorithm06 {


    /**
     * 322.零钱兑换
     */
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }


    /**
     * 300.最长递增子序列
     */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        // base case：dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);

            }
        }

        int res = 0;
        for (int j : dp) {
            res = Math.max(res, j);
        }
        return res;
    }


    /**
     * 931.下降路径最小和
     */
    public static int minFallingPathSum(int[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        if (matrix.length == 1) {
            return matrix[0][0];
        }

        int[][] dp = new int[matrix.length][matrix.length];

        for (int[] arr : dp) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        System.arraycopy(matrix[0], 0, dp[0], 0, dp.length);
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                if (j - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], matrix[i][j] + dp[i - 1][j - 1]);
                }
                dp[i][j] = Math.min(dp[i][j], matrix[i][j] + dp[i - 1][j]);
                if (j + 1 < dp.length) {
                    dp[i][j] = Math.min(dp[i][j], matrix[i][j] + dp[i - 1][j + 1]);
                }
            }
        }
        return Arrays.stream(dp[dp.length - 1]).min().orElse(0);
    }

    public static void main(String[] args) {

        int[][] matrix = {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}};
        System.out.println(minFallingPathSum(matrix));
    }
}
