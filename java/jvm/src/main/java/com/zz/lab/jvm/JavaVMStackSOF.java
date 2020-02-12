package com.zz.lab.jvm;


import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -Xss160k
 * simulate stack overflow
 * output:
 * Exception in thread "main" java.lang.StackOverflowError
 */
public class JavaVMStackSOF {
    private int stackDepth = 1;

    public void stackLeak() {
        stackDepth++;
        stackLeak();
    }


    public static void main(String... argv) {
        JavaVMStackSOF sof = new JavaVMStackSOF();

        try {
            sof.stackLeak();
        } catch (Exception e) {
            System.out.println(sof.stackDepth);
            throw e;
    }

    }
}
