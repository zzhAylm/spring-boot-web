package com.zzh.springboot.algorithm;

import java.util.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/28 09:56
 */
public class Algorithm02 {

    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);

        ListNode partition = partition(head, 3);

        while (partition != null) {
            System.out.println(partition.val);
            partition = partition.next;
        }

    }

    /**
     * 86. 分隔链表
     */
    public static ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode left = new ListNode(-1);
        ListNode leftP = left;

        ListNode right = new ListNode(-1);
        ListNode rightP = right;

        while (head != null) {
            if (head.val < x) {
                leftP.next = head;
                leftP = leftP.next;
            } else {
                rightP.next = head;
                rightP = rightP.next;
            }
            head = head.next;
        }
        rightP.next = null;
        leftP.next = right.next;
        return left.next;
    }


    /**
     * 23. 合并 K 个升序链表
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        ListNode res = new ListNode(-1);
        ListNode p = res;
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>((Comparator.comparingInt(o -> o.val)));
        for (ListNode head : lists) {
            while (head != null) {
                ListNode pre = head;
                head = head.next;
                pre.next = null;
                priorityQueue.add(pre);
            }
        }
        while (!priorityQueue.isEmpty()) {
            p.next = priorityQueue.poll();
            p = p.next;
        }
        return res.next;
    }


    /**
     * 19. 删除链表的倒数第 N 个结点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode header = new ListNode(-1);
        header.next = head;



        return header.next;

    }


}
