package com.zz.playground.algo.leetcode;

import java.util.HashMap;
import java.util.Map;

/***
 * Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
 *
 * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2.
 *
 * Note:
 *
 * Your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use the same element twice.
 * Example:
 *
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.
 */
public class TwoSum2 {
    public int[] twoSum(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            int currentValue = nums[i] + nums[j];
            if (currentValue == target) {
                return new int[]{i,j};
            }else if (currentValue < target) {
                i++;
            }else {
                j--;
            }
        }

        return new int[]{};
    }

    public static void main(String ...args) {
        TwoSum2 solution = new TwoSum2();
        int []numbers = {2, 7, 11, 15};
        int []results = solution.twoSum(numbers, 9);
        for (int i : results) {
            System.out.println(i);
        }
    }
}
