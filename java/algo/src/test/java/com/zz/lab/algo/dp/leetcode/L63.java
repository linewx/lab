package com.zz.lab.algo.dp.leetcode;

import org.junit.Test;


//https://leetcode-cn.com/problems/unique-paths-ii/

public class L63 {
    @Test
    public void test() {

        int []nums = new int[]{0};
        int [][]obstacleGrid = new int[3][3];
        obstacleGrid[1][1] = 1;
        int result = uniquePathsWithObstacles(obstacleGrid);
        System.out.println(result);
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int [][]dp = new int[m+1][n+1];

        dp[1][1] = 1;
        for (int i=1; i<=m; i++) {
            for  (int j=1; j<=n; j++) {
                if (obstacleGrid[i-1][j-1] == 1) {
                    dp[i][j] = 0;
                    continue;
                }
                if (i == 1 ) {
                    dp[i][j] = dp[i][j-1];
                }else if(j == 1) {
                    dp[i][j] = dp[i-1][j];
                }else {
                    dp[i][j] = dp[i-1][j] +  dp[i][j-1];
                }
            }
        }

        return dp[m][n];
    }
}
