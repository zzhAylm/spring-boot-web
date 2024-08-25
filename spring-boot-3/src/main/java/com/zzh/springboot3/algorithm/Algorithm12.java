package com.zzh.springboot3.algorithm;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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


//    /**
//     *
//     * */
//    public boolean canConstruct(String ransomNote, String magazine) {
//
//    }

}





