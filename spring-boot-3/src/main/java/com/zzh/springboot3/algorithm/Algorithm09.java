package com.zzh.springboot3.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Description: 动态规划
 * @Author: zzh
 * @Crete 2024/7/4 14:57
 */
public class Algorithm09 {


    public static void main(String[] args) {

    }


    /**
     * 300.最长递增子序列
     */
    public int lengthOfLIS(int[] nums) {

        if (nums.length == 1) {
            return 1;
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().orElse(1);
    }


    /**
     * 354. 俄罗斯套娃信封问题
     */
    public static int maxEnvelopes(int[][] envelopes) {

        Arrays.sort(envelopes, (int[] a, int[] b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

        int[] dp = new int[envelopes.length];
        Arrays.fill(dp, 1);
        int res = 1;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[i][1] > envelopes[j][1] && envelopes[i][0] > envelopes[j][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    @Test
    public void test() {

        int[][] envelopes = {{4, 5}, {4, 6}, {6, 7}, {2, 3}, {1, 1}};
        maxEnvelopes(envelopes);
    }


//
//
//    /**
//     * 下降路径最小和
//     * */
//    public int minFallingPathSum(int[][] matrix) {
//
//
//    }


    /**
     * 72.编辑距离
     */
    public int minDistance2(String word1, String word2) {
        return dp(word1, word1.length() - 1, word2, word2.length() - 1);
    }

    /**
     * 递归计算最少需要几次编辑，将s2->s1
     */
    public int dp(String s1, int n, String s2, int m) {
        if (n == -1) {
            return m + 1;
        }
        if (m == -1) {
            return n + 1;
        }
        if (s1.charAt(n) == s2.charAt(m)) {
            return dp(s1, n - 1, s2, m - 1);
        } else {
            return Math.min(dp(s1, n - 1, s2, m) + 1, Math.min(dp(s1, n - 1, s2, m - 1) + 1, dp(s1, n, s2, m - 1) + 1));
        }
    }

    public int minDistance1(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0;
        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j < m + 1; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + 1, dp[i][j - 1] + 1), dp[i - 1][j] + 1);
                }
            }
        }
        return dp[n][m];
    }


    public int dp(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < m + 1; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + 1, dp[i][j - 1] + 1), dp[i - 1][j] + 1);
                }
            }
        }

        return dp[m][m];
    }


    /**
     * 53. 最大子数组和
     */
    public int maxSubArray(int[] nums) {

        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];

        System.arraycopy(nums, 0, dp, 0, dp.length);

        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(dp[i], dp[i - 1] + nums[i]);
        }
        return Arrays.stream(dp).max().orElse(-1);
    }


    /**
     * 1143.最长公共子序列
     */
    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];

        int max = 0;
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = Math.max(Math.max(dp[i - 1][j - 1] + 1, dp[i - 1][j]), dp[i][j - 1]);
                } else {
                    dp[i][j] = Math.max(Math.max(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]);
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }

    @Test
    public void test2() {
        String text1 = "bsbininm";
        String text2 = "jmjkbkjkv";
        System.out.println(longestCommonSubsequence(text1, text2));
    }


    /**
     * 583.两个字符串的删除操作
     */
    public static int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i < m + 1; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp[n][m];
    }

    @Test
    public void test3() {
        System.out.println(minDistance("a", "b"));
    }


    /**
     * 712.两个字符串的最小ASXCll删除和
     */
    public int minimumDeleteSum(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = s1.charAt(i - 1) + dp[i - 1][0];
        }
        for (int i = 1; i < m + 1; i++) {
            dp[0][i] = s2.charAt(i - 1) + dp[0][i - 1];
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1), dp[i][j - 1] + s2.charAt(j - 1));
                }
            }
        }
        return dp[n][m];
    }


//    /**
//     * LCR 095.最长公共子序列
//     */
//    public int longestCommonSubsequence1(String text1, String text2) {
//
//
//    }


    /**
     * 134.加油站
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sum = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
        }
        if (sum < 0) {
            return -1;
        }
        sum = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
            if (sum < 0) {
                sum = 0;
                start = i + 1;
            }
        }
        return start == gas.length ? 0 : start;
    }

}
