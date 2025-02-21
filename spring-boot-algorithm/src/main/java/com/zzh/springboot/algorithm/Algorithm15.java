package com.zzh.springboot.algorithm;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
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


    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode slow = pre;
        ListNode fast = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return pre.next;
    }


    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode p = pre;
        ListNode first = head;
        while (first != null && first.next != null) {
            if (first.val == first.next.val) {
                while (first.next != null && first.val == first.next.val) {
                    first.next = first.next.next;
                }
                p.next = first.next;
                first = p.next;
            } else {
                first = first.next;
                p = p.next;
            }
        }
        return pre.next;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merge = merge(nums1, nums2);
        int length = merge.length;
        if (merge.length % 2 == 0) {
            return (merge[length / 2] + merge[length / 2 - 1]) / 2.0;
        } else {
            return merge[length / 2];
        }
    }

    public int[] merge(int[] nums1, int[] nums2) {
        int[] merge = new int[nums1.length + nums2.length];
        int index = 0;
        int left = 0;
        int right = 0;
        while (left < nums1.length && right < nums2.length) {
            if (nums1[left] <= nums2[right]) {
                merge[index++] = nums1[left];
                left++;
            } else {
                merge[index++] = nums2[right];
                right++;
            }
        }
        while (left < nums1.length) {
            merge[index++] = nums1[left];
            left++;
        }
        while (right < nums2.length) {
            merge[index++] = nums2[right];
            right++;
        }
        return merge;
    }


    public int search_1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }

        }
        return -1;
    }


    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (i == size - 1) {
                    res.add(poll.val);
                }
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
        }
        return res;
    }


    class MyQueue {

        Stack<Integer> stack1;
        Stack<Integer> stack2;

        public MyQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        public void push(int x) {
            stack1.push(x);
        }

        public int pop() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }

        public int peek() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.peek();
        }

        public boolean empty() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.isEmpty();
        }
    }


    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        while (head != null) {
            ListNode node = head;
            head = head.next;
            node.next = null;
            priorityQueue.add(node);
        }
        ListNode pre = new ListNode();
        ListNode p = pre;

        while (!priorityQueue.isEmpty()) {
            ListNode poll = priorityQueue.poll();
            p.next = poll;
            p = p.next;
        }
        return pre.next;
    }

//    public ListNode sortList_1(ListNode head) {
//        if (head == null || head.next == null) {
//            return head;
//        }
//
//    }

    public ListNode mergeSort(ListNode start, ListNode end) {
        if (start == null) {
            return null;
        }
        if (start.next == end) {
            start.next = null;
            return start;
        }
        ListNode slow = start;
        ListNode fast = start;

        while (fast != null && fast != end) {
            slow = slow.next;
            fast = fast.next;
            if (fast != end) {
                fast = fast.next;
            }
        }
        ListNode left = mergeSort(start, slow);
        ListNode right = mergeSort(slow, end);
        return merge(left, right);
    }

    public ListNode merge(ListNode left, ListNode right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        ListNode pre = new ListNode(-1);
        ListNode temp = pre;
        while (left != null && right != null) {
            if (left.val < right.val) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;
            }
            temp = temp.next;
        }
        while (left != null) {
            temp.next = left;
            left = left.next;
            temp = temp.next;
        }
        while (right != null) {
            temp.next = right;
            right = right.next;
            temp = temp.next;
        }
        return pre.next;
    }


    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int[] arr = new int[nums.length];
        System.arraycopy(nums, 0, arr, 0, nums.length);
        Arrays.sort(arr);
        List<List<Integer>> res = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        Arrays.fill(visited, false);
        backTrack(arr, new LinkedList<>(), res, visited);
        for (int i = 0; i < res.size(); i++) {
            if (isSameArray(nums, res.get(i))) {
                List<Integer> list;
                if (i == res.size() - 1) {
                    list = res.get(0);
                } else {
                    list = res.get(i + 1);
                }
                for (int j = 0; j < nums.length; j++) {
                    nums[j] = list.get(j);
                }
                return;
            }
        }
    }

    public boolean isSameArray(int[] nums1, List<Integer> nums2) {
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] != nums2.get(i)) {
                return false;
            }
        }
        return true;
    }

    public void backTrack(int[] nums, LinkedList<Integer> temp, List<List<Integer>> res, boolean[] visited) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                continue;
            }

            visited[i] = true;
            temp.add(nums[i]);
            backTrack(nums, temp, res, visited);
            temp.removeLast();
            visited[i] = false;
        }
    }

    @Test
    public void nextPermutationTest() {
        int[] nums = {1, 2, 3};
        nextPermutation(nums);
    }


    public void nextPermutation_1(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int index = nums.length - 2;
        while (index >= 0) {
            if (nums[index] < nums[index + 1]) {
                for (int i = nums.length - 1; i >= 0; i--) {
                    if (nums[index] < nums[i]) {
                        int temp = nums[index];
                        nums[index] = nums[i];
                        nums[i] = temp;
                        Arrays.sort(nums, index + 1, nums.length);
                        return;
                    }
                }
            }
            index--;
        }
        Arrays.sort(nums, 0, nums.length);
    }


    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        backTrack(res, new StringBuilder(), 0, 0, n);
        return res;
    }

    public void backTrack(List<String> res, StringBuilder temp, int left, int right, int n) {
        if (left == n && right == n) {
            res.add(temp.toString());
            return;
        }
        if (left > n || right > left) {
            return;
        }
        temp.append("(");
        backTrack(res, temp, left + 1, right, n);
        temp.deleteCharAt(temp.length() - 1);
        temp.append(")");
        backTrack(res, temp, left, right + 1, n);
        temp.deleteCharAt(temp.length() - 1);
    }


