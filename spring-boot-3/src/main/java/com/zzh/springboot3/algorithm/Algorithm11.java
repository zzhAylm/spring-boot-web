package com.zzh.springboot3.algorithm;

import lombok.val;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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
        if (node.left != null && node.val <= node.left.val) {
            return false;
        }
        if (node.right != null && node.val >= node.right.val) {
            return false;
        }
        if (node.val <= min || node.val >= max) {
            return false;
        }
        return traverseValidBST(node.left, min, node.val) && traverseValidBST(node.right, node.val, max);
    }

}
