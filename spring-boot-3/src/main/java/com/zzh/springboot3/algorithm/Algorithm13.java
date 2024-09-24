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
        tmp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    int[] tmp;
    public void mergeSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) >> 1;
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        int i = l, j = mid + 1;
        int cnt = 0;
        while (i <= mid && j <= r) {
            if (nums[i] <= nums[j]) {
                tmp[cnt++] = nums[i++];
            } else {
                tmp[cnt++] = nums[j++];
            }
        }
        while (i <= mid) {
            tmp[cnt++] = nums[i++];
        }
        while (j <= r) {
            tmp[cnt++] = nums[j++];
        }
        for (int k = 0; k < r - l + 1; ++k) {
            nums[k + l] = tmp[k];
        }
    }


}
