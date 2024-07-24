package com.zzh.springboot3.algorithm;

import io.swagger.models.auth.In;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/7/8 17:19
 */
public class Algorithm11 {

    public static void main(String[] args) {

    }

    /**
     * 1. 两数之和
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(target - nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && map.get(nums[i]) != i) {
                return new int[]{i, map.get(nums[i])};
            }
        }
        return null;
    }

    /**
     * 49. 字母异位词分组
     */
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String sort = sort(str);
            if (map.containsKey(sort)) {
                List<String> integers = map.get(sort);
                integers.add(str);
            } else {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(sort, list);
            }
        }
        return new ArrayList<>(map.values());
    }

    public String sort(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return Arrays.toString(chars);
    }


    /**
     * 128.最长连续序列
     */
    public int longestConsecutive(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 1;

        for (int num : set) {
            if (set.contains(num - 1)) {
                continue;
            }
            int temp = num + 1;
            int tempMax = 1;
            while (set.contains(temp)) {
                tempMax++;
                temp++;
                max = Math.max(max, tempMax);
            }
        }
        return max;
    }


    /**
     * 283.零移动
     */
    public void moveZeroes(int[] nums) {
        if (nums.length <= 1) {
            return;
        }
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int num = nums[index];
                nums[index] = nums[i];
                nums[i] = num;
                index++;
            }
        }
    }


    /***
     * 11.盛水最多的容器
     * */
    public int maxArea(int[] height) {
        if (height.length <= 1) {
            return 0;
        }
        int max = 0;
        int leftMax = 0;
        for (int left = 0; left < height.length; left++) {
            if (height[left] < leftMax) {
                continue;
            }
            int rightMax = 0;
            for (int right = height.length - 1; right > left; right--) {
                if (height[right] < rightMax) {
                    continue;
                }
                rightMax = height[right];
                max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            }
            leftMax = height[left];
        }
        return max;
    }

    /**
     * 11.盛水最多的容器
     */
    public int maxArea1(int[] height) {

        if (height.length <= 1) {
            return 0;
        }
        int max = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }

    /**
     * 15.三数之和
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        int left = 0;
        Arrays.sort(nums);
        while (left < nums.length) {
            if (left > 0 && nums[left] == nums[left - 1]) {
                left++;
                continue;
            }
            int mid = left + 1;
            int right = nums.length - 1;
            while (mid < right) {
                int sum = nums[left] + nums[right] + nums[mid];
                if (sum == 0) {
                    res.add(List.of(nums[left], nums[mid], nums[right]));
                    while (mid < right && nums[mid] == nums[mid + 1]) mid++;
                    while (right > 0 && nums[right] == nums[right - 1]) right--;
                    mid++;
                    right--;
                } else if (sum < 0) {
                    mid++;
                } else {
                    right--;
                }
            }
            left++;
        }
        return res;
    }


    /**
     * 42.接雨水
     */
    public static int trap(int[] height) {
        if (height.length <= 2) {
            return 0;
        }
        int sum = 0;
        int left = 0;
        int maxIndex = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] > height[maxIndex]) {
                maxIndex = i;
            }
        }
        int leftNum = height[left];
        while (left < maxIndex) {
            if (height[left] < leftNum) {
                sum += (leftNum - height[left]);
            } else {
                leftNum = height[left];
            }
            left++;
        }
        int right = height.length - 1;
        int rightNum = height[right];
        while (right > maxIndex) {
            if (height[right] < rightNum) {
                sum += (rightNum - height[right]);
            } else {
                rightNum = height[right];
            }
            right--;
        }
        return sum;
    }


    @Test
    public void test() {
        int[] arr = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trap(arr));
    }


    /***
     * 3.无重复字符串的最长子串
     * */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int max = 1;
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 0;
        Map<Character, Integer> map = new HashMap<>();
        while (left < chars.length && right < chars.length) {
            if (map.size() == right - left) {
                max = Math.max(max, right - left);
                if (map.containsKey(chars[right])) {
                    Integer integer = map.get(chars[right]);
                    map.put(chars[right], integer + 1);
                } else {
                    map.put(chars[right], 1);
                }
                right++;
            } else {
                Integer integer = map.get(chars[left]);
                if (integer == 1) {
                    map.remove(chars[left]);
                } else {
                    map.put(chars[left], integer - 1);
                }
                left++;
            }
        }
        if (map.size() == right - left) {
            max = Math.max(max, right - left);
        }
        return max;
    }


    public int lengthOfLongestSubstring2(String s) {
        if (s.length() <= 1) {
            return s.length();
        }
        int max = 1;
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 1;
        Set<Character> set = new HashSet<>();
        set.add(chars[left]);
        while (right < chars.length && left < chars.length) {
            if (set.contains(chars[right])) {
                set.remove(chars[left]);
                left++;
            } else {
                set.add(chars[right]);
                right++;
            }
            max = Math.max(max, set.size());
        }
        return max;
    }


    /**
     * 438. 找到字符串中所有字母异位词
     */
    public List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        char[] chars = s.toCharArray();
        char[] ps = p.toCharArray();
        Arrays.sort(ps);
        String str = Arrays.toString(ps);
        int left = 0;
        int right = ps.length;

        char[] temp = new char[ps.length];

        while (left < chars.length && right < chars.length) {
            int index = 0;
            for (int i = left; i < right; i++) {
                temp[index++] = chars[i];
            }
            Arrays.sort(temp);
            if (str.equals(Arrays.toString(temp))) {
                res.add(left);
            }
            left++;
            right++;
        }

        int index = 0;
        for (int i = left; i < right; i++) {
            temp[index++] = chars[i];
        }
        Arrays.sort(temp);
        if (str.equals(Arrays.toString(temp))) {
            res.add(left);
        }


        return res;
    }

    public List<Integer> findAnagrams1(String s, String p) {
        if (s.length() < p.length()) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();

        char[] sChar = s.toCharArray();
        char[] pChar = p.toCharArray();
        int[] sNum = new int[26];
        int[] pNum = new int[26];
        int left = 0;
        int right = p.length();
        for (int i = 0; i < pChar.length; i++) {
            sNum[sChar[i] - 'a']++;
            pNum[pChar[i] - 'a']++;
        }
        if (Arrays.equals(sNum, pNum)) {
            res.add(left);
        }

        while (right < sChar.length) {
            sNum[sChar[left++] - 'a']--;
            sNum[sChar[right++] - 'a']++;
            if (Arrays.equals(sNum, pNum)) {
                res.add(left);
            }
        }
        return res;
    }


    /**
     * 560.和为k的子数组
     */
    public static int subarraySum(int[] nums, int k) {
        int res = 0;
        int left = 0;
        while (left < nums.length) {
            int right = left;
            int temp = 0;
            while (right < nums.length) {
                temp += nums[right];
                if (temp == k) {
                    res++;
                }
                right++;
            }
            left++;
        }
        return res;
    }


    @Test
    public void test1() {
        int[] nums = {1, 1, 1};
        subarraySum(nums, 2);

    }

    /**
     * 239.滑动窗口最大值
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        int index = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k, (a, b) -> b - a);
        for (int i = 0; i < k; i++) {
            priorityQueue.add(nums[i]);
        }
        res[index++] = priorityQueue.peek().intValue();
        for (int i = k; i < nums.length; i++) {
            priorityQueue.remove(nums[i - k]);
            priorityQueue.add(nums[i]);
            res[index++] = priorityQueue.peek().intValue();
        }
        return res;
    }


    public int[] maxSlidingWindow2(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        int index = 0;

        Deque<Integer> deque = new LinkedBlockingDeque<>();
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        res[index++] = nums[deque.peekFirst().intValue()];
        for (int i = k; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst().intValue() <= i - k) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            res[index++] = nums[deque.peekFirst().intValue()];
        }
        return res;
    }


    /**
     * 76. 最小覆盖子串
     */
    public static String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        int[] tNum = new int[58];
        for (char c : tChar) {
            tNum[c - 'A']++;
        }
        int[] temp = new int[58];
        int left = 0;
        int right = 0;

        String res = "";
        boolean init = true;
        while (left < sChar.length && right < sChar.length) {
            if (condition(tNum, temp)) {
                if (right - left < res.length() || init) {
                    res = s.substring(left, right);
                    init = false;
                }
                temp[sChar[left] - 'A']--;
                left++;
            } else {
                temp[sChar[right] - 'A']++;
                right++;
            }
        }

        while (condition(tNum, temp)) {
            if (right - left < res.length() || init) {
                res = s.substring(left, right);
                init = false;
            }
            temp[sChar[left] - 'A']--;
            left++;
        }
        return res;
    }

    public static boolean condition(int[] tNum, int[] temp) {
        for (int i = 0; i < 58; i++) {
            if (tNum[i] > temp[i]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testStr() {
        System.out.println(minWindow("cgklivwehljxrdzpfdqsapogwvjtvbzahjnsejwnuhmomlfsrvmrnczjzjevkdvroiluthhpqtffhlzyglrvorgnalk", "mqfff"));

    }


    /**
     * 53.最大子数组和
     */
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }


    /***
     * 56.合同分区
     * */
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int[][] dp = new int[intervals.length][2];
        int index = 0;
        dp[index] = intervals[0];
        for (int i = 1; i < dp.length; i++) {
            if (intervals[i][0] > dp[index][1]) {
                index++;
                dp[index] = intervals[i];
            } else {
                dp[index][0] = Math.min(dp[index][0], intervals[i][0]);
                dp[index][1] = Math.max(dp[index][1], intervals[i][1]);
            }
        }
        int[][] res = new int[index + 1][2];
        System.arraycopy(dp, 0, res, 0, index + 1);
        return res;
    }


    /**
     * 189.轮转数组
     */
    public void rotate(int[] nums, int k) {
        if (nums.length <= 1) {
            return;
        }
        if (k == 0 || k % nums.length == 0) {
            return;
        }

        k = k % nums.length;
        int[] arr = new int[nums.length + k];
        for (int i = 0; i < nums.length; i++) {
            arr[i + k] = nums[i];
        }
        for (int i = k; i < arr.length; i++) {
            nums[i % nums.length] = arr[i];
        }
    }

    public void rotate1(int[] nums, int k) {
        if (nums.length <= 1) {
            return;
        }
        if (k == 0 || k % nums.length == 0) {
            return;
        }
        k = k % nums.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + k >= nums.length) {
                stack.push(nums[i]);
            } else {
                nums[i + k] = nums[i];
            }
        }
        int index = 0;
        while (!stack.isEmpty()) {
            nums[index++] = stack.pop();
        }

    }

    /**
     * 238.除自身以外数组的乘机
     */
    public int[] productExceptSelf(int[] nums) {
        if (nums.length <= 1) {
            return nums;
        }
        int[] dp = new int[nums.length];
        int[] dpBack = new int[nums.length];

        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = dp[i - 1] * nums[i];
        }
        dpBack[nums.length - 1] = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            dpBack[i] = dpBack[i + 1] * nums[i];
        }
        int[] res = new int[nums.length];
        res[0] = dpBack[1];
        res[res.length - 1] = dp[res.length - 2];
        for (int i = 1; i < res.length - 1; i++) {
            res[i] = dpBack[i + 1] * dp[i - 1];
        }
        return res;
    }


    /**
     * 41.确实的一个正数
     */
    public int firstMissingPositive(int[] nums) {
        if (nums.length == 0) {
            return 1;
        }
        if (nums.length == 1) {
            if (nums[0] != 1) {
                return 1;
            } else {
                return 2;
            }
        }

        Set<Integer> set = new HashSet<>();
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num > 0) {
                if (num < min) {
                    min = num;
                }
                set.add(num);
            }
        }
        if (min != 1) return 1;
        while (set.contains(min)) {
            min++;
        }
        return min;
    }


    /**
     * 73.矩阵置零
     */
    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0) {
            return;
        }
        Set<Integer> row = new HashSet<>();
        Set<Integer> col = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    row.add(i);
                    col.add(j);
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (row.contains(i) || col.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /***
     * 54.螺旋矩阵
     * */
    public static List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        int row = 0;
        int col = 0;
        int top = 0;
        int bottom = matrix.length;
        int left = 0;
        int right = matrix[0].length;
        boolean[][] flag = new boolean[matrix.length][matrix[0].length];
        while (left < matrix.length && top < matrix[0].length && !flag[left][top]) {
            row = top;
            col = left;
            while (col < right - 1 && !flag[row][col]) {
                res.add(matrix[row][col]);
                flag[row][col] = true;
                col++;
            }
            while (row < bottom - 1 && !flag[row][col]) {
                res.add(matrix[row][col]);
                flag[row][col] = true;
                row++;
            }
            while (col > left && !flag[row][col]) {
                res.add(matrix[row][col]);
                flag[row][col] = true;
                col--;
            }
            while (row > top && !flag[row][col]) {
                res.add(matrix[row][col]);
                flag[row][col] = true;
                row--;
            }
            if (!flag[row][col]) {
                res.add(matrix[row][col]);
                flag[row][col] = true;
            }
            left++;
            top++;
            bottom--;
            right--;
        }
        return res;
    }

    @Test
    public void testMir() {
        int[][] martix = {{6, 9, 7}};
        System.out.println(spiralOrder(martix));
    }


    /**
     * 48.旋转图像
     */
    public static void rotate(int[][] matrix) {
        if (matrix.length <= 1 || matrix[0].length <= 1) {
            return;
        }
        int row = 0;
        int col = 0;
        int left = 0;
        int top = 0;
        int right = matrix[0].length;
        int bottom = matrix.length;
        while (left < right && top < bottom) {
            row = top;
            col = left;
            Deque<Integer> deque = new LinkedBlockingDeque<>();
            for (int i = bottom - 1; i > top; i--) {
                deque.addLast(matrix[i][left]);
            }
            while (col < right - 1) {
                deque.addLast(matrix[row][col]);
                matrix[row][col] = deque.pop();
                col++;
            }
            while (row < bottom - 1) {
                deque.addLast(matrix[row][col]);
                matrix[row][col] = deque.pop();
                row++;
            }
            while (col > left) {
                deque.addLast(matrix[row][col]);
                matrix[row][col] = deque.pop();
                col--;
            }
            while (row > top) {
                deque.addLast(matrix[row][col]);
                matrix[row][col] = deque.pop();
                row--;
            }
            left++;
            right--;
            top++;
            bottom--;
        }
    }

    @Test
    public void testMatrix() {
        int[][] matrix = {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
        rotate(matrix);
    }


    /**
     * 240. 搜索二维矩阵 II
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }
        if (target < matrix[0][0] || target > matrix[matrix.length - 1][matrix[0].length - 1]) {
            return false;
        }
        for (int[] ints : matrix) {
            if (ints[0] <= target && ints[matrix[0].length - 1] >= target && binary(ints, target, 0, matrix[0].length)) {
                return true;
            }
        }
        return false;

    }

    public boolean binary(int[] matrix, int target, int left, int right) {
        if (right < left) {
            return false;
        }
        int mid = left + (right - left) / 2;
        if (matrix[mid] == target) {
            return true;
        } else if (target > matrix[mid]) {
            return binary(matrix, target, mid + 1, right);
        } else {
            return binary(matrix, target, left, mid - 1);
        }
    }

    public int binaryMatrixLeft(int[][] matrix, int target, int left, int right) {
        int mid = left + (right - left) / 2;
        if (matrix[mid][0] == target) {
            return mid;
        } else if (target > matrix[mid][0]) {
            if (mid == left) {
                return matrix[right][0] < target ? right : left;
            }
            return binaryMatrixLeft(matrix, target, mid, right);
        } else {
            return binaryMatrixLeft(matrix, target, left, mid - 1);
        }
    }

    public int binaryMatrixRight(int[][] matrix, int target, int left, int right) {
        int mid = left + (right - left) / 2;
        if (matrix[mid][matrix[0].length - 1] == target) {
            return mid;
        } else if (target > matrix[mid][matrix[0].length - 1]) {
            return binaryMatrixRight(matrix, target, mid + 1, right);
        } else {
            if (mid == left) {
                return target < matrix[mid][left] ? left : right;
            }
            return binaryMatrixRight(matrix, target, left, mid);
        }
    }


    /**
     * 160.相交链表
     * <p>
     * 两个指针都走一边，第一个指针走完第一个，再走第二个，
     * 第二个指针，走完第二个，在走第一个
     * 如过有相交就会来指针走到同一个节点
     **/
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode p1 = headA;
        ListNode p2 = headB;
        boolean f1 = true;
        boolean f2 = true;

        while (p1 != p2) {
            if (p1.next != null) {
                p1 = p1.next;
            } else if (f1) {
                f1 = false;
                p1 = headB;
            } else {
                return null;
            }
            if (p2.next != null) {
                p2 = p2.next;
            } else if (f2) {
                f2 = false;
                p2 = headA;
            } else {
                return null;
            }
        }
        return p1;
    }


    /**
     * 206.反转链表
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode p = head.next;

        while (p != null) {
            head.next = p.next;
            p.next = pre.next;
            pre.next = p;

            p = head.next;
        }
        return pre.next;
    }

    /**
     * 234. 回文链表
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode slow = head;
        ListNode fast = head;
        Stack<Integer> stack = new Stack<>();
        while (fast != null) {
            if (fast.next != null) {
                fast = fast.next.next;
                stack.push(slow.val);
            } else {
                fast = null;
            }
            slow = slow.next;
        }

        while (slow != null) {
            if (stack.pop() != slow.val) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    public static boolean isPalindrome1(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        List<Integer> arr = new ArrayList<>();
        ListNode p = head;
        while (p != null) {
            arr.add(p.val);
            p = p.next;
        }
        int left = 0;
        int right = arr.size() - 1;

        while (left <= right) {
            if (arr.get(left) != arr.get(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    @Test
    public void testNode() {
        ListNode node = new ListNode(1);
        node.next = new ListNode(0);
        node.next.next = new ListNode(1);
        System.out.println(isPalindrome(node));
    }


    /**
     * 141.环型链表
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast != null && slow == fast) {
                return true;
            }
        }
        return false;
    }


    /**
     * 142.环形链表II
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == null || slow == null) {
                return null;
            }
            if (slow == fast) {
                break;
            }
        }

        ListNode p = head;
        if (p == slow) {
            return p;
        }
        while (p != null && slow != null) {
            p = p.next;
            slow = slow.next;
            if (p == slow) {
                return p;
            }
        }
        return null;
    }


    /**
     * 21.合并连个有序链表
     */
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
            if (p1.val < p2.val) {
                ListNode next = p1.next;
                p1.next = null;
                p.next = p1;
                p1 = next;
            } else {
                ListNode next = p2.next;
                p2.next = null;
                p.next = p2;
                p2 = next;
            }
            p = p.next;
        }
        while (p1 != null) {
            ListNode next = p1.next;
            p1.next = null;
            p.next = p1;
            p1 = next;
            p = p.next;
        }
        while (p2 != null) {
            ListNode next = p2.next;
            p2.next = null;
            p.next = p2;
            p2 = next;
            p = p.next;
        }
        return pre.next;
    }


    /**
     * 2.两数相加
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode res = new ListNode(-1);
        ListNode p = res;
        int next = 0;
        ListNode p1 = l1;
        ListNode p2 = l2;

        while (p1 != null && p2 != null) {
            int num = p1.val + p2.val + next;
            next = num / 10;
            int now = num % 10;
            p.next = new ListNode(now);
            p = p.next;
            p1 = p1.next;
            p2 = p2.next;
        }

        while (p1 != null) {
            int num = p1.val + next;
            next = num / 10;
            int now = num % 10;
            p.next = new ListNode(now);
            p = p.next;
            p1 = p1.next;
        }
        while (p2 != null) {
            int num = p2.val + next;
            int now = num % 10;
            next = num / 10;
            p.next = new ListNode(now);
            p = p.next;
            p2 = p2.next;
        }
        if (next != 0) {
            p.next = new ListNode(next);
            p = p.next;
        }
        return res.next;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     **/
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode pre = new ListNode();
        pre.next = head;

        ListNode p = pre;

        for (int i = 0; i < n; i++) {
            p = p.next;
        }
        ListNode slow = pre;

        while (p.next != null) {
            p = p.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return pre.next;
    }


    /**
     * 24. 两两交换链表中的节点
     */
    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;


        ListNode left = pre;
        ListNode right = left.next;

        while (right != null && right.next != null) {
            ListNode next = right.next;
            right.next = next.next;
            next.next = right;
            left.next = next;


            left = right;
            right = left.next;
        }
        return pre.next;
    }

    @Test
    public void testNode1() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        swapPairs(head);
    }


    /**
     * 25. K 个一组翻转链表
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k == 1) {
            return head;
        }
        return reverse(head, k);
    }

    public ListNode reverse(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode h = head;
        int count = 0;
        while (h != null) {
            count++;
            h = h.next;
        }
        if (count < k) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode p = pre.next;
        int num = 1;
        while (p.next != null) {
            ListNode next = p.next;
            p.next = next.next;
            next.next = pre.next;
            pre.next = next;
            num++;
            if (num == k) {
                p.next = reverse(p.next, k);
                break;
            }
        }
        return pre.next;
    }


    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 138. 随机链表的复制
     */
    public static Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Map<Node, Node> randomMap = new HashMap<>();

        Map<Node, Integer> indexMap = new HashMap<>();

        // 节点index-> 前一个节点
        Map<Integer, Node> indexNodeMap = new HashMap<>();

        Node pre = new Node(-1);
        Node cur = pre;
        Node p = head;
        int index = 0;
        while (p != null) {
            cur.next = new Node(p.val);
            cur = cur.next;
            randomMap.put(p, p.random);
            indexMap.put(p, index++);
            p = p.next;
        }

        randomMap.forEach((key, value) -> {
            if (value != null) {
                Integer integer = indexMap.get(value);
                indexNodeMap.put(integer, key);
            }
        });

        Node p1 = pre.next;
        index = 0;
        while (p1 != null) {
            Node node = indexNodeMap.get(index++);
            if (node != null) {
                node.random = p1;
            }
            p1 = p1.next;
        }
        return pre.next;
    }


    /**
     * 138. 随机链表的复制
     */
    Map<Node, Node> nodeCacheMap = new HashMap<>();

    public Node copyRandomList1(Node head) {
        if (head == null) {
            return null;
        }
        if (!nodeCacheMap.containsKey(head)) {
            Node newNode = new Node(head.val);
            nodeCacheMap.put(head, newNode);
            newNode.next = copyRandomList(head.next);
            newNode.random = copyRandomList(head.random);
        }
        return nodeCacheMap.get(head);
    }

    @Test
    public void testRandom() {
        Node head = new Node(7);
        Node node13 = new Node(13);
        Node node11 = new Node(11);
        Node node10 = new Node(10);
        Node node1 = new Node(1);
        head.next = node13;
        node13.next = node11;
        node11.next = node10;
        node10.next = node1;
        node13.random = head;
        node11.random = node1;
        node10.random = node11;
        node1.random = head;
        copyRandomList(head);
    }


    /**
     * 148. 排序链表
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode p = head;
        while (p.next != null) {
            ListNode next = p.next;
            if (p.val < next.val) {
                p = p.next;
            } else {
                p.next = next.next;
                ListNode first = pre;
                while (first.next.val < next.val) {
                    first = first.next;
                }
                next.next = first.next;
                first.next = next;
            }
        }
        return pre.next;
    }


    /**
     * 23. 合并 K 个升序链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        ListNode pre = new ListNode(-1);
        ListNode point = pre;

        while (true) {
            ListNode p = null;
            int index = 0;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    continue;
                }
                if (p == null || p.val > lists[i].val) {
                    p = lists[i];
                    index = i;
                }
            }
            if (p == null) {
                return pre.next;
            }
            lists[index] = p.next;
            point.next = p;
            p.next = null;
            point = point.next;
        }
    }


    /**
     * 146. LRU 缓存
     */
    class LRUCache {

        private final Entry tail;
        private final Entry head;
        private final Integer capacity;
        private final Map<Integer, Entry> cache;

        public LRUCache(int capacity) {
            this.cache = new HashMap<>();
            this.capacity = capacity;
            this.head = new Entry();
            this.tail = new Entry();
            this.head.next = this.tail;
            this.tail.pre = this.head;
        }

        public int get(int key) {
            Entry entry = cache.get(key);
            if (entry == null) {
                return -1;
            }
            moveHead(entry);
            return entry.value;
        }

        public void put(int key, int value) {
            Entry entry = cache.get(key);
            if (entry == null) {
                entry = new Entry(key, value);
                cache.put(key, entry);
                addHead(entry);
                if (cache.size() > capacity) {
                    Entry removeEntry = removeEntry();
                    cache.remove(removeEntry.key);
                }
            } else {
                entry.value = value;
                moveHead(entry);
            }

        }

        public void moveHead(Entry entry) {
            Entry next = entry.next;
            Entry pre = entry.pre;
            pre.next = next;
            next.pre = pre;

            Entry second = head.next;
            head.next = entry;
            entry.next = second;
            entry.pre = head;
            second.pre = entry;
        }

        public void addHead(Entry entry) {
            Entry second = head.next;
            head.next = entry;
            entry.next = second;
            entry.pre = head;
            second.pre = entry;
        }

        public Entry removeEntry() {
            Entry last = tail.pre;
            if (last == null) {
                return null;
            }
            Entry pre = last.pre;
            pre.next = tail;
            tail.pre = pre;
            return last;
        }


        public static class Entry {
            private Integer key;
            private Integer value;
            private Entry pre;
            private Entry next;

            public Entry(Integer key, Integer value) {
                this.key = key;
                this.value = value;
            }

            public Entry() {
            }
        }

    }


    /**
     * 94.二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        traverse(root, res);
        return res;
    }

    public void traverse(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        traverse(node.left, res);
        res.add(node.val);
        traverse(node.right, res);
    }


    /***
     * 104.二叉树的最大深度
     * */
    public int maxDepth(TreeNode root) {

        traverse(root, 1);
        return max;
    }

    private Integer max = 0;

    public void traverse(TreeNode node, int dept) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            max = Math.max(dept, max);
        }
        traverse(node.left, dept + 1);

        traverse(node.right, dept + 1);

    }

    /**
     * 226.翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        return traverseInvert(root);
    }

    public TreeNode traverseInvert(TreeNode node) {
        if (node == null || (node.left == null && node.right == null)) {
            return node;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        node.right = traverseInvert(left);
        node.left = traverseInvert(right);
        return node;
    }


    /**
     * 101. 对称二叉树
     **/
    public boolean isSymmetric(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return traverseMetric(root.left, root.right);
    }


    public boolean traverseMetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (right == null || left == null) {
            return false;
        }
        return left.val == right.val && traverseMetric(left.left, right.right) && traverseMetric(left.right, right.left);
    }


    /***
     * 543.二叉树的直径
     * */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        traverseDiameter(root);
        return maxDiameter;
    }

    private int maxDiameter = 0;

    public int traverseDiameter(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        int left = traverseDiameter(node.left);
        int right = traverseDiameter(node.right);
        maxDiameter = Math.max(left + right, maxDiameter);
        return Math.max(left, right) + 1;
    }


    /**
     * 102. 二叉树的层序遍历
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll != null) {
                    level.add(poll.val);
                    if (poll.left != null) {
                        queue.add(poll.left);
                    }
                    if (poll.right != null) {
                        queue.add(poll.right);
                    }
                }
            }
            res.add(level);
        }
        return res;
    }


    /***
     * 108. 将有序数组转换为二叉搜索树
     * */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        return sortArrayToBST(nums, 0, nums.length - 1);
    }

    public TreeNode sortArrayToBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (right + left) / 2;
        TreeNode treeNode = new TreeNode(nums[mid]);
        treeNode.left = sortArrayToBST(nums, left, mid - 1);
        treeNode.right = sortArrayToBST(nums, mid + 1, right);
        return treeNode;
    }


    /**
     * 98.验证二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return traverseValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean traverseValidBST(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        if (node.val <= min || node.val >= max) {
            return false;
        }
        return traverseValidBST(node.left, min, node.val) && traverseValidBST(node.right, node.val, max);
    }


    /**
     * 98.检验是否是二叉搜索树
     **/
    public boolean isValidBST1(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        traverseMid(root);

        for (int i = 1; i < midTraverseList.size(); i++) {
            if (midTraverseList.get(i) <= midTraverseList.get(i - 1)) {
                return false;
            }

        }
        return true;
    }

    public List<Long> midTraverseList = new ArrayList<>();

    public void traverseMid(TreeNode node) {
        if (node == null) {
            return;
        }
        traverseMid(node.left);
        midTraverseList.add((long) node.val);
        traverseMid(node.right);
    }

    /**
     * 98.检验是否是二叉搜索树
     **/
    public boolean isValidBST2(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        long preNum = Long.MIN_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.val <= preNum) {
                return false;
            }
            preNum = root.val;
            root = root.right;
        }
        return true;
    }

    public boolean isValidBST3(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return isBST2(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isBST2(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        if (node.val >= max || node.val <= min) {
            return false;
        }
        return isBST2(node.left, min, node.val) && isBST2(node.right, node.val, max);
    }


    /**
     * 230.二叉树嗖嗖树中第K小的元素
     */
    public int kthSmallest(TreeNode root, int k) {
        count = 0;
        traverseMid(root, k);
        return resNum;
    }

    int resNum;
    int count;

    public void traverseMid(TreeNode node, int k) {
        if (node == null || count >= k) {
            return;
        }
        traverseMid(node.left, k);
        count++;
        if (count == k) {
            resNum = node.val;
            return;
        }
        traverseMid(node.right, k);
    }


    /***
     * 199. 二叉树的右视图
     * */
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> rightSideViewList = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode poll = null;
            for (int i = 0; i < size; i++) {
                poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            rightSideViewList.add(poll.val);
        }

        return rightSideViewList;
    }


    /***
     * 114. 二叉树展开为链表
     * */
    public void flatten(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }
        flattenNode(root);
    }

    public TreeNode flattenNode(TreeNode node) {
        if (node == null || (node.left == null && node.right == null)) {
            return node;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        node.left = null;
        node.right = null;
        node.right = flattenNode(left);
        node.left = null;
        TreeNode p = node;
        while (p.right != null) {
            p = p.right;
        }
        p.right = flattenNode(right);
        return node;
    }


    /***
     * 105. 从前序与中序遍历序列构造二叉树
     * */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }
        if (preorder.length == 1) {
            return new TreeNode(preorder[0]);
        }
        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder, int preLeft, int preRight, int inLeft, int inRight) {
        if (preLeft > preRight || inLeft > inRight) {
            return null;
        }
        if (preLeft == preRight) {
            return new TreeNode(preorder[preLeft]);
        }
        int val = preorder[preLeft];
        TreeNode node = new TreeNode(val);
        int index = inLeft;
        for (int i = inLeft; i <= inRight; i++) {
            if (inorder[i] == val) {
                index = i;
                break;
            }
        }
        node.left = buildTree(preorder, inorder, preLeft + 1, preLeft + index - inLeft, inLeft, index - 1);

        node.right = buildTree(preorder, inorder, preLeft + index - inLeft + 1, preRight, index + 1, inRight);
        return node;
    }


    /**
     * 437. 路径总和 III
     **/
    public int pathSum(TreeNode root, int targetSum) {

        pathSumNode(root, targetSum);
        return pathSum;
    }

    int pathSum = 0;

    Map<TreeNode, List<Long>> pathSumMap = new HashMap<>();

    public void pathSumNode(TreeNode node, int target) {
        if (node == null) {
            return;
        }
        pathSumNode(node.left, target);
        pathSumNode(node.right, target);
        List<Long> leftList = pathSumMap.get(node.left);
        List<Long> rightList = pathSumMap.get(node.right);
        List<Long> list = new ArrayList<>();
        if (leftList != null) {
            for (Long num : leftList) {
                long sum = num + node.val;
                if (sum == target) {
                    pathSum++;
                }
                list.add(sum);
            }
        }
        if (rightList != null) {
            for (Long num : rightList) {
                long sum = num + node.val;
                if (sum == target) {
                    pathSum++;
                }
                list.add(sum);
            }
        }
        list.add((long) node.val);
        if (node.val == target) {
            pathSum++;
        }
        pathSumMap.put(node, list);
    }


    /**
     * 236. 二叉树的最近公共
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (isAncestor(root, p) && isAncestor(root, q)) {
            TreeNode left = root.left;
            TreeNode right = root.right;
            if (isAncestor(left, p) && isAncestor(left, q)) {
                return lowestCommonAncestor(root.left, p, q);
            }
            if (isAncestor(right, p) && isAncestor(right, q)) {
                return lowestCommonAncestor(root.right, p, q);
            }
        }
        return root;
    }

    public boolean isAncestor(TreeNode root, TreeNode node) {
        if (root == null) {
            return false;
        }
        if (root == node) {
            return true;
        }
        return isAncestor(root.left, node) || isAncestor(root.right, node);
    }

    /**
     * 二叉树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {


        traverseDfs(root, p, q);
        return lowestCommonAncestor;
    }

    TreeNode lowestCommonAncestor;

    public boolean traverseDfs(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        boolean left = traverseDfs(node.left, p, q);
        boolean right = traverseDfs(node.right, p, q);
        if ((left && right) || ((node == p || node == q) && (left || right))) {
            lowestCommonAncestor = node;
        }
        return (left || right) || (node == p || node == q);
    }


    /**
     * 124. 二叉树中的最大路径和
     */
    public int maxPathSum(TreeNode root) {

        traverseMaxPathSum(root);
        return maxPathSum;
    }

    int maxPathSum = Integer.MIN_VALUE;

    public int traverseMaxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = traverseMaxPathSum(root.left);
        int right = traverseMaxPathSum(root.right);
        int max = root.val;
        max = Math.max(max, root.val + left);
        max = Math.max(max, root.val + right);
        if (max > maxPathSum) {
            maxPathSum = max;
        }
        if (root.val + left + right > maxPathSum) {
            maxPathSum = root.val + left + right;
        }
        return max;
    }


    /**
     * 200. 岛屿数量
     */
    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    sum++;
                    diffusion(i, j, visited, grid);
                }
            }
        }
        return sum;
    }

    public void diffusion(int i, int j, boolean[][] visited, char[][] grid) {
        if (i < 0 || j < 0 || i >= visited.length || j >= visited[0].length) {
            return;
        }
        visited[i][j] = true;
        if (i - 1 >= 0 && grid[i - 1][j] == '1' && !visited[i - 1][j]) {
            diffusion(i - 1, j, visited, grid);
        }
        if (i + 1 < visited.length && grid[i + 1][j] == '1' && !visited[i + 1][j]) {
            diffusion(i + 1, j, visited, grid);
        }
        if (j - 1 >= 0 && grid[i][j - 1] == '1' && !visited[i][j - 1]) {
            diffusion(i, j - 1, visited, grid);
        }
        if (j + 1 < visited[0].length && grid[i][j + 1] == '1' && !visited[i][j + 1]) {
            diffusion(i, j + 1, visited, grid);
        }
    }


    /**
     * 994. 腐烂的橘子
     **/
    public static int orangesRotting(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int count = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        while (true) {
            int sum = 0;
            boolean[][] temp = new boolean[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 2 && !visited[i][j]) {
                        temp[i][j] = true;
                    }
                }
            }
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    if (temp[i][j]) {
                        sum += diffusion(grid, i, j, visited);
                    }
                }
            }
            if (sum == 0) {
                break;
            }
            count++;
        }
        for (int[] ints : grid) {
            for (int anInt : ints) {
                if (anInt == 1) {
                    return -1;
                }
            }
        }
        return count;
    }

    public static int diffusion(int[][] grid, int i, int j, boolean[][] visited) {
        visited[i][j] = true;
        int count = 0;
        if (i - 1 >= 0 && grid[i - 1][j] == 1) {
            grid[i - 1][j] = 2;
            count++;
        }
        if (i + 1 < grid.length && grid[i + 1][j] == 1) {
            grid[i + 1][j] = 2;
            count++;
        }
        if (j - 1 >= 0 && grid[i][j - 1] == 1) {
            grid[i][j - 1] = 2;
            count++;
        }
        if (j + 1 < grid[0].length && grid[i][j + 1] == 1) {
            grid[i][j + 1] = 2;
            count++;
        }
        return count;
    }

    @Test
    public void testDiffusion() {
        int[][] arr = {{2, 1, 0, 2}};
        orangesRotting(arr);
    }


    /***
     * 207. 课程表
     * */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites.length <= 1) {
            return true;
        }
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        boolean[] visited = new boolean[numCourses];
        boolean[] visitedPath = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            traverseCanFinish(graph, i, visited, visitedPath);
        }
        return !cycle;
    }

    boolean cycle = false;

    public void traverseCanFinish(List<Integer>[] graph, int index, boolean[] visited, boolean[] visitedPath) {
        if (visitedPath[index] || cycle) {
            cycle = true;
            return;
        }
        if (visited[index]) {
            return;
        }
        visited[index] = true;
        visitedPath[index] = true;
        List<Integer> list = graph[index];
        for (Integer integer : list) {
            traverseCanFinish(graph, integer, visited, visitedPath);
        }
        visitedPath[index] = false;
    }

    public List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] pre : prerequisites) {
            graph[pre[0]].add(pre[1]);
        }
        return graph;
    }


    @Test
    public void testCanFinish() {
        int[][] arr = {{1, 0}, {1, 2}, {0, 1}};
        canFinish(3, arr);
    }


    /**
     * 208. 实现 Trie (前缀树)
     **/

    public static class Trie1 {

        Set<String> words;
        Set<String> prefix;

        public Trie1() {
            words = new HashSet<>();
            prefix = new HashSet<>();

        }

        public void insert(String word) {
            words.add(word);
            for (int i = 1; i < word.length(); i++) {
                prefix.add(word.substring(0, i));
            }
        }

        public boolean search(String word) {
            return words.contains(word);
        }

        public boolean startsWith(String prefix) {
            return words.contains(prefix) || this.prefix.contains(prefix);
        }
    }

    public static class Trie {

        private final Trie[] children;
        boolean flag = false;

        public Trie() {
            children = new Trie[26];
        }

        public void insert(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new Trie();
                }
                node = node.children[index];
            }
            node.flag = true;
        }

        public boolean search(String word) {
            return searchWord(word, false);
        }

        public boolean startsWith(String prefix) {
            return searchWord(prefix, true);
        }

        public boolean searchWord(String word, boolean prefix) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int index = c - 'a';
                Trie child = node.children[index];
                if (child == null) {
                    return false;
                }
                node = child;
            }
            return prefix || node.flag;
        }

    }


    /**
     * 46. 全排列
     **/
    public List<List<Integer>> permute(int[] nums) {
        boolean[] visited = new boolean[nums.length];
        backtrack(new LinkedList<>(), nums, visited);

        return permuteList;
    }

    List<List<Integer>> permuteList = new ArrayList<>();

    public void backtrack(LinkedList<Integer> list, int[] nums, boolean[] visited) {
        if (list.size() == nums.length) {
            permuteList.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                list.add(nums[i]);
                visited[i] = true;
                backtrack(list, nums, visited);
                visited[i] = false;
                list.removeLast();
            }
        }
    }

    /**
     * 78. 子集
     */
    public List<List<Integer>> subsets(int[] nums) {
        backtrackSubsets(new LinkedList<>(), nums, 0);
        return subsetsList;
    }

    List<List<Integer>> subsetsList = new ArrayList<>();

    public void backtrackSubsets(LinkedList<Integer> list, int[] nums, int index) {
        subsetsList.add(new ArrayList<>(list));
        for (int i = index; i < nums.length; i++) {
            list.add(nums[i]);
            backtrackSubsets(list, nums, i + 1);
            list.removeLast();
        }
    }


    /***
     * 17. 电话号码的字母组合
     * */
    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return new LinkedList<>();
        }
        List<String> strList = new ArrayList<>();
        for (int i = 0; i < digits.length(); i++) {
            strList.add(getNumStr(digits.charAt(i)));
        }
        backtrackLetterCombinations(new LinkedList<>(), strList, 0);
        return letterCombinations;
    }

    List<String> letterCombinations = new ArrayList<>();

    public void backtrackLetterCombinations(LinkedList<Character> list, List<String> strings, int index) {
        if (list.size() == strings.size()) {
            StringBuilder str = new StringBuilder();
            for (Character s : list) {
                str.append(s);
            }
            letterCombinations.add(str.toString());
            return;
        }
        String s = strings.get(index);

        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
            backtrackLetterCombinations(list, strings, index + 1);
            list.removeLast();
        }
    }

    public String getNumStr(char c) {
        switch (c) {
            case '2' -> {
                return "abc";
            }
            case '3' -> {
                return "def";
            }
            case '4' -> {
                return "ghi";
            }
            case '5' -> {
                return "jkl";
            }
            case '6' -> {
                return "mno";
            }
            case '7' -> {
                return "pqrs";
            }
            case '8' -> {
                return "tuv";
            }
            case '9' -> {
                return "wxyz";
            }
        }
        return null;
    }


    /**
     * 39. 组合总和
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtrackCombination(new LinkedList<>(), candidates, target, 0, 0);
        return combinationSumList;
    }

    List<List<Integer>> combinationSumList = new ArrayList<>();

    public void backtrackCombination(LinkedList<Integer> list, int[] candidates, int target, int index, int sum) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            combinationSumList.add(new ArrayList<>(list));
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            list.add(candidates[i]);
            backtrackCombination(list, candidates, target, i, sum + candidates[i]);
            list.removeLast();
        }
    }


    /**
     * 22. 括号生成
     */
    public List<String> generateParenthesis(int n) {
        if (n == 0) {
            return new LinkedList<>();
        }
        backtrackGenerateParenthesis(new StringBuilder(), n, 0, 0);
        return res;
    }

    List<String> res = new ArrayList<>();

    public void backtrackGenerateParenthesis(StringBuilder builder, int n, int left, int right) {
        if (right > n || left > n) {
            return;
        }
        if (left == n && right == n) {
            res.add(new String(builder));
            return;
        }
        if (left < n) {
            builder.append("(");
            backtrackGenerateParenthesis(builder, n, left + 1, right);
            builder.deleteCharAt(builder.length() - 1);
        }
        if (right < left) {
            builder.append(")");
            backtrackGenerateParenthesis(builder, n, left, right + 1);
            builder.deleteCharAt(builder.length() - 1);
        }
    }


    /**
     * 79.单词搜索
     */
    public boolean exist(char[][] board, String word) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        char[] charArray = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!existWord && board[i][j] == charArray[0]) {
                    backtrackExist(board, i, j, visited, charArray, 0);
                }
            }
        }
        return existWord;
    }

    boolean existWord = false;

    public void backtrackExist(char[][] board, int i, int j, boolean[][] visited, char[] words, int index) {
        if (index == words.length || existWord) {
            existWord = true;
            return;
        }
        if (i < 0 || j < 0 || i >= visited.length || j >= board[0].length) {
            return;
        }
        if (!visited[i][j] && board[i][j] == words[index]) {
            visited[i][j] = true;
            backtrackExist(board, i + 1, j, visited, words, index + 1);
            backtrackExist(board, i - 1, j, visited, words, index + 1);
            backtrackExist(board, i, j - 1, visited, words, index + 1);
            backtrackExist(board, i, j + 1, visited, words, index + 1);
            visited[i][j] = false;
        }
    }


    /**
     * 131. 分割回文串
     */
    public List<List<String>> partition(String s) {
        if (s.length() == 0) {
            return new LinkedList<>();
        }
        backtrackPartition(new LinkedList<>(), s, 0);
        return partitionList;
    }

    List<List<String>> partitionList = new ArrayList<>();

    public void backtrackPartition(LinkedList<String> subList, String str, int index) {
        if (index == str.length()) {
            partitionList.add(new ArrayList<>(subList));
            return;
        }
        if (index >= str.length()) {
            return;
        }
        for (int i = index; i < str.length(); i++) {
            String substring = str.substring(index, i + 1);
            if (isPartition(substring)) {
                subList.add(substring);
                backtrackPartition(subList, str, i + 1);
                subList.removeLast();
            }
        }
    }

    public boolean isPartition(String str) {
        if (str == null) {
            return false;
        }
        if (str.length() <= 1) {
            return true;
        }
        int left = 0;
        int right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }


    /**
     * 51. N 皇后
     **/
    public List<List<String>> solveNQueens(int n) {
        boolean[][] visited = new boolean[n][n];
        backSolveNQueens(new LinkedList<>(), visited, n, 0);
        return solveNQueens;
    }

    List<List<String>> solveNQueens = new ArrayList<>();

    public void backSolveNQueens(LinkedList<String> solve, boolean[][] visited, int n, int index) {
        if (solve.size() == n) {
            solveNQueens.add(new ArrayList<>(solve));
            return;
        }
        if (solve.size() >= n) {
            return;
        }
        for (int i = 0; i < n; i++) {
            visited[index][i] = true;
            if (isSolve(visited)) {
                solve.add(getSolve(i, n));
                backSolveNQueens(solve, visited, n, index + 1);
                solve.removeLast();
            }
            visited[index][i] = false;
        }
    }

    public String getSolve(int index, int n) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i == index) {
                res.append("Q");
            } else {
                res.append(".");
            }
        }
        return res.toString();
    }


    public boolean isSolve(boolean[][] visited) {
        int[] nums = new int[visited.length];
        int count = 0;
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                if (visited[i][j]) {
                    nums[i] = j;
                    count++;
                }

            }
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (i != j) {
                    if (nums[i] == nums[j] || Math.abs(i - j) == Math.abs(nums[i] - nums[j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 35. 搜索插入位置
     **/
    public int searchInsert(int[] nums, int target) {
        return binarySearch(nums, target, 0, nums.length - 1);
    }

    public int binarySearch(int[] nums, int target, int left, int right) {
        if (left > right) {
            return left;
        }
        if (right < 0) {
            return left;
        }
        if (left >= nums.length) {
            return right;
        }
        int mid = (right - left) / 2 + left;
        if (nums[mid] > target) {
            return binarySearch(nums, target, left, mid - 1);
        } else if (nums[mid] < target) {
            return binarySearch(nums, target, mid + 1, right);
        } else {
            return mid;
        }
    }


    /**
     * 74.搜索二维矩阵
     **/
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        if (matrix[0][0] > target || target > matrix[matrix.length - 1][matrix[0].length - 1]) {
            return false;
        }
        for (int[] ints : matrix) {
            if (ints[0] <= target && target <= ints[ints.length - 1] && binarySearchMatrix(ints, target, 0, ints.length - 1)) {
                return true;
            }
        }
        return false;
    }

    public boolean binarySearchMatrix(int[] matrix, int target, int left, int right) {
        if (left > right || right < 0 || left >= matrix.length) {
            return false;
        }
        int mid = (right - left) / 2 + left;
        if (matrix[mid] > target) {
            return binarySearchMatrix(matrix, target, left, mid - 1);
        } else if (matrix[mid] < target) {
            return binarySearchMatrix(matrix, target, mid + 1, right);
        } else {
            return true;
        }
    }


    @Test
    public void searchMatrixTest() {
        int[][] arr = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        searchMatrix1(arr, 3);
    }


    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     **/
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0 || target < nums[0] || target > nums[nums.length - 1]) {
            return new int[]{-1, -1};
        }
        int start = binarySearchRange(nums, target, 0, nums.length - 1);
        if (nums[start] != target) {
            return new int[]{-1, -1};
        }
        int end = start;
        while (end < nums.length && nums[end] == target) {
            end++;
        }
        return new int[]{start, end - 1};
    }

    public int binarySearchRange(int[] nums, int target, int left, int right) {
        if (left > right) {
            return left;
        }
        int mid = (right - left) / 2 + left;
        if (nums[mid] >= target) {
            return binarySearchRange(nums, target, left, mid - 1);
        } else {
            return binarySearchRange(nums, target, mid + 1, right);
        }
    }

    /**
     * 33. 搜索旋转排序数组
     **/
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return binarySearch1(nums, target, 0, nums.length - 1);
    }

    public int binarySearch1(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = (right - left) / 2 + left;
        if (nums[mid] > target) {
            if (nums[mid] > nums[right]) {
                if (target > nums[right]) {
                    return binarySearch1(nums, target, left, mid - 1);
                } else {
                    return binarySearch1(nums, target, left + 1, right);
                }
            } else {
                if (target > nums[left]) {
                    return binarySearch1(nums, target, left + 1, mid);
                } else {
                    return binarySearch1(nums, target, left, mid - 1);
                }
            }
        } else if (nums[mid] < target) {
            if (nums[mid] < nums[right]) {
                if (nums[right] >= target) {
                    return binarySearch1(nums, target, mid + 1, right);
                } else {
                    return binarySearch1(nums, target, left, mid - 1);
                }
            } else {
                if (target < nums[right]) {
                    return binarySearch1(nums, target, mid, right - 1);
                } else {
                    return binarySearch1(nums, target, mid + 1, right);
                }
            }
        } else {
            return mid;
        }
    }


    /***
     * 153. 寻找旋转排序数组中的最小值
     * */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        return binaryFindMin(nums, 0, nums.length - 1);
    }

    public int binaryFindMin(int[] nums, int left, int right) {
        if (left >= right) {
            return nums[left];
        }
        int mid = (right - left) / 2 + left;
        if (nums[mid] > nums[right]) {
            return binaryFindMin(nums, mid + 1, right);
        } else {
            return binaryFindMin(nums, left, mid);
        }
    }


    /**
     * 4. 寻找两个正序数组的中位数
     **/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int[] merge = merge(nums1, nums2);
        int length = merge.length;
        if (length % 2 == 0) {
            int index = (length - 1) / 2;
            return (merge[index] + merge[index + 1]) / 2.0;
        } else {
            return merge[(length - 1) / 2];
        }
    }

    public int[] merge(int[] nums1, int[] nums2) {
        int left1 = 0;
        int left2 = 0;
        int[] nums = new int[nums1.length + nums2.length];
        int index = 0;
        while (left1 < nums1.length && left2 < nums2.length) {
            if (nums1[left1] < nums2[left2]) {
                nums[index] = nums1[left1++];
            } else {
                nums[index] = nums2[left2++];
            }
            index++;
        }
        while (left1 < nums1.length) {
            nums[index] = nums1[left1++];
            index++;
        }
        while (left2 < nums2.length) {
            nums[index] = nums2[left2++];
            index++;
        }
        return nums;
    }


    /**
     * 20. 有效的括号
     */
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();

        char[] chars = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            if (stack.isEmpty()) {
                stack.push(chars[i]);
            } else {
                if (valid(stack.peek(), chars[i])) {
                    stack.pop();
                } else {
                    stack.push(chars[i]);
                }
            }
        }
        return stack.isEmpty();
    }

    public boolean valid(char c1, char c2) {
        if (c1 == '(' && c2 == ')') {
            return true;
        }
        if (c1 == '{' && c2 == '}') {
            return true;
        }
        return c1 == '[' && c2 == ']';
    }


    /**
     * 155. 最小栈
     **/
    public static class MinStack {

        Stack<Integer> stack;
        int min = Integer.MAX_VALUE;

        public MinStack() {
            stack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            min = Math.min(min, val);
        }

        public void pop() {
            Integer pop = stack.pop();
            if (pop == min) {
                min = Integer.MAX_VALUE;
                stack.forEach(num -> min = Math.min(min, num));
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }


    /**
     * 394. 字符串解码
     **/
    public String decodeString(String s) {

        if (s == null || s.length() == 0) {
            return null;

        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ']') {
                StringBuilder stringBuilder = new StringBuilder();
                while (!stack.isEmpty() && Character.isLetter(stack.peek())) {
                    stringBuilder.append(stack.pop());
                }
                stack.pop();
                StringBuilder countBuilder = new StringBuilder();
                while (!stack.isEmpty() && Character.isDigit(stack.peek())) {
                    countBuilder.append(stack.pop());
                }
                int integer = Integer.parseInt(countBuilder.reverse().toString());
                String s1 = stringBuilder.reverse().toString();
                String s2 = "";
                for (int j = 0; j < integer; j++) {
                    s2 += s1;
                }
                for (int i1 = 0; i1 < s2.toCharArray().length; i1++) {
                    stack.push(s2.charAt(i1));
                }
            } else {
                stack.push(c);
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }
        return builder.reverse().toString();
    }

    @Test
    public void testDecodeString() {
        String str = "3[a]2[bc]";
        decodeString(str);
    }


    /**
     * 739. 每日温度
     **/
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return new int[0];
        }
        int[] res = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        LinkedList<Integer> index = new LinkedList<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && stack.peek() < temperatures[i]) {
                stack.pop();
                Integer pre = index.removeLast();
                res[pre] = i - pre;
            }
            stack.push(temperatures[i]);
            index.add(i);
        }
        while (!stack.isEmpty()) {
            stack.pop();
            res[index.removeLast()] = 0;
        }
        return res;
    }


    /**
     * 84. 柱状图中最大的矩形
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            int min = heights[i];
            for (int j = i; j >= 0; j--) {
                min = Math.min(heights[j], min);
                max = Math.max((i - j + 1) * min, max);
            }
        }
        return max;
    }


    /**
     * 84.柱状图中的最大的矩形，单调栈
     */
    public int largestRectangleArea1(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }
        Deque<Integer> stack = new ArrayDeque<>(len);
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peekLast()] > heights[i]) {
                int height = heights[stack.pollLast()];
                while (!stack.isEmpty() && height == heights[stack.peekLast()]) {
                    stack.pollLast();
                }
                int weight;
                if (stack.isEmpty()) {
                    weight = i;
                } else {
                    weight = i - stack.peekLast() - 1;
                }
                max = Math.max(max, height * weight);
            }
            stack.addLast(i);
        }
        while (!stack.isEmpty()) {
            int height = heights[stack.pollLast()];
            while (!stack.isEmpty() && height == heights[stack.peekLast()]) {
                stack.pollLast();
            }
            int weight;
            if (stack.isEmpty()) {
                weight = len;
            } else {
                weight = len - stack.peekLast() - 1;
            }
            max = Math.max(max, height * weight);
        }
        return max;
    }


    @Test
    public void testLargestRectangleArea() {
        int[] arr = {2, 1, 5, 6, 2, 3};
        largestRectangleArea1(arr);
    }


    /**
     * 215. 数组中的第K个最大元素
     */
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0 || nums.length < k) {
            return -1;
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k, (v1, v2) -> v2 - v1);

        for (int num : nums) {
            priorityQueue.add(num);
        }
        int res = -1;
        while (k > 0) {
            res = priorityQueue.poll();
            k--;
        }
        return res;
    }

    /***
     * 347. 前 K 个高频元素
     * */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>(((v1, v2) -> v2.getValue() - v1.getValue()));

        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            priorityQueue.add(integerIntegerEntry);
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = priorityQueue.poll().getKey();
        }
        return res;
    }

    /**
     * 295. 数据流的中位数
     */
    public static class MedianFinder {

        PriorityQueue<Integer> priorityQueue1;

        PriorityQueue<Integer> priorityQueue2;

        public MedianFinder() {
            priorityQueue1 = new PriorityQueue<>((c1, c2) -> c2 - c1);
            priorityQueue2 = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (priorityQueue1.isEmpty()) {
                priorityQueue1.add(num);
            } else {
                if (priorityQueue1.size() > priorityQueue2.size()) {
                    if (num >= priorityQueue1.peek()) {
                        priorityQueue2.add(num);
                    } else {
                        priorityQueue1.add(num);
                        priorityQueue2.add(priorityQueue1.poll());
                    }
                } else {
                    if (num <= priorityQueue2.peek()) {
                        priorityQueue1.add(num);
                    } else {
                        priorityQueue2.add(num);
                        priorityQueue1.add(priorityQueue2.poll());
                    }
                }
            }
        }

        public double findMedian() {
            if (priorityQueue1.isEmpty()) {
                return 0.0;
            }
            if (priorityQueue1.size() == priorityQueue2.size()) {
                return (priorityQueue1.peek() + priorityQueue2.peek()) / 2.0;
            }
            return priorityQueue1.peek();
        }
    }

    @Test
    public void testMedian() {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(2);
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
    }


    /**
     * 121.买卖股票的最佳时机
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[prices.length - 1][0];
    }


    /**
     * 买卖股票的最佳时机 II
     **/
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[prices.length - 1][0];
    }


    /**
     * 55. 跳跃游戏
     */
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;
        for (int i = 0; i < nums.length; i++) {
            if (dp[i]) {
                for (int j = i + 1; j < nums.length && j <= i + nums[i]; j++) {
                    dp[j] = true;
                }
            }
        }
        return dp[nums.length - 1];
    }


    public boolean canJump2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }
        int right = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (i > right) {
                continue;
            }
            right = Math.max(right, i + nums[i]);
            if (right >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 45. 跳跃游戏 II
     **/
    public int jump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j <= i + nums[i] && j < nums.length; j++) {
                dp[j] = Math.min(dp[j], dp[i] + 1);
            }
        }
        return dp[nums.length - 1];
    }


    /***
     *763. 划分字母区间
     * */
    public List<Integer> partitionLabels(String s) {

        if (s == null || s.length() == 0) {
            return new LinkedList<>();
        }
        if (s.length() == 1) {
            return List.of(1);
        }
        List<Integer> res = new ArrayList<>();
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        int end = Integer.MIN_VALUE;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (end == i) {
                res.add(end - start + 1);
                start = end + 1;
            }
        }
        return res;
    }


    /**
     * 70. 爬楼梯
     **/
    public int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }


    /**
     * 118. 杨辉三角
     **/
    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> nums = new LinkedList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    nums.add(1);
                } else {
                    List<Integer> pre = res.get(i - 1);
                    nums.add(pre.get(j) + pre.get(j - 1));
                }
            }
            res.add(nums);
        }
        return res;
    }


    /**
     * 198. 打家劫舍
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[][] dp = new int[nums.length][2];

        dp[0][0] = 0;
        dp[0][1] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = dp[i - 1][0] + nums[i];
        }
        return Math.max(dp[nums.length - 1][1], dp[nums.length - 1][0]);
    }


    /**
     * 279. 完全平方数
     **/
    public int numSquares(int n) {
        if (n <= 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        List<Integer> sqrtList = new LinkedList<>();
        for (int i = 1; i < n + 1; i++) {
            int num = (int) Math.sqrt(i);
            if (num * num == i) {
                dp[i] = 1;
                sqrtList.add(i);
            } else {
                for (int j = sqrtList.size() - 1; j >= 0; j--) {
                    dp[i] = Math.min(dp[i - sqrtList.get(j)] + 1, dp[i]);
                }
            }
        }
        return dp[n];
    }


    /**
     * 零钱兑换
     */
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        Set<Integer> set = new HashSet<>();
        Arrays.stream(coins).forEach(set::add);
        for (int i = 1; i < dp.length; i++) {
            if (set.contains(i)) {
                dp[i] = 1;
            } else {
                for (int j = coins.length - 1; j >= 0; j--) {
                    if (i - coins[j] >= 0 && dp[i - coins[j]] != Integer.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                    }
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }


    /**
     * 139. 单词拆分
     **/
    public boolean wordBreak(String s, List<String> wordDict) {

        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

}
