package com.zzh.springboot3.algorithm;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/11 11:54
 */
public class Algorithm07 {

    public static void main(String[] args) {
        Algorithm07 algorithm07 = new Algorithm07();

        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        algorithm07.findDuplicateSubtrees(root);

        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        treeNode.right.left = new TreeNode(4);
        treeNode.right.left.left = new TreeNode(6);
        treeNode.right.left.right = new TreeNode(7);
        treeNode.right.right = new TreeNode(5);
        System.out.println(algorithm07.serialize(treeNode));

        Codec Codec = new Codec();
        System.out.println(Codec.serialize(treeNode));

    }

    /**
     * 652.寻找重复子树 -----序列化
     */
    public List<TreeNode> findDuplicateSubtrees2(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return new ArrayList<>();
        }
        serial(root);
        return serialMaps.values().stream().filter(nodes -> !nodes.isEmpty()).map(nodes -> nodes.get(0)).collect(Collectors.toList());
    }


    Map<String, List<TreeNode>> serialMaps = new HashMap<>();


    public String serial(TreeNode treeNode) {
        if (treeNode == null) {
            return "";
        }
        String serial = treeNode.val +
                "(" +
                serial(treeNode.left) +
                ")" +
                "(" +
                serial(treeNode.right) +
                ")";
        List<TreeNode> treeNodes = serialMaps.get(serial);
        if (treeNodes == null) {
            treeNodes = new ArrayList<>();
        } else {
            treeNodes.add(treeNode);
        }
        serialMaps.put(serial, treeNodes);
        return serial;
    }


    /**
     * 652.寻找重复子树
     */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return new ArrayList<>();
        }
        List<TreeNode> res = new ArrayList<>();
        traverse(root);
        nodeMaps.values().stream().filter(nodes -> nodes != null && !nodes.isEmpty()).forEach(nodes -> {

            List<TreeNode> collect = nodes.stream().filter(node -> node.left == null && node.right == null).toList();
            if (collect.size() > 1) {
                res.add(collect.get(0));
            }

            List<TreeNode> collect1 = nodes.stream().filter(node -> node.left != null || node.right != null).toList();
            if (collect1.size() > 1) {
                for (int i = 0; i < collect1.size(); i++) {
                    TreeNode left = collect1.get(i);
                    for (int j = i + 1; j < collect1.size(); j++) {
                        TreeNode right = collect1.get(j);
                        if (treeEqual(left, right)) {
                            res.add(left);
                        }
                    }
                }
            }
        });
        return res;
    }

    private final Map<Integer, List<TreeNode>> nodeMaps = new HashMap<>();

    public void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        add(root);
        traverse(root.left);
        traverse(root.right);
    }

    public void add(TreeNode root) {
        List<TreeNode> treeNodes = nodeMaps.get(root.val);
        if (treeNodes == null) {
            treeNodes = new ArrayList<>();
        }
        treeNodes.add(root);
        nodeMaps.put(root.val, treeNodes);
    }

    public boolean treeEqual(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val && treeEqual(left.left, right.left) && treeEqual(left.right, right.right);
    }


    /**
     * 297。二叉树的序列化和反序列化
     * <p>
     * 如果有空节点：先序遍历或者后序遍历可以确定一个二叉树
     * 没有空节点：有先序遍历和中序遍历   或者 后序遍历和中序遍历 可以确定一个二叉树
     * <p>
     * 其他情况都无法还原一颗二叉树；
     */

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "#";
        }
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }


    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        List<String> dataList = new LinkedList<>(Arrays.asList(data.split(",")));
        return traverse(dataList);
    }

    public TreeNode traverse(List<String> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return null;
        }
        String first = dataList.remove(0);
        if ("#".equals(first)) {
            return null;
        }
        TreeNode treeNode = new TreeNode(Integer.parseInt(first));
        treeNode.left = traverse(dataList);
        treeNode.right = traverse(dataList);
        return treeNode;
    }


    int maxDept = 0;

    public void maxDept(TreeNode root, int dept) {
        if (root == null) {
            maxDept = Math.max(maxDept, dept);
            return;
        }
        maxDept(root.left, dept + 1);
        maxDept(root.right, dept + 1);
    }


    @Data
    public static class Codec {
        public String serialize(TreeNode root) {
            return rserialize(root, "");
        }

        public TreeNode deserialize(String data) {
            String[] dataArray = data.split(",");
            List<String> dataList = new LinkedList<>(Arrays.asList(dataArray));
            return rdeserialize(dataList);
        }

        public String rserialize(TreeNode root, String str) {
            if (root == null) {
                str += "None,";
            } else {
                str += root.val + ",";
                str = rserialize(root.left, str);
                str = rserialize(root.right, str);
            }
            return str;
        }

        public TreeNode rdeserialize(List<String> dataList) {
            if (dataList.get(0).equals("None")) {
                dataList.remove(0);
                return null;
            }

            TreeNode root = new TreeNode(Integer.parseInt(dataList.get(0)));
            dataList.remove(0);
            root.left = rdeserialize(dataList);
            root.right = rdeserialize(dataList);

            return root;
        }
    }


    @Test
    public void test() {


    }


    /**
     * 315.计算右侧小于当前数的元素个数
     */
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return null;
        }
        if (nums.length == 1) {
            res.add(0);
            return res;
        }
        int index = nums.length - 1;
        int[] arr = new int[nums.length];
        arr[index] = 0;
        for (int i = index - 1; i >= 0; i--) {
            arr[i] = max(nums, arr, i, i + 1);
        }
        return Arrays.stream(arr).boxed().toList();
    }

    public int max(int[] nums, int[] arr, int i, int j) {
        int max = 0;
        for (; j < nums.length; j++) {
            if (nums[j] < nums[i]) {
                max = Math.max(max, arr[j] + 1);
            } else if (nums[i] == nums[j]) {
                max = Math.max(max, arr[j]);
            }
        }
        return max;
    }


    @Test
    public void testSortArray() {

        int test[] = {2, 0, 1};
        countSmaller(test);

    }

    public static int[] sortArray(int[] nums) {
        Arrays.sort(nums);
        return nums;
    }


    /**
     * 最小路径和
     */
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return -1;
        }
        int[][] multi = new int[grid.length][grid[0].length];

        multi[0][0] = grid[0][0];

        for (int i = 1; i < grid.length; i++) {
            multi[i][0] = multi[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < grid[0].length; i++) {
            multi[0][i] = multi[0][i - 1] + grid[0][i];
        }

        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[i].length; j++) {
                multi[i][j] = Math.min(multi[i - 1][j], multi[i][j - 1]) + grid[i][j];
            }
        }
        return multi[grid.length - 1][grid[0].length - 1];

    }


    /**
     * 199.二叉树的右视图
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> nodeQueue = new LinkedBlockingQueue<>();
        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = nodeQueue.poll();
                if (poll.left != null) {
                    nodeQueue.add(poll.left);
                }
                if (poll.right != null) {
                    nodeQueue.add(poll.right);
                }
                if (i == size - 1) {
                    res.add(poll.val);
                }
            }
        }
        return res;
    }












}
