package com.zzh.springboot.algorithm;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/3/6 21:44
 */
public class Algorithm16 {

    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        if (m == 1 || n == 1) {
            return 1;
        }

        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }
        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strings[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strings, (a, b) -> (b + a).compareTo(a + b));
        if (strings[0].equals("0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }


    public int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        int max = 0;
        int res = 0;
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();

        for (Map.Entry<Integer, Integer> entry : entries) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                res = entry.getKey();
            }
        }
        return res;
    }


    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode();
        pre.next = head;
        ListNode left = head;
        ListNode right = head.next;

        while (right != null) {
            if (right.val == left.val) {
                left.next = right.next;
            } else {
                left = left.next;
            }
            right = left.next;
        }
        return pre.next;
    }


    public int findLength(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return 0;
        }
        int[][] dp = new int[nums1.length][nums2.length];

        int max = 0;
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] == nums2[0]) {
                dp[i][0] = 1;
                max = Math.max(max, dp[i][0]);
            }
        }

        for (int i = 0; i < nums2.length; i++) {
            if (nums2[i] == nums1[0]) {
                dp[0][i] = 1;
                max = Math.max(max, dp[0][i]);
            }
        }

        for (int i = 1; i < nums1.length; i++) {
            for (int j = 1; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]) {
                    dp[i][j] = Math.max(dp[i - 1][j - 1] + 1, dp[i][j]);
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max;
    }


    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        Set<Character> window = new HashSet<>();
        char[] chars = s.toCharArray();
        int left = 0;
        int max = 0;
        for (char c : chars) {
            while (window.contains(c)) {
                window.remove(chars[left++]);
            }
            window.add(c);
            max = Math.max(max, window.size());
        }
        return max;
    }


    class LRUCache {

        private Map<Integer, LRUNode> values;
        private Integer capacity;
        private LRUNode head;
        private LRUNode tail;

        public LRUCache(int capacity) {
            this.values = new HashMap<>();
            this.capacity = capacity;
            this.head = new LRUNode();
            this.tail = new LRUNode();
            this.head.next = this.tail;
            this.tail.pre = this.head;
        }

        public int get(int key) {
            if (!values.containsKey(key)) {
                return -1;
            }
            LRUNode node = values.get(key);
            moveHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (values.containsKey(key)) {
                LRUNode lruNode = values.get(key);
                lruNode.setValue(value);
                moveHead(lruNode);
                return;
            }
            if (capacity == values.size()) {
                LRUNode lruNode = removeLast();
                values.remove(lruNode.getKey());
            }

            LRUNode lruNode = new LRUNode();
            lruNode.setKey(key);
            lruNode.setValue(value);
            values.put(key, lruNode);
            addHead(lruNode);

        }

        public void moveHead(LRUNode node) {
            LRUNode pre = node.pre;
            LRUNode next = node.next;
            pre.next = next;
            next.pre = pre;
            node.next = null;
            node.pre = null;
            addHead(node);
        }

        public void addHead(LRUNode node) {
            LRUNode next = head.next;
            head.next = node;
            node.pre = head;
            node.next = next;
            next.pre = node;
        }

        public LRUNode removeLast() {
            LRUNode last = tail.pre;
            LRUNode pre = last.pre;
            pre.next = tail;
            tail.pre = pre;
            last.pre = null;
            last.next = null;
            return last;
        }


    }

    public static class LRUNode {
        private Integer key;
        private Integer value;
        private LRUNode next;
        private LRUNode pre;

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public LRUNode getNext() {
            return next;
        }

        public void setNext(LRUNode next) {
            this.next = next;
        }

        public LRUNode getPre() {
            return pre;
        }

        public void setPre(LRUNode pre) {
            this.pre = pre;
        }
    }


    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode();
        pre.next = head;
        while (head.next != null) {
            ListNode next = head.next;
            head.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return pre.next;
    }


    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return -1;
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k, ((o1, o2) -> o2 - o1));
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
        ListNode first = new ListNode();
        ListNode pre = first;
        pre.next = head;

        while (reverseFlag(head, k)) {
            for (int i = 0; i < k - 1; i++) {
                ListNode next = head.next;
                head.next = next.next;
                next.next = pre.next;
                pre.next = next;
            }
            pre = head;
            head = head.next;
        }
        return first.next;
    }


    public boolean reverseFlag(ListNode node, int k) {
        ListNode p = node;
        int count = 1;
        while (p != null) {
            if (count >= k) {
                return true;
            }
            p = p.next;
            count++;
        }
        return false;
    }


    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int left = 0;
        while (left < nums.length - 2) {
            if (nums[left] > 0 || (left > 0 && nums[left] == nums[left - 1])) {
                left++;
                continue;
            }
            int mid = left + 1;
            int right = nums.length - 1;
            while (mid < right) {
                int sum = nums[mid] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    mid++;
                } else {
                    res.add(Arrays.asList(nums[left], nums[mid], nums[right]));
                    while (mid < right && nums[mid] == nums[mid + 1]) {
                        mid++;
                    }
                    while (right > mid && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    right--;
                    mid++;
                }
            }
            left++;
        }
        return res;
    }


    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }

        return max;

    }


    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        return mergeSort(nums, 0, nums.length - 1);
    }

    public int[] mergeSort(int[] nums, int left, int right) {
        if (left == right) {
            return new int[]{nums[left]};
        }
        int mid = left + (right - left) / 2;
        int[] leftNums = mergeSort(nums, left, mid);
        int[] rightNums = mergeSort(nums, mid + 1, right);
        int[] res = new int[leftNums.length + rightNums.length];
        int l = 0;
        int r = 0;
        int index = 0;
        while (l < leftNums.length && r < rightNums.length) {
            if (leftNums[l] <= rightNums[r]) {
                res[index++] = leftNums[l++];
            } else {
                res[index++] = rightNums[r++];
            }
        }
        while (l < leftNums.length) {
            res[index++] = leftNums[l++];
        }

        while (r < rightNums.length) {
            res[index++] = rightNums[r++];
        }
        return res;
    }


    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int max = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return max;

    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return root;
        }
        lowerCommon(root, p, q);
        return lowestCommonAncestor;
    }

    TreeNode lowestCommonAncestor;

    public boolean lowerCommon(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null && (node == p || node == q)) {
            return true;
        }
        boolean left = lowerCommon(node.left, p, q);
        boolean right = lowerCommon(node.right, p, q);
        if ((left && right) || ((node == p || node == q) && (right || left))) {
            lowestCommonAncestor = node;
        }
        return right || left || (node == p || node == q);
    }


    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
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

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> res = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > right) {
                res.add(new int[]{left, right});
                left = intervals[i][0];
                right = intervals[i][1];
            } else {
                left = Math.min(left, intervals[i][0]);
                right = Math.max(right, intervals[i][1]);
            }
        }
        res.add(new int[]{left, right});
        return res.toArray(int[][]::new);
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        Stack<ListNode> stack = new Stack<>();

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        while (slow.next != null) {
            ListNode next = slow.next;
            slow.next = null;
            slow = next;
            stack.push(next);
        }
        ListNode cur = head;
        while (cur != null && !stack.isEmpty()) {
            ListNode pop = stack.pop();
            ListNode next = cur.next;
            cur.next = pop;
            pop.next = next;
            cur = next;
        }

    }

    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0 || target <= 0) {
            return 0;
        }
        int left = 0;
        int right = 0;
        int sum = nums[0];
        int min = Integer.MAX_VALUE;
        while (right < nums.length) {
            if (sum > target) {
                sum -= nums[left];
                left++;
            } else if (sum < target) {
                right++;
                if (right < nums.length) {
                    sum += nums[right];
                }
            } else {
                min = Math.min(min, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public int lengthOfLongestSubstring_1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return s.length();
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 1;
        Set<Character> window = new HashSet<>();
        window.add(chars[left]);
        int max = 1;
        while (right < chars.length) {
            while (window.contains(chars[right])) {
                window.remove(chars[left++]);
            }
            window.add(chars[right++]);
            max = Math.max(max, right - left);
        }

        return max;


    }

    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == val) {
                while (right > left && nums[right] == val) {
                    right--;
                }
                if (right > left) {
                    nums[left] = nums[right];
                    nums[right] = val;
                } else {
                    break;
                }
            }
            left++;
        }
        return left;
    }


    public int maxProfit_1(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;

        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            dp[i][1] = Math.max(prices[i] + dp[i - 1][0], dp[i - 1][1]);
        }
        return dp[prices.length - 1][1];
    }

    @Test
    public void pro(){
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1,o2)->o2-o1);
        priorityQueue.add(1);
        priorityQueue.add(2);
        priorityQueue.add(3);
        while (!priorityQueue.isEmpty()){
            System.out.printf(String.valueOf(priorityQueue.poll()));
        }
    }




    @Test
    public void runThread(){

        new Thread(() -> {
            try {
                throw new   InterruptedException("zzh");
            } catch (InterruptedException e) {
                throw new IllegalArgumentException("timeout value is negative");
            }
        }).start();
    }

}

