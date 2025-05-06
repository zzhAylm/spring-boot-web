package com.zzh.springboot3.test;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/4/29 20:32
 */
public class TestCommonParent {

    public static class Node {
        private int val;
        private Node left;
        private Node right;
    }

    private Node commonPatent;

    public Node commonParent(Node root, Node left, Node right) {
        if (root == null) {
            return null;
        }
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        commonLoop(root, left, right);
        return commonPatent;
    }

    public boolean commonLoop(Node node, Node left, Node right) {
        if (node == null) {
            return false;
        }
        boolean leftCommon = commonLoop(node.left, left, right);
        boolean rightCommon = commonLoop(node.right, left, right);
        if ((leftCommon && rightCommon) || ((node == left || node == right) && (rightCommon || leftCommon))) {
            commonPatent = node;
        }
        return node == left || node == right || leftCommon || rightCommon;
    }

}
