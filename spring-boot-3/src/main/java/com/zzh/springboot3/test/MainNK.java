package com.zzh.springboot3.test;

import java.util.*;

public class MainNK {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
//        Map<String, Integer> values = new HashMap<>();
//        Queue<String> queue = new LinkedList<>();
//        while (in.hasNextLine()) { // 注意 while 处理多个 case
//            String str = in.nextLine();
//            String[] strings = str.split("\\s+");
//            String[] split = strings[0].split("\\\\");
//            String fileName = split[split.length - 1];
//            if (fileName.length() > 16) {
//                fileName = fileName.substring(fileName.length() - 16);
//            }
//            int n = Integer.parseInt(strings[1]);
//            String key = fileName + "\\" + n;
//            if (values.containsKey(key)) {
//                values.put(key, values.get(key) + 1);
//            } else {
//                values.put(key, 1);
//                queue.add(key);
//                while (queue.size() > 8) {
//                    queue.poll();
//                }
//            }
//        }
//        while (!queue.isEmpty()) {
//            String key = queue.poll();
//            String[] split = key.split("\\\\");
//            System.out.println(split[0] + " " + split[1] + " " + values.get(key));
//        }
        String s = in.nextLine();
        System.out.println(sortStr(s));
    }


//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        long num = in.nextLong();
//        long max = (long) Math.sqrt(num);
//        for (int i = 2; i <= max; i++) {
//            while (num % i == 0) {
//                System.out.print(i + " ");
//                num = num / i;
//            }
//        }
//        System.out.print(num == 1 ? "" : num);
//
//    }


    public static String sort(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        Set<Character> set = new HashSet<>();
        char[] chars = str.toCharArray();
        StringBuilder res = new StringBuilder();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (set.contains(chars[i])) {
                continue;
            }
            res.append(chars[i]);
            set.add(chars[i]);
        }
        return res.toString();

    }


    public static Integer workLength(String str) {
        if (str == null || str.trim().length() == 0) {
            return 0;
        }
        String[] split = str.trim().split(" ");
        return split[split.length - 1].trim().length();
    }

    public static Integer worldCount(String str, String str2) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char c = str2.trim().toLowerCase().charAt(0);
        char[] chars = str.toLowerCase().toCharArray();
        int[] cs = new int[26];
        int[] ns = new int[10];
        for (char aChar : chars) {
            if (aChar >= 'a' && aChar <= 'z') {
                cs[aChar - 'a']++;
            } else if (aChar >= '0' && aChar <= '9') {
                ns[aChar - '0']++;
            }
        }
        if (c >= 'a' && c <= 'z') {
            return cs[c - 'a'];
        }
        return ns[c - '0'];
    }

    public static String[] countArray8(String str) {
        if (str == null || str.length() == 0) {
            return new String[]{};
        }
        List<String> result = new ArrayList<>();
        char[] chars = str.toCharArray();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (temp.length() == 8) {
                result.add(temp.toString());
                temp.delete(0, temp.length());
            }
            temp.append(chars[i]);
        }
        if (temp.length() > 0) {
            while (temp.length() < 8) {
                temp.append("0");
            }
            result.add(temp.toString());
        }

        String[] strArr = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            strArr[i] = result.get(i);
        }
        return strArr;
    }

    public static Integer jz2Ten(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int bit = 1;
        int res = 0;
        char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i > 1; i--) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                res += (chars[i] - '0') * bit;
            } else if (chars[i] >= 'A' && chars[i] <= 'F') {
                res += ((chars[i] - 'A') + 10) * bit;
            }
            bit *= 16;
        }
        return res;
    }


    public static int charCount(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        Set<Character> set = new HashSet<>();
        int sum = 0;
        for (char c : chars) {
            if (!set.contains(c) && c <= 127) {
                sum++;
                set.add(c);
            }
        }
        return sum;
    }

    public int arr(int[] arr) {
        int[] leftDp = new int[arr.length];
        leftDp[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    leftDp[i] = Math.max(leftDp[i], leftDp[j] + 1);
                }
            }
        }
        int[] rightDp = new int[arr.length];
        rightDp[rightDp.length - 1] = 1;
        for (int i = rightDp.length - 2; i >= 0; i--) {
            for (int j = rightDp.length - 1; j > i; j--) {
                if (arr[i] > arr[j]) {
                    rightDp[i] = Math.max(rightDp[i], rightDp[j] + 1);
                }
            }
        }

        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (leftDp[i] + rightDp[i] - 1 > max) {
                max = leftDp[i] + rightDp[i] - 1;
            }
        }
        return max;
    }


    public static String sortStr(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        char[] chars = str.toCharArray();
        List<Character> characters = new ArrayList<>();
        Map<Integer, Character> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= 'a' && chars[i] <= 'z') || (chars[i] >= 'A' && chars[i] <= 'Z')) {
                characters.add(chars[i]);
            } else {
                map.put(i, chars[i]);
            }
        }

        characters.sort(Comparator.comparingInt(Character::toLowerCase));
        StringBuilder res = new StringBuilder();
        for (int i = 0; res.length() < str.length(); ) {
            if (map.containsKey(res.length())) {
                res.append(map.get(res.length()));
            } else {
                res.append(characters.get(i));
                i++;
            }
        }
        return res.toString();
    }

    public static void brotherWorld(List<String> strings, String str, int index) {
        if (strings == null || strings.size() == 0) {
            System.out.println(0);
            return;
        }
        List<String> brothers = new ArrayList<>();
        int[] arr = new int[26];
        for (int i = 0; i < str.length(); i++) {
            arr[str.charAt(i) - 'a']++;
        }
        for (String s : strings) {
            if (s.length() != str.length() || s.equals(str)) {
                continue;
            }
            int[] arr1 = new int[26];
            for (int i = 0; i < s.length(); i++) {
                arr1[s.charAt(i) - 'a']++;
            }
            int sum = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != arr1[i]) {
                    sum++;
                }
            }
            if (sum == 0) {
                brothers.add(s);
            }
        }
        brothers.sort(String::compareTo);
        System.out.println(brothers.size());
        if (index <= brothers.size()) {
            System.out.println(brothers.get(index));
        }

    }






}
