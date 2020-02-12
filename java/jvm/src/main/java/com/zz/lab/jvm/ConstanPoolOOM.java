package com.zz.lab.jvm;


import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -XX:PermSize=10m -XX:MaxPermSize=10m, no p
 * simulate constant pool out of memory
 * output:
 * Exception in thread "main" java.lang.StackOverflowError
 */
public class ConstanPoolOOM {


    public static void main(String...argv) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while(true) {
            list.add(String.valueOf(i++).intern());
        }

    }
}
