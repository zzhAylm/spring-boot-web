package com.zzh.springboot3.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Description: CodeTop
 * @Author: zzh
 * @Crete 2024/9/23 19:50
 */
@Slf4j
public class Algorithm13 {


    /**
     * 3. 无重复字符的最长子串
     **/
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() <= 1) {
            return s.length();
        }
        int max = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            while (set.contains(s.charAt(i))) {
                set.remove(s.charAt(i - set.size()));
            }
            set.add(s.charAt(i));
            max = Math.max(max, set.size());
        }
        return max;
    }


    /***
     *146. LRU 缓存
     * **/
    class LRUCache {

        private Map<Integer, Entry> entryMap;
        private Entry tail;
        private Entry head;
        private Integer capacity;

        public LRUCache(int capacity) {
            this.entryMap = new HashMap<>();
            this.capacity = capacity;
            this.tail = new Entry();
            this.head = new Entry();
            this.tail.pre = head;
            this.head.next = tail;
        }

        public int get(int key) {
            if (!entryMap.containsKey(key)) {
                return -1;
            }
            Entry entry = entryMap.get(key);
            moveHead_2(entry);
            return entry.getValue();
        }

        public void put(int key, int value) {
            if (entryMap.containsKey(key)) {
                Entry entry = entryMap.get(key);
                entry.setValue(value);
                moveHead_2(entry);
            } else {
                removeLast();
                Entry entry = new Entry(key, value);
                moveHead(entry);
                entryMap.put(key, entry);
            }
        }

        public void moveHead_2(Entry entry) {
            remove(entry);
            moveHead(entry);
        }

        public void remove(Entry entry) {
            Entry next = entry.next;
            Entry pre = entry.pre;
            next.pre = pre;
            pre.next = next;
            entry.next = null;
            entry.pre = null;
        }

        public void moveHead(Entry entry) {
            Entry next = head.next;
            entry.next = next;
            next.pre = entry;
            head.next = entry;
            entry.pre = head;
        }

        public void removeLast() {
            if (entryMap.size() == 0 || entryMap.size() < capacity) {
                return;
            }
            Entry entry = tail.pre;
            Entry pre = entry.pre;
            pre.next = tail;
            tail.pre = pre;
            entry.next = null;
            entry.pre = null;
            entryMap.remove(entry.key);
        }

        public class Entry {
            private Entry pre;
            private Entry next;
            private Integer key;
            private Integer value;

            public Entry(Integer key, Integer value) {
                this.key = key;
                this.value = value;
            }

            public Entry() {
            }

            public Integer getValue() {
                return value;
            }

            public void setValue(Integer value) {
                this.value = value;
            }

            public Entry getPre() {
                return pre;
            }

            public Entry getNext() {
                return next;
            }

            public void setPre(Entry pre) {
                this.pre = pre;
            }

            public void setNext(Entry next) {
                this.next = next;
            }
        }

    }


    /**
     * 206. 反转链表
     **/
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode();
        pre.next = head;
        ListNode cur = head.next;
        while (cur != null) {
            head.next = cur.next;
            ListNode next = pre.next;
            pre.next = cur;
            cur.next = next;
            cur = head.next;
        }
        return pre.next;
    }


    /***
     *215. 数组中的第K个最大元素
     * **/
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return -1;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(k, (v1, v2) -> v2 - v1);
        for (int num : nums) {
            queue.add(num);
        }
        int res = -1;
        while (k > 0) {
            res = queue.poll();
            k--;
        }
        return res;
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k <= 1) {
            return head;
        }
        ListNode point = new ListNode();
        point.next = head;
        ListNode pre = point;
        ListNode first = head;
        ListNode cur = head.next;
        while (revers(first, k)) {
            int count = 1;
            while (count < k) {
                first.next = cur.next;
                ListNode next = pre.next;
                pre.next = cur;
                cur.next = next;
                count++;
                cur = first.next;
            }
            pre = first;
            first = first.next;
            if (first == null || first.next == null) {
                break;
            }
            cur = first.next;
        }
        return point.next;
    }

    public boolean revers(ListNode node, int k) {
        ListNode cur = node;
        int count = 0;
        while (cur != null && k > count) {
            count++;
            cur = cur.next;
        }
        return count >= k;
    }


    /**
     * 15. 三数之和
     **/
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
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
                int sum = nums[left] + nums[right] + nums[mid];
                if (sum < 0) {
                    mid++;
                } else if (sum > 0) {
                    right--;
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
     * 53.在最大子数组和
     **/
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        }
        return Arrays.stream(dp).max().getAsInt();
    }


    /**
     * 912. 排序数组
     **/

    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    int[] temp;

    public void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) >> 1;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        int count = 0;
        int i = left;
        int j = mid + 1;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[count++] = nums[i++];
            } else {
                temp[count++] = nums[j++];
            }
        }
        while (i <= mid) {
            temp[count++] = nums[i++];
        }
        while (j <= right) {
            temp[count++] = nums[j++];
        }
        if (right + 1 - left >= 0) System.arraycopy(temp, 0, nums, left, right + 1 - left);
    }


    /**
     * 21. 合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode pre = new ListNode();
        ListNode cur = pre;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        while (list1 != null) {
            cur.next = list1;
            list1 = list1.next;
            cur = cur.next;
        }
        while (list2 != null) {
            cur.next = list2;
            list2 = list2.next;
            cur = cur.next;
        }
        return pre.next;
    }


    /**
     * 5. 最长回文子串
     **/
    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        String res = "";
        for (int i = 0; i < s.length(); i++) {
            String s1 = subLongestPalindrome(s, i, i);
            String s2 = subLongestPalindrome(s, i, i + 1);
            res = s1.length() > res.length() ? s1 : res;
            res = s2.length() > res.length() ? s2 : res;
        }

        return res;
    }

    public String subLongestPalindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }


    /**
     * 1.两数之和
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[2];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[]{i, map.get(nums[i])};
            }
            map.put(target - nums[i], i);
        }
        return new int[2];
    }


    /**
     * 33. 搜索旋转排序数组
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return binarySearch(nums, target, 0, nums.length - 1);
    }

    public int binarySearch(int[] nums, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (target > nums[mid]) {
            if (target <= nums[right]) {
                return binarySearch(nums, target, mid + 1, right);
            } else {
                if (nums[mid] >= nums[left]) {
                    return binarySearch(nums, target, mid + 1, right);
                } else {
                    return binarySearch(nums, target, left, mid - 1);
                }
            }
        } else if (target < nums[mid]) {
            if (target >= nums[left]) {
                return binarySearch(nums, target, left, mid - 1);
            } else {
                if (nums[mid] >= nums[right]) {
                    return binarySearch(nums, target, mid + 1, right);
                } else {
                    return binarySearch(nums, target, left, mid - 1);
                }
            }
        } else {
            return mid;
        }
    }


    /**
     * 92. 反转链表 II
     **/
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right) {
            return head;
        }
        ListNode pre = new ListNode();
        pre.next = head;
        ListNode cur = pre;
        int count = 1;
        while (count != left) {
            count++;
            cur = cur.next;
        }
        ListNode first = cur.next;

        while (left < right) {
            ListNode p = first.next;
            first.next = first.next.next;
            ListNode next = cur.next;
            cur.next = p;
            p.next = next;
            left++;
        }
        return pre.next;
    }


    /**
     * 23. 合并 K 个升序链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        ListNode pre = new ListNode();
        ListNode cur = pre;
        while (true) {
            int index = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    continue;
                }
                if (index == -1) {
                    index = i;
                } else {
                    if (lists[i].val < lists[index].val) {
                        index = i;
                    }
                }
            }
            if (index == -1) {
                break;
            }
            cur.next = lists[index];
            lists[index] = lists[index].next;
            cur = cur.next;
        }
        return pre.next;
    }


}
