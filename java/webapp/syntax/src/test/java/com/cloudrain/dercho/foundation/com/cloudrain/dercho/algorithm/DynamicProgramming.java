package com.cloudrain.dercho.foundation.com.cloudrain.dercho.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lugan on 5/16/2016.
 */
public class DynamicProgramming {
    public static Map<Integer, Integer> results = new HashMap<>();

    static {
        results.put(0, 1);
        results.put(1, 1);
    }
    public static int fib(int x) {
        if (x == 0 || x == 1) {
            return 1;
        }else{
            return fib(x-1) + fib(x-2);
        }
    }

    public static Integer fib2(Integer x) {
        return results.computeIfAbsent(x, n->fib2(n-1)+fib2(n-2));
    }

    public static void main(String ...argv) {
        System.out.println(DynamicProgramming.fib2(50));
    }
}
