package com.zzh.springboot.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/1/22 09:56
 */
@Slf4j
public class Algorithm14 {


    /**
     * 88.合并两个有序数组
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n <= 0) {
            return;
        }
        int[] arr = new int[m + n];
        int left = 0;
        int right = 0;
        for (int i = 0; i < arr.length; i++) {
            if (left < m && right < n) {
                arr[i] = nums1[left] <= nums2[right] ? nums1[left++] : nums2[right++];
            } else {
                if (left >= m) {
                    arr[i] = nums2[right++];
                } else {
                    arr[i] = nums1[left++];
                }
            }

        }
        System.arraycopy(arr, 0, nums1, 0, nums1.length);
    }


    /**
     * 27.移除元素
     **/
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int count = 0;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == val) {
                while (nums[right] == val && right > left) {
                    right--;
                }
                if (right > left) {
                    nums[left] = nums[right--];
                } else {
                    break;
                }
            }
            count++;
            left++;
        }
        return count;
    }


    /**
     * 26.删除有序数组中的重复项
     **/
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums.length;
        }
        int left = 0;
        int right = 1;
        while (right < nums.length) {
            while (right < nums.length && nums[left] == nums[right]) {
                right++;
            }
            if (right < nums.length) {
                nums[++left] = nums[right];
                right++;
            }
        }
        return left + 1;
    }


    //80. 删除有序数组中的重复项 II
    public int removeDuplicates_2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length <= 2) {
            return nums.length;
        }
        int left = 0;
        int right = left + 1;
        while (right < nums.length) {
            while (right < nums.length && nums[right] == nums[left] && left > 0 && nums[left - 1] == nums[left]) {
                right++;
            }
            if (right < nums.length) {
                nums[++left] = nums[right];
                right++;
            }
        }
        return left + 1;
    }


    /**
     * 169.多数元素
     */
    public int majorityElement(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }


    /***
     * 189.轮转数组
     * */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length <= 1 || k % nums.length == 0) {
            return;
        }
        k = k % nums.length;
        int[] arr = new int[nums.length + k];
        System.arraycopy(nums, 0, arr, k, nums.length);
        for (int i = k; i < arr.length; i++) {
            nums[i % nums.length] = arr[i];
        }
    }


    /**
     * 121. 买卖股票的最佳时机
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int preMin = prices[0];
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > preMin) {
                max = Math.max(max, prices[i] - preMin);
            } else {
                preMin = Math.min(preMin, prices[i]);
            }
        }
        return max;
    }

    /**
     * 122. 买卖股票的最佳时机 II
     */
    public int maxProfit_2(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[2][prices.length];

        dp[0][0] = 0;
        dp[1][0] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[0][i] = Math.max(prices[i] + dp[1][i - 1], dp[0][i - 1]);
            dp[1][i] = Math.max(dp[0][i - 1] - prices[i], dp[1][i - 1]);
        }
        return dp[0][prices.length - 1];
    }


    /**
     * 55. 跳跃游戏
     */
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }
        boolean[] dp = new boolean[nums.length];
        Arrays.fill(dp, false);
        dp[0] = true;
        int max = nums[0];
        for (int i = 1; i < dp.length; i++) {
            if (i <= max) {
                dp[i] = true;
                max = Math.max(max, i + nums[i]);
            }
        }
        return dp[dp.length - 1];
    }


    /**
     * 45.跳跃游戏II
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
        return dp[dp.length - 1];
    }


    /**
     * 274.H指数
     */
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int[] dp = new int[citations.length + 1];
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= dp.length) {
                dp[citations.length]++;
            } else {
                dp[citations[i]]++;
            }
        }
        int count = 0;
        for (int i = citations.length; i >= 0; i--) {
            count += dp[i];
            if (count >= i) {
                return i;
            }
        }
        return 0;

    }


    @Test
    public void hIndexTest() {
        int[] citations = {3, 0, 6, 1, 5};
        hIndex(citations);
    }


    /**
     * 380. O(1) 时间插入、删除和获取随机元素
     */
    class RandomizedSet {

        Map<Integer, Integer> map;
        List<Integer> list;
        Random random;

        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            map.put(val, list.size());
            list.add(list.size(), val);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            int index = map.get(val);
            int lastIndex = list.size() - 1;
            Integer last = list.get(lastIndex);
            list.set(index, last);
            map.put(last, index);
            list.remove(lastIndex);
            map.remove(val);
            return true;
        }

        public int getRandom() {
            return list.get(random.nextInt(list.size()));
        }
    }


    /**
     * 238. 除自身以外数组的乘积
     */
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0) {
            return nums;
        }

        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        left[0] = nums[0];
        right[nums.length - 1] = nums[nums.length - 1];
        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] * nums[i];
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                nums[i] = right[i + 1];
            } else if (i == nums.length - 1) {
                nums[i] = left[i - 1];
            } else {
                nums[i] = left[i - 1] * right[i + 1];
            }
        }
        return nums;
    }


    /**
     * 134.加油站
     **/
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return -1;
        }
        int min = 0;
        int index = 0;
        int sum = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += (gas[i] - cost[i]);
            if (sum < min) {
                min = sum;
                index = i + 1;
            }
        }
        return sum < 0 ? -1 : index;
    }


    /**
     * 42.接雨水
     **/
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int maxIndex = -1;
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] > max) {
                max = height[i];
                maxIndex = i;
            }
        }
        int sum = 0;
        int h = height[0];
        for (int i = 1; i < maxIndex; i++) {
            if (height[i] < h) {
                sum += (h - height[i]);
            } else {
                h = height[i];
            }
        }

        h = height[height.length - 1];
        for (int i = height.length - 2; i > maxIndex; i--) {
            if (h > height[i]) {
                sum += (h - height[i]);
            } else {
                h = height[i];
            }
        }
        return sum;
    }


    /**
     * 13.罗马数字转整数
     */
    public int romanToInt(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int num = 0;
        int left = 0;
        int right = 1;
        while (left < s.length()) {
            int nums1 = ramanToIntCase(s.substring(left, left + 1));
            if (right < s.length()) {
                int nums2 = ramanToIntCase(s.substring(right, right + 1));
                if (nums1 < nums2) {
                    num -= nums1;
                    num += nums2;
                    left++;
                } else {
                    num += nums1;
                }
            } else {
                num += nums1;
            }
            left++;
            right = left + 1;
        }
        return num;
    }

    public int ramanToIntCase(String s) {
        switch (s) {
            case "I" -> {
                return 1;
            }
            case "V" -> {
                return 5;
            }
            case "X" -> {
                return 10;
            }
            case "L" -> {
                return 50;
            }
            case "C" -> {
                return 100;
            }
            case "D" -> {
                return 500;
            }
            case "M" -> {
                return 1000;
            }
            default -> {
                return 0;
            }

        }
    }


    /**
     * 58.最后一个单词的长度
     */
    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        String[] split = s.split(" ");

        return split[split.length - 1].length();


    }


    /**
     * 14.最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while (index < strs[0].length()) {
            char c = strs[0].charAt(index);
            for (int i = 1; i < strs.length; i++) {
                if (index >= strs[i].length() || strs[i].charAt(index) != c) {
                    return prefix.toString();
                }
            }
            prefix.append(c);
            index++;
        }
        return prefix.toString();
    }


    /**
     * 151. 反转字符串中的单词
     */
    public String reverseWords(String s) {
        if (s == null || s.trim().length() == 0) {
            return "";
        }
        String[] split = s.trim().split(" ");

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = split.length - 1; i >= 0; i--) {
            if (split[i].trim().length() == 0) {
                continue;
            }
            stringBuilder.append(split[i].trim());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().trim();
    }


    /**
     * 双指针
     * **/


    /**
     * 125. 验证回文串
     */
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        StringBuilder str = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if ((aChar >= 'a' && aChar <= 'z') || (aChar >= 'A' && aChar <= 'Z') || (aChar >= '0' && aChar <= '9')) {
                str.append(aChar);
            }
        }
        String s1 = str.toString().toUpperCase();
        String s2 = str.reverse().toString().toUpperCase();
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void isPalindrome() {
        String str = "A man, a plan, a canal: Panama";


        isPalindrome(str);
    }

    /**
     * 392. 判断子序列
     **/
    public boolean isSubsequence(String s, String t) {
        if (s == null || t == null || s.length() > t.length()) {
            return false;
        }
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        int index1 = 0;
        int index2 = 0;

        while (index1 < chars1.length && index2 < chars2.length) {
            if (chars1[index1] == chars2[index2]) {
                index2++;
                index1++;
            } else {
                index2++;
            }
        }

        return index1 == chars1.length;

    }

    /**
     * 167. 两数之和 II - 输入有序数组
     */
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length <= 1) {
            return new int[]{-1, -1};
        }

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int number = numbers[right] + numbers[left];
            if (number > target) {
                right--;
            } else if (number < target) {
                left++;
            } else {
                return new int[]{left + 1, right + 1};
            }
        }
        return new int[]{-1, -1};
    }


    /***
     * 11.盛水最多的容器
     * */
    public int maxArea(int[] height) {
        if (height == null || height.length <= 1) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while (left < right) {
            max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return max;
    }


    /***
     * 15.三数之和
     *
     * 固定left，
     *
     * left 更新是要更新去重
     *
     * 每次更新 mid 和right
     * mid 和 right 更新也要去重
     * */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int left = 0;

        while (left < nums.length - 2) {
            if (nums[left] > 0 || (left > 0 && nums[left] == nums[left - 1])) {
                left++;
                continue;
            }
            int mid = left + 1;
            int right = nums.length - 1;
            while (mid < right) {
                int sum = nums[left] + nums[mid] + nums[right];

                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    mid++;
                } else {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[left]);
                    temp.add(nums[mid]);
                    temp.add(nums[right]);
                    res.add(temp);
                    while (mid < right && nums[mid] == nums[mid + 1]) {
                        mid++;
                    }
                    while (right > mid && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    mid++;
                    right--;
                }
            }
            left++;
        }
        return res;
    }


    /**
     * 209. 长度最小的子数组
     **/
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0 || target == 0) {
            return 0;
        }
        int left = 0;
        int right = 0;
        int min = Integer.MAX_VALUE;
        int sum = nums[0];

        while (left <= right && right < nums.length) {
            if (sum >= target) {
                min = Math.min(min, right - left + 1);
                sum -= nums[left];
                left++;
            } else {
                right++;
                if (right < nums.length) {
                    sum += nums[right];
                }
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }


    /**
     * 3.无重复字符的最长子串
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 1;
        Set<Character> set = new HashSet<>();
        set.add(chars[0]);
        int max = 1;
        while (left <= right && right < s.length()) {
            if (set.contains(chars[right])) {
                set.remove(chars[left]);
                left++;
            } else {
                set.add(chars[right]);
                max = Math.max(max, right - left + 1);
                right++;
            }
        }

        return max;
    }


    @Test
    public void integerTest() {
        Integer c = 200;
        Integer d = 200;
        Integer a = 100;
        Integer b = 100;
        int num = 128;
        log.info("{}", c == d);
        log.info("{}", a == b);
    }


    /**
     * LCR 053. 二叉搜索树中的中序后继
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        List<TreeNode> visited = new ArrayList<>();
        midOrder(root, visited);
        for (int i = 0; i < visited.size(); i++) {
            if (visited.get(i) == p) {
                return i + 1 >= visited.size() ? null : visited.get(i + 1);
            }
        }
        return null;
    }

    public void midOrder(TreeNode node, List<TreeNode> visited) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            midOrder(node.left, visited);
        }
        visited.add(node);
        if (node.right != null) {
            midOrder(node.right, visited);
        }

    }

    @Test
    public void treeTest() {
        TreeNode treeNode = new TreeNode(2);
        treeNode.left = new TreeNode(1);

    }


    /**
     * 70. 爬楼梯
     **/
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];

    }


    /**
     * 155.最小栈
     */
    class MinStack {

        Stack<Integer> stack;
        Integer min = Integer.MAX_VALUE;

        public MinStack() {
            stack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            min = Math.min(val, min);

        }

        public void pop() {
            Integer pop = stack.pop();
            if (Objects.equals(pop, min)) {
                min = Integer.MAX_VALUE;
                stack.forEach(num -> {
                    min = Math.min(num, min);
                });
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
     * 128. 最长连续序列
     **/
    public int longestConsecutive(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 1) {
            return nums.length;
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        int max = 1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int temp = 1;
            set.remove(num);
            while (set.contains(num + 1)) {
                temp++;
                num = num + 1;
                set.remove(num);
                max = Math.max(max, temp);
            }
            num = nums[i];
            while (set.contains(num - 1)) {
                temp++;
                num = num - 1;
                set.remove(num);
                max = Math.max(max, temp);
            }
        }
        return max;
    }


    /**
     * 53. 最大子数组和
     **/
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }
        return Arrays.stream(dp).max().getAsInt();

    }


    /***
     * 415. 字符串相加
     * */
    public String addStrings(String num1, String num2) {
        if (num1 == null || num1.length() == 0) {
            return num2;
        }
        if (num2 == null || num2.length() == 0) {
            return num1;
        }
        char[] chars1 = num1.toCharArray();

        char[] chars2 = num2.toCharArray();

        int index1 = chars1.length - 1;

        int index2 = chars2.length - 1;
        StringBuilder builder = new StringBuilder();
        int bit = 0;
        while (index1 >= 0 && index2 >= 0) {
            int c1 = charToInt(chars1[index1]);
            int c2 = charToInt(chars2[index2]);
            int sum = c1 + c2 + bit;
            bit = sum / 10;
            builder.append(sum % 10);
            index1--;
            index2--;
        }
        while (index1 >= 0) {
            int c1 = charToInt(chars1[index1]);
            int sum = c1 + bit;
            bit = sum / 10;
            builder.append(sum % 10);
            index1--;
        }
        while (index2 >= 0) {
            int c2 = charToInt(chars2[index2]);
            int sum = c2 + bit;
            bit = sum / 10;
            builder.append(sum % 10);
            index2--;
        }
        if (bit != 0) {
            builder.append(bit);
        }

        return builder.reverse().toString();
    }

    public int charToInt(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                return 0;

        }
    }


    /**
     * 54. 螺旋矩阵
     **/
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (left < right && top < bottom) {
            for (int i = left; i < right; i++) {
                res.add(matrix[top][i]);
            }
            for (int i = top; i < bottom; i++) {
                res.add(matrix[i][right]);
            }
            for (int i = right; i > left; i--) {
                res.add(matrix[bottom][i]);
            }
            for (int i = bottom; i > top; i--) {
                res.add(matrix[i][left]);
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        if (left <= right && top <= bottom) {
            if (left == right) {
                for (int i = top; i <= bottom; i++) {
                    res.add(matrix[i][left]);
                }
            } else {
                for (int i = left; i <= right; i++) {
                    res.add(matrix[top][i]);
                }
            }
        }
        return res;
    }


    /**
     * 110. 平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        int left = height(root.left);
        int right = height(root.right);
        return Math.abs(left - right) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    public int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(height(node.left), height(node.right)) + 1;
    }


    /**
     * 98.验证二叉搜索树
     **/
    public boolean isValidBST(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return isValidBST2(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST2(TreeNode node, Long min, Long max) {
        if (node == null) {
            return true;
        }
        if (node.val <= min || node.val >= max) {
            return false;
        }
        return isValidBST2(node.left, min, (long) node.val) && isValidBST2(node.right, (long) node.val, max);
    }


    /**
     * 146. LRU 缓存
     **/
    class LRUCache {

        private final Integer capacity;
        private final Map<Integer, LRUNode> values;

        private final LRUNode head;
        private final LRUNode tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.values = new HashMap<>();
            this.head = new LRUNode();
            this.tail = new LRUNode();
            this.head.setNext(tail);
            this.tail.setPre(head);
        }

        public int get(int key) {
            if (!values.containsKey(key)) {
                return -1;
            }
            LRUNode node = values.get(key);
            remove(node);
            addHead(node);
            return node.getValue();
        }

        public void put(int key, int value) {
            if (values.containsKey(key)) {
                LRUNode node = values.get(key);
                node.setValue(value);
                remove(node);
                addHead(node);
                return;
            }
            if (values.size() == capacity) {
                removeTail();
            }
            LRUNode node = new LRUNode();
            node.setValue(value);
            node.setKey(key);
            addHead(node);
            values.put(key, node);

        }

        public void addHead(LRUNode node) {
            LRUNode second = head.getNext();
            head.setNext(node);
            node.setPre(head);
            node.setNext(second);
            second.setPre(node);
        }

        public void remove(LRUNode node) {
            LRUNode pre = node.getPre();
            LRUNode next = node.getNext();
            pre.setNext(next);
            next.setPre(pre);
            node.setNext(null);
            node.setPre(null);
        }

        public void removeTail() {
            LRUNode node = tail.getPre();
            LRUNode pre = node.getPre();
            pre.setNext(tail);
            tail.setPre(pre);
            node.setNext(null);
            node.setPre(null);
            values.remove(node.getKey());
        }

        public static class LRUNode {
            private LRUNode pre;
            private LRUNode next;
            private Integer key;
            private Integer value;


            public Integer getKey() {
                return key;
            }

            public void setKey(Integer key) {
                this.key = key;
            }

            public LRUNode getPre() {
                return pre;
            }

            public LRUNode getNext() {
                return next;
            }

            public Integer getValue() {
                return value;
            }

            public void setPre(LRUNode pre) {
                this.pre = pre;
            }

            public void setNext(LRUNode next) {
                this.next = next;
            }

            public void setValue(Integer value) {
                this.value = value;
            }
        }

    }


    /**
     * 22. 括号生成
     **/
    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        List<String> parens = new ArrayList<>();

        generate(new StringBuilder(), 0, 0, n, parens);
        return parens;
    }

    public void generate(StringBuilder stringBuilder, int left, int right, int n, List<String> parens) {
        if (left > n || right > n || left < right) {
            return;
        }
        if (left == n && right == n) {
            parens.add(stringBuilder.toString());
            return;
        }
        stringBuilder.append("(");
        generate(stringBuilder, left + 1, right, n, parens);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(")");
        generate(stringBuilder, left, right + 1, n, parens);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }


    /**
     * 2.两数相加
     **/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode pre = new ListNode();
        ListNode point = pre;
        int bit = 0;
        while (l2 != null && l1 != null) {
            int sum = l2.val + l1.val + bit;
            bit = sum / 10;
            point.next = new ListNode(sum % 10);
            l2 = l2.next;
            l1 = l1.next;
            point = point.next;
        }
        while (l1 != null) {
            int sum = l1.val + bit;
            bit = sum / 10;
            point.next = new ListNode(sum % 10);
            point = point.next;
            l1 = l1.next;
        }

        while (l2 != null) {
            int sum = l2.val + bit;
            bit = sum / 10;
            point.next = new ListNode(sum % 10);
            point = point.next;
            l2 = l2.next;
        }
        if (bit != 0) {
            point.next = new ListNode(bit);
        }
        return pre.next;
    }


    /**
     * LCR 025. 两数相加 II
     */
    public ListNode addTwoNumbers_2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode pre = new ListNode();

        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        while (l1 != null) {
            stack1.push(l1);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2);
            l2 = l2.next;
        }
        int bit = 0;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            ListNode node1 = stack1.pop();
            ListNode node2 = stack2.pop();
            int sum = bit + node2.val + node1.val;
            bit = sum / 10;
            ListNode node = new ListNode(sum % 10);
            addHeader(pre, node);
        }
        while (!stack1.isEmpty()) {
            ListNode node1 = stack1.pop();
            int sum = bit + node1.val;
            bit = sum / 10;
            ListNode node = new ListNode(sum % 10);
            addHeader(pre, node);
        }
        while (!stack2.isEmpty()) {
            ListNode node2 = stack2.pop();
            int sum = bit + node2.val;
            bit = sum / 10;
            ListNode node = new ListNode(sum % 10);
            addHeader(pre, node);
        }
        if (bit != 0) {
            ListNode node = new ListNode(bit);
            addHeader(pre, node);
        }
        return pre.next;
    }

    public void addHeader(ListNode pre, ListNode node) {
        ListNode next = pre.next;
        pre.next = node;
        node.next = next;
    }


    /**
     * 56. 合并区间
     **/
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparing(ints -> ints[0]));
        List<int[]> integers = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] nums = intervals[i];
            if (right < nums[0]) {
                integers.add(new int[]{left, right});
                left = nums[0];
                right = nums[1];
            } else {
                left = Math.min(left, nums[0]);
                right = Math.max(right, nums[1]);
            }
        }
        integers.add(new int[]{left, right});
        return integers.toArray(int[][]::new);
    }


    /***
     * 3.无重复字符的最长字串
     * */
    public int lengthOfLongestSubstring_2(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }
        char[] chars = s.toCharArray();
        Set<Character> window = new HashSet<>();
        int left = 0;
        int right = 1;
        int max = 1;
        window.add(chars[left]);
        while (right < s.length()) {
            while (window.contains(chars[right])) {
                window.remove(chars[left++]);
            }
            window.add(chars[right++]);
            max = Math.max(max, window.size());
        }
        return max;
    }


    /**
     * 21. 合并两个有序链表
     **/
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode res = new ListNode();
        ListNode p = res;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                p.next = list1;
                p = p.next;
                list1 = list1.next;
                p.next = null;
            } else {
                p.next = list2;
                p = p.next;
                list2 = list2.next;
                p.next = null;
            }
        }
        while (list1 != null) {
            p.next = list1;
            p = p.next;
            list1 = list1.next;
            p.next = null;
        }
        while (list2 != null) {
            p.next = list2;
            p = p.next;
            list2 = list2.next;
            p.next = null;
        }
        return res.next;
    }


    /**
     * 86. 分隔链表
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        ListNode next = new ListNode(-1);
        ListNode p = head;
        ListNode p1 = pre;
        ListNode p2 = next;
        while (p != null) {
            if (p.val < x) {
                p1.next = p;
                p1 = p1.next;
                p = p.next;
                p1.next = null;
            } else {
                p2.next = p;
                p2 = p2.next;
                p = p.next;
                p2.next = null;
            }
        }
        p1.next = next.next;
        return pre.next;
    }


    /***
     * 23. 合并 K 个升序链表
     * */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }

        ListNode res = new ListNode(-1);
        ListNode p = res;

        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));

        for (ListNode list : lists) {
            if (list != null) {
                priorityQueue.add(list);
            }
        }
        while (!priorityQueue.isEmpty()) {
            ListNode poll = priorityQueue.poll();
            p.next = poll;
            p = p.next;
            if (poll.next != null) {
                priorityQueue.add(poll.next);
            }
            p.next = null;
        }
        return res.next;
    }


    /**
     *
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        Map<Character, Integer> map = new HashMap<>();
        char[] ss = s.toCharArray();
        char[] ts = t.toCharArray();
        for (char c : ts) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        int left = 0;
        int right = 0;
        String res = s;
        boolean init = false;
        while (right < s.length()) {
            if (map.containsKey(ss[right])) {
                map.put(ss[right], map.get(ss[right]) - 1);
            }
            right++;
            if (map.entrySet().stream().allMatch(entry -> entry.getValue() <= 0)) {
                while (left < right) {
                    if (map.containsKey(ss[left])) {
                        if (map.get(ss[left]) == 0) {
                            break;
                        } else {
                            map.put(ss[left], map.get(ss[left]) + 1);
                        }
                    }
                    left++;
                }
                init = true;
                res = s.substring(left, right).length() < res.length() ? s.substring(left, right) : res;
            }
        }
        return init ? res : "";
    }


    /**
     * 567. 字符串的排列
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        }
        char[] cs1 = s1.toCharArray();
        char[] cs2 = s2.toCharArray();
        Map<Character, Integer> needs = new HashMap<>();
        Map<Character, Integer> windows = new HashMap<>();
        for (char c : cs1) {
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }
        int left = 0;
        int right = 0;
        int valid = 0;
        while (right < cs2.length) {
            if (needs.containsKey(cs2[right])) {
                windows.put(cs2[right], windows.getOrDefault(cs2[right], 0) + 1);
                if (windows.get(cs2[right]).equals(needs.get(cs2[right]))) {
                    valid++;
                }
            }
            if (right - left + 1 == s1.length()) {
                if (valid == needs.size()) {
                    return true;
                }
                if (needs.containsKey(cs2[left])) {
                    if (windows.get(cs2[left]).equals(needs.get(cs2[left]))) {
                        valid--;
                    }
                    windows.put(cs2[left], windows.get(cs2[left]) - 1);
                }
                left++;
            }
            right++;
        }
        return false;
    }

    public List<Integer> findAnagrams(String s, String p) {
        if (s == null || p == null || s.length() < p.length()) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        char[] ps = p.toCharArray();
        char[] ss = s.toCharArray();
        Map<Character, Integer> needs = new HashMap<>();
        Map<Character, Integer> windows = new HashMap<>();
        for (char c : ps) {
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }

        int left = 0;
        int right = 0;
        int valid = 0;
        while (right < ss.length) {
            if (needs.containsKey(ss[right])) {
                windows.put(ss[right], windows.getOrDefault(ss[right], 0) + 1);
                if (needs.get(ss[right]).equals(windows.get(ss[right]))) {
                    valid++;
                }
            }
            if (right - left + 1 == ps.length) {
                if (valid == needs.size()) {
                    res.add(left);
                }
                if (windows.containsKey(ss[left])) {
                    if (needs.get(ss[left]).equals(windows.get(ss[left]))) {
                        valid--;
                    }
                    windows.put(ss[left], windows.get(ss[left]) - 1);
                }
                left++;
            }
            right++;
        }
        return res;
    }


    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        return -1;
    }


    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid - 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        int start = right + 1;
        if (start >= nums.length || nums[start] != target) {
            return new int[]{-1, -1};
        }
        int end = start;
        for (int i = start; i < nums.length; i++) {
            if (nums[i] == target) {
                end = i;
            } else {
                break;
            }
        }

        return new int[]{start, end};
    }


    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return amount;
        }
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int coin : coins) {
                if (i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }


    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, new LinkedList<>(), nums, new HashSet<>());
        return res;
    }

    public void backtrack(List<List<Integer>> res, LinkedList<Integer> temp, int[] nums, Set<Integer> used) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used.contains(i)) {
                continue;
            }
            temp.add(nums[i]);
            used.add(i);
            backtrack(res, temp, nums, used);
            temp.removeLast();
            used.remove(i);
        }
    }


    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }


    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        midTraversal(root, res);
        return res;
    }

    public void midTraversal(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        res.add(node.val);
        midTraversal(node.left, res);
        midTraversal(node.right, res);
    }

    int maxDept = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }
        maxDept(root);
        return maxDept;
    }

    public int maxDept(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        int left = maxDept(node.left);
        int right = maxDept(node.right);
        maxDept = Math.max(maxDept, left + right);
        return Math.max(left, right) + 1;
    }


    public List<List<Integer>> combinationSum3(int k, int n) {
        if (n == 0 || k == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        boolean[] used = new boolean[10];
        Arrays.fill(used, false);
        backtrack(k, n, used, 1, new LinkedList<>(), res);
        return res;
    }

    public void backtrack(int count, int sum, boolean[] used, int start, LinkedList<Integer> temp, List<List<Integer>> res) {
        if (count == temp.size() && sum == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }
        if (temp.size() >= count || sum <= 0 || start >= used.length) {
            return;
        }
        for (int i = start; i < used.length; i++) {
            temp.add(i);
            used[i] = true;
            backtrack(count, sum - i, used, i + 1, temp, res);
            temp.removeLast();
            used[i] = false;
        }
    }


    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (target == 0 || candidates == null || candidates.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, candidates, 0, target, 0, new LinkedList<>());
        return res;
    }

    public void backtrack(List<List<Integer>> res, int[] candidates, int sum, int target, int start, LinkedList<Integer> temp) {
        if (sum == target) {
            res.add(new ArrayList<>(temp));
            return;
        }
        if (start >= candidates.length || sum > target) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            temp.add(candidates[i]);
            backtrack(res, candidates, sum + candidates[i], target, i, temp);
            temp.removeLast();
        }
    }


    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0 || target == 0) {
            return new ArrayList<>();
        }
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, new LinkedList<>(), candidates, 0, target, 0);

        return res;

    }

    public void backtrack(List<List<Integer>> res, LinkedList<Integer> temp, int[] candidates, int sum, int target, int start) {
        if (sum == target) {
            res.add(new ArrayList<>(temp));
            return;
        }
        if (sum > target || start >= candidates.length) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            temp.add(candidates[i]);
            backtrack(res, temp, candidates, sum + candidates[i], target, i + 1);
            temp.removeLast();
        }
    }


    /**
     *
     */
    public List<List<Integer>> permute_1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, new LinkedList<>(), res, used);
        return res;
    }

    public void backtrack(int[] nums, LinkedList<Integer> temp, List<List<Integer>> res, boolean[] used) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            temp.add(nums[i]);
            used[i] = true;
            backtrack(nums, temp, res, used);
            temp.removeLast();
            used[i] = false;
        }

    }

    public List<List<Integer>> permuteUnique(int[] nums) {

        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        Arrays.sort(nums);
        backtrackPermuteUnique(nums, new LinkedList<>(), res, used);
        return res;
    }

    public void backtrackPermuteUnique(int[] nums, LinkedList<Integer> temp, List<List<Integer>> res, boolean[] used) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }
            temp.add(nums[i]);
            used[i] = true;
            backtrackPermuteUnique(nums, temp, res, used);
            temp.removeLast();
            used[i] = false;
        }

    }


    public List<List<Integer>> combine(int n, int k) {
        if (n <= 0 || k <= 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        backtrack(n, k, 1, new LinkedList<>(), res);
        return res;
    }

    public void backtrack(int n, int k, int start, LinkedList<Integer> temp, List<List<Integer>> res) {
        if (temp.size() == k) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i <= n; i++) {
            temp.add(i);
            backtrack(n, k, i + 1, temp, res);
            temp.removeLast();
        }
    }


    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        subBacktrack(res, new LinkedList<>(), nums, 0);
        return res;
    }

    public void subBacktrack(List<List<Integer>> res, LinkedList<Integer> temp, int[] nums, int start) {
        if (start == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        temp.add(nums[start]);
        subBacktrack(res, temp, nums, start + 1);
        temp.removeLast();
        subBacktrack(res, temp, nums, start + 1);
    }


    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        subDupBacktrack(res, new LinkedList<>(), nums, 0);
        return res;
    }

    public void subDupBacktrack(List<List<Integer>> res, LinkedList<Integer> temp, int[] nums, int start) {
        res.add(new ArrayList<>(temp));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            temp.add(nums[i]);
            subDupBacktrack(res, temp, nums, i + 1);
            temp.removeLast();
        }
    }


    public List<List<Integer>> permuteUnique_1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        permuteUniqueBacktrack(res, new LinkedList<>(), nums, new boolean[nums.length]);
        return res;
    }

    public void permuteUniqueBacktrack(List<List<Integer>> res, LinkedList<Integer> temp, int[] nums, boolean[] used) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            temp.add(nums[i]);
            used[i] = true;
            permuteUniqueBacktrack(res, temp, nums, used);
            temp.removeLast();
            used[i] = false;
        }
    }

    public List<List<Integer>> combinationSum2_2(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        combinationBacktrack(res, new LinkedList<>(), candidates, target, 0, new boolean[candidates.length], 0);
        return res;
    }

    public void combinationBacktrack(List<List<Integer>> res, LinkedList<Integer> temp, int[] nums, int target, int sum, boolean[] used, int start) {
        if (target == sum) {
            res.add(new ArrayList<>(temp));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = start; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            temp.add(nums[i]);
            used[i] = true;
            combinationBacktrack(res, temp, nums, target, sum + nums[i], used, i + 1);
            temp.removeLast();
            used[i] = false;
        }
    }


    public boolean canJump_2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }
        boolean[] dp = new boolean[nums.length];
        Arrays.fill(dp, false);
        dp[0] = true;
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (dp[i] || i <= max) {
                dp[i] = true;
                max = Math.max(max, i + nums[i]);
            }
        }
        return dp[nums.length - 1];
    }

    public int jump_2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= nums[i]; j++) {
                if (j + i < nums.length) {
                    dp[j + i] = Math.min(dp[j + i], dp[i] + 1);
                }

            }
        }
        return dp[dp.length - 1];
    }


    public List<String> generateParenthesis_1(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        backtrack(res, new StringBuilder(), 0, 0, n);
        return res;
    }

    public void backtrack(List<String> res, StringBuilder temp, int left, int right, int n) {
        if (left == n && right == n) {
            res.add(temp.toString());
            return;
        }
        if (left > n || right > n || right > left) {
            return;
        }
        temp.append("(");
        backtrack(res, temp, left + 1, right, n);
        temp.deleteCharAt(temp.length() - 1);
        temp.append(")");
        backtrack(res, temp, left, right + 1, n);
        temp.deleteCharAt(temp.length() - 1);
    }


//    public List<List<Integer>> combination(int n, int k) {
//        if (n <= 0 || k <= 0 || k > n) {
//            return new ArrayList<>();
//        }
//    }
}
