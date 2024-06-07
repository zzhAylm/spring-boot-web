package com.zzh.springboot3.algorithm;

import lombok.val;

import java.util.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/3 10:50
 */
public class Algorithm05 {

    public static void main(String[] args) {
//        Node root = new Node(1);
//        root.left = new Node(2);
//        root.right = new Node(3);
//        root.left.left = new Node(4);
//        root.left.right = new Node(5);
//        root.right.left = new Node(6);
//        root.right.right = new Node(7);
//        connect(root);

//        int[] pre = new int[]{3, 9, 20, 15, 7};
//        int[] mid = new int[]{9, 3, 15, 20, 7};
//        buildTree(pre, mid);

        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(4);
        TreeNode left_right = new TreeNode(2);
        root.left = left;
        root.right = right;
        root.left.right = left_right;
        lowestCommonAncestor1(root, left_right, root);
    }

    /**
     * 116.填充每棵二叉树的右侧指针
     */
    public static Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> deque = new ArrayDeque<>();
        deque.add(root);
        while (!deque.isEmpty()) {
            List<Node> list = new ArrayList<>();
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                Node pop = deque.poll();
                list.add(pop);
                if (pop.left != null) {
                    deque.add(pop.left);
                }
                if (pop.right != null) {
                    deque.add(pop.right);
                }
            }
            for (int i = list.size() - 1; i >= 0; i--) {
                if (i == list.size() - 1) {
                    list.get(i).next = null;
                } else {
                    list.get(i).next = list.get(i + 1);
                }
            }
        }
        return root;
    }

    public static Node connect2(Node root) {
        if (root == null) {
            return null;
        }
        traverse(root.left, root.right);
        return root;
    }

    public static void traverse(Node left, Node right) {
        if (left == null || right == null) {
            return;
        }
        left.next = right;
        traverse(left.left, left.right);
        traverse(left.right, right.left);
        traverse(right.left, right.right);
    }


    /**
     * 226.翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {

        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        traverse(root);
        return root;

    }

    public void traverse(TreeNode node) {
        if (node == null) {
            return;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        node.left = right;
        node.right = left;
        traverse(left);
        traverse(right);
    }


    /**
     * 从先序遍历和中序遍历中构造二叉树
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return structure(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public static TreeNode structure(int[] pre, int[] mid, int l, int r, int ll, int rr) {
        if (l > r || ll > rr) {
            return null;
        }
        if (l == r) {
            return new TreeNode(pre[l]);
        }
        int index = ll;
        while (index <= rr) {
            if (mid[index] == pre[l]) {
                break;
            }
            index++;
        }
        int count = index - ll;
        TreeNode root = new TreeNode(pre[l]);
        root.left = structure(pre, mid, l + 1, l + count, ll, index - 1);
        root.right = structure(pre, mid, l + count + 1, r, index + 1, rr);
        return root;
    }


    /**
     * 654.最大二叉树
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        return structure(nums, 0, nums.length - 1);
    }

    public TreeNode structure(int[] nums, int l, int r) {
        if (l > r) {
            return null;
        }
        if (l == r) {
            return new TreeNode(nums[l]);
        }
        int index = l;
        for (int i = l + 1; i <= r; i++) {
            if (nums[i] > nums[index]) {
                index = i;
            }
        }
        TreeNode treeNode = new TreeNode(nums[index]);

        treeNode.left = structure(nums, l, index - 1);
        treeNode.right = structure(nums, index + 1, r);
        return treeNode;
    }


    /**
     * 把二叉搜索树转换为累加树
     */
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        traverseBST(root);
        return root;
    }

    int num = 0;

    public void traverseBST(TreeNode node) {
        if (node == null) {
            return;
        }
        traverseBST(node.right);
        num += node.val;
        node.val = num;
        traverseBST(node.left);
    }


    /**
     * 236.二叉树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return null;
        }
        traverse(root, p, q);
        return father;
    }

    TreeNode father;

    public boolean traverse(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        boolean left = traverse(node.left, p, q);
        boolean right = traverse(node.right, p, q);
        if (left && right || ((node.val == p.val || node.val == q.val) && (left || right))) {
            father = node;
        }
        return (left || right || node.val == p.val || node.val == q.val);
    }


    /**
     * 193.二叉搜索树的最近公公祖先
     */
    public static TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return null;
        }
        dfs(root, p, q);
        return fatherNode;
    }

    static TreeNode fatherNode;

    public static boolean dfs(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) return false;
        if (node.val > p.val && node.val > q.val) {
            return dfs(node.left, p, q);
        } else if (node.val < p.val && node.val < q.val) {
            return dfs(node.right, p, q);
        } else {
            boolean left = dfs(node.left, p, q);
            boolean right = dfs(node.right, p, q);
            if ((left && right) || ((left || right) && (node.val == p.val || node.val == q.val))) {
                fatherNode = node;
            }
            return left || right || node.val == p.val || node.val == q.val;
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

        int dept = 0;
        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            dept++;
            for (int i = 0; i < size; i++) {
                TreeNode poll = nodeQueue.poll();
                if (poll.left == null && poll.right == null) {
                    return dept;
                }
                TreeNode left = poll.left;
                if (left != null) {
                    nodeQueue.add(left);
                }
                TreeNode right = poll.right;
                if (right != null) {
                    nodeQueue.add(right);
                }
            }
        }
        return dept;
    }






}