//    public int myAtoi(String s) {
//
//    }


    public int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }
        int left = 1;
        int right = x - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (x / mid >= mid && x / (mid + 1) < mid + 1) {
                return mid;
            } else if (x / mid > mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }


    public int compareVersion(String version1, String version2) {
        int n = version1.length();
        int m = version2.length();
        int index1 = 0;
        int index2 = 0;
        while (index1 < n || index2 < m) {
            int num1 = 0;
            int num2 = 0;
            for (; index1 < n && version1.charAt(index1) != '.'; index1++) {
                num1 = num1 * 10 + version1.charAt(index1) - '0';
            }
            index1++;
            for (; index2 < m && version2.charAt(index2) != '.'; index2++) {
                num2 = num2 * 10 + version2.charAt(index2) - '0';
            }
            index2++;
            if (num1 != num2) {
                return num1 > num2 ? 1 : -1;
            }
        }
        return 0;
    }


    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length <= k || k <= 1) {
            return nums;
        }
        Deque<Integer> deque = new LinkedBlockingDeque<>(k);
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && deque.peek() <= nums[i]) {
                deque.pollFirst();
            }
            deque.addLast(nums[i]);
        }
        int index = 0;
        res[index++] = deque.peek();
        for (int i = k; i < nums.length; i++) {
            if (deque.size() == k) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && deque.peek() <= nums[i]) {
                deque.pollFirst();
            }
            deque.addLast(nums[i]);
            res[index++] = deque.peek();
        }
        return res;
    }


    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        Set<Integer> set = new HashSet<>();
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= 0) {
                continue;
            }
            if (num < min) {
                min = num;
            }

            set.add(num);
        }
        if (min != 1) {
            return 1;
        }

        while (set.contains(min)) {
            min++;
        }
        return min;

    }


    public int longestValidParentheses(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        int[] dp = new int[s.length()];
        dp[0] = 0;
        int max = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = i >= 2 ? dp[i - 2] + 2 : 2;
                } else {
                    if (i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                        dp[i] = (i - dp[i - 1] - 2 >= 0 ? (dp[i - dp[i - 1] - 2]) : 0) + dp[i - 1] + 2;
                    }
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }


    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }


    public ListNode trainingPlan(ListNode head, int cnt) {
        if (head == null || cnt <= 0) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head;

        for (int i = 0; i < cnt; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }


    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        int left = 0;
        int right = 0;
        String min = s;
        boolean init = false;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            map.put(t.charAt(i), map.getOrDefault(t.charAt(i), 0) + 1);
        }
        while (right < s.length()) {
            char c = s.charAt(right);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                while (map.values().stream().allMatch(value -> value <= 0)) {
                    if (right - left + 1 <= min.length()) {
                        min = s.substring(left, right + 1);
                        init = true;
                    }
                    char c1 = s.charAt(left);
                    if (map.containsKey(c1)) {
                        map.put(c1, map.get(c1) + 1);
                    }
                    left++;
                }
            }
            right++;
        }
        return init ? min : "";
    }

    @Test
    public void testMinWindow() {
        String s = "cabwefgewcwaefgcf";
        String t = "cae";
        minWindow(s, t);
    }


    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }
        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder, int preLeft, int preRight, int inLeft, int inRight) {
        if (preLeft > preRight || inLeft > inRight) {
            return null;
        }
        if (preLeft == preRight && inLeft == inRight) {
            return new TreeNode(preorder[preLeft]);
        }
        int num = preorder[preLeft];
        TreeNode treeNode = new TreeNode(num);
        int index = inLeft;
        int count = 0;
        while (index < inRight) {
            if (num == inorder[index]) {
                break;
            }
            count++;
            index++;
        }
        treeNode.left = buildTree(preorder, inorder, preLeft + 1, preLeft + count, inLeft, index - 1);
        treeNode.right = buildTree(preorder, inorder, preLeft + 1 + count, preRight, index + 1, inRight);
        return treeNode;
    }


    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        subBackTrack(res, nums, 0, new LinkedList<>());
        return res;
    }

    public void subBackTrack(List<List<Integer>> res, int[] nums, int start, LinkedList<Integer> temp) {
        res.add(new ArrayList<>(temp));
        for (int i = start; i < nums.length; i++) {
            temp.add(nums[i]);
            subBackTrack(res, nums, i + 1, temp);
            temp.removeLast();
        }
    }

    @Test
    public void multiplyTest() {
        multiply("123", "456");
    }


    public String multiply(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0 || num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        String res = "0";
        for (int i = num2.length() - 1; i >= 0; i--) {
            String multi = multi(num1, num2, i);
            res = add(res, multi);
        }
        return res;
    }

    public String multi(String num1, String num2, int index) {
        int num = num2.charAt(index) - '0';
        if (num == 0) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int bit = 0;
        for (int i = num1.length() - 1; i >= 0; i--) {
            int temp = num1.charAt(i) - '0';
            int multi = temp * num + bit;
            bit = multi / 10;
            stringBuilder.append(multi % 10);
        }
        if (bit != 0) {
            stringBuilder.append(bit);
        }
        StringBuilder reverse = stringBuilder.reverse();
        for (int i = 0; i < num2.length() - 1 - index; i++) {
            reverse.append("0");
        }
        return reverse.toString();
    }

    public String add(String num1, String num2) {
        if (num2 == null || num2.length() == 0 || num2.equals("0")) {
            return num1;
        }
        if (num1 == null || num1.length() == 0 || num1.equals("0")) {
            return num2;
        }
        int bit = 0;
        StringBuilder builder = new StringBuilder();
        int left = num1.length() - 1;
        int right = num2.length() - 1;

        while (left >= 0 && right >= 0) {
            int s1 = num1.charAt(left) - '0';
            int s2 = num2.charAt(right) - '0';
            int sum = s1 + s2 + bit;
            bit = sum / 10;
            builder.append(sum % 10);
            left--;
            right--;
        }
        while (left >= 0) {
            int s1 = num1.charAt(left) - '0';
            int sum = s1 + bit;
            bit = sum / 10;
            builder.append(sum % 10);
            left--;
        }
        while (right >= 0) {
            int s2 = num2.charAt(right) - '0';
            int sum = s2 + bit;
            bit = sum / 10;
            builder.append(sum % 10);
            right--;
        }
        if (bit != 0) {
            builder.append(bit);
        }
        return builder.reverse().toString();
    }

    public String reverseWords(String s) {
        if (s == null || s.length() == 0 || s.trim().length() == 0) {
            return s;
        }

        String[] split = s.trim().split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            if (split[i].trim().length() == 0) {
                continue;
            }
            stringBuilder.append(split[i].trim()).append(" ");
        }
        return stringBuilder.toString().trim();
    }


    class MinStack {

        Stack<Integer> stack;

        Deque<Integer> min;

        public MinStack() {
            stack = new Stack<>();
            min = new LinkedList<>();
        }

        public void push(int val) {
            stack.push(val);
            if (min.isEmpty() || val <= min.peek()) {
                min.push(val);
            }
        }

        public void pop() {
            Integer pop = stack.pop();
            if (Objects.equals(pop, min.peek())) {
                min.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min.peek();
        }
    }


    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }
        subTreeNode(root, new StringBuilder());
        return sumNumber;
    }

    int sumNumber = 0;

    public void subTreeNode(TreeNode node, StringBuilder stringBuilder) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            stringBuilder.append(node.val);
            sumNumber += Integer.parseInt(stringBuilder.toString());
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return;
        }
        stringBuilder.append(node.val);
        subTreeNode(node.left, stringBuilder);
        subTreeNode(node.right, stringBuilder);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }


    public boolean isSymmetric(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return sameTree(root.left, root.right);
    }

    public boolean sameTree(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null || right.val != left.val) {
            return false;
        }
        return sameTree(left.left, right.right) && sameTree(left.right, right.left);
    }


    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        preOrder(root, list);

        return list;

    }

    public void preOrder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        preOrder(node.left, list);
        preOrder(node.right, list);
    }


    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return maxDepth_1(root);
    }

    public int maxDepth_1(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return Math.max(maxDepth_1(node.left), maxDepth_1(node.right)) + 1;
    }


    public boolean isBalanced(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        int right = maxDepthTree(root.left);
        int left = maxDepthTree(root.right);
        return Math.abs(left - right) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }


    public int maxDepthTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(maxDepthTree(root.left), maxDepthTree(root.right)) + 1;
    }



//    public List<List<Integer>> combinationSum(int[] candidates, int target) {
//
//
//
//    }
}

