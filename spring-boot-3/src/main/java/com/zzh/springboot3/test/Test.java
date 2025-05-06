package com.zzh.springboot3.test;

import java.util.Objects;
import java.util.Stack;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/4/17 22:55
 */
public class Test {

    public static void main(String[] args) {
        add("2[a2[c]]");
    }

    public static void add(String str) {
        char[] chars = str.toCharArray();

        Stack<String> stack = new Stack<>();
        String res = "";
        int index = 0;
        while (index < chars.length) {
            if (chars[index] >= 'a' || chars[index] <= 'z') {
                res += chars[index];
            } else if (chars[index] >= '0' || chars[index] <= '9') {
                stack.push(str.substring(index, index + 1));
                index++;
                String temp = "";
                while (!stack.isEmpty()) {
                    String pop = stack.pop();
                    if (Objects.equals(pop, "[")) {
                        int count = Integer.parseInt(stack.pop());
                        String sub = "";
                        for (int i = 0; i < count; i++) {
                            sub = sub + temp;
                        }
                        if (!stack.isEmpty()){
                            stack.push(sub);
                        }
                    }

                    temp = pop + temp;
                }
            }
            index++;
        }
        System.out.println(res);

    }
}
