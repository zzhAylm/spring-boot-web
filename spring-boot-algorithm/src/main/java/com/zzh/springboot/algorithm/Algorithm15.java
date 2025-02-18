package com.zzh.springboot.algorithm;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/2/14 11:01
 */
public class Algorithm15 {

    public int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        int max = 1;

        Set<Character> window = new HashSet<>();
        char[] chars = s.toCharArray();
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            while (window.contains(chars[i])) {
                window.remove(chars[left++]);
            }
            window.add(chars[i]);
            max = Math.max(window.size(), max);
        }
        return max;
    }


    class LRUCache {

        private Map<Integer, Node> values;
        private Integer capacity;

        private Node head;
        private Node tail;


        public LRUCache(int capacity) {
            this.values = new HashMap<>();
            this.capacity = capacity;
            this.head = new Node();
            this.tail = new Node();
            this.head.setNext(tail);
            this.tail.setPre(head);
        }

        public int get(int key) {
            if (values.containsKey(key)) {
                Node node = values.get(key);
                moveHead(node);
                return node.getValue();
            }
            return -1;
        }

        public void put(int key, int value) {
            if (values.containsKey(key)) {
                Node node = values.get(key);
                node.setValue(value);
                moveHead(node);
                return;
            }
            if (values.size() == capacity) {
                Node remove = removeLast();
                values.remove(remove.getKey());
            }
            Node cur = new Node();
            cur.setKey(key);
            cur.setValue(value);
            addHead(cur);
            values.put(key, cur);
        }

        public Node removeLast() {
            Node last = tail.getPre();
            Node lastPre = last.getPre();
            lastPre.setNext(tail);
            tail.setPre(lastPre);
            last.setNext(null);
            last.setPre(null);
            return last;
        }

        public void addHead(Node node) {
            Node second = head.getNext();
            second.setPre(node);
            node.setNext(second);
            node.setPre(head);
            head.setNext(node);
        }

        public void moveHead(Node node) {
            Node pre = node.getPre();
            Node next = node.getNext();
            pre.setNext(next);
            next.setPre(pre);
            addHead(node);
        }
    }

    public static class Node {
        private Node pre;
        private Node next;
        private Integer key;
        private Integer value;

        public Node getPre() {
            return pre;
        }

        public Node getNext() {
            return next;
        }

        public Integer getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }


