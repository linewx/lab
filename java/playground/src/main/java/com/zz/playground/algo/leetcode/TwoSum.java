package com.zz.playground.algo.leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> targets = new HashMap<>();
        for(int i=0; i<nums.length; i++) {
            if (targets.containsKey(nums[i])) {
                return new int[]{targets.get(nums[i]), i};
            }else {
                targets.put(target - nums[i], i);
            }

        }

        return new int[]{};
    }

    public static void main(String ...args) {
        TwoSum solution = new TwoSum();
        int []numbers = {2, 7, 11, 15};
        int []results = solution.twoSum(numbers, 9);
        for (int i : results) {
            System.out.println(i);
        }
    }
}
