package com.zzh.springboot.algorithm;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/5/30 14:07
 */
public class Algorithm03 {

    public static void main(String[] args) {
        int[][] arr = {{4, 5, 6}, {6, 4, 7}, {4, 3, 5}, {2, 3, 5}};
        System.out.println(carPooling(arr, 13));
    }

    /**
     * 有序数组去重复
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int left = 0;
        int right = 1;
        while (left < nums.length && right < nums.length) {
            if (nums[left] == nums[right]) {
                while (++right < nums.length) {
                    if (nums[right] != nums[left] && nums[right] > nums[left]) {
                        nums[++left] = nums[right];
                        break;
                    }
                }
            } else {
                left++;
                right++;
            }
        }
        return left + 1;
    }


    /**
     * 拼车
     */
    public static boolean carPooling(int[][] trips, int capacity) {
        if (trips.length == 1) {
            return capacity >= trips[0][0];
        }
        int macLen = getMacLen(trips);
        for (int i = 0; i <= macLen; i++) {
            int num = 0;
            for (int[] trip : trips) {
                if (trip[1] == i) {
                    num -= trip[0];
                }
                if (trip[2] == i) {
                    num += trip[0];
                }
            }
            capacity += num;
            if (capacity < 0) {
                return false;
            }
        }
        return true;
    }


    public static int getMacLen(int[][] trips) {
        int max = -1;
        for (int[] trip : trips) {
            max = Math.max(max, trip[2]);
        }
        return max;
    }





}
