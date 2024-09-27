package com.zzh.springboot3.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

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


    /***
     * 103. 二叉树的锯齿形层序遍历
     * */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (level % 2 == 0) {
                    temp.add(poll.val);
                } else {
                    stack.push(poll.val);
                }
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            while (!stack.isEmpty()) {
                temp.add(stack.pop());
            }
            res.add(temp);
            level++;
        }

        return res;
    }


    /**
     * 236. 二叉树的最近公共祖先
     **/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        commonAncestor(root, p, q);
        return commonParent;
    }

    public boolean commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }
        boolean left = commonAncestor(root.left, p, q);
        boolean right = commonAncestor(root.right, p, q);
        if ((left && right) || ((root == p || root == q) && (left || right))) {
            commonParent = root;
        }
        return root == p || root == q || left || right;
    }

    TreeNode commonParent = null;

    public boolean isParent(TreeNode node, TreeNode cur) {
        if (node == null) {
            return false;
        }
        return node.val == cur.val || isParent(node.left, cur) || isParent(node.right, cur);
    }

    /**
     * 143. 重排链表
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Stack<ListNode> stack = new Stack<>();
        while (slow.next != null) {
            ListNode next = slow.next;
            slow.next = null;
            slow = next;
            stack.push(next);
        }
        ListNode cur = head;
        while (cur != null && !stack.isEmpty()) {
            ListNode pop = stack.pop();
            ListNode next = cur.next;
            cur.next = pop;
            pop.next = next;
            cur = next;
        }
    }


    /***
     * 200. 岛屿数量
     * */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int count = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    printNums(grid, i, j, visited);
                }
            }
        }
        return count;
    }


    public void printNums(char[][] grid, int i, int j, boolean[][] visited) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1' || visited[i][j]) {
            return;
        }
        grid[i][j] = '2';
        visited[i][j] = true;
        if (j + 1 < visited[0].length && !visited[i][j + 1]) {
            printNums(grid, i, j + 1, visited);
        }
        if (j - 1 >= 0 && !visited[i][j - 1]) {
            printNums(grid, i, j - 1, visited);
        }
        if (i + 1 < visited.length && !visited[i + 1][j]) {
            printNums(grid, i + 1, j, visited);
        }
        if (i - 1 >= 0 && !visited[i - 1][j]) {
            printNums(grid, i - 1, j, visited);
        }

    }


    /***
     * 54. 螺旋矩阵
     * **/
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        int x1 = 0;
        int x2 = matrix[0].length - 1;
        int y1 = 0;
        int y2 = matrix.length - 1;
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        while (x1 <= x2 && y1 <= y2) {
            for (int i = x1; i < x2; i++) {
                if (visited[y1][i]) {
                    continue;
                }
                visited[y1][i] = true;
                res.add(matrix[y1][i]);
            }
            for (int i = y1; i < y2; i++) {
                if (visited[i][x2]) {
                    continue;
                }
                visited[i][x2] = true;
                res.add(matrix[i][x2]);
            }
            for (int i = x2; i > 0; i--) {
                if (visited[y2][i]) {
                    continue;
                }
                visited[y2][i] = true;
                res.add(matrix[y2][i]);
            }
            for (int i = y2; i > 0; i--) {
                if (visited[i][x1]) {
                    continue;
                }
                visited[i][x1] = true;
                res.add(matrix[i][x1]);
            }
            if (x1 == x2 && y1 == y2 && !visited[x1][y1]) {
                res.add(matrix[y1][x1]);
                visited[y1][x1] = true;
            }
            x1++;
            x2--;
            y1++;
            y2--;
        }
        return res;
    }


    /**
     * 42. 接雨水
     **/
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            max = height[i] > height[max] ? i : max;
        }
        int sum = 0;
        int leftMax = 0;
        for (int i = leftMax; i < max; i++) {
            if (height[i] >= height[leftMax]) {
                leftMax = i;
            } else {
                sum += (height[leftMax] - height[i]);
            }
        }
        int rightMax = height.length - 1;
        for (int i = height.length - 1; i > max; i--) {
            if (height[i] >= height[rightMax]) {
                rightMax = i;
            } else {
                sum += (height[rightMax] - height[i]);
            }
        }
        return sum;
    }


    /**
     * 121. 买卖股票的最佳时机
     **/
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], prices[i] - dp[i - 1][1]);
            dp[i][1] = Math.min(dp[i - 1][1], prices[i]);
        }
        return dp[prices.length - 1][0];
    }


    /**
     * 160. 相交链表
     **/
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode p1 = headA;
        ListNode p2 = headB;
        boolean initA = true;
        boolean initB = true;
        while (p1 != p2) {
            if (p1.next != null) {
                p1 = p1.next;
            } else if (initA) {
                p1 = headB;
                initA = false;
            } else {
                return null;
            }
            if (p2.next != null) {
                p2 = p2.next;
            } else if (initB) {
                p2 = headA;
                initB = false;
            } else {
                return null;
            }
        }
        return p1;
    }


    /**
     * 141. 环形链表
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
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

}