//    public ListNode reverseList(ListNode head) {
//
//
//    }

    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return -1;
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int num : nums) {
            priorityQueue.add(num);
        }
        Integer pre = priorityQueue.poll();
        int count = 1;
        while (count < k && !priorityQueue.isEmpty()) {
            pre = priorityQueue.poll();
            count++;
        }
        return pre;
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k <= 1) {
            return head;
        }
        ListNode dynamic = new ListNode(-1);
        dynamic.next = head;
        ListNode pre = dynamic;
        ListNode point = pre.next;
        while (countReverseGroup(pre, k)) {
            reverseK(k, pre, point);
            pre = point;
            point = pre.next;
        }
        return dynamic.next;
    }

    public boolean countReverseGroup(ListNode pre, int k) {
        ListNode point = pre.next;
        int count = 0;
        while (point != null) {
            count++;
            if (count >= k) {
                return true;
            }
            point = point.next;
        }
        return false;
    }

    public void reverseK(int count, ListNode pre, ListNode point) {
        while (count > 1) {
            ListNode first = point.next;
            point.next = first.next;
            first.next = pre.next;
            pre.next = first;
            count--;
        }
    }


    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int left = 0;
        while (left < nums.length - 2) {
            if (nums[left] > 0) {
                left++;
                continue;
            }
            int mid = left + 1;
            int right = nums.length - 1;
            while (mid < right) {
                int sum = nums[left] + nums[right] + nums[mid];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    mid++;
                } else {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[left]);
                    temp.add(nums[mid]);
                    temp.add(nums[right]);
                    result.add(temp);
                    while (mid + 1 < right && nums[mid] == nums[mid + 1]) {
                        mid++;
                    }
                    while (right - 1 > mid && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    mid++;
                    right--;
                }
            }
            left++;
            while (left < nums.length - 2 && nums[left] == nums[left - 1]) {
                left++;
            }
        }
        return result;
    }

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
        }

        return Arrays.stream(dp).max().orElse(0);
    }


    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        return mergeSort(0, nums.length - 1, nums);

    }

    public int[] mergeSort(int left, int right, int[] nums) {
        if (left == right) {
            return new int[]{nums[left]};
        }
        int mid = left + (right - left) / 2;
        int[] leftNums = mergeSort(left, mid, nums);
        int[] rightNums = mergeSort(mid + 1, right, nums);
        int[] mergeNums = new int[leftNums.length + rightNums.length];
        int l = 0;
        int r = 0;
        int index = 0;
        while (l < leftNums.length && r < rightNums.length) {
            if (leftNums[l] <= rightNums[r]) {
                mergeNums[index] = leftNums[l];
                l++;
            } else {
                mergeNums[index] = rightNums[r];
                r++;
            }
            index++;
        }
        while (l < leftNums.length) {
            mergeNums[index] = leftNums[l];
            l++;
            index++;
        }
        while (r < rightNums.length) {
            mergeNums[index] = rightNums[r];
            r++;
            index++;
        }
        return mergeNums;
    }


    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode pre = new ListNode(-1);
        ListNode p = pre;
        ListNode p1 = list1;
        ListNode p2 = list2;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }
        while (p1 != null) {
            p.next = p1;
            p1 = p1.next;
            p = p.next;
        }

        while (p2 != null) {
            p.next = p2;
            p2 = p2.next;
            p = p.next;
        }
        return pre.next;
    }


    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        int index = 0;
        int max = 1;
        String subStr = s.substring(index, index + 1);

        while (index < s.length()) {
            int left = index;
            int right = index + 1;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {

                if (right - left + 1 > max) {
                    max = right - left + 1;
                    subStr = s.substring(left, right + 1);
                }
                left--;
                right++;
            }
            left = index - 1;
            right = index + 1;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > max) {
                    max = right - left + 1;
                    subStr = s.substring(left, right + 1);
                }
                left--;
                right++;
            }
            index++;
        }

        return subStr;
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();

            while (size > 0) {
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
                temp.add(poll.val);
                size--;
            }
            res.add(temp);
        }
        return res;

    }


    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            return new int[]{-1, -1};
        }
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = target - nums[i];
            if (map.containsKey(num)) {
                return new int[]{map.get(num), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }


    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return binarySearch(nums, 0, nums.length - 1, target);
    }

    public int binarySearch(int[] nums, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (target > nums[mid]) {
            if (nums[right] >= target) {
                return binarySearch(nums, mid + 1, right, target);
            } else {
                if (nums[mid] >= nums[left]) {
                    return binarySearch(nums, mid + 1, right, target);
                } else {
                    return binarySearch(nums, left, mid - 1, target);
                }
            }
        } else if (target < nums[mid]) {
            if (nums[left] <= target) {
                return binarySearch(nums, left, mid - 1, target);
            } else {
                if (nums[mid] <= nums[right]) {
                    return binarySearch(nums, left, mid - 1, target);
                } else {
                    return binarySearch(nums, mid + 1, right, target);
                }
            }

        } else {
            return mid;
        }
    }


    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int min = prices[0];
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return max;
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        boolean left = ancestor(root.left, p, q);
        boolean right = ancestor(root.right, p, q);
        if (left && right) {
            return root;
        } else if (left) {
            if (root == p || root == q) {
                return root;
            }
            return lowestCommonAncestor(root.left, p, q);
        } else {
            if (root == p || root == q) {
                return root;
            }
            return lowestCommonAncestor(root.right, p, q);
        }
    }

    public boolean ancestor(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        if (node == p || node == q) {
            return true;
        }
        return ancestor(node.left, p, q) || ancestor(node.right, p, q);
    }


    public TreeNode lowestCommonAncestor_1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        ancestor_1(root, p, q);
        return commonAncestor;
    }

    TreeNode commonAncestor = null;

    public boolean ancestor_1(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        boolean left = ancestor_1(node.left, p, q);
        boolean right = ancestor_1(node.right, p, q);
        if (left && right) {
            commonAncestor = node;
        }
        if ((left || right) && (node == p || node == q)) {
            commonAncestor = node;
        }

        return (node == p || node == q) || (left || right);
    }


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
                temp.add(poll.val);
            }
            if (res.size() % 2 != 0) {
                Collections.reverse(temp);
            }
            res.add(temp);
        }
        return res;
    }


    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));


        List<int[]> res = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (interval[0] > right) {
                res.add(new int[]{left, right});
                left = interval[0];
                right = interval[1];
            } else {
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }

        res.add(new int[]{left, right});
        return res.toArray(new int[res.size()][]);
    }


    int maxPathSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        maxPath(root);
        return maxPathSum;
    }

    public int maxPath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxPath(root.left);
        int right = maxPath(root.right);
        maxPathSum = Math.max(maxPathSum, root.val + Math.max(Math.max(Math.max(left, right), left + right), 0));
        return Math.max(root.val, root.val + Math.max(left, right));
    }


    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        int[][] dp = new int[text1.length()][text2.length()];
        if (text1.charAt(0) == text2.charAt(0)) {
            dp[0][0] = 1;
        }
        for (int i = 1; i < dp.length; i++) {
            if (text1.charAt(i) == text2.charAt(0)) {
                dp[i][0] = 1;
            } else {
                dp[i][0] = dp[i - 1][0];
            }
        }
        for (int i = 1; i < dp[0].length; i++) {
            if (text1.charAt(0) == text2.charAt(i)) {
                dp[0][i] = 1;
            } else {
                dp[0][i] = dp[0][i - 1];
            }
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = Math.max(dp[i - 1][j - 1] + 1, Math.max(dp[i - 1][j], dp[i][j - 1]));
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[dp.length - 1][dp[0].length - 1];

    }


}
