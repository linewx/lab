package com.zz.lab.jvm;


import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * simulate heap out of memory
 * output:
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class HeapOOM {
    public static class OOMObject {
        OOMObject() {

        }
    }

    public static void main(String...argv) {
        List<OOMObject> list = new ArrayList<>();
        while(true) {
            list.add(new OOMObject());
        }
    }
}
