package com.zz.playground.algo.leetcode;

import java.util.*;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);
        HashMap<Integer, Integer> targets = new HashMap<>();
        for (int i =0; i< nums.length-2; i++) {
            if (i !=0 && nums[i] == nums[i-1]) {
                continue;
            }
            int j=i+1;
            int k=nums.length -1;
            while(j < k) {
                int theSum = nums[j] + nums[k] + nums[i];
                if (theSum > 0 ) {
                    k--;
                }else if(theSum < 0) {
                    j++;
                }else {
                    results.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    k--;
                    j++;
                }
            }
        }
        return results;
    }

    public static void main(String ...args) {
        ThreeSum solution = new ThreeSum();
        int []numbers = {-1,0,1,2,-1,-4};
        List<List<Integer>>results = solution.threeSum(numbers);
        System.out.println(results);
    }
}
