package com.zz.lab.algo.dp.leetcode;

import org.junit.Test;


//https://leetcode-cn.com/problems/target-sum/submissions/

public class L494 {
    @Test
    public void test() {

        int []nums = new int[]{0};

        int result = findTargetSumWays(nums, 0);
        System.out.println(result);
    }
    private int [][]cache = new int[1001][20];
    public int findTargetSumWays(int[] nums, int S) {
        //init cache with -1
        for (int i=0; i<1001; i++) {
            for (int j=0; j<20; j++) {
                cache[i][j] = -1;
            }
        }
        return find(nums, S, nums.length -1);
    }

    public int find(int[]nums, int target, int index) {
        if (target > 1000) {
            return 0;
        }
        target = Math.abs(target);
        if (cache[target][index] != -1) {
            return cache[target][index];
        }
        if (index == 0) {
            if (Math.abs(target) == nums[index]) {
                cache[target][index] = 1;
                return 1;
            }else {
                cache[target][index] = 0;
                return 0;
            }
        }else {

            int result =  find(nums, target - nums[index], index-1) + find(nums, target + nums[index], index-1);
            cache[target][index] = result;
            return result;
        }
    }
}
