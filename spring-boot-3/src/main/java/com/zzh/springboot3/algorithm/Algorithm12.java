package com.zzh.springboot3.algorithm;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description: 面试经典150题
 * @Author: zzh
 * @Crete 2024/8/19 09:55
 */
public class Algorithm12 {

    /**
     * 45. 跳跃游戏 II
     */
    public int jump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int[] arr = new int[nums.length];
        Arrays.fill(arr, -1);
        arr[0] = 0;
        int cur = 0;
        int next = nums[0];
        for (int i = 1; i < arr.length; i++) {
            if (i <= cur) {
                arr[i] = arr[i - 1];
            } else {
                cur = next;
                arr[i] = arr[i - 1] + 1;
            }
            next = Math.max(next, nums[i] + i);
        }
        return arr[arr.length - 1];
    }

    @Test
    public void testJump() {
        int[] nums = {1, 2};

        jump(nums);
    }


//    /**
//     * 135.分发糖果
//     **/
//    public int candy(int[] ratings) {
//
//
//    }


    /**
     * 42.接雨水
     */
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int sum = 0;
        int maxIndex = 0;
        for (int i = 1; i < height.length; i++) {
            if (height[i] > height[maxIndex]) {
                maxIndex = i;
            }
        }
        int cur = 0;
        for (int i = 1; i < maxIndex; i++) {
            if (height[i] >= height[cur]) {
                cur = i;
            } else {
                sum += (height[cur] - height[i]);
            }
        }

        cur = height.length - 1;
        for (int i = cur - 1; i > maxIndex; i--) {
            if (height[i] >= height[cur]) {
                cur = i;
            } else {
                sum += (height[cur] - height[i]);
            }
        }
        return sum;
    }


    /**
     * 13.罗马数字转整数
     */
    public int romanToInt(String s) {
        int sum = 0;
        int pre = 3999;
        for (int i = 0; i < s.length(); i++) {
            int cur = getNumByChar(s.charAt(i));
            if (cur <= pre) {
                pre = cur;
                sum += cur;
            } else {
                sum = sum - pre * 2 + cur;
                pre = cur - pre;
            }
        }
        return sum;
    }

    public int getNumByChar(Character character) {
        return switch (character) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }


    /**
     * 12.整数转罗马数字
     */
    public String intToRoman(int num) {
        int bit = 1;
        StringBuilder res = new StringBuilder();
        while (bit <= 3999) {
            int cur = (num % (bit * 10)) / bit;
            StringBuilder app = new StringBuilder();
            if (cur == 4 || cur == 9) {
                app.append(getCharByNum(bit));
                app.append(getCharByNum((cur + 1) * bit));
            } else {
                if (cur >= 5) {
                    app.append(getCharByNum(5 * bit));
                }
                for (int i = 0; i < cur % 5; i++) {
                    app.append(getCharByNum(bit));
                }
            }
            res.insert(0, app);
            bit *= 10;
        }

        return res.toString();
    }

    public Character getCharByNum(int num) {
        return switch (num) {
            case 1 -> 'I';
            case 5 -> 'V';
            case 10 -> 'X';
            case 50 -> 'L';
            case 100 -> 'C';
            case 500 -> 'D';
            case 1000 -> 'M';
            default -> throw new IllegalStateException("Unexpected value: " + num);
        };
    }


    /**
     * 58.最后一个单词的长度
     **/
    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        String trim = s.trim();
        String[] split = trim.split(" ");
        return split[split.length - 1].length();
    }


    /***
     * 14.最长公共前缀
     * */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while (index < strs[0].length()) {
            for (int i = 0; i < strs.length; i++) {
                if (index >= strs[i].length()) {
                    return prefix.toString();
                }
                if (i > 0 && strs[i].charAt(index) != strs[i - 1].charAt(index)) {
                    return prefix.toString();
                }
            }
            prefix.append(strs[0].charAt(index));
            index++;
        }
        return prefix.toString();
    }


    /**
     * 151. 反转字符串中的单词
     */
    public String reverseWords(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] split = s.trim().split(" ");
        for (int i = split.length - 1; i >= 0; i--) {
            if (split[i].trim().length() == 0) {
                continue;
            }
            stringBuilder.append(split[i]);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().trim();
    }


    /**
     * 6. Z 字形变换
     */
    public String convert(String s, int numRows) {
        if (s == null || s.length() <= 2 || numRows < 2) {
            return s;
        }
        char[][] arr = new char[numRows][s.length()];
        int index = 0;
        int x = 0;
        int y = 0;
        boolean down = false;
        while (index < s.length()) {
            arr[x][y] = s.charAt(index);
            if (x == 0) {
                down = true;
            }
            if (x == numRows - 1) {
                down = false;
            }
            if (down) {
                x++;
            } else {
                x--;
                y++;
            }
            index++;
        }
        StringBuilder builder = new StringBuilder();
        for (char[] chars : arr) {
            for (char aChar : chars) {
                if ((aChar <= 'z' && aChar >= 'A') || (aChar == '.' || aChar == ',')) {
                    builder.append(aChar);
                }
            }
        }
        return builder.toString();
    }


    /***
     *28. 找出字符串中第一个匹配项的下标
     * **/
    public int strStr(String haystack, String needle) {
        if (haystack == null || haystack.length() == 0 || needle == null || needle.length() == 0 || needle.length() > haystack.length()) {
            return -1;
        }

        for (int i = 0; i < haystack.length(); i++) {
            if ((i + needle.length() - 1) < haystack.length() && haystack.startsWith(needle, i)) {
                return i;
            }
        }
        return -1;
    }

