package com.zzh.springboot.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
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


    /***
     * 46. 全排列
     * */
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        backTrack(nums, new LinkedList<>());
        return permuteList;
    }

    List<List<Integer>> permuteList = new ArrayList<>();

    public void backTrack(int[] nums, LinkedList<Integer> temp) {
        if (temp.size() == nums.length) {
            permuteList.add(new ArrayList<>(temp));
            return;
        }
        for (int num : nums) {
            if (!temp.contains(num)) {
                temp.add(num);
                backTrack(nums, temp);
                temp.removeLast();
            }
        }
    }


    /**
     * 300. 最长递增子序列
     **/
    public int lengthOfLIS(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 1) {
            return nums.length;
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }


    /**
     * 82. 删除排序链表中的重复元素 II
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(Integer.MIN_VALUE);
        pre.next = head;
        ListNode p = pre;
        ListNode first = head;
        while (first != null) {
            ListNode next = first.next;
            if (next != null && first.val == next.val) {
                while (next != null && next.val == first.val) {
                    next = next.next;
                }
                p.next = next;
            } else {
                p = p.next;
            }
            first = p.next;
        }

        return pre.next;
    }


    /**
     * 88. 合并两个有序数组
     **/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2 == null || nums2.length == 0) {
            return;
        }
        int[] temp = new int[nums1.length];
        int index1 = 0;
        int index2 = 0;
        int index = 0;
        while (index1 < m && index2 < n) {
            if (nums1[index1] <= nums2[index2]) {
                temp[index++] = nums1[index1++];
            } else {
                temp[index++] = nums2[index2++];
            }
        }
        while (index1 < m) {
            temp[index++] = nums1[index1++];
        }
        while (index2 < n) {
            temp[index++] = nums2[index2++];
        }
        System.arraycopy(temp, 0, nums1, 0, temp.length);
    }


    /**
     * 124. 二叉树中的最大路径和
     **/
    public int maxPathSum(TreeNode root) {
        pathSum(root);

        return maxPathSum;
    }

    Integer maxPathSum = Integer.MIN_VALUE;

    public int pathSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftSum = 0;
        int rightSum = 0;
        if (node.left != null) {
            leftSum = Math.max(pathSum(node.left), leftSum);
        }
        if (node.right != null) {
            rightSum = Math.max(rightSum, pathSum(node.right));
        }
        maxPathSum = Math.max(maxPathSum, node.val + leftSum + rightSum);
        return leftSum > rightSum ? node.val + leftSum : node.val + rightSum;
    }


    /**
     * 199.二叉树的右视图
     **/
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new ConcurrentLinkedQueue<>();
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
     * 56. 合并区间
     **/
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparing(ints -> ints[0]));
        List<String> list = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int l = intervals[i][0];
            int r = intervals[i][1];
            if (l > right) {
                list.add(left + "," + right);
                left = l;
                right = r;
            } else {
                right = Math.max(right, r);
            }
        }
        list.add(left + "," + right);
        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String[] split = s.split(",");
            res[i][0] = Integer.parseInt(split[0]);
            res[i][1] = Integer.parseInt(split[1]);
        }
        return res;
    }


    /**
     * 31. 下一个排列
     **/
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int left = nums.length - 2;
        while (left >= 0) {
            if (nums[left] < nums[left + 1]) {
                for (int i = nums.length - 1; i >= 0; i--) {
                    if (nums[i] > nums[left]) {
                        int temp = nums[left];
                        nums[left] = nums[i];
                        nums[i] = temp;
                        Arrays.sort(nums, left + 1, nums.length);
                        return;
                    }
                }
            }
            left--;
        }
        Arrays.sort(nums, left + 1, nums.length);
    }

    @Test
    public void nextPermutationTest() {
        int[] nums = {1, 3, 2};
        nextPermutation(nums);
    }


    /**
     * 415. 字符串相加
     */
    public String addStrings(String num1, String num2) {
        if (num1 == null || num1.length() == 0) {
            return num2;
        }
        if (num2 == null || num2.length() == 0) {
            return num1;
        }
        StringBuilder builder = new StringBuilder();
        char[] chars1 = num1.toCharArray();
        char[] chars2 = num2.toCharArray();
        int index1 = chars1.length - 1;
        int index2 = chars2.length - 1;
        int pre = 0;
        while (index2 >= 0 && index1 >= 0) {
            char c1 = chars1[index1--];
            char c2 = chars2[index2--];
            int sum = getIntByChar(c1) + getIntByChar(c2) + pre;
            pre = sum / 10;
            builder.append(sum % 10);
        }
        while (index1 >= 0) {
            char c1 = chars1[index1--];
            int sum = getIntByChar(c1) + pre;
            pre = sum / 10;
            builder.append(sum % 10);
        }
        while (index2 >= 0) {
            char c2 = chars2[index2--];
            int sum = getIntByChar(c2) + pre;
            pre = sum / 10;
            builder.append(sum % 10);
        }
        if (pre != 0) {
            builder.append(pre);
        }
        return builder.reverse().toString();
    }

    public int getIntByChar(char c) {
        return switch (c) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            default -> 0;
        };
    }


    /***
     * 142. 环形链表 II
     * **/
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                break;
            }
        }
        if (slow == null || fast == null) {
            return null;
        }
        ListNode first = head;
        ListNode second = slow;

        while (first != null && second != null) {
            if (first == second) {
                return first;
            }
            first = first.next;
            second = second.next;
        }

        return null;
    }


    /**
     * 148. 排序链表
     **/
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        return mergeSort(head, null);
    }

    // 归并排序
    public ListNode mergeSort(ListNode start, ListNode end) {
        if (start == null) {
            return null;
        }
        if (start.next == end) {
            start.next = null;
            return start;
        }
        ListNode slow = start;
        ListNode fast = start;
        while (fast != end) {
            slow = slow.next;
            fast = fast.next;
            if (fast != end) {
                fast = fast.next;
            }
        }
        ListNode left = mergeSort(start, slow);
        ListNode right = mergeSort(slow, end);
        return merge(left, right);
    }

    public ListNode merge(ListNode left, ListNode right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        ListNode pre = new ListNode(-1);
        ListNode temp = pre;
        while (left != null && right != null) {
            if (left.val < right.val) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;
            }
            temp = temp.next;
        }
        while (left != null) {
            temp.next = left;
            left = left.next;
            temp = temp.next;
        }
        while (right != null) {
            temp.next = right;
            right = right.next;
            temp = temp.next;
        }
        return pre.next;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     **/
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode pre = new ListNode();
        pre.next = head;
        ListNode fast = pre;
        ListNode slow = pre;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return pre.next;
    }

    /**
     * 20. 有效的括号
     **/
    public boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty() && match(stack.peek(), c)) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public boolean match(Character left, Character right) {
        return (left == '(' && right == ')') || (left == '{' && right == '}') || (left == '[' && right == ']');
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
        ListNode pre = new ListNode();
        ListNode p = pre;
        int val = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + val;
            p.next = new ListNode(sum % 10);
            val = sum / 10;
            p = p.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int sum = l1.val + val;
            p.next = new ListNode(sum % 10);
            val = sum / 10;
            p = p.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            int sum = l2.val + val;
            p.next = new ListNode(sum % 10);
            val = sum / 10;
            p = p.next;
            l2 = l2.next;
        }
        if (val != 0) {
            p.next = new ListNode(val);
        }
        return pre.next;
    }


    /**
     * 41. 缺失的第一个正数
     */
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        Set<Integer> set = new HashSet<>();
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= 0) {
                continue;
            }
            if (num < min) {
                min = num;
            }
            set.add(num);
        }
        if (min != 1) return 1;
        while (set.contains(min)) {
            min++;
        }
        return min;
    }


}
