package com.zz.playground.algo;

/**
 * Created by luganlin on 31/05/2018.
 */
public class LintCode {
    public static void main(String ...argv) {
        long sum = 0;
        int n = 999;
        while (n != 0) {
            sum += n / 5;
            n /= 5;
        }
        System.out.println(sum);
    }
}
