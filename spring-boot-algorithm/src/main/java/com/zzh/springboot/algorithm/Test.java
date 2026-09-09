package com.zzh.springboot.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/6/9 15:01
 */
public class Test {


    /**
     * 1.字符串由单词和下划线组成，单词和单词之间下划线个数不一定，要求单词倒序输出、下划线按原有顺序输出 示例：
     * 输入：“survival__or_____destruction_is___a______question”
     * 输出： “question__a_____is_destruction___or______survival”
     * 注.输入和输出的下划线顺序未发生变化仅单词做了倒序
     */
    public static void main(String[] args) {

        String res = reverse("____survival__or_____destruction_is___a______question");
        System.out.println(res);

    }

    public static String reverse(String str) {

        String[] strs = str.split("_");
        System.out.println(Arrays.toString(strs));
        StringBuilder sb = new StringBuilder();
        List<String> stringList = new ArrayList<>();
        for (int i = strs.length - 1; i >= 0; i--) {
            if (strs[i].length() != 0) {
                stringList.add(strs[i]);
            }
        }
        int index = 0;
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() == 0) {
                sb.append("_");
            } else {
                sb.append(stringList.get(index++));
                if (index != stringList.size()) {
                    sb.append("_");
                }
            }
        }

        if (str.endsWith("_")) {
            int in = str.length() - 1;
            while (in >= 0 && str.charAt(in) == '_') {
                in--;
                sb.append("_");
            }
        }

        return sb.toString();
    }


}