//    /**
//     * 68. 文本左右对齐
//     */
//    public List<String> fullJustify(String[] words, int maxWidth) {
//
//
//    }


    /**
     * 134.加油站
     **/
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int index = 0;
        int min = 0;
        int sum = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
            if (sum < min) {
                min = sum;
                index = i + 1;
            }
        }
        return sum < 0 ? -1 : index;
    }

    /**
     * 125.验证回文串
     */
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            if (!((chars[left] >= 'A' && chars[left] <= 'Z') || (chars[left] >= 'a' && chars[left] <= 'z') || (chars[left] >= '0' && chars[left] <= '9'))) {
                left++;
                continue;
            }
            if (!((chars[right] >= 'A' && chars[right] <= 'Z') || (chars[right] >= 'a' && chars[right] <= 'z') || (chars[right] >= '0' && chars[right] <= '9'))) {
                right--;
                continue;
            }

            int l = chars[left];
            if (chars[left] < 'a') {
                l += 32;
            }
            int r = chars[right];
            if (chars[right] < 'a') {
                r += 32;
            }
            if (l != r) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }


    /***
     * 392.判断子序列
     * */
    public boolean isSubsequence(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        char[] subs = s.toCharArray();
        char[] ts = t.toCharArray();
        int index1 = 0;
        int index2 = 0;
        while (index1 < subs.length && index2 < ts.length) {
            if (subs[index1] == ts[index2]) {
                index1++;
            }
            index2++;
        }
        return index1 == subs.length;
    }

    /**
     * 167.两数之和II-输入有序数组
     */
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length == 0) {
            return new int[2];
        }
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] > target) {
                right--;
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            } else {
                return new int[]{left + 1, right + 1};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 11.盛水最多水的容器
     **/
    public int maxArea(int[] height) {
        if (height == null || height.length <= 1) {
            return 0;
        }
        int sum = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int area = (right - left) * (Math.min(height[left], height[right]));
            sum = Math.max(sum, area);
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return sum;
    }


    /**
     * 15.三数之和
     **/
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] > 0 || (left > 0 && nums[left] == nums[left - 1])) {
                left++;
                continue;
            }
            int mid = left + 1;
            right = nums.length - 1;
            while (mid < right) {
                if (nums[mid] + nums[right] + nums[left] > 0) {
                    right--;
                } else if (nums[mid] + nums[right] + nums[left] < 0) {
                    mid++;
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[left]);
                    list.add(nums[mid]);
                    list.add(nums[right]);
                    res.add(list);
                    while (right > mid && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    while (mid < right && nums[mid] == nums[mid + 1]) {
                        mid++;
                    }
                    right--;
                    mid++;
                }
            }
            left++;

        }
        return res;
    }


    /**
     * 209.长度最小的子数组
     */
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0;
        int right = 0;
        int cur = nums[left];
        int min = Integer.MAX_VALUE;
        while (right < nums.length && left < nums.length) {
            if (cur < target) {
                right++;
                if (right < nums.length) {
                    cur += nums[right];
                }
            } else {
                min = Math.min(min, right - left + 1);
                cur -= nums[left];
                left++;
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
        int right = 0;
        int max = 0;
        Set<Character> set = new HashSet<>();
        while (right < s.length() && left < s.length()) {
            if (set.contains(s.charAt(right))) {
                set.remove(chars[left++]);
            } else {
                set.add(chars[right++]);
                max = Math.max(max, set.size());
            }
        }
        return max;
    }


    /**
     * 30.串联所有单词的子串
     **/
    public List<Integer> findSubstring(String s, String[] words) {
        if (s == null || words == null || s.length() == 0 || words.length == 0) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        int step = words[0].length();
        for (int i = 0; i < step; i++) {
            Map<String, Integer> map = new HashMap<>();
            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
            int left = i;
            int right = i;
            int count = 0;
            while (right + step <= s.length()) {
                String substring = s.substring(right, right + step);
                Integer integer = map.get(substring);
                if (integer != null && integer > 0) {
                    map.put(substring, integer - 1);
                    count++;
                    right += step;
                    if (count == words.length) {
                        res.add(left);
                    }
                } else {
                    if (count > 0) {
                        String leftSub = s.substring(left, left + step);
                        map.put(leftSub, map.get(leftSub) + 1);
                        left += step;
                        count--;
                    } else {
                        left += step;
                        right += step;
                    }
                }
            }
        }
        return res;
    }


    public List<Integer> findSubstring1(String s, String[] words) {
        if (s == null || words == null || s.length() == 0 || words.length == 0) {
            return new ArrayList<>();
        }
        permutation(words, new LinkedList<>(), new LinkedList<>());
        List<Integer> res = new ArrayList<>();
        int left = 0;
        int right = permutations.get(0).length();
        while (right < s.length()) {
            String substring = s.substring(left, right);
            if (Arrays.stream(words).allMatch(substring::contains) && permutations.contains(substring)) {
                res.add(left);
            }
            left++;
            right++;
        }
        return res;
    }

    private final List<String> permutations = new ArrayList<>();

    public void permutation(String[] words, LinkedList<Integer> set, LinkedList<String> strings) {
        if (set.size() == words.length) {
            StringBuilder stringBuilder = new StringBuilder();
            strings.forEach(stringBuilder::append);
            permutations.add(stringBuilder.toString());
            return;
        }
        for (int i = 0; i < words.length; i++) {
            if (set.contains(i)) {
                continue;
            }
            set.addLast(i);
            strings.addLast(words[i]);
            permutation(words, set, strings);
            strings.removeLast();
            set.removeLast();
        }
    }

    @Test
    public void testPermutation() {
        String s = "barfoothefoobarman";
        String[] words = {"foo", "bar"};
        findSubstring1(s, words);
    }

    public List<Integer> findSubstring2(String s, String[] words) {
        // 单词的数量
        int allCount = words.length;
        // 单个单词的长度
        int aloneLen = words[0].length();
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < aloneLen; i++) {
            // 单词统计计数
            Map<String, Integer> map = new HashMap<>();
            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
            // 左边界，起始下标
            int left = i;
            // 右边界
            int right = i;
            // 当前匹配上的单词数量
            int curCount = 0;
            while (right <= s.length() - aloneLen) {
                String code = s.substring(right, right + aloneLen);
                Integer cnt = map.getOrDefault(code, 0);
                if (cnt > 0) {
                    // 如果有剩余则添加，并减数量
                    curCount++;
                    map.put(code, cnt - 1);
                    right += aloneLen;
                    if (curCount == allCount) {
                        ret.add(left);
                    }
                } else {
                    // 如果没有剩余且有消耗，则释放 left那边的字符
                    if (curCount > 0) {
                        String preCode = s.substring(left, left + aloneLen);
                        map.put(preCode, map.get(preCode) + 1);
                        left += aloneLen;
                        curCount--;
                    } else {
                        // 这里增长 aloneLen，如果这里 +1，则会有重复数据（步长 aloneLen，最外层 for保证了每个位置都遍历到了）
                        // 分享踩坑：最开始我没有最外层的那个 for循环，没有办法通过用例 "aaaaaaaaaaaaaa"，["aa","aa"]，之前我这里写的是 +1，然后加上最外层的循环，这里如果是 +1，则会有重复数据。
                        left += aloneLen;
                        right += aloneLen;
                    }
                }
            }
        }
        return ret;
    }


    /**
     * 76. 最小覆盖子串
     **/
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        String res = s;
        boolean flag = false;
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int count = 0;
        int right = 0;
        int left = 0;
        while (right + 1 <= s.length()) {
            Character character = s.charAt(right);
            Integer integer = map.get(character);
            if (integer != null) {
                map.put(character, integer - 1);
                if (integer > 0) {
                    count++;
                }
                if (count == t.length()) {
                    char c = s.charAt(left);
                    while (!map.containsKey(c) || map.get(c) < 0) {
                        if (map.containsKey(c)) {
                            map.put(c, map.get(c) + 1);
                        }
                        c = s.charAt(++left);
                    }
                    String substring = s.substring(left, right + 1);
                    if (substring.length() <= res.length()) {
                        flag = true;
                        res = substring;
                    }
                    char l = s.charAt(left);
                    map.put(l, map.get(l) + 1);
                    left++;
                    count--;
                }
            }
            right++;
        }
        return flag ? res : "";
    }


    @Test
    public void strTest() {
        String s = "a";
        String t = "a";
        minWindow(s, t);
    }


    /**
     * 36.有效的数独
     **/
    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length == 0) {
            return false;
        }
        int[][] row = new int[9][10];
        int[][] col = new int[9][10];
        int[][] box = new int[9][10];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') continue;
                int curNum = board[i][j] - '0';
                if (row[i][curNum] == 1 || col[j][curNum] == 1 || box[(i / 3) * 3 + j / 3][curNum] == 1) return false;
                row[i][curNum]++;
                col[j][curNum]++;
                box[(i / 3) * 3 + j / 3][curNum]++;
            }
        }
        return true;
    }


    /**
     * 383. 赎金信
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote == null || magazine == null || ransomNote.length() > magazine.length()) {
            return false;
        }
        int[] arr = new int[26];
        char[] chars = ransomNote.toCharArray();
        for (char c : chars) {
            arr[c - 'a']++;
        }
        char[] magazines = magazine.toCharArray();
        for (char c : magazines) {
            arr[c - 'a']--;
        }
        return Arrays.stream(arr).allMatch(i -> i <= 0);
    }


    /**
     * 205. 同构字符串
     */
    public boolean isIsomorphic(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        char[] ss = s.toCharArray();

        char[] ts = t.toCharArray();
        Map<Character, Character> characterMap = new HashMap<>();

        for (int i = 0; i < ss.length; i++) {
            char from = ss[i];
            char to = ts[i];
            if (characterMap.containsKey(from)) {
                if (characterMap.get(from) != to) {
                    return false;
                } else {
                    continue;
                }
            }
            if (characterMap.values().stream().anyMatch(character -> character == to)) {
                return false;
            }
            characterMap.put(from, to);
        }
        return true;
    }


    /**
     * 290. 单词规律
     **/
    public boolean wordPattern(String pattern, String s) {
        if (pattern == null || s == null) {
            return false;
        }

        char[] chars = pattern.toCharArray();
        String[] split = s.split("\\s+");
        if (chars.length != split.length) {
            return false;
        }
        Map<Character, String> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            String s1 = split[i];
            if (map.containsKey(c)) {
                if (!map.get(c).equals(s1)) {
                    return false;
                }
                continue;
            }
            if (map.values().stream().anyMatch(str -> str.equals(s1))) {
                return false;
            }
            map.put(c, s1);
        }
        return true;
    }


    /**
     * 242. 有效的字母异位词
     **/
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || t.length() != s.length()) {
            return false;
        }
        char[] ss = s.toCharArray();
        char[] ts = t.toCharArray();
        int[] nums = new int[26];
        for (char s1 : ss) {
            nums[s1 - 'a']++;
        }
        for (char t1 : ts) {
            nums[t1 - 'a']--;
        }
        return Arrays.stream(nums).allMatch(num -> num >= 0);
    }


    /**
     * 49. 字母异位词分组
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            StringBuilder builder = new StringBuilder();
            for (Character c : chars) {
                builder.append(c);
            }
            String s = builder.toString();
            List<String> orDefault = map.getOrDefault(s, new LinkedList<>());
            orDefault.add(str);
            map.put(s, orDefault);
        }
        return new ArrayList<>(map.values());
    }


    /**
     * 1. 两数之和
     */
    public int[] twoSum2(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            return new int[]{-1, -1};
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[]{i, map.get(nums[i])};
            }
            map.put(target - nums[i], i);
        }
        return new int[]{-1, -1};
    }


    /**
     * 202. 快乐数
     **/
    public boolean isHappy(int n) {
        if (n <= 0) {
            return false;
        }
        Set<Long> set = new HashSet<>();
        set.add((long) n);
        while (n != 1) {
            long bit = 1;
            List<Long> nums = new LinkedList<>();
            while (bit <= n) {
                nums.add((n % (bit * 10)) / bit);
                bit *= 10;
            }
            long temp = 0;
            for (long t : nums) {
                temp = temp + (t * t);
            }
            if (set.contains(temp)) {
                return false;
            }
            set.add(temp);
            n = (int) temp;
        }
        return true;
    }

    @Test
    public void happyTest() {
        System.out.println(isHappy(1999999999));
    }


    /**
     * 219. 存在重复元素 II
     **/
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length <= 1) {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;

    }


    /**
     * 128. 最长连续序列
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums.length;
        }
        Set<Integer> set = new HashSet<>();
        Set<Integer> black = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int res = 0;
        for (int j : nums) {
            if (black.contains(j)) {
                continue;
            }
            black.add(j);
            int count = 1;
            int num = j - 1;
            while (set.contains(num)) {
                count++;
                num--;
                black.add(num);
            }
            num = j + 1;
            while (set.contains(num)) {
                count++;
                num++;
                black.add(num);
            }
            res = Math.max(res, count);
        }
        return res;
    }


    /**
     * 228. 汇总区间
     **/
    public List<String> summaryRanges(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        int first = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1] + 1) {
                if (first == nums[i - 1]) {
                    res.add(String.valueOf(nums[i - 1]));
                } else {
                    res.add(first + "->" + nums[i - 1]);
                }
                first = nums[i];
            }
        }
        if (first == nums[nums.length - 1]) {
            res.add(String.valueOf(nums[nums.length - 1]));
        } else {
            res.add(first + "->" + nums[nums.length - 1]);
        }
        return res;
    }


    /**
     * 56. 合并区间
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }
        Arrays.sort(intervals, Comparator.comparing(ints1 -> ints1[0]));
        int[] pre = intervals[0];
        List<String> res = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= pre[0] && intervals[i][0] <= pre[1]) {
                pre[0] = Math.min(pre[0], intervals[i][0]);
                pre[1] = Math.max(pre[1], intervals[i][1]);
            } else {
                res.add(pre[0] + "," + pre[1]);
                pre = intervals[i];
            }
        }
        res.add(pre[0] + "," + pre[1]);
        int[][] ints = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            String[] split = res.get(i).split(",");
            ints[i][0] = Integer.parseInt(split[0]);
            ints[i][1] = Integer.parseInt(split[1]);
        }
        return ints;
    }


    /***
     * 57.插入区间
     * **/
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (newInterval == null || newInterval.length == 0) {
            return intervals;
        }
        if (intervals == null || intervals.length == 0) {
            int[][] ints = new int[1][2];
            ints[0][0] = newInterval[0];
            ints[0][1] = newInterval[1];
            return ints;
        }
        int[][] dp = new int[intervals.length + 1][2];
        int index = 0;
        boolean flag = true;
        for (int[] interval : intervals) {
            if (interval[1] < newInterval[0] || interval[0] > newInterval[1]) {
                if (interval[0] > newInterval[1] && flag) {
                    dp[index][0] = newInterval[0];
                    dp[index][1] = newInterval[1];
                    index++;
                    flag = false;
                }
                dp[index][0] = interval[0];
                dp[index][1] = interval[1];
                index++;
            } else {
                newInterval[0] = Math.min(interval[0], newInterval[0]);
                newInterval[1] = Math.max(interval[1], newInterval[1]);
            }
        }
        if (flag) {
            dp[index][0] = newInterval[0];
            dp[index][1] = newInterval[1];
            index++;
        }
        int[][] res = new int[index][2];
        System.arraycopy(dp, 0, res, 0, index);
        return res;
    }


    /***
     * 452. 用最少数量的箭引爆气球
     * **/
    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        if (points.length == 1) {
            return points.length;
        }
        Arrays.sort(points, Comparator.comparing(nums -> nums[0]));
        int[][] dp = new int[points.length][2];
        int index = 0;
        dp[index] = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > dp[index][1]) {
                index++;
                dp[index] = points[i];
            } else {
                dp[index][0] = Math.max(dp[index][0], points[i][0]);
                dp[index][1] = Math.min(dp[index][1], points[i][1]);
            }
        }
        return index + 1;
    }


    /**
     * 20. 有效的括号
     **/
    public boolean isValid(String s) {
        if (s == null || s.length() == 0 || s.length() % 2 != 0) {
            return false;
        }
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char aChar : chars) {
            if (stack.isEmpty() || aChar == '{' || aChar == '[' || aChar == '(' || !pair(stack.peek(), aChar)) {
                stack.push(aChar);
                continue;
            }
            stack.pop();
        }
        return stack.isEmpty();

    }

    public boolean pair(Character left, Character right) {
        return (left == '{' && right == '}') || (left == '[' && right == ']') || (left == '(' && right == ')');
    }


    /***
     * 71. 简化路径
     * **/
    public String simplifyPath(String path) {
        if (path == null || path.length() <= 1) {
            return path;
        }
        path = path.replaceAll("//", "/");
        Deque<String> stack = new LinkedBlockingDeque<>();
        String[] paths = path.split("/");
        for (String p : paths) {
            if (p.length() == 0 || p.equals(".")) {
                continue;
            }
            if (p.equals("..")) {
                if (!stack.isEmpty()) stack.pop();
                continue;
            }
            stack.push(p);
        }
        StringBuilder builder = new StringBuilder("/");
        while (!stack.isEmpty()) {
            builder.append(stack.pollLast());
            builder.append("/");
        }
        return builder.length() == 1 ? builder.toString() : builder.substring(0, builder.length() - 1);
    }


    /**
     * 155. 最小栈
     **/
    class MinStack {

        Stack<Integer> stack;
        Integer min = Integer.MAX_VALUE;

        public MinStack() {
            stack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            min = Math.min(min, val);
        }

        public void pop() {
            Integer pop = stack.pop();
            if (pop.equals(min)) {
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
     * 150. 逆波兰表达式求值
     **/
    public int evalRPN(String[] tokens) {
        if (tokens == null) {
            return 0;
        }
        Stack<String> stack = new Stack<>();
        for (String str : tokens) {
            if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                Integer right = Integer.valueOf(stack.pop());
                Integer left = Integer.valueOf(stack.pop());
                Integer now = switch (str) {
                    case "+" -> left + right;
                    case "-" -> left - right;
                    case "*" -> left * right;
                    default -> left / right;
                };
                stack.push(String.valueOf(now));
                continue;
            }
            stack.push(str);
        }
        return Integer.parseInt(stack.peek());
    }

    /**
     * 224.基本计算器
     **/
    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        s = s.replaceAll("\\s+", "");
        int sum = 0;
        Stack<String> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ('0' <= chars[i] && chars[i] <= '9') {
                StringBuilder nums = new StringBuilder(String.valueOf(chars[i]));
                if (i >= 1 && ('0' <= chars[i - 1] && chars[i - 1] <= '9')) {
                    nums.insert(0, stack.pop());
                }
                stack.push(nums.toString());
            } else if ('+' == chars[i] || chars[i] == '-') {
                String operator = String.valueOf(chars[i]);
                while (i >= 1 && ('-' == chars[i - 1] || chars[i - 1] == '+')) {
                    if (stack.pop().equals(operator)) {
                        operator = "+";
                    } else {
                        operator = "-";
                    }
                }
                stack.push(operator);
            } else if (chars[i] == ')') {
                int tempSum = 0;
                while (!stack.peek().equals("(")) {
                    String pop = stack.pop();
                    if (!(pop.equals("+") || pop.equals("-")) && !stack.peek().equals("(")) {
                        String operater = stack.pop();
                        tempSum += Long.parseLong(operater + pop);
                    } else {
                        tempSum += Long.parseLong(pop);
                    }
                }
                stack.pop();
                String s1 = String.valueOf(tempSum);
                if (!stack.isEmpty() && (s1.charAt(0) == '+' || s1.charAt(0) == '-') && (Objects.equals(stack.peek(), "+") || Objects.equals(stack.peek(), "-"))) {
                    String operator = stack.pop();
                    stack.push((s1.substring(0, 1).equals(operator) ? "+" : "-"));
                    s1 = s1.substring(1);
                }
                stack.push(s1);
            } else {
                stack.push(String.valueOf(chars[i]));
            }
        }
        while (!stack.isEmpty()) {
            String pop = stack.pop();
            if (!(pop.equals("+") || pop.equals("-"))) {
                if (!stack.isEmpty()) {
                    String operater = stack.pop();
                    sum += Long.parseLong(operater + pop);
                } else {
                    sum += Long.parseLong(pop);
                }
            }
        }
        return sum;
    }

    @Test
    public void OperaterTest() {
        String str = "2147483647";
        System.out.println(calculate(str));
    }


    /**
     * 141.环形链表
     **/
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null && slow != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }


    /**
     * 2. 两数相加
     **/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode head = new ListNode(-1);
        ListNode point = head;
        ListNode head1 = l1;
        ListNode head2 = l2;
        int pre = 0;
        while (head1 != null && head2 != null) {
            int value = head1.val + head2.val + pre;
            pre = value / 10;
            point.next = new ListNode(value % 10);
            point = point.next;
            head1 = head1.next;
            head2 = head2.next;
        }
        while (head1 != null) {
            int value = head1.val + pre;
            pre = value / 10;
            point.next = new ListNode(value % 10);
            point = point.next;
            head1 = head1.next;
        }
        while (head2 != null) {
            int value = head2.val + pre;
            pre = value / 10;
            point.next = new ListNode(value % 10);
            point = point.next;
            head2 = head2.next;
        }
        if (pre != 0) {
            point.next = new ListNode(pre % 10);
        }
        return head.next;
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
        ListNode pre = new ListNode(-1);
        ListNode head = pre;
        ListNode p1 = list1;
        ListNode p2 = list2;

        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                ListNode next = p1.next;
                p1.next = null;
                head.next = p1;
                p1 = next;
            } else {
                ListNode next = p2.next;
                p2.next = null;
                head.next = p2;
                p2 = next;
            }
            head = head.next;
        }
        while (p1 != null) {
            ListNode next = p1.next;
            p1.next = null;
            head.next = p1;
            p1 = next;
            head = head.next;
        }
        while (p2 != null) {
            ListNode next = p2.next;
            p2.next = null;
            head.next = p2;
            p2 = next;
            head = head.next;
        }
        return pre.next;
    }

    class NodeRandom {
        int val;
        NodeRandom next;
        NodeRandom random;

        public NodeRandom(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 138. 随机链表的复制
     **/
    Map<NodeRandom, NodeRandom> chacheMap = new HashMap<>();

    public NodeRandom copyRandomList(NodeRandom head) {
        if (head == null) {
            return null;
        }
        if (!chacheMap.containsKey(head)) {
            NodeRandom newNode = new NodeRandom(head.val);
            chacheMap.put(head, newNode);
            newNode.next = copyRandomList(head.next);
            newNode.random = copyRandomList(head.random);
        }
        return chacheMap.get(head);
    }


    /***
     *92. 反转链表 II
     * **/
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right) {
            return head;
        }
        int count = right - left + 1;
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode point = pre;
        while (left > 1) {
            point = point.next;
            left--;
        }
        ListNode cur = point.next;
        while (count > 1 && cur.next != null) {
            ListNode next = cur.next;
            ListNode last = next.next;
            ListNode first = point.next;
            cur.next = last;
            point.next = next;
            next.next = first;
            count--;
        }
        return pre.next;
    }

    @Test
    public void testNode() {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        reverseBetween(listNode, 2, 4);
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
        int count = 0;
        ListNode p = head;
        while (p != null) {
            p = p.next;
            count++;
        }
        if (count < k) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;

        int num = 1;
        while (head.next != null) {
            ListNode next = head.next;
            head.next = next.next;
            next.next = pre.next;
            pre.next = next;
            num++;
            if (num == k) {
                head.next = reverse(head.next, k);
                break;
            }
        }
        return pre.next;
    }


    /***
     * 19. 删除链表的倒数第 N 个结点
     * */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode right = pre;
        ListNode left = pre;
        while (n > 0) {
            right = right.next;
            n--;
        }
        while (right.next != null) {
            right = right.next;
            left = left.next;
        }
        left.next = left.next.next;
        return pre.next;
    }


    /**
     * 82. 删除排序链表中的重复元素 II
     **/
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode left = pre;
        ListNode right = head;
        while (right != null) {
            if (right.next == null || right.next.val != right.val) {
                left = left.next;
                right = right.next;
                continue;
            }
            ListNode p = right.next;
            while (p != null && p.val == right.val) {
                p = p.next;
            }
            right = p;
            left.next = right;
        }
        return pre.next;
    }


    /**
     * 61. 旋转链表
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        ListNode p = head;
        int count = 0;
        while (p != null) {
            p = p.next;
            count++;
        }
        int step = count - (k % count);
        if (step == count) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode point = pre;
        while (step > 0) {
            point = point.next;
            step--;
        }

        ListNode newHead = point.next;

        ListNode next = point;
        while (next.next != null) {
            next = next.next;
        }
        next.next = pre.next;
        point.next = null;
        return newHead;
    }

    @Test
    public void rotateRightTest() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        rotateRight(head, 1);
    }

    /**
     * 86. 分隔链表
     **/
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode right = pre;
        ListNode left = pre;

        while (right.next != null) {
            if (right.next.val < x) {
                ListNode cur = right.next;
                right.next = right.next.next;

                ListNode next = left.next;
                left.next = cur;
                cur.next = next;
                left = left.next;
                right = left;
            } else {
                right = right.next;
            }
        }
        return pre.next;
    }


    @Test
    public void partitionTest() {

        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);

        partition(head, 3);
    }


    class LRUCache {

        private Map<Integer, Entry> cacheMap;
        private Entry head;
        private Entry tail;
        private Integer capacity;

        public LRUCache(int capacity) {
            this.cacheMap = new HashMap<>();
            this.capacity = capacity;
            this.head = new Entry();
            this.tail = new Entry();
            this.head.next = tail;
            this.tail.pre = head;
        }

        public int get(int key) {
            if (!cacheMap.containsKey(key)) {
                return -1;
            }
            Entry entry = cacheMap.get(key);
            moveHead(entry);
            return entry.value;
        }

        public void put(int key, int value) {
            if (cacheMap.containsKey(key)) {
                Entry entry = cacheMap.get(key);
                entry.setValue(value);
                cacheMap.put(key, entry);
                moveHead(entry);
                return;
            }
            if (cacheMap.size() == capacity) {
                Entry entry = removeLast();
                cacheMap.remove(entry.key);
            }
            Entry entry = new Entry(key, value);
            cacheMap.put(key, entry);
            addHead(entry);
        }

        public void moveHead(Entry entry) {
            remove(entry);
            addHead(entry);
        }

        public void remove(Entry entry) {
            Entry pre = entry.pre;
            Entry next = entry.next;
            pre.next = next;
            next.pre = pre;
            entry.pre = null;
            entry.next = null;
        }

        public Entry removeLast() {
            Entry last = tail.pre;
            if (last == null) {
                return null;
            }
            Entry pre = last.pre;
            Entry next = last.next;
            pre.next = next;
            next.pre = pre;
            return last;
        }

        public void addHead(Entry entry) {
            Entry first = head.next;
            head.next = entry;
            entry.next = first;
            entry.pre = head;
            first.pre = entry;
        }

        public static class Entry {
            private Entry pre;
            private Entry next;
            private Integer value;
            private Integer key;

            public Entry(Integer key, Integer value) {
                this.value = value;
                this.key = key;
            }

            public Entry() {
            }

            public void setValue(Integer value) {
                this.value = value;
            }
        }
    }


    /**
     * 104. 二叉树的最大深度
     **/
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }


    /**
     * 100. 相同的树
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        return (p != null && q != null && p.val == q.val) && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 翻转二叉树
     **/
    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        TreeNode right = root.right;
        TreeNode left = root.left;
        root.left = invertTree(right);
        root.right = invertTree(left);
        return root;
    }


    /**
     * 101. 对称二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        return isSame(left, right);
    }

    public boolean isSame(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        return right != null && left != null && right.val == left.val && isSame(left.left, right.right) && isSame(left.right, right.left);
    }


    /**
     * 105. 从前序与中序遍历序列构造二叉树
     **/
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int left, int right, int[] inorder, int orderLeft, int orderRight) {
        if (left > right || orderLeft > orderRight) {
            return null;
        }
        if (left == right) {
            return new TreeNode(preorder[left]);
        }
        int value = preorder[left];
        TreeNode treeNode = new TreeNode(value);

        int index = orderLeft;
        for (int i = orderLeft; i <= orderRight; i++) {
            if (inorder[i] == value) {
                index = i;
                break;
            }
        }
        treeNode.left = buildTree(preorder, left + 1, left + index - orderLeft, inorder, orderLeft, index - 1);
        treeNode.right = buildTree(preorder, left + 1 + index - orderLeft, right, inorder, index + 1, orderRight);
        return treeNode;
    }


    @Test
    public void testOrderTree() {
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};

        buildTree1(inorder, postorder);
    }


    /**
     * 106. 从中序与后序遍历序列构造二叉树
     */
    public TreeNode buildTree1(int[] inorder, int[] postorder) {

        return buildTreeByInAndPost(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
    }

    public TreeNode buildTreeByInAndPost(int[] inorder, int[] postorder, int inLeft, int inRight, int postLeft, int postRight) {
        if (inLeft > inRight || postLeft > postRight) {
            return null;
        }
        if (inLeft == inRight) {
            return new TreeNode(postorder[postRight]);
        }
        int val = postorder[postRight];
        TreeNode treeNode = new TreeNode(val);

        int index = inLeft;
        for (; index <= inRight; index++) {
            if (inorder[index] == val) {
                break;
            }
        }
        treeNode.left = buildTreeByInAndPost(inorder, postorder, inLeft, index - 1, postLeft, postLeft + (index - inLeft) - 1);
        treeNode.right = buildTreeByInAndPost(inorder, postorder, index + 1, inRight, postLeft + (index - inLeft), postRight - 1);
        return treeNode;
    }


    /**
     * 117. 填充每个节点的下一个右侧节点指针 II
     **/
    public com.zzh.springboot3.algorithm.Node connect(com.zzh.springboot3.algorithm.Node root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        Queue<com.zzh.springboot3.algorithm.Node> queue = new LinkedBlockingQueue<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            com.zzh.springboot3.algorithm.Node pre = null;
            for (int i = 0; i < size; i++) {
                com.zzh.springboot3.algorithm.Node poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (pre != null) {
                    pre.next = poll;
                }
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
                pre = poll;
            }
        }
        return root;
    }

    /**
     * 114. 二叉树展开为链表
     **/
    public void flatten(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }
        expand(root);
    }

    public TreeNode expand(TreeNode node) {
        if (node == null || (node.left == null && node.right == null)) {
            return node;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        node.left = null;
        node.right = null;

        node.right = expand(left);
        TreeNode point = node;
        while (point.right != null) {
            point = point.right;
        }
        point.right = expand(right);
        return node;
    }


    /**
     * 112. 路径总和
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }


    /**
     * 129. 求根节点到叶节点数字之和
     **/
    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        sumNumbers(root, 0);
        return sum;
    }


    int sum = 0;

    public void sumNumbers(TreeNode node, int num) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            sum += (num * 10 + node.val);
            return;
        }
        sumNumbers(node.left, num * 10 + node.val);
        sumNumbers(node.right, num * 10 + node.val);
    }


    /**
     * 124. 二叉树中的最大路径和
     */
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        maxPathSums(root);
        return max;
    }

    int max = Integer.MIN_VALUE;

    public int maxPathSums(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            max = Math.max(max, node.val);
            return node.val;
        }
        int leftSum = Math.max(maxPathSums(node.left), 0);
        int rightSum = Math.max(maxPathSums(node.right), 0);


        max = Math.max(node.val + leftSum + rightSum, max);
        return Math.max(leftSum, rightSum) + node.val;
    }


    class BSTIterator {

        private List<Integer> nodeList = new LinkedList<>();
        private Integer index = 0;

        public BSTIterator(TreeNode root) {
            mid(root, nodeList);
        }

        public int next() {
            return nodeList.get(index++);
        }

        public boolean hasNext() {
            return index <= nodeList.size() - 1;
        }

        public void mid(TreeNode root, List<Integer> nodeList) {
            if (root == null) {
                return;
            }
            if (root.left != null) {
                mid(root.left, nodeList);
            }
            nodeList.add(root.val);
            if (root.right != null) {
                mid(root.right, nodeList);
            }
        }
    }


    /**
     * 222. 完全二叉树的节点个数
     **/
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }


    /**
     * 236.二叉树的最近公共祖先
     **/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        fatherTreeNode(root, p, q);
        return lowestCommonAncestor;

    }

    TreeNode lowestCommonAncestor;

    public boolean fatherTreeNode(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        boolean left = fatherTreeNode(node.left, p, q);
        boolean right = fatherTreeNode(node.right, p, q);
        if (((node == p || node == q) && (left || right)) || (left && right)) {
            lowestCommonAncestor = node;
        }
        return (left || right) || (node == p || node == q);
    }


    /**
     * 199. 二叉树的右视图
     **/
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
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


    /**
     * 637. 二叉树的层平均值
     **/
    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        List<Double> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            double sum = 0.0;
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                sum += poll.val;
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            res.add(sum / size);
        }
        return res;
    }


    /***
     *103. 二叉树的锯齿形层序遍历
     * */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (level % 2 == 0) {
                    stack.push(poll);
                } else {
                    temp.add(poll.val);
                }
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }

            while (!stack.isEmpty()) {
                temp.add(stack.pop().val);
            }
            res.add(temp);
            level++;
        }
        return res;
    }


    /**
     * 530. 二叉搜索树的最小绝对差
     **/

    public int getMinimumDifference(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return -1;
        }
        midTreeNode(root);
        int res = Integer.MAX_VALUE;
        for (int i = 1; i < minimumDifference.size(); i++) {
            res = Math.min(res, Math.abs(minimumDifference.get(i) - minimumDifference.get(i - 1)));
        }
        return res;
    }

    List<Integer> minimumDifference = new ArrayList<>();

    public void midTreeNode(TreeNode node) {
        if (node == null) {
            return;
        }
        midTreeNode(node.left);
        minimumDifference.add(node.val);
        midTreeNode(node.right);
    }


    /**
     * 230. 二叉搜索树中第 K 小的元素
     */
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }
        preSmallest(root, k);
        return kthSmallest;
    }

    int kthSmallest;
    int count = 0;

    public void preSmallest(TreeNode node, int k) {
        if (node == null) {
            return;
        }
        preSmallest(node.left, k);
        count++;
        if (count == k) {
            kthSmallest = node.val;
        }
        preSmallest(node.right, k);
    }


    /**
     * 98. 验证二叉搜索树
     **/
    public boolean isValidBST(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }
        return isValidBSTTreeNode(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBSTTreeNode(TreeNode node, Long min, Long max) {
        if (node == null) {
            return true;
        }
        if (node.val <= min || node.val >= max) {
            return false;
        }
        return isValidBSTTreeNode(node.left, min, (long) node.val) && isValidBSTTreeNode(node.right, (long) node.val, max);
    }


    /**
     * 200. 岛屿数量
     **/
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    infect(grid, i, j);
                }
            }
        }
        return count;
    }

    public void infect(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        infect(grid, i + 1, j);
        infect(grid, i - 1, j);
        infect(grid, i, j + 1);
        infect(grid, i, j - 1);
    }


    /**
     * 130. 被围绕的区域
     **/
    public void solve(char[][] board) {
        if (board == null || board.length <= 2 || board[0].length <= 2) {
            return;
        }
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ((i == 0 || i == board.length - 1 || j == 0 || j == board[i].length - 1) && board[i][j] == 'O') {
                    solve(board, i, j, visited);
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'O' && !visited[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void solve(char[][] board, int i, int j, boolean[][] visited) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return;
        }
        if (board[i][j] != 'O' || visited[i][j]) {
            return;
        }
        visited[i][j] = true;
        solve(board, i - 1, j, visited);
        solve(board, i + 1, j, visited);
        solve(board, i, j - 1, visited);
        solve(board, i, j + 1, visited);
    }


    public void solve1(char[][] board) {
        if (board == null || board.length <= 2 || board[0].length <= 2) {
            return;
        }
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'O' && !solve1(board, i, j, visited)) {
                    infectBoard(board, i, j);
                }
            }
        }
    }

    public void infectBoard(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] == 'O') {
            return;
        }
        board[i][j] = 'O';
        infectBoard(board, i + 1, j);
        infectBoard(board, i - 1, j);
        infectBoard(board, i, j + 1);
        infectBoard(board, i, j - 1);
    }

    public boolean solve1(char[][] board, int i, int j, boolean[][] visited) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }
        if (board[i][j] != 'O' || visited[i][j]) {
            return false;
        }
        visited[i][j] = true;
        if (i == 0 || j == 0 || i == board.length - 1 || j == board[0].length - 1) {
            return true;
        }
        return solve1(board, i - 1, j, visited) || solve1(board, i + 1, j, visited) || solve1(board, i, j - 1, visited) || solve1(board, i, j + 1, visited);
    }

    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    /**
     * 133. 克隆图
     **/
    public Node cloneGraph(Node node) {
        if (node == null || node.neighbors == null) {
            return node;
        }
        return copy(node);
    }

    private final Map<Integer, Node> nodeMap = new HashMap<>();

    public Node copy(Node source) {
        if (source == null) {
            return null;
        }
        Node target;
        if (nodeMap.containsKey(source.val)) {
            return nodeMap.get(source.val);
        } else {
            target = new Node();
            target.val = source.val;
            nodeMap.put(source.val, target);
        }
        List<Node> neighbors = source.neighbors;
        neighbors.forEach(neighbor -> target.neighbors.add(copy(neighbor)));
        return target;
    }


