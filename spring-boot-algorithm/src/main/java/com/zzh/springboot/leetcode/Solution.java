package com.zzh.springboot.leetcode;

import java.util.*;

public class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * <p>
     * 获取队列位置为n的数字的值
     *
     * @param n int整型 队列位置
     * @return int整型
     */
    public static int getResult(int n) {
        // write code here
        if (n <= 7) {
            return n;
        }
        int[] dp = new int[n + 1];

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(7);
        PriorityQueue<Integer> priorityQueueMax = new PriorityQueue<>(7, (o1, o2) -> o2 - o1);
        for (int i = 1; i < dp.length; i++) {
            if (i <= 7) {
                dp[i] = i;
                priorityQueue.add(dp[i]);
                priorityQueueMax.add(dp[i]);
            } else {
//                dp[i] = dp[i - 1] + dp[i - 2] - dp[i - 7] - dp[i - 6];
                Integer min1 = priorityQueue.poll();
                Integer min2 = priorityQueue.poll();
                Integer max1 = priorityQueueMax.poll();
                Integer max2 = priorityQueueMax.poll();
                dp[i] = max1 + max2 - min1 - min2;
                priorityQueue.add(min1);
                priorityQueue.add(min2);
                priorityQueueMax.add(max1);
                priorityQueueMax.add(max2);
                priorityQueue.remove(dp[i - 7]);
                priorityQueueMax.remove(dp[i - 7]);
                priorityQueue.add(dp[i]);
                priorityQueueMax.add(dp[i]);

            }
        }
        return dp[n];
    }

    public static void main(String[] args) {

        int[][] graph = new int[][]{
                {1, 2},
                {1, 3},
                {2, 4},
                {3, 5},
                {3, 6}

        };
        GraphTraversal(6, 5, graph);

        ArrayList<Integer> integers = restricted_jobs(5, 5, 2, 1);

        System.out.println(integers);
    }


    public static int[] GraphTraversal(int n, int m, int[][] graph) {
        // write code here

        if (graph.length == 0 || graph[0].length == 0) {
            return new int[]{};
        }

        Map<Integer, List<Integer>> graphMap = new HashMap<>();


        for (int[] arr : graph) {
            if (!graphMap.containsKey(arr[0])) {
                graphMap.put(arr[0], new ArrayList<>());
            }
            List<Integer> nodes1 = graphMap.get(arr[0]);
            nodes1.add(arr[1]);

            if (!graphMap.containsKey(arr[1])) {
                graphMap.put(arr[1], new ArrayList<>());
            }
            List<Integer> nodes2 = graphMap.get(arr[1]);
            nodes2.add(arr[0]);
        }

        System.out.println(graphMap);

        for (int i = 1; i <= n; i++) {
            List<Integer> integers = graphMap.get(i);
            if (integers != null) {
                Collections.sort(integers);
            }
        }

        List<Integer> temp = new ArrayList<>();

        boolean[] visited = new boolean[n + 1];

        dfs(temp, graphMap, visited, 1);
        System.out.println(temp);
        int[] res = new int[temp.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = temp.get(i);
        }
        return res;

    }

    public static void dfs(List<Integer> res, Map<Integer, List<Integer>> grapMap, boolean[] visited, int index) {

        if (visited[index]) {
            return;
        }
        res.add(index);
        visited[index] = true;
        List<Integer> integers = grapMap.get(index);

        if (integers == null) {
            return;
        }
        Collections.sort(integers);
        for (int i = 0; i < integers.size(); i++) {
            if (visited[integers.get(i)]) {
                continue;
            }
            dfs(res, grapMap, visited, integers.get(i));
        }


    }


    public static ArrayList<Integer> restricted_jobs(int new_tasks, int budget, int add_cost, int return_cost) {
        // write code here
        ArrayList<Integer> res = new ArrayList<>();
        if (new_tasks == 1) {
            res.add(0);
            res.add(0);
            return res;
        }

        List<Integer> integers = processor(new_tasks, add_cost, return_cost);
        if (integers.get(0) > budget) {
            res.add(-1);
            res.add(-1);
            return res;
        }
        res.addAll(integers);
        return res;
    }

    public static List<Integer> processor(int target, int addCB, int returnCB) {
        List<Integer> res = new ArrayList<>();
        if (target == 1) {
            res.add(0);
            res.add(0);
            return res;
        }

        if (target % 2 == 0) {
            target = target / 2;

            List<Integer> processor = processor(target, addCB, returnCB);
            res.add(processor.get(0) + 1);
            res.add(processor.get(1) + 1);
            return res;
        } else {
            List<Integer> processor1 = processor(target + 1, addCB, returnCB);

            List<Integer> processor2 = processor(target - 1, addCB, returnCB);

            if (processor1.get(0) + addCB < processor2.get(0) + returnCB) {
                res.add(processor1.get(0) + addCB);
                res.add(processor1.get(1) + 1);
                return res;
            } else {
                res.add(processor2.get(0) + returnCB);
                res.add(processor2.get(1) + 1);
                return res;

            }

        }

    }




}
