package com.zzh.springboot3.algorithm;

import io.swagger.models.auth.In;

import java.util.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/31 14:27
 */
public class Algorithm04 {


    public static void main(String[] args) {

        System.out.println(combinationSum3(3, 7));
    }

    /**
     * 104.二叉树的最大深度
     */

    int dept = 0;
    int res = 0;

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        traverse(root);
        return res;
    }

    public void traverse(TreeNode node) {
        if (node == null) {
            return;
        }
        dept++;
        res = Math.max(dept, res);
        traverse(node.left);
        traverse(node.right);
        dept--;
    }

    int max = 0;

    /**
     * 543.二叉树的直径
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }
        traverse1(root);
        return max;
    }

    int traverse1(TreeNode node) {
        if (node.left == null && node.right == null) {
            return 0;
        }
        int left = 0;
        if (node.left != null) {
            left = traverse1(node.left) + 1;
        }

        int right = 0;
        if (node.right != null) {
            right = traverse1(node.right) + 1;
        }
        max = Math.max(left + right, max);
        return Math.max(left, right);
    }


    /**
     * 216. 组合总和 III
     */
    static List<List<Integer>> resList = new ArrayList<>();

    public static List<List<Integer>> combinationSum3(int k, int n) {
        traverse2(new HashSet<>(), k, n, 1);
        return resList;
    }

    public static void traverse2(Set<Integer> nums, int k, int n, int index) {
        if (n == 0 && k == 0) {
            resList.add(new ArrayList<>(nums));
            return;
        }
        for (int i = index; i <= 9; i++) {
            if (n - i < 0 || k == 0) {
                return;
            }
            nums.add(i);
            traverse2(nums, k - 1, n - i, i + 1);
            nums.remove(i);
        }
    }


    /**
     * 114.二叉树展开为链表
     */
    public void flatten(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }
        traverseTree(root);
    }

    public TreeNode traverseTree(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode left = node.left;
        TreeNode leftNode = traverseTree(left);
        TreeNode right = node.right;
        TreeNode rightNode = traverseTree(right);
        node.right = leftNode;
        node.left = null;
        TreeNode p = node;
        while (p.right != null) {
            p = p.right;
        }
        p.right = rightNode;
        return node;
    }
}