//    /**
//     * 399. 除法求值
//     */
//    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
//        if (equations == null || equations.size() == 0 || queries == null || queries.size() == 0) {
//            return new double[0];
//        }
//        double[][] temp = new double[26][26];
//        queries.forEach(arr->{
//            int s = arr.get(0).-'a';
//            char s = arr.get(1);
//
//
//        });
//
//    }


    /**
     * 207. 课程表
     **/
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses == 0 || prerequisites == null || prerequisites.length <= 1) {
            return true;
        }
        Map<Integer, List<Integer>> courses = new HashMap<>();
        for (int[] ints : prerequisites) {
            List<Integer> list = courses.computeIfAbsent(ints[0], (key) -> new ArrayList<>());
            list.add(ints[1]);
        }
        List<Integer> visited = new ArrayList<>();
        LinkedList<Integer> visitedPath = new LinkedList<>();
        for (Map.Entry<Integer, List<Integer>> entry : courses.entrySet()) {
            if (!course(visited, visitedPath, courses, entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }


    public boolean course(List<Integer> visited, LinkedList<Integer> visitedPath, Map<Integer, List<Integer>> courses, int course, List<Integer> preCourses) {
        if (visited.contains(course)) {
            return true;
        }
        for (Integer preCourse : preCourses) {
            if (visited.contains(preCourse)) {
                continue;
            }
            if (visitedPath.contains(preCourse)) {
                return false;
            }
            visitedPath.add(course);
            if (courses.containsKey(preCourse) && !course(visited, visitedPath, courses, preCourse, courses.get(preCourse))) {
                return false;
            }
        }
        visited.add(course);
        return true;
    }

    @Test
    public void testCourse() {
        int[][] courses = {{1, 4}, {2, 4}, {3, 1}, {3, 2}};
        System.out.println(canFinish(5, courses));
    }


    /**
     * 210. 课程表 II
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0) {
            int[] arr = new int[numCourses];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
        }
        List<Integer>[] graph = graph(prerequisites, numCourses);
        boolean[] visited = new boolean[numCourses];
        boolean[] path = new boolean[numCourses];
        for (int i = 0; i < graph.length; i++) {
            leanCourse(graph, i, graph[i], visited, path);
        }
        if (cycle) {
            return new int[0];
        }
        int[] res = new int[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            res[i] = courses.get(i);
        }
        return res;
    }

    List<Integer> courses = new ArrayList<>();
    boolean cycle = false;

    public void leanCourse(List<Integer>[] graph, int course, List<Integer> preCourses, boolean[] visited, boolean[] path) {
        if (path[course] || cycle) {
            cycle = true;
            return;
        }
        if (visited[course]) {
            return;
        }
        path[course] = true;
        for (Integer pre : preCourses) {
            leanCourse(graph, pre, graph[pre], visited, path);
        }
        visited[course] = true;
        courses.add(course);
        path[course] = false;
    }

    public List<Integer>[] graph(int[][] prerequisites, int numCourses) {
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] arr : prerequisites) {
            graph[arr[0]].add(arr[1]);
        }
        return graph;
    }


    /***
     *  课程表： List<Integer>[] graph=new LinkedList[num];
     * */


    /**
     * 433. 最小基因变化
     **/
    public int minMutation(String startGene, String endGene, String[] bank) {
        if (startGene == null || endGene == null || startGene.equals(endGene)) {
            return 0;
        }
        if (bank == null || bank.length == 0) {
            return -1;
        }
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        char[] start = startGene.toCharArray();
        char[] end = endGene.toCharArray();
        if (minMutation(start, end, bankSet, 0)) {
            return minMutation;
        }
        return -1;
    }

    Integer minMutation = 0;

    public boolean minMutation(char[] start, char[] end, Set<String> bankSet, int count) {
        if (new String(start).equals(new String(end))) {
            minMutation = count;
            return true;
        }
        for (int i = 0; i < start.length; i++) {
            if (start[i] == end[i]) {
                continue;
            }
            char pre = start[i];
            start[i] = end[i];
            count++;
            if (bankSet.contains(new String(start))) {
                return minMutation(start, end, bankSet, count);
            }
            count--;
            start[i] = pre;
        }
        return false;
    }

    @Test
    public void minMutationTest() {
        String[] strs = new String[]{"AACCGATT", "AACCGATA", "AAACGATA", "AAACGGTA"};
        minMutation("AACCGGTT", "AAACGGTA", strs);
    }


    public int minMutation1(String start, String end, String[] bank) {
        Set<String> cnt = new HashSet<String>();
        Set<String> visited = new HashSet<String>();
        char[] keys = {'A', 'C', 'G', 'T'};
        Collections.addAll(cnt, bank);
        if (start.equals(end)) {
            return 0;
        }
        if (!cnt.contains(end)) {
            return -1;
        }
        Queue<String> queue = new ArrayDeque<String>();
        queue.offer(start);
        visited.add(start);
        int step = 1;
        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                String curr = queue.poll();
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (keys[k] != curr.charAt(j)) {
                            StringBuffer sb = new StringBuffer(curr);
                            sb.setCharAt(j, keys[k]);
                            String next = sb.toString();
                            if (!visited.contains(next) && cnt.contains(next)) {
                                if (next.equals(end)) {
                                    return step;
                                }
                                queue.offer(next);
                                visited.add(next);
                            }
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }

}


