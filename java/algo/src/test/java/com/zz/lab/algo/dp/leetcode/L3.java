package com.zz.lab.algo.dp.leetcode;

import org.junit.Test;

public class L3 {
    @Test
    public void test() {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int max = 1;
        int dp[] = new int[s.length()];


        dp[0] = 1;

        for (int i=1; i<s.length(); i++) {
            String subString = s.substring(i-dp[i-1], i);
            char lastc = s.charAt(i);
            if (subString.indexOf(lastc) != -1) {
                //contains
                dp[i] = subString.length() -1 - subString.lastIndexOf(lastc) + 1;
            }else {
                dp[i] = dp[i-1] + 1;
            }
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}
