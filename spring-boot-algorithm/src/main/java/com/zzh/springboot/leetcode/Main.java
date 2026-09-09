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
                return new int[]{left+1, right+1};
            }
        }

        return new int[]{-1, -1};

    }



    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length == 0){
            return res;
        }
        dfs(nums,new ArrayList<>(),new boolean[nums.length], res);
        return res;
    }

    public void dfs(int[] nums,List<Integer> path,boolean[] used,List<List<Integer>> res){
        if (path.size()==nums.length){
            res.add(new ArrayList<>(path));
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]){
                continue;
            }
            used[i]=true;
            path.add(nums[i]);
            dfs(nums,path,used,res);
            used[i]=false;
            path.remove(path.size()-1);
        }

    }

    public ListNode reverseList(ListNode head) {

        if(head==null||head.next==null){
            return head;
        }
        ListNode pre=new ListNode(-1);
        ListNode cur=head;
        ListNode next=cur.next;

        while (next!=null){

            ListNode node = pre.next;
            pre.next=cur;
            cur.next=node;

            cur=next;
            next=cur.next;
        }
        ListNode node = pre.next;
        pre.next=cur;
        cur.next=node;

        return pre.next;
    }



    public int[][] merge1(int[][] intervals) {
        if(intervals.length<=1){
            return intervals;
        }
        Arrays.sort(intervals,(arr1,arr2)->Integer.compare(arr1[0], arr2[0]));
        List<int[]> res=new ArrayList<>();
        int[] cur=new int[]{intervals[0][0],intervals[0][1]};

        for (int i=1;i<intervals.length;i++){
            int[] interval = intervals[i];
            if (cur[1]<interval[0]){
                res.add(new int[]{cur[0],cur[1]});
                cur[0]=interval[0];
                cur[1]=interval[1];
            }else {
                cur[0]=Math.min(cur[0],interval[0]);
                cur[1]=Math.max(cur[1],interval[1]);
            }
        }
        res.add(new int[]{cur[0],cur[1]});
        return res.toArray(new int[res.size()][]);
    }


    public List<List<Integer>> levelOrder(TreeNode root) {

        if (root==null){
            return new ArrayList<>();
        }
        List<List<Integer>> res=new ArrayList<>();

        Queue<TreeNode> queue=new ArrayDeque<>();

        queue.add(root);

        while (!queue.isEmpty()){

            int size = queue.size();
            List<Integer> level=new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll!=null){
                    level.add(poll.val);
                    if (poll.left!=null){
                        queue.add(poll.left) ;
                    }
                    if (poll.right!=null){
                        queue.add(poll.right) ;
                    }
                }

            }
            res.add(level);
        }



        return res;

    }



    public int climbStairs(int n) {
        if (n<=2){
            return n;
        }
        int[] dp=new int[n+1];
        dp[1]=1;
        dp[2]=2;
        for (int i =3; i <dp.length ; i++) {
            dp[i]=dp[i-1]+dp[i-2];
        }

        return dp[n];

    }




}
