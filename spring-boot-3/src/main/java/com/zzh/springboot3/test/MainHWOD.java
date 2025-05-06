package com.zzh.springboot3.test;

import java.util.*;
import java.util.stream.Collectors;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class MainHWOD {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int size = Integer.parseInt(in.nextLine());
        List<Set<Integer>> res = new ArrayList<>();
        if (size > 10) {
            System.out.println("[[]]");
            return;
        }
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add(in.nextLine());
        }
        List<Set<Integer>> numsList = new ArrayList<>();
        for (String str : arrayList) {
            Set<Integer> nums = Arrays.stream(str.split(",")).map(Integer::parseInt).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            numsList.add(nums);
        }

        for (Set<Integer> integers : numsList) {
            if (integers.size() < 2) {
                res.add(integers);
                continue;
            }
            if (res.size() == 0) {
                res.add(integers);
                continue;
            }
            int count = res.size();
            boolean temp = false;
            for (int j = 0; j < count; j++) {
                if (merge(integers, res.get(j))) {
                    Set<Integer> set = res.get(j);
                    set.addAll(integers);
                    temp = true;
                    mer(res, j);
                }
            }
            if (!temp) {
                res.add(integers);
            }
        }

        System.out.print("[");
        for (int i = 0; i < res.size(); i++) {
            Set<Integer> set = res.get(i);
            if (i > 0) {
                System.out.print(",");
            }
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            set.stream().sorted().forEach(n -> {
                builder.append(n).append(",");
            });
            builder.deleteCharAt(builder.length() - 1);
            builder.append("]");
            System.out.print(builder);
        }
        System.out.print("]");
    }

    public static boolean merge(Set<Integer> left, Set<Integer> right) {
        if (right.size() < 2) {
            return false;
        }
        Set<Integer> merge = new HashSet<>();
        merge.addAll(left);
        merge.addAll(right);
        return merge.size() <= (left.size() + right.size()) - 2;
    }

    public static void mer(List<Set<Integer>> res, int j) {
        for (int i = 0; i < res.size(); i++) {
            int delete = -1;
            int mergeIndex = -1;
            if (i != j && merge(res.get(j), res.get(i))) {
                if (i > j) {
                    Set<Integer> set1 = res.get(j);
                    set1.addAll(res.get(i));
                    delete = i;
                    mergeIndex = j;
                } else {
                    Set<Integer> set1 = res.get(i);
                    set1.addAll(res.get(j));
                    delete = j;
                    mergeIndex = i;
                }
            }
            if (delete != -1) {
                res.remove(delete);
                mer(res, mergeIndex);
            }
        }
    }

    public static int add(int sum, char prefix, String temp) {
        if (temp.equals("")) {
            return sum;
        }
        if (prefix == '+') {
            sum += Integer.parseInt(temp);
        } else {
            sum -= Integer.parseInt(temp);
        }
        return sum;
    }
}
