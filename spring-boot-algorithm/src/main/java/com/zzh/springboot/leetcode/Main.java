package com.zzh.springboot.leetcode;

import com.zzh.springboot.algorithm.ListNode;
import com.zzh.springboot.algorithm.TreeNode;

import java.util.*;

public class Main {
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> res = new ArrayList<>();
        for (int[] cur : intervals) {
            if (res.isEmpty() || res.get(res.size() - 1)[1] < cur[0]) {
                res.add(cur);
            } else {
                res.get(res.size() - 1)[1] = Math.max(res.get(res.size() - 1)[1], cur[1]);
            }
        }
        return res.toArray(new int[0][]);
    }


    public static void main(String[] args) {
        String str = "abbccaabbc";
        int left = 0;
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int right = 0; right < str.length(); right++) {

            char c = str.charAt(right);
            if (map.containsKey(c)) {
                left = Math.max(map.get(c) + 1, left);
            }
            map.put(c, right);
            max = Math.max(max, right - left + 1);

        }

        System.out.println(max);

        minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});

    }

    public int[] twoSum(int[] numbers, int target) {

        if (numbers.length <= 1) {
            return new int[]{-1, -1};
        }
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                return new int[]{left + 1, right + 1};
            }
        }

        return new int[]{-1, -1};

    }


    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }
        dfs(nums, new ArrayList<>(), new boolean[nums.length], res);
        return res;
    }

    public void dfs(int[] nums, List<Integer> path, boolean[] used, List<List<Integer>> res) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            path.add(nums[i]);
            dfs(nums, path, used, res);
            used[i] = false;
            path.remove(path.size() - 1);
        }

    }

    public ListNode reverseList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        ListNode cur = head;
        ListNode next = cur.next;

        while (next != null) {

            ListNode node = pre.next;
            pre.next = cur;
            cur.next = node;

            cur = next;
            next = cur.next;
        }
        ListNode node = pre.next;
        pre.next = cur;
        cur.next = node;

        return pre.next;
    }


    public int[][] merge1(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, (arr1, arr2) -> Integer.compare(arr1[0], arr2[0]));
        List<int[]> res = new ArrayList<>();
        int[] cur = new int[]{intervals[0][0], intervals[0][1]};

        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (cur[1] < interval[0]) {
                res.add(new int[]{cur[0], cur[1]});
                cur[0] = interval[0];
                cur[1] = interval[1];
            } else {
                cur[0] = Math.min(cur[0], interval[0]);
                cur[1] = Math.max(cur[1], interval[1]);
            }
        }
        res.add(new int[]{cur[0], cur[1]});
        return res.toArray(new int[res.size()][]);
    }


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


    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];

    }

    public int coinChange(int[] coins, int amount) {

        if (coins.length == 0) {
            return -1;
        }
        if (amount <= 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] > i) {
                    continue;
                }
                if (i - coins[j] == 0) {
                    dp[i] = 1;
                }
                if (i - coins[j] > 0 && dp[i - coins[j]] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i - coins[j]] + 1, dp[i]);
                }
            }
        }


        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];

    }

    public int maxArea(int[] height) {

        if (height == null || height.length <= 1) {
            return 0;
        }

        int left = 0;

        int max = 0;

        int right = height.length - 1;


        while (right > left) {
            max = Math.max((right - left) * Math.min(height[left], height[right]), max);


            if (height[left] >= height[right]) {
                right--;
            } else {
                left++;
            }

        }


        return max;

    }

    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                Character peek = stack.peek();

                if ((c == ')' && peek == '(') || (c == '}' && peek == '{') || c == ']' && peek == '[') {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();

    }


    public static int minSubArrayLen(int target, int[] nums) {

        int left = 0;
        int min = Integer.MAX_VALUE;
        int right = 0;
        int sum = 0;
        while (right < nums.length) {

            sum += nums[right];

            while (sum >= target && left <= right) {
                min = Math.min(min, right - left + 1);
                sum -= nums[left];
                left++;
            }
            right++;

        }


        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public int subarraySum(int[] nums, int k) {

        Map<Integer, Integer> preMap = new HashMap<>();

        int preSum = 0;
        int count = 0;
        preMap.put(0, 1);
        for (int i = 0; i < nums.length; i++) {

            preSum += nums[i];

            if (preMap.containsKey(preSum - k)) {
                count += preMap.get(preSum - k);
            }
            preMap.put(preSum, preMap.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }


    public int search(int[] nums, int target) {

        if (nums.length == 0) {
            return -1;
        }

        return binarySearch(nums, target, 0, nums.length - 1);
    }

    public int binarySearch(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;

        if (target == nums[mid]) {
            return mid;
        }

        if (nums[0] <= nums[mid]) {
            if (nums[0] <= target && nums[mid] > target) {
                return binarySearch(nums, target, left, mid - 1);
            } else {
                return binarySearch(nums, target, mid + 1, right);
            }
        } else {
            if (nums[mid] < target && nums[nums.length - 1] >= target) {
                return binarySearch(nums, target, mid + 1, right);
            } else {
                return binarySearch(nums, target, left, mid - 1);
            }
        }

    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];

        Stack<Integer> stack = new Stack<>();
        LinkedList<Integer> indexList = new LinkedList<>();

        for (int i = 0; i < temperatures.length; i++) {
            int num = temperatures[i];

            while (!stack.isEmpty() && stack.peek() < num) {
                stack.pop();
                Integer index = indexList.removeLast();
                res[index] = i - index;
            }
            indexList.add(i);
            stack.push(num);
        }

        return res;

    }


    public List<String> generateParenthesis(int n) {

        if (n < 0) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        backGenerate(res, new StringBuilder(), n, 0, 0);
        return res;
    }

    public void backGenerate(List<String> res, StringBuilder path, int n, int left, int right) {

        if (left == n && right == n) {
            res.add(path.toString());
            return;
        }
        if (left > n || right > n || right > left) {
            return;
        }
        path.append("(");
        backGenerate(res, path, n, left + 1, right);
        path.deleteCharAt(path.length() - 1);
        path.append(")");
        backGenerate(res, path, n, left, right + 1);
        path.deleteCharAt(path.length() - 1);
    }

}
