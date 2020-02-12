package com.zz.lab.algo.dp.leetcode;

import org.junit.Test;

public class L10 {
    @Test
    public void test() {
        System.out.println(isMatch("ab", ".*"));
    }

    public boolean isMatch(String s, String p) {
        int lengths = s.length();
        int lengthp = p.length();
        boolean dp[][] = new boolean[lengths+1][lengthp+1];

        for (int n1=0; n1<=lengths; n1++) {
            for (int n2=0; n2<=lengthp; n2++) {
                dp[n1][n2] = false;
            }
        }
        dp[0][0] = true;

        if (p.charAt(lengthp-1) == '*') {
            for (int k=0; k<=lengths; k++) {
                dp[k][1] = true;
            }
        }

        for (int i=1; i<=lengths; i++) {
            for (int j=1; j<=lengthp; j++) {
                if (p.charAt(lengthp - j) == '*') {
                    dp[i][j] = dp[i-1][j];
                }else if (p.charAt(j-1) == '.') {
                    dp[i][j] = dp[i-1][j-1];
                }else if(p.charAt(lengthp-j) == s.charAt(lengths-i))  {
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[lengths][lengthp];
    }
}
