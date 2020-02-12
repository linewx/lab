package com.zz.lab.algo.dp;

import org.junit.Test;

import javax.security.sasl.SaslServer;
import java.util.Map;

public class DPTest {
    @Test
    public void testDP() {
        String a = "";
        a.indexOf('a');
        int [][]b = new int[10][10];
        System.out.println(b[2][2]);
        //System.out.println(longestPalindrome(a));
        //System.out.println(minDistance("house", "ros"));
    }

    public int minDistance(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();

        int [][]results = new int[n1+1][n2+1];
        for (int i = 0; i<=n1; i++) {
            for (int j=0; j<=n2; j++) {
                if (i == 0) {
                    results[i][j] = j;
                }else if (j == 0) {
                    results[i][j] = i;
                }
                else {
                    if (word1.charAt(i-1) == word2.charAt(j-1)) {
                        results[i][j] = results[i-1][j-1];
                    }else {
                        int min = results[i-1][j];
                        min = Math.min(min, results[i][j-1]);
                        min = Math.min(min, results[i-1][j-1]);
                        results[i][j] = min + 1;
                    }
                }
            }
        }
        return results[n1][n2];
    }

    public String longestPalindrome(String s) {
        int length = s.length();
        boolean [][]results = new boolean[length][length];
        int max = 0;
        int start = 0;
        int end = 0;
        for (int j=0; j<length; j++) {
            for (int i=0; i<=j; i++) {
                if (j - i < 2) {
                    //长度是1或者2的string
                    results[i][j] = (s.charAt(i) == s.charAt(j));
                }else {
                    results[i][j] = (s.charAt(i) == s.charAt(j)) && results[i+1][j-1];
                }

                if (results[i][j]) {
                    if ((j - i) > max) {
                        max = j - i;
                        start = i;
                        end = j;
                    }
                }
            }



        }

        return s.substring(start, end + 1);
    }


}
