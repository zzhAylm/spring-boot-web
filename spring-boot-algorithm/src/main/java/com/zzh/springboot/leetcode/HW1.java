package com.zzh.springboot.leetcode;

import com.zzh.springboot.algorithm.TreeNode;

import java.util.*;

/**
 * @Description:
 * @Author: zzh
 * @Create 2026/9/16 18:15
 */
public class HW1 {

    public static void main(String[] args) {
        HW1 hw1 = new HW1();
        hw1.rob1(new int[]{1});
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        changeTreeNode(root);
        return root;

    }

    public void changeTreeNode(TreeNode node) {
        if (node == null || (node.left == null && node.right == null)) {
            return;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        node.right = left;
        node.left = right;
        changeTreeNode(node.left);
        changeTreeNode(node.right);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        int left = 0;
        while (left < nums.length) {
            if (nums[left] > 0) {
                left++;
                continue;
            }
            int mid = left + 1;
            int right = nums.length - 1;
            while (mid < right) {
                int target = nums[left] + nums[mid] + nums[right];
                if (target > 0) {
                    right--;
                } else if (target < 0) {
                    mid++;
                } else {
                    res.add(List.of(nums[left], nums[mid], nums[right]));
                    mid++;
                    right--;
                    while (mid < right && nums[mid] == nums[mid - 1]) {
                        mid++;
                    }
                    while (right > mid && nums[right] == nums[right + 1]) {
                        right--;
                    }
                }
            }
            left++;
            while (left < nums.length && nums[left] == nums[left - 1]) {
                left++;
            }
        }


        return res;

    }


    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int left = 0;
        int right = 0;
        int max = 1;
        Map<Character, Integer> maps = new HashMap<>();

        while (right < s.length()) {
            char c = s.charAt(right);
            while (maps.containsKey(c)) {
                maps.remove(s.charAt(left));
                left++;
            }
            maps.put(c, right);
            max = Math.max(max, maps.size());
            right++;
        }
        return max;

    }

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[][] dp = new int[nums.length][2];
        dp[0][0] = 0;
        dp[0][1] = nums[0];
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][0] + nums[i]);
            max = Math.max(max, dp[i][0]);
            max = Math.max(max, dp[i][1]);
        }

        return max;
    }

    public int rob1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[] arr1 = Arrays.copyOfRange(nums, 0, nums.length - 1);
        int[] arr2 = Arrays.copyOfRange(nums, 1, nums.length);
        return Math.max(rowMax(arr1), rowMax(arr2));
    }

    public int rowMax(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];

        dp[0] = nums[0];
        dp[1] = Math.max(nums[1], dp[0]);
        int max = Math.max(dp[0], dp[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
            max = Math.max(dp[i], max);
        }
        return max;

    }
}
