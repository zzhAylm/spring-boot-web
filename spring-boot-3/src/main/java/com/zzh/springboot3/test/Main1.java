package com.zzh.springboot3.test;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int size = Integer.parseInt(in.nextLine());
        List<List<Integer>> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String[] split = in.nextLine().split(" ");
            ArrayList<Integer> e = new ArrayList<>();
            e.add(Integer.valueOf(split[0]));
            e.add(Integer.valueOf(split[1]));
            arrayList.add(e);
        }
        Integer id = Integer.parseInt(in.nextLine());

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (List<Integer> a : arrayList) {
            if (map.containsKey(a.get(0))) {
                List<Integer> value = map.get(a.get(0));
                value.add(a.get(1));
            } else {
                List<Integer> value = new ArrayList<>();
                value.add(a.get(1));
                map.put(a.get(0), value);
            }
        }
        List<Integer> send = map.get(id);
        if (send == null) {
            send = new ArrayList<>();
        }
        List<Integer> sendA = new ArrayList<>();
        map.entrySet().forEach(entry -> {
            if (entry.getValue().contains(id)) {
                sendA.add(entry.getKey());
            }
        });

        Map<Integer, Integer> count = new HashMap<>();
        for (Integer i : send) {
            Integer integer = count.get(i);
            if (integer == null) {
                count.put(i, 1);
            } else {
                count.put(i, count.get(i));
            }
        }
        AtomicBoolean boo = new AtomicBoolean(false);
        count.entrySet().forEach(e -> {

            if (e.getValue() > 5) {
                boo.set(true);
            }
        });


        HashSet<Integer> integers = new HashSet<>(sendA);
        long n = send.stream().collect(Collectors.toSet()).stream().filter(num -> !integers.contains(num)).count();
        int m = send.size() - sendA.size();
        System.out.print((boo.get() || n > 5 || m > 10) + " " + n + " " + m);

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
