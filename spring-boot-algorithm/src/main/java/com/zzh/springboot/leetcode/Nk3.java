package com.zzh.springboot.leetcode;

import com.zzh.springboot.algorithm.ListNode;
import com.zzh.springboot.algorithm.TreeNode;

import java.util.*;

/**
 * @Description:
 * @Author: zzh
 * @Create 2026/9/8 14:03
 */
public class Nk3 {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        String line = in.nextLine();
//
//        String newLine = line.replace("0x", "");
//        int length = newLine.length();
//
//        int n = 1;
//        int sum = 0;
//        for (int i = length - 1; i >= 0; i--) {
//            sum += char2num(newLine.charAt(i)) * n;
//            n *= 16;
//        }
//        System.out.println(sum);

        minWindow("cabwefgewcwaefgcf", "cae");
    }

    public static int char2num(Character c) {
        if ('0' <= c && c <= '9') {
            return Integer.parseInt(String.valueOf(c));
        }
        return c - 'A' + 10;
    }


    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) {
            return s.length();
        }


        int left = 0;
        Map<Character, Integer> charMap = new HashMap<>();
        int max = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (charMap.containsKey(c)) {
                left = Math.max(left, charMap.get(c) + 1);
            }
            charMap.put(c, right);
            max = Math.max(max, right - left + 1);

        }

        return max;

    }


    public int[] twoSum(int[] nums, int target) {
        if (nums.length <= 1) {
            return new int[]{-1, -1};
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};

    }

    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, (arr1, arr2) -> Integer.compare(arr1[0], arr2[0]));
        List<int[]> res = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (right < interval[0]) {
                res.add(new int[]{left, right});
                left = interval[0];
                right = interval[1];
            } else {
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }
        res.add(new int[]{left, right});
        return res.toArray(new int[][]{});
    }


    public static String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }
        if (s.equals(t)) {
            return s;
        }
        int[] need = new int['z' - 'A' + 1];
        Set<Character> sets = new HashSet<>();
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i) - 'A']++;
            sets.add(t.charAt(i));
        }
        int left = 0;
        int min = s.length();
        String res = "";
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (sets.contains(c)) {
                need[c - 'A']--;
                while (needStr(need)) {
                    if (right - left + 1 <= min) {
                        res = s.substring(left, right + 1);
                        min = res.length();
                    }
                    if (sets.contains(s.charAt(left))) {
                        need[s.charAt(left) - 'A']++;
                    }
                    left++;
                }
            }
        }

        return res;
    }

    public static boolean needStr(int[] need) {
        for (int i = 0; i < need.length; i++) {
            if (need[i] > 0) {
                return false;
            }
        }
        return true;
    }

    public int singleNumber(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        Arrays.sort(nums);
        int left = 0;
        int right = 1;
        while (right < nums.length) {
            if (nums[right] == nums[left]) {
                left += 3;
                right = left + 1;
            } else {
                return nums[left];
            }
        }
        return nums[left];
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        boolean[][] booleans = new boolean[grid.length][grid[0].length];
        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1' && !booleans[i][j]) {
                    num += 1;
                    fill(grid, booleans, i, j);
                }
            }
        }

        return num;
    }

    public void fill(char[][] gird, boolean[][] booleans, int i, int j) {
        if (i < 0 || j < 0 || i >= gird.length || j >= gird[0].length) {
            return;
        }
        if (gird[i][j] == '1' && !booleans[i][j]) {
            booleans[i][j] = true;
            fill(gird, booleans, i - 1, j);
            fill(gird, booleans, i + 1, j);
            fill(gird, booleans, i, j - 1);
            fill(gird, booleans, i, j + 1);
        }


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
            if (text2.charAt(0) == text1.charAt(i)) {
                dp[i][0] = 1;
            } else {
                dp[i][0] = dp[i - 1][0];
            }
        }
        for (int j = 1; j < dp[0].length; j++) {
            if (text1.charAt(0) == text2.charAt(j)) {
                dp[0][j] = 1;
            } else {
                dp[0][j] = dp[0][j - 1];
            }
        }


        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1] + 1);
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }

            }
        }

        return dp[dp.length - 1][dp[0].length - 1];

    }

    public int calculateDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(calculateDepth(root.left), calculateDepth(root.right)) + 1;

    }


    public int findLengthOfLCIS(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        int left = 0;
        int right = 1;
        int max = 1;
        while (right < nums.length) {
            if (nums[right] > nums[right - 1]) {
                max = Math.max(max, right - left + 1);
            } else {
                left = right;
            }
            right++;
        }

        return max;

    }

    public String longestPalindrome(String s) {
        if (s.length() <= 1) {
            return s;
        }

        int index = 0;
        String res = String.valueOf(s.charAt(index));

        while (index < s.length()) {
            int left = index - 1;
            int right = index + 1;

            while (left >= 0 && right < s.length()) {
                if (s.charAt(left) == s.charAt(right)) {
                    String substring = s.substring(left, right + 1);
                    if (substring.length() > res.length()) {
                        res = substring;
                    }
                    left--;
                    right++;
                } else {
                    break;
                }
            }
            left = index;
            right = index + 1;
            while (left >= 0 && right < s.length()) {
                if (s.charAt(left) == s.charAt(right)) {
                    String substring = s.substring(left, right + 1);
                    if (substring.length() > res.length()) {
                        res = substring;
                    }
                    left--;
                    right++;
                } else {
                    break;
                }
            }

            index++;

        }

        return res;


    }

    public boolean checkSymmetricTree(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return true;
        }
        return check2(root.left, root.right);
    }

    public boolean check2(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val && check2(left.left, right.right) && check2(left.right, right.left);
    }


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();

        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = deque.poll();
                if (poll != null) {
                    list.add(poll.val);
                    if (poll.left != null) {
                        deque.addLast(poll.left);
                    }
                    if (poll.right != null) {
                        deque.addLast(poll.right);
                    }
                }


            }
            if (res.size() % 2 != 0) {
                Collections.reverse(list);
            }
            res.add(list);
        }
        return res;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        if (headA == null || headB == null) {
            return null;
        }

        ListNode l1 = headA;
        ListNode l2 = headB;
        while (l2 != l1) {
            l1 = l1 == null ? headA : l1.next;
            l2 = l2 == null ? headB : l2.next;
        }
        return l1;

    }

    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }
        Arrays.sort(coins);
        if (coins[0] > amount) {
            return -1;
        }
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i < dp.length; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i - coin] + 1, dp[i]);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];

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


    public void lengthOfLIS(int[] nums) {

        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int[] val = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] sym = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            while (num >= val[i]) {
                num -= val[i];
                sb.append(sym[i]);
            }
        }
        System.out.println(sb);

    }

    public void popstack(){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] t = new int[n];
        for (int i = 0; i < n; i++) t[i] = sc.nextInt();
        int[] ans = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && t[i] > t[st.peek()]) {
                int idx = st.pop();
                ans[idx] = i - idx;
            }
            st.push(i);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(ans[i]);
        }
        System.out.println(sb);
    }
}
