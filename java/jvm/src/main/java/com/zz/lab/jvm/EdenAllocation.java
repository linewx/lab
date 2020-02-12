package com.zz.lab.jvm;


import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * simulate heap out of memory
 * output:
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class EdenAllocation {
    public static class OOMObject {
        OOMObject() {

        }
    }

    public static void main(String...argv) {
        byte[] a1, a2, a3, a4, a5, a6;
        int mb = 1000 * 1000;
        a1 = new byte[2 * mb];
        a2 = new byte[2 * mb];
        a3 = new byte[2 * mb];
        a4 = new byte[4 * mb];
        //a5 = new byte[4 * mb];
        //a6 = new byte[4 * mb];

        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        System.out.println(a4);
        System.gc();
        //System.out.println(a5);
        //System.out.println(a6);
    }
}
