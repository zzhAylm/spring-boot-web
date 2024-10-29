package com.zzh.springboot3.algorithm;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/28 09:56
 */
public class Algorithm01 {

    public static void main(String[] args) {

    }

    /**
     * 21. 合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode res = new ListNode(-1);
        ListNode index = res;
        ListNode index1 = list1;
        ListNode index2 = list2;
        while (index1 != null && index2 != null) {
            if (index1.val <= index2.val) {
                index.next = index1;
                index1 = index1.next;
            } else {
                index.next = index2;
                index2 = index2.next;
            }
            index = index.next;
        }

        while (index1 != null) {
            index.next = index1;
            index1 = index1.next;
            index = index.next;
        }
        while (index2 != null) {
            index.next = index2;
            index2 = index2.next;
            index = index.next;
        }
        return res.next;
    }

    /**
     * 300. 最长递增子序列
     **/
    public int lengthOfLIS(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 1) {
            return nums.length;
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }

}
