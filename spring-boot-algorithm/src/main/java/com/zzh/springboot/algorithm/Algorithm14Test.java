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
public class Algorithm14Test {


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





}
