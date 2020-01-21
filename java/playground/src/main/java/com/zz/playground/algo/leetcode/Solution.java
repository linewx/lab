package com.zz.playground.algo.leetcode;

/* -----------------------------------
 *  WARNING:
 * -----------------------------------
 *  Your code may fail to compile
 *  because it contains public class
 *  declarations.
 *  To fix this, please remove the
 *  "public" keyword from your class
 *  declarations.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int divide(int dividend, int divisor) {
        Boolean positive = true;
        if (dividend < 0 && divisor > 0) {
            positive = false;
        }

        else if (dividend > 0 && divisor < 0) {
            positive = false;
        }

        int t1 = Math.abs(dividend);
        int t2 = Math.abs(divisor);

        int result = 0;
        int temp = t1 - t2;
        while (temp >= 0) {
            result ++;
            temp = temp - t2;

        }
        if (!positive) {

            return 0 - result;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String a = "discuss.leetcode.com";
        String []result = a.split("\\.");
        System.out.println(result.toString());
//
//        Solution solution = new Solution();
//        System.out.println(Integer.MIN_VALUE - 1);
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(solution.divide(-2147483648, -1));
    }

}
