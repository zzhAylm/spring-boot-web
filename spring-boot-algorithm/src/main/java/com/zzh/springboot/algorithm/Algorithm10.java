package com.zzh.springboot.algorithm;


import java.util.*;

/**
 * @Description: 回溯算法
 * @Author: zzh
 * @Crete 2024/7/8 13:59
 */
public class Algorithm10 {

    public static void main(String[] args) {

    }


    /**
     * 46.全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        backtrack(nums, new ArrayList<>(), new HashSet<>());
        return res;
    }

    List<List<Integer>> res = new ArrayList<>();

    public void backtrack(int[] nums, List<Integer> list, Set<Integer> chooses) {
        if (chooses.size() == nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!chooses.contains(i)) {
                list.add(nums[i]);
                chooses.add(i);
                backtrack(nums, list, chooses);
                chooses.remove(i);
                list.remove((Object) nums[i]);
            }
        }
    }


    List<List<Integer>> resList = new ArrayList<>();

    /**
     * 216.组合总和3
     **/
    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrack(new LinkedList<>(), k, n, 1);
        return resList;
    }

    public void backtrack(LinkedList<Integer> list, int k, int n, int index) {
        if (k == 0 && n == 0) {
            resList.add(new ArrayList<>(list));
            return;
        }
        for (int i = index; i <= 9; i++) {
            if (n < i || k <= 0) {
                continue;
            }
            list.add(i);
            backtrack(list, k - 1, n - i, i + 1);
            list.removeLast();
        }
    }


    /**
     * 39.组合总和
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtrace(candidates, new LinkedList<>(), target, 0);
        return targetList;
    }

    List<List<Integer>> targetList = new ArrayList<>();

    public void backtrace(int[] candidates, LinkedList<Integer> nums, int target, int index) {
        if (target == 0) {
            targetList.add(new ArrayList<>(nums));
            return;
        }

        if (target < 0) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) {
                continue;
            }
            nums.add(candidates[i]);
            backtrace(candidates, nums, target - candidates[i], i);
            nums.removeLast();
        }

    }


    /**
     * 组合总和2
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        Arrays.sort(candidates);
        backtrace2(candidates, new LinkedList<>(), target, 0);
        return targetList;
    }

    public void backtrace2(int[] candidates, LinkedList<Integer> nums, int target, int index) {
        if (target == 0) {
            targetList.add(new ArrayList<>(nums));
            return;
        }

        if (target < 0) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) {
                continue;
            }
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            nums.add(candidates[i]);
            backtrace2(candidates, nums, target - candidates[i], i + 1);
            nums.removeLast();
        }
    }


    /***
     * 47.全排列II
     * */
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        Arrays.fill(used, false);
        backtrack(new LinkedList<>(), nums, used);
        return perList;
    }

    List<List<Integer>> perList = new ArrayList<>();

    public void backtrack(LinkedList<Integer> list, int[] nums, boolean[] used) {
        if (list.size() == nums.length) {
            perList.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            list.add(nums[i]);
            used[i] = true;
            backtrack(list, nums, used);
            list.removeLast();
            used[i] = false;
        }
    }


    /**
     * 77.组合
     */
    public List<List<Integer>> combine(int n, int k) {

        backtrack3(new LinkedList<>(), n, k, 1);
        return combineList;
    }

    List<List<Integer>> combineList = new ArrayList<>();


    public void backtrack3(LinkedList<Integer> list, int n, int k, int index) {
        if (k == 0) {
            combineList.add(new ArrayList<>(list));
            return;
        }
        for (int i = index; i <= n; i++) {
            list.add(i);
            backtrack3(list, n, k - 1, i + 1);
            list.removeLast();
        }
    }


    /**
     * 78。子集
     */
    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        backtrack4(new LinkedList<>(), nums, 0);
        return sbuList;
    }

    List<List<Integer>> sbuList = new ArrayList<>();

    public void backtrack4(LinkedList<Integer> list, int[] nums, int index) {
        sbuList.add(new ArrayList<>(list));
        for (int i = index; i < nums.length; i++) {
            list.add(nums[i]);
            backtrack4(list, nums, i + 1);
            list.removeLast();
        }
    }

    /**
     * 90.子集II
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        backtrack5(new LinkedList<>(), nums, 0);
        return sbuList2;
    }

    List<List<Integer>> sbuList2 = new ArrayList<>();

    public void backtrack5(LinkedList<Integer> list, int[] nums, int index) {
        sbuList2.add(new ArrayList<>(list));
        for (int i = index; i < nums.length; i++) {

            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }

            list.add(nums[i]);
            backtrack5(list, nums, i + 1);
            list.removeLast();
        }
    }


    /**
     * 111.二叉树的最小深度
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return bfs(root, 1);

    }

    public int bfs(TreeNode node, int dept) {
        if (node.left == null && node.right == null) {
            return dept;
        }
        if (node.left == null) {
            return bfs(node.right, dept + 1);
        }
        if (node.right == null) {
            return bfs(node.left, dept + 1);
        }
        return Math.min(bfs(node.left, dept + 1), bfs(node.right, dept + 1));
    }





}
