package com.zzh.springboot.algorithm;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/21 17:06
 */
public class Algorithm08 {

    // utf-8 编码的方式一个 汉字 3 个字节

    public static void main(String[] args) {
        String str = "中文汉字";
        System.out.println(str.getBytes().length);

    }


    /**
     * 二叉排序树：Binary Search Tree  简称：BST
     *
     * */

    /**
     * 230.二叉搜索树中第K小的元素
     */
    int n = 0;
    int val = -1;

    public int kthSmallest(TreeNode root, int k) {
        n = k;
        val = root.val;
        traverse(root);
        return val;
    }


    public void traverse(TreeNode node) {
        if (node == null) {
            return;
        }
        traverse(node.left);
        if (--n == 0) {
            val = node.val;
            return;
        }
        traverse(node.right);
    }


    /**
     * 538. 把二叉搜索树转换为累加树
     */
    public TreeNode convertBST(TreeNode root) {

        traverseBST(root);
        return root;
    }

    int sum = 0;

    public void traverseBST(TreeNode node) {
        if (node == null) {
            return;
        }
        traverseBST(node.right);
        sum += node.val;
        node.val = sum;
        traverseBST(node.left);
    }


    /**
     * 98.验证二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        return valid(root) && isValidBST(root.left) && isValidBST(root.right);
    }

    public boolean valid(TreeNode root) {
        if (root == null) {
            return true;
        }
        return (root.left == null || big(root.left) < root.val) && (root.right == null || small(root.right) > root.val);
    }

    public int small(TreeNode node) {
        TreeNode temp = node;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.val;
    }

    public int big(TreeNode node) {
        TreeNode temp = node;
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.val;
    }


    public boolean isValidBST1(TreeNode root) {
        if (root == null) {
            return true;
        }

        return valid(root, null, null);
    }


    public boolean valid(TreeNode node, TreeNode max, TreeNode min) {
        if (node == null) {
            return true;
        }
        if (max != null && node.val >= max.val) {
            return false;
        }
        if (min != null && node.val <= min.val) {
            return false;
        }
        return valid(node.left, node, min) && valid(node.right, max, node);
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        if (root.val > val) {
            return searchBST(root.left, val);
        }
        return searchBST(root.right, val);
    }


    /**
     * 450.删除二叉搜索树的节点
     **/
    public TreeNode deleteNode(TreeNode root, int key) {

        if (root == null) return null;
        if (root.val == key) {
            // 这两个 if 把情况 1 和 2 都正确处理了
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            // 处理情况 3
            // 获得右子树最小的节点
            TreeNode minNode = getMin(root.right);
            // 删除右子树最小的节点
            root.right = deleteNode(root.right, minNode.val);
            // 用右子树最小的节点替换 root 节点
            minNode.left = root.left;
            minNode.right = root.right;
            root = minNode;
        } else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        }
        return root;
    }

    TreeNode getMin(TreeNode node) {
        // BST 最左边的就是最小的
        while (node.left != null) node = node.left;
        return node;
    }



    /***
     * 95.不同的二叉搜索树2
     * */
//    public List<TreeNode> generateTrees(int n) {
//
//
//
//    }

}
